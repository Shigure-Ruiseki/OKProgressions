package ruiseki.okprogressions.common.block.botanypot;

import java.util.List;
import java.util.stream.IntStream;

import net.minecraft.block.Block;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import org.jetbrains.annotations.Nullable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lombok.experimental.Delegate;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import ruiseki.okcore.addon.waila.IWailaTileInfoProvider;
import ruiseki.okcore.capabilities.resolver.BasicCapabilityResolver;
import ruiseki.okcore.helper.InventoryHelpers;
import ruiseki.okcore.helper.LangHelpers;
import ruiseki.okcore.helper.TileHelpers;
import ruiseki.okcore.helper.WailaHelpers;
import ruiseki.okcore.item.ItemHelpers;
import ruiseki.okcore.item.ItemTransfer;
import ruiseki.okcore.item.capability.CapabilityItemHandler;
import ruiseki.okcore.item.component.IInventoryExclusion;
import ruiseki.okcore.item.component.SidedInventoryComponent;
import ruiseki.okcore.item.handler.ItemStackHandler;
import ruiseki.okcore.persist.nbt.NBTPersist;
import ruiseki.okcore.tileentity.TileEntityOK;
import ruiseki.okprogressions.common.data.crop.CropInfo;
import ruiseki.okprogressions.common.data.soil.SoilInfo;
import ruiseki.okprogressions.common.helper.BotanyPotHelpers;

public class TEBotanyPot extends TileEntityOK
    implements TileEntityOK.ITickingTile, ISidedInventory, IWailaTileInfoProvider {

    @Delegate
    protected final TileEntityOK.ITickingTile tickingTileComponent = new TileEntityOK.TickingTileComponent(this);

    @NBTPersist("inventory")
    private final ItemStackHandler inventory = new ItemStackHandler(14) {

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            if (stack == null) return false;

            if (slot == 0) {
                return BotanyPotHelpers.getSoilFormStack(stack) != null;
            } else if (slot == 1 && getStackInSlot(0) != null) {
                return BotanyPotHelpers.getCropFormStack(stack) != null;
            }
            return super.isItemValid(slot, stack);
        }

        @Override
        public int getSlotLimit(int slot) {
            if (slot == 0 || slot == 1) {
                return 1;
            }
            return super.getSlotLimit(slot);
        }
    };

    @Delegate(excludes = IInventoryExclusion.class)
    protected final SidedInventoryComponent inventoryComponent = new SidedInventoryComponent(this) {

        @Override
        public int[] getAccessibleSlotsFromSide(int side) {
            return inventory.getSlotArray();
        }

        @Override
        public boolean canInsertItem(int slot, ItemStack stack, int side) {
            return this.isItemValidForSlot(slot, stack) && getStackInSlot(slot) == null;
        }

        @Override
        public boolean canExtractItem(int index, ItemStack stack, int side) {
            return index != 0 && index != 1;
        }

        @Override
        public void setInventorySlotContents(int index, ItemStack stack) {

            if (index == 0) {
                soil = BotanyPotHelpers.getSoilFormStack(stack);
            } else if (index == 1) {
                crop = BotanyPotHelpers.getCropFormStack(stack);
            }

            resetGrowthTime();
            super.setInventorySlotContents(index, stack);
        }
    };

    @Nullable
    private SoilInfo soil;

    @Nullable
    private CropInfo crop;

    @NBTPersist
    private int totalGrowthTicks = -1;

    @NBTPersist
    private int currentGrowthTicks = 0;

    public TEBotanyPot() {
        this.capabilityCache
            .addCapabilityResolver(BasicCapabilityResolver.create(CapabilityItemHandler.ITEM_HANDLER, () -> inventory));
    }

    public boolean canSetSoil(@Nullable SoilInfo newSoil) {
        return newSoil == null || this.getSoil() == null;
    }

    public void setSoil(@Nullable SoilInfo newSoil, ItemStack stack) {
        this.soil = newSoil;
        this.inventory.setStackInSlot(0, stack);
        this.resetGrowthTime();
        this.markDirty();
        onSendUpdate();
    }

    public boolean canSetCrop(@Nullable CropInfo newCrop) {
        return newCrop == null || this.getSoil() != null && this.getCrop() == null;
    }

    public void setCrop(@Nullable CropInfo newCrop, ItemStack stack) {
        this.crop = newCrop;
        this.inventory.setStackInSlot(1, stack);
        this.resetGrowthTime();
        this.markDirty();
        onSendUpdate();
    }

    public @Nullable SoilInfo getSoil() {
        return soil;
    }

    public @Nullable CropInfo getCrop() {
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
            this.soil = BotanyPotHelpers.getSoilFormStack(this.soil.getStack());
            if (this.soil == null) {
                this.crop = null;
            }
        }

        if (this.crop != null) {
            this.crop = BotanyPotHelpers.getCropFormStack(this.crop.getStack());
        }

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
            if (this.isDoneGrowing()) {
                List<ItemStack> drops = getDrops();
                if (!drops.isEmpty()) {
                    boolean movedToSelf = false;
                    for (ItemStack stack : drops) {
                        if (stack == null) continue;
                        ItemStack stackToInsert = InventoryHelpers.insertStack(this.inventory, stack, false);
                        if (stackToInsert == null || stackToInsert.stackSize < stack.stackSize) {
                            movedToSelf = true;
                        }
                    }
                    if (movedToSelf) this.resetGrowthTime();
                }
                attemptTransfer();
            }
        }
    }

    private void attemptTransfer() {
        boolean hasItemToTransfer = false;
        for (int slot : IntStream.range(2, this.inventory.getSlots())
            .toArray()) {
            if (this.inventory.getStackInSlot(slot) != null) {
                hasItemToTransfer = true;
                break;
            }
        }

        if (!hasItemToTransfer) return;

        TileEntity tile = TileHelpers.getSafeTile(
            worldObj,
            this.getPos()
                .offset(ForgeDirection.DOWN),
            TileEntity.class);
        if (tile != null) {
            ItemTransfer transfer = new ItemTransfer();
            transfer.source(ItemHelpers.getItemHandler(this, ForgeDirection.DOWN));
            transfer.sink(ItemHelpers.getItemHandler(tile, ForgeDirection.UP));
            transfer.setSourceSlots(
                IntStream.range(2, inventory.getSlots())
                    .toArray());
            transfer.setStacksToTransfer(inventory.getSlots());

            int moved = transfer.transfer();
            if (moved > 0) {
                this.markDirty();
                onSendUpdate();
            }
        }
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
        return this.getStackInSlot(0);
    }

    public ItemStack getCropStack() {
        return this.getStackInSlot(1);
    }

    @Override
    public void readCommon(NBTTagCompound tag) {
        super.readCommon(tag);

        ItemStack soilStack = this.getSoilStack();
        if (soilStack != null) {
            this.soil = BotanyPotHelpers.getSoilFormStack(soilStack);
        } else {
            this.soil = null;
        }

        ItemStack cropStack = this.getCropStack();
        if (cropStack != null) {
            this.crop = BotanyPotHelpers.getCropFormStack(cropStack);
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
}
