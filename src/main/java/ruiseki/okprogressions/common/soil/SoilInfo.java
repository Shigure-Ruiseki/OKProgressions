package ruiseki.okprogressions.common.soil;

import java.util.Arrays;

import net.minecraft.item.ItemStack;

import lombok.Getter;

@Getter
public class SoilInfo {

    public final ItemStack stack;
    public final float growthModifier;
    public final String[] categories;

    public SoilInfo(ItemStack stack, float modifier, String[] cats) {
        this.stack = stack;
        this.growthModifier = modifier;
        this.categories = cats;
    }

    public boolean hasCategory(String category) {
        return Arrays.asList(categories)
            .contains(category);
    }
}
