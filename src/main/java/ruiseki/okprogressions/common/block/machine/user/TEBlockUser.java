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
    @NBTPersist
    private boolean useLeftHand = false;
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
        if (!isRunning()) {
            markDirty();
            return;
        }

        boolean trigger = updateTimerIsZero();
        if (trigger) {
            try {
                if (fakePlayer == null) {
                    fakePlayer = setupBeforeTrigger((WorldServer) worldObj, "user", uuid);
                }

                TEMachine.tryEquipItem(inventory.getStackInSlot(0), fakePlayer);

                BlockPos start = getPos().offset(this.direction);
                if (targetPos == null || targetPos != start) targetPos = start;
                if (useLeftHand) {
                    TEMachine.leftClickBlock(fakePlayer, worldObj, targetPos, this.direction);
                } else {
                    TEMachine.rightClickBlock(fakePlayer, worldObj, targetPos, this.direction);
                }
                TEMachine.syncEquippedItem(inventory, fakePlayer, 0);
                this.markDirty();
            } catch (Exception e) {
                OKProgressions.okLog(Level.ERROR, "User action item error", e);
            }
        }
    }

    public boolean isUsingLeftHand() {
        return useLeftHand;
    }

    public void setUseLeftHand(final boolean useLeftHand) {
        this.useLeftHand = useLeftHand;
    }

    @Override
    public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
        return new BlockUserPanel(this, data, syncManager, settings);
    }
}
