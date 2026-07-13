package ruiseki.okprogressions.common.block.machine;

import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.ModularScreen;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;

import lombok.experimental.Delegate;
import ruiseki.okcore.block.IBlockDirection;
import ruiseki.okcore.enums.RedstoneMode;
import ruiseki.okcore.persist.nbt.NBTPersist;
import ruiseki.okcore.tileentity.IRedstoneMode;
import ruiseki.okcore.tileentity.TileEntityOK;
import ruiseki.okprogressions.Reference;

public class TEMachine extends TileEntityOK
    implements IBlockDirection, IRedstoneMode, TileEntityOK.ITickingTile, IGuiHolder<PosGuiData> {

    @NBTPersist
    protected ForgeDirection direction = ForgeDirection.NORTH;

    @Delegate
    @SuppressWarnings("all")
    protected final ITickingTile tickingTileComponent = new TickingTileComponent(this);

    @NBTPersist
    protected RedstoneMode redstoneMode = RedstoneMode.ALWAYS_ON;

    @NBTPersist
    protected int speed = 1;
    @NBTPersist
    protected int timer;

    public TEMachine() {}

    @Override
    public ForgeDirection getDirection(IBlockAccess world, int x, int y, int z) {
        return direction;
    }

    @Override
    public void setDirection(World world, int x, int y, int z, ForgeDirection direction) {
        this.direction = direction;
        this.markDirty();
        this.onSendUpdate();

    }

    public ForgeDirection getDirection() {
        return direction;
    }

    public boolean isRunning() {
        if (!this.isValid()) return false;
        return this.canRunInWorld(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
    }

    public boolean isValid() {
        return worldObj != null && !this.isInvalid()
            && this.getPos()
                .isLoaded(worldObj);
    }

    @Override
    public ModularScreen createScreen(PosGuiData data, ModularPanel mainPanel) {
        return new ModularScreen(Reference.MOD_ID, mainPanel);
    }

    @Override
    public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
        return new MachinePanel<>(this, data, syncManager, settings);
    }

    @Override
    public RedstoneMode getRedstoneMode() {
        return redstoneMode;
    }

    @Override
    public void setRedstoneMode(RedstoneMode mode) {
        this.redstoneMode = mode;
        this.markDirty();
        this.onSendUpdate();
    }

    protected boolean updateTimerIsZero() {
        timer -= this.getSpeed();
        if (timer < 0) {
            timer = 0;
        }
        return timer == 0;
    }

    public int getSpeed() {
        return speed;
    }
}
