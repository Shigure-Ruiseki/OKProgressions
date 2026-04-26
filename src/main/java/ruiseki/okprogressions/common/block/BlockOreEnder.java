package ruiseki.okprogressions.common.block;

import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import ruiseki.okcore.block.BlockOK;
import ruiseki.okcore.recipe.IOreDictEntry;
import ruiseki.okprogressions.OKPCreativeTab;
import ruiseki.okprogressions.common.init.ModItems;

public class BlockOreEnder extends BlockOK implements IOreDictEntry {

    private final Random rand = new Random();

    public BlockOreEnder() {
        super("ore_ender", Material.rock);
        this.setHardness(8.0F);
        this.setResistance(10.0F);
        this.setHarvestLevel("pickaxe", 1);
        this.setStepSound(soundTypeStone);
        this.setCreativeTab(OKPCreativeTab.INSTANCE);
    }

    @Override
    public Map<String, ItemStack> getOreMappings() {
        return Map.of("oreEnderOre", new ItemStack(this));
    }

    @Override
    public void randomDisplayTick(World worldIn, int x, int y, int z, Random rand) {
        super.randomDisplayTick(worldIn, x, y, z, rand);
        for (int i = 0; i < 4; ++i) {
            double d0 = ((float) x + rand.nextFloat());
            double d1 = ((float) y + rand.nextFloat());
            double d2 = ((float) z + rand.nextFloat());
            double d3 = ((double) rand.nextFloat() - 0.5D) * 0.5D;
            double d4 = ((double) rand.nextFloat() - 0.5D) * 0.5D;
            double d5 = ((double) rand.nextFloat() - 0.5D) * 0.5D;
            int j = rand.nextInt(2) * 2 - 1;

            if (worldIn.getBlock(x - 1, y, z) != this && worldIn.getBlock(x + 1, y, z) != this) {
                d0 = (double) x + 0.5D + 0.25D * (double) j;
                d3 = (rand.nextFloat() * 2.0F * (float) j);
            } else {
                d2 = (double) z + 0.5D + 0.25D * (double) j;
                d5 = (rand.nextFloat() * 2.0F * (float) j);
            }

            worldIn.spawnParticle("portal", d0, d1, d2, d3, d4, d5);
        }
    }

    @Override
    public int quantityDropped(Random rand) {
        return 1 + rand.nextInt(5);
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune) {
        return ModItems.ENDER_DUST.getItem();
    }

    @Override
    public int quantityDroppedWithBonus(int maxBonus, Random rand) {
        int total = quantityDropped(rand) + rand.nextInt(maxBonus + 1);

        if (total < 1) total = 1;
        if (total > 6) total = 6;

        return total;
    }

    @Override
    public int getExpDrop(IBlockAccess world, int metadata, int fortune) {
        return (this.getItemDropped(metadata, rand, fortune) != Item.getItemFromBlock(this)) ? (1 + rand.nextInt(5))
            : 0;
    }

    @Override
    public boolean isReplaceableOreGen(World world, int x, int y, int z, Block target) {
        return false;
    }
}
