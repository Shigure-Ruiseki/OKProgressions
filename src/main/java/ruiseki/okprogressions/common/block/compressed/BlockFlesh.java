package ruiseki.okprogressions.common.block.compressed;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import ruiseki.okcore.block.IBlock;
import ruiseki.okprogressions.OKPCreativeTab;

public class BlockFlesh extends BlockFalling implements IBlock {

    public BlockFlesh() {
        super(Material.sand);
        this.setHardness(0.5F);
        this.setResistance(5.0F);
        this.setStepSound(soundTypeSand);
        this.setCreativeTab(OKPCreativeTab.INSTANCE);
    }

    @Override
    public boolean canDropFromExplosion(Explosion explosionIn) {
        return false;
    }

    @Override
    public void onFallenUpon(World worldIn, int x, int y, int z, Entity entity, float fallDistance) {
        entity.fallDistance = fallDistance * 3.0F;
    }

    @Override
    public void onEntityWalking(World worldIn, int x, int y, int z, Entity entityIn) {
        entityIn.motionX *= 0.4D;
        entityIn.motionZ *= 0.4D;
    }

    @Override
    public Block getBlock() {
        return this;
    }

    @Override
    public boolean isHasSubtypes() {
        return false;
    }

    @Override
    public String getName() {
        return "flesh_block";
    }
}
