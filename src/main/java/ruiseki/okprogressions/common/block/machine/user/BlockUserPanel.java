package ruiseki.okprogressions.common.block.machine.user;

import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.IntSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.SliderWidget;
import com.cleanroommc.modularui.widgets.layout.Flow;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;

import ruiseki.okcore.client.OKCGuiTextures;
import ruiseki.okprogressions.common.block.machine.MachinePanel;

public class BlockUserPanel extends MachinePanel<TEBlockUser> {

    protected IntSyncValue sliderSyncer;

    public BlockUserPanel(TEBlockUser machine, PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
        super(machine, data, syncManager, settings);

        createSlider();
    }

    private void createSlider() {
        this.sliderSyncer = new IntSyncValue(this.machine::getTimerDelay, this.machine::setTimerDelay).allowC2S();
        this.syncManager.syncValue("sliderSyncer", sliderSyncer);

        Flow col = Flow.column();
        col.child(
            IKey.dynamic(() -> sliderSyncer.getStringValue())
                .asWidget()
                .height(18))
            .child(
                new SliderWidget().background(OKCGuiTextures.SLIDER_BACKGROUND)
                    .bounds(1, machine.MAX_DELAY)
                    .value(sliderSyncer)
                    .sliderSize(4, 16)
                    .size(135, 18))
            .childPadding(5)
            .coverChildren()
            .pos(30, 7);

        this.child(col);
    }

    @Override
    public Flow createSettingColumn() {
        return super.createSettingColumn().child(new ItemSlot().slot(new ModularSlot(this.machine.getInventory(), 0)));
    }
}
