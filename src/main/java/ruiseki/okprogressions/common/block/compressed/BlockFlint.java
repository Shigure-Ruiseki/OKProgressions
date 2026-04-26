package ruiseki.okprogressions.common.block.compressed;

import java.util.Map;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import ruiseki.okcore.block.BlockOK;
import ruiseki.okcore.recipe.IOreDictEntry;
import ruiseki.okprogressions.OKPCreativeTab;

public class BlockFlint extends BlockOK implements IOreDictEntry {

    public BlockFlint() {
        super("flint_block", Material.ground);
        this.setHardness(1.0F);
        this.setResistance(5.0F);
        this.setStepSound(soundTypeStone);
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
    public Map<String, ItemStack> getOreMappings() {
        return Map.of("blockFlint", new ItemStack(this));
    }
}
