package ruiseki.okprogressions.common.block.machine.user;

import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;

import ruiseki.okprogressions.common.block.machine.MachinePanel;

public class BlockUserPanel extends MachinePanel<TEBlockUser> {

    public BlockUserPanel(TEBlockUser machine, PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
        super(machine, data, syncManager, settings);

        this.child(new ItemSlot().slot(new ModularSlot(this.machine.getInventory(), 0)));
    }

}
