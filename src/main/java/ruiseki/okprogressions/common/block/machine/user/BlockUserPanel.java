package ruiseki.okprogressions.common.block.machine.user;

import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.drawable.GuiTextures;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.BooleanSyncValue;
import com.cleanroommc.modularui.value.sync.IntSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.SliderWidget;
import com.cleanroommc.modularui.widgets.ToggleButton;
import com.cleanroommc.modularui.widgets.layout.Flow;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;

import ruiseki.okcore.client.OKCGuiTextures;
import ruiseki.okcore.helper.LangHelpers;
import ruiseki.okprogressions.common.block.machine.MachinePanel;

public class BlockUserPanel extends MachinePanel<TEBlockUser> {

    protected IntSyncValue sliderSyncer;
    protected BooleanSyncValue useLeftHandSyncer;
    protected BooleanSyncValue interactWithEntitySyncer;

    public BlockUserPanel(TEBlockUser machine, PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
        super(machine, data, syncManager, settings);
        this.height(176);
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
            .childPadding(2)
            .coverChildren()
            .pos(30, 7);

        this.child(col);
    }

    @Override
    public Flow createSettingColumn() {
        this.useLeftHandSyncer = new BooleanSyncValue(this.machine::isUsingLeftHand, this.machine::setUseLeftHand)
            .allowC2S();
        this.syncManager.syncValue("useLeftHandSyncer", useLeftHandSyncer);

        this.interactWithEntitySyncer = new BooleanSyncValue(
            this.machine::isInteractWithEntity,
            this.machine::setInteractWithEntity).allowC2S();
        this.syncManager.syncValue("interactWithEntitySyncer", interactWithEntitySyncer);

        return super.createSettingColumn().child(new ItemSlot().slot(new ModularSlot(this.machine.getInventory(), 0)))
            .child(
                new ToggleButton().value(useLeftHandSyncer)
                    .tooltip(richTooltip -> richTooltip.add(LangHelpers.localize("gui.user.hand")))
                    .overlay(true, GuiTextures.CHECK_BOX_FULL)
                    .overlay(false, GuiTextures.CHECK_BOX_EMPTY)
                    .size(18, 18))
            .child(
                new ToggleButton().value(interactWithEntitySyncer)
                    .tooltip(true, richTooltip -> richTooltip.add(LangHelpers.localize("gui.user.type.entity")))
                    .tooltip(false, richTooltip -> richTooltip.add(LangHelpers.localize("gui.user.type.block")))
                    .overlay(true, GuiTextures.CHECK_BOX_FULL)
                    .overlay(false, GuiTextures.CHECK_BOX_EMPTY)
                    .size(18, 18));
    }
}
