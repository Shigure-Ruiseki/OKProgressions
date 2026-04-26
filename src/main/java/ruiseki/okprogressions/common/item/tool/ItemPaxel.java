package ruiseki.okprogressions.common.item.tool;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

import com.google.common.collect.Sets;

import ruiseki.okcore.item.IItem;
import ruiseki.okprogressions.OKPCreativeTab;
import ruiseki.okprogressions.Reference;

public class ItemPaxel extends ItemPickaxe implements IItem {

    private final String name;

    private static final Set<Block> effectiveAgainst = Sets.newHashSet(
        Blocks.planks,
        Blocks.bookshelf,
        Blocks.log,
        Blocks.log2,
        Blocks.chest,
        Blocks.pumpkin,
        Blocks.lit_pumpkin,
        Blocks.melon_block,
        Blocks.ladder,
        Blocks.wooden_button,
        Blocks.wooden_pressure_plate,
        Blocks.activator_rail,
        Blocks.coal_ore,
        Blocks.cobblestone,
        Blocks.detector_rail,
        Blocks.diamond_block,
        Blocks.diamond_ore,
        Blocks.double_stone_slab,
        Blocks.golden_rail,
        Blocks.gold_block,
        Blocks.gold_ore,
        Blocks.ice,
        Blocks.iron_block,
        Blocks.iron_ore,
        Blocks.lapis_block,
        Blocks.lapis_ore,
        Blocks.lit_redstone_ore,
        Blocks.mossy_cobblestone,
        Blocks.netherrack,
        Blocks.packed_ice,
        Blocks.rail,
        Blocks.redstone_ore,
        Blocks.sandstone,
        Blocks.stone,
        Blocks.stone_slab,
        Blocks.stone_button,
        Blocks.stone_pressure_plate,
        Blocks.clay,
        Blocks.dirt,
        Blocks.farmland,
        Blocks.grass,
        Blocks.gravel,
        Blocks.mycelium,
        Blocks.sand,
        Blocks.snow,
        Blocks.snow_layer,
        Blocks.soul_sand,
        Blocks.web);

    public ItemPaxel(String name, ToolMaterial material) {
        super(material);

        this.name = name;
        this.setUnlocalizedName(name);
        this.setMaxStackSize(1);
        this.setCreativeTab(OKPCreativeTab.INSTANCE);

        this.setHarvestLevel("pickaxe", material.getHarvestLevel());
        this.setHarvestLevel("axe", material.getHarvestLevel());
        this.setHarvestLevel("shovel", material.getHarvestLevel());
    }

    public Item getItem() {
        return this;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean func_150897_b(Block block) {
        return effectiveAgainst.contains(block) || super.func_150897_b(block);
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta) {
        if (block == Blocks.web) {
            return 15.0F;
        }

        if (effectiveAgainst.contains(block)) {
            return this.efficiencyOnProperMaterial;
        }

        Material material = block.getMaterial();
        if (material == Material.wood || material == Material.vine
            || material == Material.plants
            || material == Material.leaves
            || material == Material.gourd
            || material == Material.ground
            || material == Material.grass
            || material == Material.sand) {
            return this.efficiencyOnProperMaterial;
        }

        return 1.0F;
    }

}
