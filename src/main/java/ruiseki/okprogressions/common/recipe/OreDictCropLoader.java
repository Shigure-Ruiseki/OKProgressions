package ruiseki.okprogressions.common.recipe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

import com.gtnewhorizon.gtnhlib.blockstate.core.BlockState;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ruiseki.okcore.event.recipes.RecipesRegisterEvent;
import ruiseki.okcore.helper.BlockStateHelpers;
import ruiseki.okcore.recipe.ingredient.Ingredient;
import ruiseki.okprogressions.common.data.crop.CropInfo;
import ruiseki.okprogressions.common.data.crop.HarvestEntry;
import ruiseki.okprogressions.common.helper.BotanyPotHelpers;

public class OreDictCropLoader {

    public static final OreDictCropLoader INSTANCE = new OreDictCropLoader();

    private static Field seedCropBlockField = null;
    private static boolean fieldSearched = false;

    @SubscribeEvent
    public void onRegisterRecipes(RecipesRegisterEvent event) {
        Map<String, List<String>> itemToOreNames = new HashMap<>();
        for (String oreName : OreDictionary.getOreNames()) {
            for (ItemStack stack : OreDictionary.getOres(oreName)) {
                if (stack == null || stack.getItem() == null) continue;
                itemToOreNames.computeIfAbsent(key(stack), k -> new ArrayList<>())
                    .add(oreName);
            }
        }

        Set<String> added = new HashSet<>();

        for (ItemStack seed : OreDictionary.getOres("listAllSeed")) {
            if (skip(seed, added)) continue;
            added.add(key(seed));

            CropInfo crop = buildCropInfo(seed, findCropOutput(seed, itemToOreNames), getCategories(seed), 1200);
            if (crop != null) {
                event.addRecipe(crop);
            }
        }

        for (String oreName : OreDictionary.getOreNames()) {

            if (oreName.startsWith("seed")) {
                List<ItemStack> cropItems = OreDictionary.getOres("crop" + oreName.substring(4));
                ItemStack output = cropItems.isEmpty() ? null : cropItems.get(0);

                for (ItemStack seed : OreDictionary.getOres(oreName)) {
                    if (skip(seed, added)) continue;
                    added.add(key(seed));

                    CropInfo crop = buildCropInfo(seed, output, getCategories(seed), 1200);
                    if (crop != null) {
                        event.addRecipe(crop);
                    }
                }
            } else if (oreName.startsWith("treeSapling")) {
                String logName = "log" + oreName.substring("treeSapling".length());
                List<ItemStack> saplingItems = OreDictionary.getOres(oreName);
                List<ItemStack> logItems = OreDictionary.getOres(logName);

                ItemStack logOutput = logItems.isEmpty() ? null : logItems.get(0);

                for (ItemStack sapling : saplingItems) {
                    if (skip(sapling, added)) continue;
                    added.add(key(sapling));

                    CropInfo treeCrop = buildTreeCropInfo(sapling, logOutput, Collections.singleton("dirt"), 2400);
                    if (treeCrop != null) {
                        event.addRecipe(treeCrop);
                    }
                }
            }
        }
    }

    private static boolean skip(ItemStack seed, Set<String> added) {
        if (seed == null || seed.getItem() == null) return true;
        if (added.contains(key(seed))) return true;
        if (BotanyPotHelpers.getCropFormStack(seed) != null) return true;
        return false;
    }

    private static CropInfo buildCropInfo(ItemStack seed, ItemStack output, Set<String> categories, int growthTicks) {
        List<HarvestEntry> results = new ArrayList<>();
        if (output != null) {
            results.add(new HarvestEntry(1.0f, output.copy(), 1, 1));
        }
        results.add(new HarvestEntry(0.5f, seed.copy(), 1, 1));

        return createCrop(seed, results, categories, growthTicks);
    }

    private static CropInfo buildTreeCropInfo(ItemStack sapling, ItemStack logOutput, Set<String> categories,
        int growthTicks) {
        List<HarvestEntry> results = new ArrayList<>();

        if (logOutput != null) {
            results.add(new HarvestEntry(1.0f, logOutput.copy(), 1, 1));
        }
        results.add(new HarvestEntry(0.5f, sapling.copy(), 1, 1));

        return createCrop(sapling, results, categories, growthTicks);
    }

    private static CropInfo createCrop(ItemStack seed, List<HarvestEntry> results, Set<String> categories,
        int growthTicks) {
        String itemName = Item.itemRegistry.getNameForObject(seed.getItem());
        String sanitized = itemName != null ? itemName.replace(":", "_")
            .replace("/", "_") : "unknown";
        ResourceLocation id = new ResourceLocation(
            "okprogressions",
            "oredict_" + sanitized + "_" + seed.getItemDamage());

        Ingredient seedIngredient = Ingredient.of(seed);
        BlockState displayState = resolveDisplayBlock(seed);

        return new CropInfo(id, seedIngredient, categories, growthTicks, results, displayState, -1);
    }

    private static BlockState resolveDisplayBlock(ItemStack seed) {
        Item item = seed.getItem();

        if (item instanceof ItemSeeds) {
            try {
                if (!fieldSearched) {
                    for (Field f : ItemSeeds.class.getDeclaredFields()) {
                        if (f.getType() == Block.class) {
                            f.setAccessible(true);
                            seedCropBlockField = f;
                            break;
                        }
                    }
                    fieldSearched = true;
                }

                if (seedCropBlockField != null) {
                    Block cropBlock = (Block) seedCropBlockField.get(item);
                    if (cropBlock != null && cropBlock != net.minecraft.init.Blocks.air) {
                        return BlockStateHelpers.getState(cropBlock, 7);
                    }
                }
            } catch (Exception ignored) {}
        }

        Block block = Block.getBlockFromItem(item);
        if (block != null && block != net.minecraft.init.Blocks.air) {
            return BlockStateHelpers.getState(block, seed.getItemDamage());
        }

        return null;
    }

    private static ItemStack findCropOutput(ItemStack seed, Map<String, List<String>> itemToOreNames) {
        List<String> names = itemToOreNames.get(key(seed));
        if (names == null) return null;
        for (String name : names) {
            if (name.startsWith("seed")) {
                List<ItemStack> cropItems = OreDictionary.getOres("crop" + name.substring(4));
                if (!cropItems.isEmpty()) return cropItems.get(0);
            }
        }
        return null;
    }

    private static Set<String> getCategories(ItemStack seed) {
        if (seed.getItem() == Items.nether_wart) {
            return Collections.singleton("nether");
        }
        return Collections.singleton("dirt");
    }

    private static String key(ItemStack stack) {
        int id = Item.getIdFromItem(stack.getItem());
        int meta = stack.getItemDamage() == OreDictionary.WILDCARD_VALUE ? 0 : stack.getItemDamage();
        return id + ":" + meta;
    }
}
