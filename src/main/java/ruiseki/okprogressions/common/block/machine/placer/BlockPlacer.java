package ruiseki.okprogressions.common.block.machine.placer;

import net.minecraft.block.material.Material;

import ruiseki.okprogressions.OKPCreativeTab;
import ruiseki.okprogressions.common.block.machine.BlockMachine;

public class BlockPlacer extends BlockMachine {

    public BlockPlacer() {
        super("block_placer", TEBlockPlacer.class, Material.iron);
        this.setHardness(3.0F);
        this.setResistance(5.0F);
        this.setStepSound(soundTypeMetal);
        this.setCreativeTab(OKPCreativeTab.INSTANCE);
        this.isDirection = true;
    }
}
