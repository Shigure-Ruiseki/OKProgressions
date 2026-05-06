package ruiseki.okprogressions.common.block.cobblegen;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import lombok.experimental.Delegate;
import ruiseki.okcore.helper.InventoryHelpers;
import ruiseki.okcore.item.ItemStackHandler;
import ruiseki.okcore.persist.nbt.NBTPersist;
import ruiseki.okcore.tileentity.TileEntityOK;

public class TECobblegen extends TileEntityOK implements TileEntityOK.ITickingTile, ISidedInventory {

    @Delegate
    protected final TileEntityOK.ITickingTile tickingTileComponent = new TileEntityOK.TickingTileComponent(this);

    @NBTPersist("outputInventory")
    public final ItemStackHandler outputInventory;

    @NBTPersist
    private int cycle = 0;
    @NBTPersist
    private int cycleUpdate = 40;
    @NBTPersist
    private int maxStackSize = 32;

    public TECobblegen() {
        this.outputInventory = new ItemStackHandler(1);
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

        // Generate cobble
        if (stack == null) {
            stack = new ItemStack(Blocks.cobblestone, 1);
            outputInventory.setStackInSlot(0, stack);
        } else if (stack.getItem() == Item.getItemFromBlock(Blocks.cobblestone)) {
            if (stack.stackSize < getMaxStackSize()) {
                stack.stackSize++;
            }
        } else {
            // Replace wrong item
            stack = new ItemStack(Blocks.cobblestone, 1);
            outputInventory.setStackInSlot(0, stack);
        }

        IInventory inventory = InventoryHelpers.getInventoryAtSide(worldObj, getPos(), ForgeDirection.DOWN);
        if (inventory != null && stack.stackSize > 0) {
            ItemStack remaining = InventoryHelpers.addToInventory(
                inventory,
                new ItemStack(stack.getItem(), 1, stack.getItemDamage()),
                ForgeDirection.UP,
                false);
            if (remaining == null) {
                stack.stackSize--;
                inventory.markDirty();
            }
        }

        markDirty();
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int slot) {
        return new int[] { 0 };
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        return false;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return true;
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int slotIn) {
        return outputInventory.getStackInSlot(slotIn);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return outputInventory.extractItem(index, count, false);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int index) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        outputInventory.setStackInSlot(index, stack);
    }

    @Override
    public String getInventoryName() {
        return "";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return maxStackSize;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return false;
    }
}
