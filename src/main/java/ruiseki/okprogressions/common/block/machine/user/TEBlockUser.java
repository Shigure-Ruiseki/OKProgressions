package ruiseki.okprogressions.common.block.machine.user;

import java.lang.ref.WeakReference;
import java.util.UUID;

import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;

import org.apache.logging.log4j.Level;

import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;

import ruiseki.okcore.datastructure.BlockPos;
import ruiseki.okcore.persist.nbt.NBTPersist;
import ruiseki.okprogressions.OKProgressions;
import ruiseki.okprogressions.common.block.machine.TEMachine;
import ruiseki.okprogressions.common.block.machine.TEMachineInventory;

public class TEBlockUser extends TEMachineInventory {

    @NBTPersist
    private UUID uuid = UUID.randomUUID();
    @NBTPersist
    private int timerDelay = 20;

    public final int MAX_DELAY = 64;

    @NBTPersist
    private boolean useLeftHand = false;
    @NBTPersist
    private boolean interactWithEntity = false;
    @NBTPersist
    private BlockPos targetPos = null;

    private WeakReference<FakePlayer> fakePlayer;

    public TEBlockUser() {
        super(1);
        this.inventory.setSlotsForBoth();
    }

    @Override
    protected void doUpdate() {
        super.doUpdate();
        if (this.worldObj == null || this.worldObj.isRemote) {
            return;
        }

        if (!isRunning()) {
            markDirty();
            return;
        }

        boolean trigger = updateTimerIsZero();
        if (trigger) {
            try {
                this.timer = timerDelay;

                if (this.worldObj instanceof WorldServer worldServer) {
                    if (this.fakePlayer == null || this.fakePlayer.get() == null) {
                        this.fakePlayer = setupBeforeTrigger(worldServer, "user", this.uuid);
                    }
                }

                if (this.fakePlayer == null || this.fakePlayer.get() == null) {
                    return;
                }

                TEMachine.tryEquipItem(this.inventory.getStackInSlot(0), this.fakePlayer);

                BlockPos start = getPos().offset(this.direction);
                if (this.targetPos == null || !this.targetPos.equals(start)) {
                    this.targetPos = start;
                }

                if (this.useLeftHand) {
                    if (interactWithEntity) {
                        TEMachine.leftClickEntity(this.fakePlayer, this.worldObj, this.targetPos);
                    } else {
                        TEMachine.leftClickBlock(this.fakePlayer, this.worldObj, this.targetPos, this.direction);
                    }
                } else {
                    if (interactWithEntity) {
                        TEMachine.rightClickEntity(this.fakePlayer, this.worldObj, this.targetPos);
                    } else {
                        TEMachine.rightClickBlock(this.fakePlayer, this.worldObj, this.targetPos, this.direction);
                    }
                }

                TEMachine.syncEquippedItem(this.inventory, this.fakePlayer, 0);
                this.markDirty();
            } catch (Exception e) {
                OKProgressions.okLog(Level.ERROR, "User action item error", e);
            }
        }
    }

    public boolean isUsingLeftHand() {
        return this.useLeftHand;
    }

    public void setUseLeftHand(final boolean useLeftHand) {
        this.useLeftHand = useLeftHand;
        this.markDirty();
    }

    public boolean isInteractWithEntity() {
        return this.interactWithEntity;
    }

    public void setInteractWithEntity(boolean interactWithEntity) {
        this.interactWithEntity = interactWithEntity;
        this.markDirty();
    }

    public int getTimerDelay() {
        return timerDelay;
    }

    public void setTimerDelay(int timerDelay) {
        this.timerDelay = timerDelay;
        this.markDirty();
    }

    @Override
    public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
        return new BlockUserPanel(this, data, syncManager, settings);
    }
}
