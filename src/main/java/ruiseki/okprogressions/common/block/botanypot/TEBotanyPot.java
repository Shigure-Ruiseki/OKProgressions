package ruiseki.okprogressions.common.block.botanypot;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.jetbrains.annotations.Nullable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lombok.experimental.Delegate;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import ruiseki.okcore.addon.waila.IWailaTileInfoProvider;
import ruiseki.okcore.helper.InventoryHelpers;
import ruiseki.okcore.helper.LangHelpers;
import ruiseki.okcore.helper.WailaHelpers;
import ruiseki.okcore.item.ItemStackHandler;
import ruiseki.okcore.persist.nbt.NBTPersist;
import ruiseki.okcore.tileentity.TileEntityOK;
import ruiseki.okprogressions.common.crop.CropMaterial;
import ruiseki.okprogressions.common.crop.CropRegistry;
import ruiseki.okprogressions.common.helper.BotanyPotHelpers;
import ruiseki.okprogressions.common.soil.SoilMaterial;
import ruiseki.okprogressions.common.soil.SoilRegistry;

public class TEBotanyPot extends TileEntityOK
    implements TileEntityOK.ITickingTile, ISidedInventory, IWailaTileInfoProvider {

    @Delegate
    protected final TileEntityOK.ITickingTile tickingTileComponent = new TileEntityOK.TickingTileComponent(this);

    @NBTPersist("inventory")
    private final ItemStackHandler inv = new ItemStackHandler(2) {

        @Override
        protected int getStackLimit(int slot, @Nullable ItemStack stack) {
            return 1;
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            if (stack == null) return false;

            if (slot == 0) {
                return SoilRegistry.getByStack(stack) != null;
            } else if (slot == 1 && getStackInSlot(0) != null) {
                return CropRegistry.getByStack(stack) != null;
            }
            return false;
        }
    };

    @Nullable
    private SoilMaterial soil;

    @Nullable
    private CropMaterial crop;

    @NBTPersist
    private int totalGrowthTicks = -1;

    @NBTPersist
    private int currentGrowthTicks = 0;

    @NBTPersist
    private int autoHarvestCooldown = 0;

    public TEBotanyPot() {

    }

    public boolean canSetSoil(@Nullable SoilMaterial newSoil) {
        return newSoil == null || this.getSoil() == null;
    }

    public void setSoil(@Nullable SoilMaterial newSoil, ItemStack stack) {
        this.soil = newSoil;
        this.inv.setStackInSlot(0, stack);
        this.resetGrowthTime();
        this.markDirty();
        onSendUpdate();
    }

    public boolean canSetCrop(@Nullable CropMaterial newCrop) {
        return newCrop == null || this.getSoil() != null && this.getCrop() == null;
    }

    public void setCrop(@Nullable CropMaterial newCrop, ItemStack stack) {
        this.crop = newCrop;
        this.inv.setStackInSlot(1, stack);
        this.resetGrowthTime();
        this.markDirty();
        onSendUpdate();
    }

    public @Nullable SoilMaterial getSoil() {
        return soil;
    }

    public @Nullable CropMaterial getCrop() {
        return crop;
    }

    public int getTotalGrowthTicks() {
        return totalGrowthTicks;
    }

    public int getCurrentGrowthTicks() {
        return currentGrowthTicks;
    }

    public boolean canHarvest() {
        return this.crop != null && this.totalGrowthTicks > 0 && this.currentGrowthTicks >= this.totalGrowthTicks;
    }

    public void resetGrowthTime() {
        this.totalGrowthTicks = BotanyPotHelpers.getRequiredGrowthTicks(this.getCrop(), this.getSoil());
        this.currentGrowthTicks = 0;

        if (this.soil != null) {
            this.soil = SoilRegistry.getByStack(this.soil.getStack());
            if (this.soil == null) {
                this.crop = null;
            }
        }

        if (this.crop != null) {
            this.crop = CropRegistry.getByStack(this.crop.getStack());
        }

        this.autoHarvestCooldown = 5;

        if (this.worldObj != null) {
            this.worldObj.func_147453_f(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
            if (!this.worldObj.isRemote) {
                this.markDirty();
                onSendUpdate();
            }
        }
    }

    public void addGrowth(int ticksToGrow) {
        this.currentGrowthTicks += ticksToGrow;
        if (this.currentGrowthTicks > this.totalGrowthTicks) {
            this.currentGrowthTicks = this.totalGrowthTicks;
        }

        if (!this.worldObj.isRemote) {
            markDirty();
            onSendUpdate();
        }
    }

    public float getGrowthPercent() {
        if (this.totalGrowthTicks == -1 || this.currentGrowthTicks == -1) {
            return 0f;
        }

        return (float) this.currentGrowthTicks / this.totalGrowthTicks;
    }

    @Override
    protected void doUpdate() {
        if (this.worldObj.isRemote) return;

        if (this.hasSoilAndCrop()) {
            if (this.isDoneGrowing()) {
                this.worldObj.func_147453_f(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
                this.attemptAutoHarvest();
            } else {
                this.currentGrowthTicks++;
                this.markDirty();
                onSendUpdate();
            }
        } else if (this.totalGrowthTicks != -1 || this.currentGrowthTicks != 0) {
            this.resetGrowthTime();
        }
    }

    public boolean hasSoilAndCrop() {
        return this.soil != null && this.crop != null;
    }

    public boolean isDoneGrowing() {
        return this.hasSoilAndCrop() && this.totalGrowthTicks > 0 && this.currentGrowthTicks >= this.totalGrowthTicks;
    }

    private void attemptAutoHarvest() {
        Block block = getBlockType();
        if (block instanceof BlockBotanyPot && ((BlockBotanyPot) block).isHopper()) {
            if (this.autoHarvestCooldown > 0) {
                this.autoHarvestCooldown--;
                return;
            }

            IInventory inventory = InventoryHelpers.getInventoryAtSide(worldObj, getPos(), ForgeDirection.DOWN);
            if (inventory != null) {
                boolean didAutoHarvest = false;
                final List<ItemStack> drops = getDrops();
                for (ItemStack stack : drops) {
                    if (stack == null) continue;
                    ItemStack remaining = InventoryHelpers.addToInventory(inventory, stack, ForgeDirection.UP, false);
                    if (remaining == null || remaining.stackSize < stack.stackSize) {
                        didAutoHarvest = true;
                    }
                }

                if (didAutoHarvest || drops.isEmpty()) {
                    this.resetGrowthTime();
                } else {
                    this.autoHarvestCooldown = 20;
                }
            }
        }
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[] { 0, 1 };
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        return isItemValidForSlot(slot, stack) && getStackInSlot(slot) == null;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return false;
    }

    @Override
    public int getSizeInventory() {
        return 2;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return inv.getStackInSlot(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return inv.extractItem(index, count, false);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int index) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        inv.setStackInSlot(index, stack);

        if (index == 0) {
            this.soil = SoilRegistry.getByStack(stack);
        } else if (index == 1) {
            this.crop = CropRegistry.getByStack(stack);
        }

        this.resetGrowthTime();

        if (!this.worldObj.isRemote) {
            this.markDirty();
            onSendUpdate();
        }
    }

    @Override
    public String getInventoryName() {
        return "container.botanypot";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (stack == null) return false;

        if (index == 0) {
            return SoilRegistry.getByStack(stack) != null;
        } else if (index == 1 && getSoilStack() != null) {
            return CropRegistry.getByStack(stack) != null;
        }
        return false;
    }

    public boolean isGrowing() {
        return hasSoilAndCrop() && !isDoneGrowing();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 4096.0D;
    }

    private List<ItemStack> getDrops() {
        return BotanyPotHelpers.generateDrop(worldObj.rand, this.crop);
    }

    public ItemStack getSoilStack() {
        return this.inv.getStackInSlot(0);
    }

    public ItemStack getCropStack() {
        return this.inv.getStackInSlot(1);
    }

    @Override
    public void readCommon(NBTTagCompound tag) {
        super.readCommon(tag);

        ItemStack soilStack = this.inv.getStackInSlot(0);
        if (soilStack != null) {
            this.soil = SoilRegistry.getByStack(soilStack);
        } else {
            this.soil = null;
        }

        ItemStack cropStack = this.inv.getStackInSlot(1);
        if (cropStack != null) {
            this.crop = CropRegistry.getByStack(cropStack);
        } else {
            this.crop = null;
        }
    }

    @Override
    public void getWailaBody(List<String> tooltip, ItemStack itemStack, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        float percent = getGrowthPercent() * 100;

        if (percent >= 100.0f) {
            tooltip.add(LangHelpers.localize("tooltip.pot.ready"));
        } else {
            tooltip.add(LangHelpers.localize("tooltip.pot.progress", percent));
        }

        tooltip.add(WailaHelpers.getInventoryTooltip(this));
    }

    @Override
    public void getWailaNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag, World world, int x, int y,
        int z) {}
}
