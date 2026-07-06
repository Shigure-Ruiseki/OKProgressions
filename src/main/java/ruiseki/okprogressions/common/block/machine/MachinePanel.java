package ruiseki.okprogressions.common.block.machine;

import net.minecraft.entity.player.EntityPlayer;

import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.EnumSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.layout.Flow;

import ruiseki.okcore.client.gui.component.TitleWidget;
import ruiseki.okcore.client.gui.component.button.RedstoneModeButton;
import ruiseki.okcore.enums.RedstoneMode;

public class MachinePanel<T extends TEMachine> extends ModularPanel {

    protected final PosGuiData data;
    protected final PanelSyncManager syncManager;
    protected final UISettings settings;
    protected final EntityPlayer player;
    protected final T machine;

    protected Flow settingCol;
    protected EnumSyncValue<RedstoneMode, ?> redstoneModeSyncer;

    public MachinePanel(T machine, PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
        super("gui");
        this.data = data;
        this.syncManager = syncManager;
        this.settings = settings;
        this.player = data.getPlayer();
        this.machine = machine;

        this.child(
            new TitleWidget(
                this.machine.getBlock()
                    .getLocalizedName()));

        this.settingCol = createSettingColumn();
        this.child(settingCol);
    }

    public Flow createSettingColumn() {
        this.redstoneModeSyncer = new EnumSyncValue<>(
            RedstoneMode.class,
            this.machine::getRedstoneMode,
            this.machine::setRedstoneMode).allowC2S();
        this.syncManager.syncValue("redstoneModeSyncer", redstoneModeSyncer);

        return Flow.column()
            .pos(7, 7)
            .coverChildren()
            .childPadding(2)
            .child(new RedstoneModeButton(redstoneModeSyncer));
    }
}
