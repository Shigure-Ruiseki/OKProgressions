package ruiseki.okprogressions.common.block.machine.placer;

import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.SlotGroupWidget;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;

import ruiseki.okprogressions.common.block.machine.MachinePanel;

public class BlockPlacerPanel extends MachinePanel<TEBlockPlacer> {

    public BlockPlacerPanel(TEBlockPlacer machine, PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
        super(machine, data, syncManager, settings);
        this.child(
            SlotGroupWidget.builder()
                .row("SSSSSSSSS")
                .key('S', i -> new ItemSlot().slot(new ModularSlot(this.machine.getInventory(), i)))
                .build()
                .topRel(0.25f)
                .leftRel(0.5f));
    }

}
