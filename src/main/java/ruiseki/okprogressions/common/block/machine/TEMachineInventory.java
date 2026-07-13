package ruiseki.okprogressions.common.block.machine;

import java.util.stream.IntStream;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

import lombok.experimental.Delegate;
import ruiseki.okcore.capabilities.resolver.BasicCapabilityResolver;
import ruiseki.okcore.energy.capability.CapabilityEnergy;
import ruiseki.okcore.energy.handler.EnergyStorage;
import ruiseki.okcore.helper.ItemStackHelpers;
import ruiseki.okcore.item.capability.CapabilityItemHandler;
import ruiseki.okcore.item.component.IInventoryExclusion;
import ruiseki.okcore.item.component.SidedInventoryComponent;
import ruiseki.okcore.item.handler.ItemStackHandler;
import ruiseki.okcore.item.handler.RestrictedItemStackHandler;
import ruiseki.okcore.persist.nbt.NBTPersist;

public class TEMachineInventory extends TEMachine implements ISidedInventory {

    protected static final int SPEED_FUELED = 8;
    private static final int MAX_SPEED = 10;
    public static final int MAX_ENERGY = 64 * 1000;

    @NBTPersist
    protected RestrictedItemStackHandler inventory;

    @NBTPersist
    protected EnergyStorage energyStorage = null;
    private int energyCost = 0;

    @Delegate(excludes = IInventoryExclusion.class)
    protected final SidedInventoryComponent inventoryComponent;

    public TEMachineInventory(int size) {
        this.inventory = new RestrictedItemStackHandler(size);
        this.inventoryComponent = new SidedInventoryComponent(this) {

            @Override
            public int[] getAccessibleSlotsFromSide(int side) {
                return inventory.getSlotArray();
            }
        };
        this.capabilityCache.addCapabilityResolver(
            BasicCapabilityResolver.create(CapabilityItemHandler.ITEM_HANDLER, () -> this.inventory));
        this.capabilityCache
            .addCapabilityResolver(BasicCapabilityResolver.create(CapabilityEnergy.ENERGY, () -> this.energyStorage));
        this.capabilityCache.addSemiDisabledCapability(CapabilityEnergy.ENERGY, () -> energyStorage == null);
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    protected void initEnergy(EnergyStorage store) {
        initEnergy(store, 0);
    }

    protected void initEnergy(EnergyStorage store, int energyCost) {
        this.energyStorage = store;
        this.energyCost = energyCost;
    }

    public int getEnergyMax() {
        if (energyStorage == null) {
            return 0;
        }
        return this.energyStorage.getMaxEnergyStored();
    }

    public int getEnergyCurrent() {
        if (this.energyStorage == null) {
            return 0;
        }
        return this.energyStorage.getEnergyStored();
    }

    public void setEnergyCurrent(int f) {
        this.energyStorage.setEnergyStored(f);
    }

    public int getEnergyCost() {
        return this.energyCost;
    }

    public void consumeEnergy() {
        // only drain on server, if we have enough and if not free
        if (!worldObj.isRemote && this.getEnergyCost() > 0 && this.getEnergyCurrent() >= this.getEnergyCost()) {
            this.energyStorage.extractEnergy(this.getEnergyCost(), false);
            // it drained, notify client
            this.markDirty();
        }
    }

    public int[] getFieldArray(int length) {
        return IntStream.rangeClosed(0, length - 1)
            .toArray();
    }

    public boolean isDoingWork() {
        return super.isRunning() && this.hasEnoughEnergy();
    }

    public boolean updateEnergyIsBurning() {
        if (this.getEnergyCost() > 0) {
            if (this.hasEnoughEnergy()) {
                this.consumeEnergy();
            } else {
                // dont run, dont count down, just stop now
                return false;
            }
        }
        return true;
    }

    public boolean hasEnoughEnergy() {
        if (this.getEnergyCost() == 0) {
            return true;
        }
        return this.getEnergyCurrent() >= this.getEnergyCost();
    }

    @Override
    public int getSpeed() {
        if (this.getEnergyCost() == 0) {
            return this.speed;// does not use fuel. use NBT saved speed value
        } else {
            if (this.getEnergyCurrent() == 0) {
                return 0; // do not run without fuel
            } else {
                return Math.max(this.speed, 1);// i have fuel, use what it says eh
            }
        }
    }

    public void setSpeed(int value) {
        if (value < 0) {
            value = 0;
        }
        speed = Math.min(value, MAX_SPEED);
    }

    protected void shiftAllUp() {
        shiftAllUp(0);
    }

    /**
     * pass in how many slots on the end ( right ) to skip
     *
     * @param endOffset
     */
    protected void shiftAllUp(int endOffset) {
        if (!this.worldObj.isRemote) {
            for (int i = 0; i < this.getSizeInventory() - endOffset - 1; i++) {
                shiftPairUp(i, i + 1);
            }
        }
    }

    protected void shiftPairUp(int low, int high) {
        ItemStack main = getStackInSlot(low);
        ItemStack second = getStackInSlot(high);

        if (main == null && second != null) {
            this.setInventorySlotContents(low, second);
            this.setInventorySlotContents(high, null);
        } else if (main != null && second != null) {
            if (ItemStackHelpers.areStacksEqual(main, second)) {
                int maxStack = main.getMaxStackSize();

                int roomLeft = maxStack - main.stackSize;

                if (roomLeft > 0) {
                    int amountToMove = Math.min(roomLeft, second.stackSize);
                    main.stackSize += amountToMove;
                    this.setInventorySlotContents(low, main);
                    second.stackSize -= amountToMove;

                    if (second.stackSize <= 0) {
                        this.setInventorySlotContents(high, null);
                    } else {
                        this.setInventorySlotContents(high, second);
                    }
                }
            }
        }
    }
}
