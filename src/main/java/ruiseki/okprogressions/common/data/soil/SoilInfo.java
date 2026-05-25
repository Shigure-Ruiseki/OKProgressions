package ruiseki.okprogressions.common.data.soil;

import static ruiseki.okprogressions.common.data.soil.SoilType.SOIL;

import java.util.List;
import java.util.Objects;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import ruiseki.okcore.datastructure.BlockStack;
import ruiseki.okcore.inventory.ItemStackKey;
import ruiseki.okcore.recipe.IRecipeSerializer;
import ruiseki.okcore.recipe.IRecipeType;
import ruiseki.okcore.recipe.RecipeDataBase;
import ruiseki.okcore.recipe.RecipeRegistry;

public class SoilInfo extends RecipeDataBase {

    private ItemStack stack;
    private BlockStack displayBlock;
    private float growthModifier;
    private List<String> categories;

    public SoilInfo(ResourceLocation id, ItemStack stack, BlockStack displayBlock, float growthModifier,
        List<String> categories) {
        super(id);
        this.stack = stack;
        this.displayBlock = displayBlock;
        this.growthModifier = growthModifier;
        this.categories = categories;
    }

    public ItemStack getStack() {
        return stack;
    }

    public BlockStack getDisplayBlock() {
        return displayBlock;
    }

    public float getGrowthModifier() {
        return growthModifier;
    }

    public List<String> getCategories() {
        return categories;
    }

    @Override
    public IRecipeType<?> getType() {
        return RecipeRegistry.getType(SOIL);
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeRegistry.getSerializer(SOIL);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof SoilInfo other)) return false;

        if (!Objects.equals(getId(), other.getId())) return false;
        if (Float.compare(other.growthModifier, growthModifier) != 0) return false;
        if (!Objects.equals(displayBlock, other.displayBlock)) return false;
        if (!Objects.equals(categories, other.categories)) return false;

        ItemStackKey key1 = ItemStackKey.of(this.stack);
        ItemStackKey key2 = ItemStackKey.of(other.stack);
        return Objects.equals(key1, key2);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getId());
        ruiseki.okcore.inventory.ItemStackKey key = ruiseki.okcore.inventory.ItemStackKey.of(this.stack);

        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + Objects.hashCode(displayBlock);
        result = 31 * result + Float.floatToIntBits(growthModifier);
        result = 31 * result + Objects.hashCode(categories);
        return result;
    }

    @Override
    public String toString() {
        ruiseki.okcore.inventory.ItemStackKey key = ruiseki.okcore.inventory.ItemStackKey.of(this.stack);
        return "SoilInfo{" + "id="
            + getId()
            + ", item="
            + (key != null ? key.getItem() : "null")
            + ", meta="
            + (key != null ? key.getMeta() : 0)
            + ", displayBlock="
            + displayBlock
            + ", growthModifier="
            + growthModifier
            + ", categories="
            + categories
            + '}';
    }
}
