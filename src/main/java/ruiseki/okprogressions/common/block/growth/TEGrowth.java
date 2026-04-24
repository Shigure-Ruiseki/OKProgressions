package ruiseki.okprogressions.common.block.growth;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;

import lombok.experimental.Delegate;
import ruiseki.okcore.persist.nbt.NBTPersist;
import ruiseki.okcore.tileentity.TileEntityOK;

public class TEGrowth extends TileEntityOK implements TileEntityOK.ITickingTile {

    @Delegate
    protected final TileEntityOK.ITickingTile tickingTileComponent = new TileEntityOK.TickingTileComponent(this);

    @NBTPersist
    private int range = 0;
    @NBTPersist
    private int rangeY = 0;
    @NBTPersist
    private int growthLvl = 0;

    private int scanIndex = 0;

    public TEGrowth() {}

    public TEGrowth setStats(int level, int rangeX, int rangeY) {
        this.range = rangeX;
        this.rangeY = rangeY;
        this.growthLvl = level;
        markDirty();
        return this;
    }

    @Override
    protected void doUpdate() {
        super.doUpdate();
        if (worldObj.isRemote) return;
        if (growthLvl <= 1) return;

        hydrateFarmlandSlice();
    }

    private void hydrateFarmlandSlice() {
        int xO = xCoord;
        int yO = yCoord;
        int zO = zCoord;

        int sizeX = range * 2 + 1;
        int sizeY = rangeY * 2 + 1;
        int sizeZ = range * 2 + 1;

        int volume = sizeX * sizeY * sizeZ;

        int perTick = 50 + growthLvl * 20;
        for (int i = 0; i < perTick; i++) {

            int index = (scanIndex + i) % volume;

            int xD = index % sizeX - range;
            int yD = (index / sizeX) % sizeY - rangeY;
            int zD = (index / (sizeX * sizeY)) - range;

            int x = xO + xD;
            int y = yO + yD;
            int z = zO + zD;

            Block block = worldObj.getBlock(x, y, z);

            if (block instanceof BlockFarmland) {
                if (worldObj.getBlockMetadata(x, y, z) < 7) {
                    worldObj.setBlockMetadataWithNotify(x, y, z, 7, 2);
                }
            }
        }

        scanIndex = (scanIndex + perTick) % volume;
    }
}
