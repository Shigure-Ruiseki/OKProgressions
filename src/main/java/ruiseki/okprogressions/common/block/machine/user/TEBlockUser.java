package ruiseki.okprogressions.common.block.machine.user;

import java.lang.ref.WeakReference;
import java.util.UUID;

import net.minecraftforge.common.util.FakePlayer;

import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;

import ruiseki.okcore.persist.nbt.NBTPersist;
import ruiseki.okprogressions.common.block.machine.TEMachineInventory;

public class TEBlockUser extends TEMachineInventory {

    @NBTPersist
    private UUID uuid;

    private static final int buildSpeed = 1;

    public static final int TIMER_FULL = 1;

    private WeakReference<FakePlayer> fakePlayer;

    public TEBlockUser() {
        super(1);
        this.inventory.setSlotsForBoth();
    }

    @Override
    protected void doUpdate() {
        super.doUpdate();
        shiftAllUp();
        boolean trigger = false;
        if (!isRunning()) {
            markDirty();
            return;
        }
    }

    @Override
    public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
        return new BlockUserPanel(this, data, syncManager, settings);
    }
}
