package ruiseki.okprogressions.common.block.compressed;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import ruiseki.okcore.block.BlockBase;

public class BlockBone extends BlockBase {

    public BlockBone() {
        super(Material.ground);
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
