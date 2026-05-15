package ruiseki.okprogressions.common.block.machine;

import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

import lombok.experimental.Delegate;
import ruiseki.okcore.block.IBlockDirection;
import ruiseki.okcore.persist.nbt.NBTPersist;
import ruiseki.okcore.tileentity.TileEntityOK;

public class TEMachine extends TileEntityOK implements IBlockDirection, TileEntityOK.ITickingTile {

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
}
