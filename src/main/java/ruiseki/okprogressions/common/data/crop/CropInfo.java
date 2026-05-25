package ruiseki.okprogressions.common.data.crop;

import static ruiseki.okprogressions.common.data.crop.CropType.CROP;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import ruiseki.okcore.datastructure.BlockStack;
import ruiseki.okcore.recipe.IRecipeSerializer;
import ruiseki.okcore.recipe.IRecipeType;
import ruiseki.okcore.recipe.RecipeDataBase;
import ruiseki.okcore.recipe.RecipeRegistry;
import ruiseki.okprogressions.common.data.soil.SoilInfo;

public class CropInfo extends RecipeDataBase {

    private ItemStack stack;
    private BlockStack displayBlock;
    private int growthTicks;
    private int lightLevel = -1;
    private List<String> categories;
    private List<HarvestInfo> results;

    public CropInfo(ResourceLocation id, ItemStack stack, BlockStack displayBlock, int growthTicks, int lightLevel,
        List<String> categories, List<HarvestInfo> results) {
        super(id);
        this.stack = stack;
        this.displayBlock = displayBlock;
        this.growthTicks = growthTicks;
        this.lightLevel = lightLevel;
        this.categories = categories;
        this.results = results;
    }

    public ItemStack getStack() {
        return stack;
    }

    public BlockStack getDisplayBlock() {
        return displayBlock;
    }

    public List<String> getCategories() {
        return categories;
    }

    public int getGrowthTicks() {
        return growthTicks;
    }

    public int getLightLevel() {
        return lightLevel;
    }

    public List<HarvestInfo> getResults() {
        return results;
    }

    public int getGrowthTicksForSoil(SoilInfo soil) {
        final float requiredGrowthTicks = this.growthTicks;
        final float growthModifier = soil.getGrowthModifier();
        if (growthModifier > -1) {
            return MathHelper.floor_double(requiredGrowthTicks * (1 + growthModifier * -1));
        }

        return -1;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeRegistry.getSerializer(CROP);
    }

    @Override
    public IRecipeType<?> getType() {
        return RecipeRegistry.getType(CROP);
    }
}
