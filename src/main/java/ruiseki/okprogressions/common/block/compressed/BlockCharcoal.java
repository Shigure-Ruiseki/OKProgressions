package ruiseki.okprogressions.common.block.compressed;

import net.minecraft.block.material.Material;
import net.minecraft.world.Explosion;
import net.minecraftforge.oredict.OreDictionary;

import ruiseki.okcore.block.BlockOK;
import ruiseki.okprogressions.OKPCreativeTab;

public class BlockCharcoal extends BlockOK {

    public BlockCharcoal() {
        super("charcoal_block", Material.rock);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setHarvestLevel("pickaxe", 1);
        this.setStepSound(soundTypeStone);
        this.setCreativeTab(OKPCreativeTab.INSTANCE);
    }

    @Override
    protected void registerComponent() {
        OreDictionary.registerOre("blockCharcoal", this);
    }

    @Override
    public boolean canDropFromExplosion(Explosion explosionIn) {
        return false;
    }
}
