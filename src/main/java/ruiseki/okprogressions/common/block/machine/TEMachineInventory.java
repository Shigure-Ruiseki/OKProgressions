package ruiseki.okprogressions.common.block.machine;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.NotNull;

import cofh.api.energy.IEnergyStorage;
import lombok.experimental.Delegate;
import ruiseki.okcore.capabilities.Capability;
import ruiseki.okcore.datastructure.LazyOptional;
import ruiseki.okcore.energy.EnergyStorage;
import ruiseki.okcore.energy.capability.CapabilityEnergy;
import ruiseki.okcore.helper.ItemStackHelpers;
import ruiseki.okcore.inventory.IInventoryExclusion;
import ruiseki.okcore.inventory.SidedInventoryComponent;
import ruiseki.okcore.item.InvWrapperRestricted;
import ruiseki.okcore.item.ItemStackHandler;
import ruiseki.okcore.persist.nbt.NBTPersist;

public class TEMachineInventory extends TEMachine implements ISidedInventory {

    protected static final int speedFueled = 8;
    private static final int maxSpeed = 10;
    public static final int maxEnergy = 64 * 1000;

    @NBTPersist
    protected ItemStackHandler inv;
    private int energyCost = 0;
    @NBTPersist
    protected int speed = 1;
    @NBTPersist
    protected int timer;
    InvWrapperRestricted invHandler;

    @NBTPersist
    protected EnergyStorage energyStorage = null;
    protected LazyOptional<IEnergyStorage> energyCap = LazyOptional.of(() -> energyStorage);

    @Delegate(excludes = IInventoryExclusion.class)
    protected final SidedInventoryComponent inventoryComponent;

    public TEMachineInventory(int size) {
        inv = new ItemStackHandler(size);
        invHandler = new InvWrapperRestricted(this);
        inventoryComponent = new SidedInventoryComponent(this, inv) {

            @Override
            public int[] getAccessibleSlotsFromSide(int side) {
                return IntStream.range(0, this.getSizeInventory())
                    .toArray();
            }

            @Override
            public boolean canInsertItem(int slot, ItemStack stack, int side) {
                return this.isItemValidForSlot(slot, stack) && invHandler.canInsert(slot);
            }

            @Override
            public boolean canExtractItem(int index, ItemStack stack, int side) {
                return index != getEnergyCost() && invHandler.canExtract(index);
            }
        };
    }

    protected void setSlotsForExtract(int slot) {
        this.setSlotsForExtract(List.of(slot));
    }

    protected void setSlotsForInsert(int slot) {
        this.setSlotsForInsert(List.of(slot));
    }

    protected void setSlotsForExtract(List<Integer> slots) {
        invHandler.setSlotsExtract(slots);
    }

    protected void setSlotsForExtract(int startInclusive, int endInclusive) {
        setSlotsForExtract(
            IntStream.rangeClosed(startInclusive, endInclusive)
                .boxed()
                .collect(Collectors.toList()));
    }

    protected void setSlotsForInsert(int startInclusive, int endInclusive) {
        setSlotsForInsert(
            IntStream.rangeClosed(startInclusive, endInclusive)
                .boxed()
                .collect(Collectors.toList()));
    }

    protected void setSlotsForInsert(List<Integer> slots) {
        invHandler.setSlotsInsert(slots);
    }

    protected void setSlotsForBoth(List<Integer> slots) {
        invHandler.setSlotsInsert(slots);
        invHandler.setSlotsExtract(slots);
    }

    protected void setSlotsForBoth() {
        this.setSlotsForBoth(
            IntStream.rangeClosed(0, this.getSizeInventory())
                .boxed()
                .collect(Collectors.toList()));
    }

    public ItemStackHandler getInv() {
        return inv;
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

    protected boolean updateTimerIsZero() {
        timer -= this.getSpeed();
        if (timer < 0) {
            timer = 0;
        }
        return timer == 0;
    }

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
        speed = Math.min(value, maxSpeed);
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

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == CapabilityEnergy.ENERGY && energyStorage != null) {
            return energyCap.cast();
        }
        return super.getCapability(cap);
    }
}
