package ruiseki.okprogressions.common.block.cobblegen;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import org.jetbrains.annotations.Nullable;

import lombok.experimental.Delegate;
import ruiseki.okcore.helper.ItemStackHelpers;
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

        // Push to inventory above
        TileEntity tile = getPos().offset(ForgeDirection.UP)
            .getTileEntity(worldObj);
        if (tile instanceof IInventory inv && stack.stackSize > 0) {

            ItemStack remaining = putStackInInventoryAllSlots(
                inv,
                new ItemStack(stack.getItem(), 1, stack.getItemDamage()),
                ForgeDirection.UP);

            if (remaining == null) {
                stack.stackSize--;
                inv.markDirty();
            }
        }

        markDirty();
    }

    public static ItemStack putStackInInventoryAllSlots(IInventory inventoryIn, ItemStack stack,
        @Nullable ForgeDirection side) {
        if (inventoryIn instanceof ISidedInventory isidedinventory && side != null
            && !(inventoryIn instanceof TECobblegen)) {
            int[] aint = isidedinventory.getAccessibleSlotsFromSide(side.ordinal());

            for (int k = 0; k < aint.length && stack != null && stack.stackSize > 0; ++k)
                stack = insertStack(inventoryIn, stack, aint[k], side);
        } else {
            int i = inventoryIn.getSizeInventory();

            for (int j = 0; j < i && stack != null && stack.stackSize > 0; ++j)
                stack = insertStack(inventoryIn, stack, j, side);
        }

        if (stack != null && stack.stackSize == 0) stack = null;

        return stack;
    }

    private static ItemStack insertStack(IInventory inv, ItemStack stack, int index, ForgeDirection side) {
        if (stack == null || stack.stackSize <= 0) return stack;

        if (!canInsertItemInSlot(inv, stack, index, side)) return stack;

        ItemStack existing = inv.getStackInSlot(index);

        int limit = Math.min(stack.getMaxStackSize(), inv.getInventoryStackLimit());

        if (existing == null) {
            int toMove = Math.min(stack.stackSize, limit);

            ItemStack newStack = stack.splitStack(toMove);
            inv.setInventorySlotContents(index, newStack);
            inv.markDirty();

            return stack.stackSize <= 0 ? null : stack;
        }

        if (!ItemStackHelpers.areStackMergable(existing, stack)) return stack;

        int max = Math.min(existing.getMaxStackSize(), limit);
        if (existing.stackSize >= max) return stack;

        int space = max - existing.stackSize;
        int toMove = Math.min(stack.stackSize, space);

        existing.stackSize += toMove;
        stack.stackSize -= toMove;

        inv.markDirty();

        return stack.stackSize <= 0 ? null : stack;
    }

    private static boolean canInsertItemInSlot(IInventory inventoryIn, ItemStack stack, int index,
        ForgeDirection side) {
        return inventoryIn.isItemValidForSlot(index, stack) && (!(inventoryIn instanceof ISidedInventory)
            || ((ISidedInventory) inventoryIn).canInsertItem(index, stack, side.ordinal()));
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
