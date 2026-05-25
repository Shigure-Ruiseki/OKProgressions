package ruiseki.okprogressions.common.block.machine;

import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.ModularScreen;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;

import lombok.experimental.Delegate;
import ruiseki.okcore.block.IBlockDirection;
import ruiseki.okcore.persist.nbt.NBTPersist;
import ruiseki.okcore.tileentity.TileEntityOK;
import ruiseki.okprogressions.Reference;

public class TEMachine extends TileEntityOK
    implements IBlockDirection, TileEntityOK.ITickingTile, IGuiHolder<PosGuiData> {

    @NBTPersist
    protected ForgeDirection direction = ForgeDirection.NORTH;

    @Delegate
    protected final ITickingTile tickingTileComponent = new TickingTileComponent(this);

    public TEMachine() {}

    @Override
    public ForgeDirection getDirection(IBlockAccess world, int x, int y, int z) {
        return direction;
    }

    @Override
    public void setDirection(IBlockAccess world, int x, int y, int z, ForgeDirection direction) {
        this.direction = direction;
        this.markDirty();
        this.onSendUpdate();
    }

    public boolean isRunning() {
        if (!this.isValid()) {
            return false;
        }
        return this.isPowered() || !this.onlyRunIfPowered();
    }

    public boolean isValid() {
        return worldObj != null && !this.isInvalid()
            && this.getPos()
                .isLoaded(worldObj);
    }

    public boolean isPowered() {
        return this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
    }

    public boolean onlyRunIfPowered() {
        return false;
    }

    @Override
    public ModularScreen createScreen(PosGuiData data, ModularPanel mainPanel) {
        return new ModularScreen(Reference.MOD_ID, mainPanel);
    }

    @Override
    public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
        return new ModularPanel("gui");
    }
}
