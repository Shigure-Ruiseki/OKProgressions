package ruiseki.okprogressions.common.block.compressed;

import java.util.Map;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.Explosion;

import ruiseki.okcore.block.BlockOK;
import ruiseki.okcore.recipe.IOreDictEntry;
import ruiseki.okprogressions.OKPCreativeTab;

public class BlockCharcoal extends BlockOK implements IOreDictEntry {

    public BlockCharcoal() {
        super("charcoal_block", Material.rock);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setHarvestLevel("pickaxe", 1);
        this.setStepSound(soundTypeStone);
        this.setCreativeTab(OKPCreativeTab.INSTANCE);
    }

    @Override
    public boolean canDropFromExplosion(Explosion explosionIn) {
        return false;
    }

    @Override
    public Map<String, ItemStack> getOreMappings() {
        return Map.of("blockCharcoal", new ItemStack(this));
    }
}
