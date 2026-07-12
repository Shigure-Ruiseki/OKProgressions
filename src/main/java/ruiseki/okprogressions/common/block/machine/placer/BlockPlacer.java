package ruiseki.okprogressions.common.block.machine.placer;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import ruiseki.okprogressions.OKPCreativeTab;
import ruiseki.okprogressions.common.block.machine.BlockMachine;

public class BlockPlacer extends BlockMachine {

    public BlockPlacer() {
        super(Material.iron);
        this.setHardness(3.0F);
        this.setResistance(5.0F);
        this.setStepSound(soundTypeMetal);
        this.setCreativeTab(OKPCreativeTab.INSTANCE);
        this.isDirection = true;
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TEBlockPlacer();
    }
}
