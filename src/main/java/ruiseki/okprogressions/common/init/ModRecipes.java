package ruiseki.okprogressions.common.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import ruiseki.okcore.helper.PotionHelpers;
import ruiseki.okcore.init.IInitListener;

public class ModRecipes implements IInitListener {

    @Override
    public void onInit(Step step) {
        if (step == Step.POSTINIT) {
            blockRecipes();
            itemRecipes();
            fuelRecipes();
        }
    }

    public static void blockRecipes() {

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

        // Stone Stick
        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.STONE_STICK.newItemStack(4), "c", "c", 'c', "cobblestone"));

        // EnderPear
        GameRegistry.addRecipe(
            new ShapedOreRecipe(new ItemStack(Items.ender_pearl), "dd", "dd", 'd', ModItems.ENDER_DUST.newItemStack()));

        // Birthday Pickaxe
        GameRegistry.addRecipe(
            new ShapedOreRecipe(
                ModItems.BIRTHDAY_PICKAXE.newItemStack(),
                "rdr",
                " s ",
                " s ",
                'r',
                new ItemStack(Blocks.obsidian),
                'd',
                "gemDiamond",
                's',
                "stickWood"));

        // Paxel
        GameRegistry.addRecipe(
            new ShapedOreRecipe(
                ModItems.WOODEN_PAXEL.newItemStack(),
                "ASP",
                " s ",
                " s ",
                'A',
                new ItemStack(Items.wooden_axe),
                'S',
                new ItemStack(Items.wooden_shovel),
                'P',
                new ItemStack(Items.wooden_pickaxe),
                's',
                "stickWood"));

        GameRegistry.addRecipe(
            new ShapedOreRecipe(
                ModItems.STONE_PAXEL.newItemStack(),
                "ASP",
                " s ",
                " s ",
                'A',
                new ItemStack(Items.stone_axe),
                'S',
                new ItemStack(Items.stone_shovel),
                'P',
                new ItemStack(Items.stone_pickaxe),
                's',
                "stickWood"));

        GameRegistry.addRecipe(
            new ShapedOreRecipe(
                ModItems.IRON_PAXEL.newItemStack(),
                "ASP",
                " s ",
                " s ",
                'A',
                new ItemStack(Items.iron_axe),
                'S',
                new ItemStack(Items.iron_shovel),
                'P',
                new ItemStack(Items.iron_pickaxe),
                's',
                "stickWood"));

        GameRegistry.addRecipe(
            new ShapedOreRecipe(
                ModItems.GOLDEN_PAXEL.newItemStack(),
                "ASP",
                " s ",
                " s ",
                'A',
                new ItemStack(Items.golden_axe),
                'S',
                new ItemStack(Items.golden_shovel),
                'P',
                new ItemStack(Items.golden_pickaxe),
                's',
                "stickWood"));

        GameRegistry.addRecipe(
            new ShapedOreRecipe(
                ModItems.DIAMOND_PAXEL.newItemStack(),
                "ASP",
                " s ",
                " s ",
                'A',
                new ItemStack(Items.diamond_axe),
                'S',
                new ItemStack(Items.diamond_shovel),
                'P',
                new ItemStack(Items.diamond_pickaxe),
                's',
                "stickWood"));

        // Climbing Glove
        GameRegistry.addRecipe(
            new ShapedOreRecipe(
                ModItems.CLIMBING_GLOVE.newItemStack(),
                "ssl",
                "skl",
                "lli",
                's',
                new ItemStack(Items.slime_ball, 1, 0),
                'i',
                "ingotIron",
                'k',
                new ItemStack(Items.dye, 1, 0),
                'l',
                new ItemStack(Items.leather, 1, 0)));

        // Ender Sack
        GameRegistry.addRecipe(
            new ShapedOreRecipe(
                ModItems.ENDER_SACK.newItemStack(),
                " l ",
                "lsl",
                " l ",
                's',
                new ItemStack(Blocks.ender_chest),
                'l',
                new ItemStack(Items.leather, 1, 0)));
    }

    private void fuelRecipes() {
        GameRegistry.registerFuelHandler(new IFuelHandler() {

            @Override
            public int getBurnTime(ItemStack fuel) {
                Item item = fuel.getItem();

                if (item == ModBlocks.CHARCOAL_BLOCK.getItem()) return 16000;

                return 0;
            }
        });
    }
}
