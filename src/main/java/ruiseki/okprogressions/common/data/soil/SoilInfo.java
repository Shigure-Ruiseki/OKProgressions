package ruiseki.okprogressions.common.data.soil;

import java.util.Set;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.gtnewhorizon.gtnhlib.blockstate.core.BlockState;

import ruiseki.okcore.recipe.IRecipeSerializer;
import ruiseki.okcore.recipe.IRecipeType;
import ruiseki.okcore.recipe.RecipeDataBase;
import ruiseki.okcore.recipe.ingredient.Ingredient;

public class SoilInfo extends RecipeDataBase {

    /**
     * The item used to get the soil into the pot.
     */
    private Ingredient ingredient;

    /**
     * The blockstate used to render the soil.
     */
    private BlockState renderState;

    /**
     * A modifier applied to the growth time of the crop.
     */
    private float growthModifier;

    /**
     * An array of associated soil categories.
     */
    private Set<String> categories;

    /**
     * The light level of the soil when placed in the crop. If this is not specified the light
     * level of {@link #renderState} will be used.
     */
    private int lightLevel;

    public SoilInfo(ResourceLocation id, Ingredient ingredient, BlockState renderState, float growthModifier,
        Set<String> categories, int lightLevel) {
        super(id);
        this.ingredient = ingredient;
        this.renderState = renderState;
        this.growthModifier = growthModifier;
        this.categories = categories;
        this.lightLevel = lightLevel;
    }

    public float getGrowthModifier() {

        return this.growthModifier;
    }

    public Ingredient getIngredient() {

        return this.ingredient;
    }

    public BlockState getRenderState() {
        return this.renderState;
    }

    public Set<String> getCategories() {

        return this.categories;
    }

    @Deprecated
    public ItemStack getFirstSoil() {
        final ItemStack[] matchingStacks = this.ingredient.getItems();
        return matchingStacks.length > 0 ? matchingStacks[0] : null;
    }

    public void setIngredient(Ingredient ingredient) {

        this.ingredient = ingredient;
    }

    public void setRenderState(BlockState renderState) {
        this.renderState = renderState;
    }

    public void setGrowthModifier(float modifier) {

        this.growthModifier = modifier;
    }

    public void setCategories(Set<String> categories) {

        this.categories = categories;
    }

    public void setLightLevel(int lightLevel) {
        this.lightLevel = lightLevel;
    }

    public int getLightLevel() {
        return this.lightLevel;
    }

    @Override
    public IRecipeType<?> getType() {
        return SoilTypeConfig._instance.getInstance();
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SoilSerializerConfig._instance.getInstance();
    }

    @Override
    public boolean matchesOK(IInventory inventory, World world) {
        if (inventory == null || inventory.getSizeInventory() == 0) {
            return false;
        }

        ItemStack stackInSlot = inventory.getStackInSlot(0);
        if (stackInSlot == null || stackInSlot.getItem() == null) {
            return false;
        }

        return this.getIngredient() != null && this.getIngredient()
            .test(stackInSlot);
    }
}
