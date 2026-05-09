package ruiseki.okprogressions.common.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.Nullable;

import ruiseki.okprogressions.common.crop.CropMaterial;
import ruiseki.okprogressions.common.crop.HarvestMaterial;
import ruiseki.okprogressions.common.soil.SoilMaterial;

public class BotanyPotHelpers {

    public static int getRequiredGrowthTicks(@Nullable CropMaterial crop, @Nullable SoilMaterial soil) {
        return crop == null || soil == null ? -1 : crop.getGrowthTicksForSoil(soil);
    }

    public static boolean isSoilValidForCrop(SoilMaterial soil, CropMaterial crop) {
        for (final String soilCategory : soil.getCategories()) {
            for (final String cropCategory : crop.getCategories()) {
                if (soilCategory.equalsIgnoreCase(cropCategory)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static List<ItemStack> generateDrop(Random rand, CropMaterial crop) {
        final List<ItemStack> drops = new ArrayList<>();
        for (final HarvestMaterial cropEntry : crop.getResults()) {
            if (rand.nextFloat() <= cropEntry.chance) {
                final int rolls = rand.nextInt(cropEntry.maxRolls - cropEntry.minRolls + 1) + cropEntry.minRolls;
                if (rolls > 0) {

                    for (int roll = 0; roll < rolls; roll++) {

                        drops.add(cropEntry.stack.copy());
                    }
                }
            }
        }

        return drops;
    }
}
