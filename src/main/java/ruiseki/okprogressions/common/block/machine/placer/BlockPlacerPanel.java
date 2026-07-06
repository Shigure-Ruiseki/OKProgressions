package ruiseki.okprogressions.common.block.machine.placer;

import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.ItemSlotSH;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.SlotGroupWidget;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;

import ruiseki.okprogressions.common.block.machine.MachinePanel;

public class BlockPlacerPanel extends MachinePanel<TEBlockPlacer> {

    public BlockPlacerPanel(TEBlockPlacer machine, PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
        super(machine, data, syncManager, settings);

        ItemSlotSH[] slotSyncers = new ItemSlotSH[9];
        for (int i = 0; i < slotSyncers.length; i++) {
            ModularSlot slot = new ModularSlot(this.machine.getInv(), i);
            slotSyncers[i] = new ItemSlotSH(slot);
            syncManager.syncValue("slot_syncer_" + i, i, slotSyncers[i]);
        }
        this.child(
            SlotGroupWidget.builder()
                .row("SSSSSSSSS")
                .key('S', i -> new ItemSlot().syncHandler("slot_syncer_" + i, i))
                .build()
                .topRel(0.25f)
                .leftRel(0.5f));
    }

}
