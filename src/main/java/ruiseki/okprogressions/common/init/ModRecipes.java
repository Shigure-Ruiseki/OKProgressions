package ruiseki.okprogressions.common.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

import cpw.mods.fml.common.registry.GameRegistry;
import ruiseki.okcore.helper.PotionHelpers;
import ruiseki.okcore.init.IInitListener;

public class ModRecipes implements IInitListener {

    @Override
    public void onInit(Step step) {
        if (step == Step.POSTINIT) {
            blockRecipes();
            itemRecipes();
        }
    }

    public static void blockRecipes() {
        // Cobblegen
        GameRegistry.addRecipe(
            new ShapedOreRecipe(
                ModBlocks.COBBLE_GEN.newItemStack(),
                "ccc",
                "wgl",
                "ccc",
                'c',
                "cobblestone",
                'w',
                new ItemStack(Items.water_bucket),
                'l',
                new ItemStack(Items.lava_bucket),
                'g',
                "blockGlass"));
        GameRegistry.addRecipe(
            new ShapedOreRecipe(
                ModBlocks.COBBLE_GEN.newItemStack(),
                "ccc",
                "lgw",
                "ccc",
                'c',
                "cobblestone",
                'w',
                new ItemStack(Items.water_bucket),
                'l',
                new ItemStack(Items.lava_bucket),
                'g',
                "blockGlass"));

        GameRegistry.addRecipe(
            new ShapedOreRecipe(
                ModBlocks.IRON_COBBLE_GEN.newItemStack(),
                "iii",
                "igi",
                "iii",
                'i',
                "ingotIron",
                'g',
                ModBlocks.COBBLE_GEN.newItemStack()));

        GameRegistry.addRecipe(
            new ShapedOreRecipe(
                ModBlocks.GOLD_COBBLE_GEN.newItemStack(),
                "bbb",
                "bdb",
                "bbb",
                'b',
                "ingotGold",
                'd',
                ModBlocks.IRON_COBBLE_GEN.newItemStack()));

        GameRegistry.addRecipe(
            new ShapedOreRecipe(
                ModBlocks.DIAMOND_COBBLE_GEN.newItemStack(),
                "ddd",
                "did",
                "ddd",
                'd',
                "gemDiamond",
                'i',
                ModBlocks.GOLD_COBBLE_GEN.newItemStack()));

        GameRegistry.addRecipe(
            new ShapedOreRecipe(
                ModBlocks.EMERALD_COBBLE_GEN.newItemStack(),
                "eee",
                "ebe",
                "eee",
                'e',
                "gemEmerald",
                'b',
                ModBlocks.DIAMOND_COBBLE_GEN.newItemStack()));

        // Growth Block
        GameRegistry.addRecipe(
            new ShapedOreRecipe(
                ModBlocks.GROWTH.newItemStack(),
                "ibi",
                "ded",
                "ibi",
                'i',
                new ItemStack(Items.iron_ingot),
                'b',
                ModBlocks.BONE_BLOCK.newItemStack(),
                'd',
                PotionHelpers.createPotion(8194),
                'e',
                new ItemStack(Blocks.waterlily)));

        GameRegistry.addRecipe(
            new ShapedOreRecipe(
                ModBlocks.GROWTH_UPGRADE.newItemStack(),
                "cnc",
                "wew",
                "cnc",
                'e',
                new ItemStack(Blocks.glass),
                'w',
                PotionHelpers.createPotion(8194),
                'c',
                ModBlocks.GROWTH.newItemStack(),
                'n',
                new ItemStack(Items.nether_star)));

        GameRegistry.addRecipe(
            new ShapedOreRecipe(
                ModBlocks.GROWTH_UPGRADE_TWO.newItemStack(),
                "cgc",
                "scs",
                "cgc",
                's',
                PotionHelpers.createPotion(8226),
                'g',
                ModBlocks.GROWTH_UPGRADE.newItemStack(),
                'c',
                new ItemStack(Items.ender_eye)));

        // Bone Block
        GameRegistry.addRecipe(
            new ShapedOreRecipe(
                ModBlocks.BONE_BLOCK.newItemStack(),
                "bbb",
                "bbb",
                "bbb",
                'b',
                new ItemStack(Items.bone)));

    }

    public static void itemRecipes() {

        GameRegistry.addRecipe(
            new ShapedOreRecipe(
                ModItems.DIAMOND_APPLE.newItemStack(),
                "ddd",
                "dad",
                "ddd",
                'd',
                "gemDiamond",
                'a',
                Items.apple));

        GameRegistry.addRecipe(
            new ShapedOreRecipe(
                ModItems.EMERALD_APPLE.newItemStack(),
                "eee",
                "eae",
                "eee",
                'e',
                "gemEmerald",
                'a',
                Items.apple));

        GameRegistry.addRecipe(
            new ShapedOreRecipe(
                ModItems.IRON_APPLE.newItemStack(),
                "iii",
                "iai",
                "iii",
                'i',
                "ingotIron",
                'a',
                Items.apple));

        GameRegistry.addRecipe(
            new ShapedOreRecipe(
                ModItems.REDSTONE_APPLE.newItemStack(),
                "rrr",
                "rar",
                "rrr",
                'r',
                "dustRedstone",
                'a',
                Items.apple));

        GameRegistry.addRecipe(
            new ShapedOreRecipe(
                ModItems.MYCELIUM_SEEDS.newItemStack(),
                "sss",
                "ses",
                "sss",
                's',
                new ItemStack(Items.wheat_seeds),
                'e',
                new ItemStack(Items.spider_eye)));
    }
}
