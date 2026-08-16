package ruiseki.okprogressions.common.block.compressed;

import net.minecraft.block.material.Material;
import net.minecraft.world.Explosion;

import ruiseki.okcore.config.configurable.ConfigurableBlock;
import ruiseki.okcore.config.extendedconfig.BlockConfig;
import ruiseki.okcore.config.extendedconfig.ExtendedConfig;

public class BlockCharcoal extends ConfigurableBlock {

    private static BlockCharcoal _instance = null;

    /**
     * Get the unique instance.
     *
     * @return The instance.
     */
    public static BlockCharcoal getInstance() {
        return _instance;
    }

    public BlockCharcoal(ExtendedConfig<BlockConfig> eConfig) {
        super(eConfig, Material.rock);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setHarvestLevel("pickaxe", 1);
        this.setStepSound(soundTypeStone);
    }

    @Override
    public boolean canDropFromExplosion(Explosion explosionIn) {
        return false;
    }
}
