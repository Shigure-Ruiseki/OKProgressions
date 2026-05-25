package ruiseki.okprogressions.common.helper;

import static ruiseki.okprogressions.common.data.crop.CropType.CROP;
import static ruiseki.okprogressions.common.data.soil.SoilType.SOIL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.Nullable;

import ruiseki.okcore.helper.ItemStackHelpers;
import ruiseki.okcore.recipe.RecipeManager;
import ruiseki.okcore.recipe.RecipeRegistry;
import ruiseki.okprogressions.common.data.crop.CropInfo;
import ruiseki.okprogressions.common.data.crop.HarvestInfo;
import ruiseki.okprogressions.common.data.soil.SoilInfo;

public class BotanyPotHelpers {

    public static Collection<SoilInfo> getSoils() {
        return RecipeManager.getManager()
            .getRecipesByType(RecipeRegistry.getType(SOIL));
    }

    @Nullable
    public static SoilInfo getSoilFormStack(ItemStack stack) {
        if (stack == null || stack.getItem() == null) return null;
        for (final SoilInfo soilInfo : getSoils()) {
            ItemStack soilStack = soilInfo.getStack();
            if (ItemStackHelpers.areStacksEqual(soilStack, stack)) return soilInfo;
        }
        return null;
    }

    public static Collection<CropInfo> getCrops() {
        return RecipeManager.getManager()
            .getRecipesByType(RecipeRegistry.getType(CROP));
    }

    @Nullable
    public static CropInfo getCropFormStack(ItemStack stack) {
        if (stack == null || stack.getItem() == null) return null;
        for (CropInfo cropInfo : getCrops()) {
            ItemStack soilStack = cropInfo.getStack();
            if (ItemStackHelpers.areStacksEqual(soilStack, stack)) return cropInfo;
        }
        return null;
    }

    public static int getRequiredGrowthTicks(@Nullable CropInfo crop, @Nullable SoilInfo soil) {
        return crop == null || soil == null ? -1 : crop.getGrowthTicksForSoil(soil);
    }

    public static boolean isSoilValidForCrop(SoilInfo soil, CropInfo crop) {
        for (final String soilCategory : soil.getCategories()) {
            for (String cropCategory : crop.getCategories()) {
                if (soilCategory.equalsIgnoreCase(cropCategory)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<ItemStack> generateDrop(Random rand, CropInfo crop) {
        final List<ItemStack> drops = new ArrayList<>();
        if (crop == null) return drops;
        for (HarvestInfo cropEntry : crop.getResults()) {
            if (rand.nextFloat() <= cropEntry.getChance()) {
                final int rolls = rand.nextInt(cropEntry.getMaxRolls() - cropEntry.getMinRolls() + 1)
                    + cropEntry.getMinRolls();
                if (rolls > 0) {

                    for (int roll = 0; roll < rolls; roll++) {

                        drops.add(
                            cropEntry.getStack()
                                .copy());
                    }
                }
            }
        }

        return drops;
    }
}
