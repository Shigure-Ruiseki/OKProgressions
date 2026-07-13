package ruiseki.okprogressions.common.block.cobblegen;

import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import lombok.experimental.Delegate;
import ruiseki.okcore.capabilities.resolver.BasicCapabilityResolver;
import ruiseki.okcore.helper.TileHelpers;
import ruiseki.okcore.item.ItemHelpers;
import ruiseki.okcore.item.ItemTransfer;
import ruiseki.okcore.item.capability.CapabilityItemHandler;
import ruiseki.okcore.item.component.IInventoryExclusion;
import ruiseki.okcore.item.component.SidedInventoryComponent;
import ruiseki.okcore.item.handler.ItemStackHandler;
import ruiseki.okcore.persist.nbt.NBTPersist;
import ruiseki.okcore.tileentity.TileEntityOK;

public class TECobblegen extends TileEntityOK implements TileEntityOK.ITickingTile, ISidedInventory {

    @Delegate
    protected final TileEntityOK.ITickingTile tickingTileComponent = new TileEntityOK.TickingTileComponent(this);

    @NBTPersist("outputInventory")
    public final ItemStackHandler outputInventory = new ItemStackHandler(1);

    @NBTPersist
    private int cycle = 0;
    @NBTPersist
    private int cycleUpdate = 40;
    @NBTPersist
    private int maxStackSize = 32;

    @Delegate(excludes = IInventoryExclusion.class)
    protected final SidedInventoryComponent inventoryComponent = new SidedInventoryComponent(this) {

        @Override
        public int[] getAccessibleSlotsFromSide(int side) {
            return outputInventory.getSlotArray();
        }

        @Override
        public int getInventoryStackLimit() {
            return maxStackSize;
        }

        @Override
        public boolean isItemValidForSlot(int index, ItemStack stack) {
            return false;
        }
    };

    public TECobblegen() {
        this.capabilityCache.addCapabilityResolver(
            BasicCapabilityResolver.create(CapabilityItemHandler.ITEM_HANDLER, () -> outputInventory));
    }

    public int getCycleUpdate() {
        return cycleUpdate;
    }

    public void setCycleUpdate(int cycleUpdate) {
        this.cycleUpdate = cycleUpdate;
        markDirty();
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public void setMaxStackSize(int maxStackSize) {
        this.maxStackSize = maxStackSize;
        markDirty();
    }

    public ItemStack getStack() {
        return outputInventory.getStackInSlot(0);
    }

    @Override
    protected void doUpdate() {
        if (worldObj.isRemote) return;

        if (++cycle < getCycleUpdate()) return;
        cycle = 0;

        ItemStack stack = outputInventory.getStackInSlot(0);
        boolean changed = false;

        // Generate cobble
        if (stack == null) {
            stack = new ItemStack(Blocks.cobblestone, 1);
            outputInventory.setStackInSlot(0, stack);
            changed = true;
        } else if (stack.getItem() == Item.getItemFromBlock(Blocks.cobblestone)) {
            if (stack.stackSize < getMaxStackSize()) {
                stack.stackSize++;
                changed = true;
            }
        } else {
            // Replace wrong item
            stack = new ItemStack(Blocks.cobblestone, 1);
            outputInventory.setStackInSlot(0, stack);
            changed = true;
        }

        TileEntity tile = TileHelpers.getSafeTile(
            worldObj,
            this.getPos()
                .offset(ForgeDirection.UP),
            TileEntity.class);
        if (tile != null && stack.stackSize > 0) {
            ItemTransfer transfer = new ItemTransfer();
            transfer.source(ItemHelpers.getItemHandler(this, ForgeDirection.UP));
            transfer.sink(ItemHelpers.getItemHandler(tile, ForgeDirection.DOWN));
            int moved = transfer.transfer();
            if (moved > 0) {
                changed = true;
            }
        }
        if (changed) markDirty();
    }
}
