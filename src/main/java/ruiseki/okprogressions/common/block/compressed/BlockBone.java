package ruiseki.okprogressions.common.block.compressed;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import ruiseki.okcore.config.configurable.ConfigurableBlock;
import ruiseki.okcore.config.extendedconfig.BlockConfig;
import ruiseki.okcore.config.extendedconfig.ExtendedConfig;

public class BlockBone extends ConfigurableBlock {

    private static BlockBone _instance = null;

    /**
     * Get the unique instance.
     *
     * @return The instance.
     */
    public static BlockBone getInstance() {
        return _instance;
    }

    public BlockBone(ExtendedConfig<BlockConfig> eConfig) {
        super(eConfig, Material.ground);
        this.setHardness(0.5F);
        this.setResistance(5.0F);
        this.setStepSound(soundTypeStone);
    }

    @Override
    public boolean canDropFromExplosion(Explosion explosionIn) {
        return false;
    }

    @Override
    public void onFallenUpon(World worldIn, int x, int y, int z, Entity entity, float fallDistance) {
        entity.fallDistance = fallDistance * 3.0F;
    }
}
