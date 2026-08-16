package ruiseki.okprogressions.common.data.soil;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.gtnewhorizon.gtnhlib.blockstate.core.BlockState;

import ruiseki.okcore.config.configurable.ConfigurableRecipe;
import ruiseki.okcore.config.extendedconfig.ExtendedConfig;
import ruiseki.okcore.config.extendedconfig.RecipeConfig;
import ruiseki.okcore.helper.BlockStateHelpers;
import ruiseki.okcore.helper.GsonHelpers;
import ruiseki.okcore.network.ExtendedBuffer;
import ruiseki.okcore.recipe.ingredient.Ingredient;

public class SoilSerializer extends ConfigurableRecipe<SoilInfo> {

    private static SoilSerializer _instance = null;

    /**
     * Get the unique instance.
     *
     * @return The instance.
     */
    public static SoilSerializer getInstance() {
        return _instance;
    }

    public SoilSerializer(ExtendedConfig<RecipeConfig<SoilInfo>> eConfig) {
        super(eConfig);
    }

    @Override
    public SoilInfo fromJson(ResourceLocation id, JsonObject json) {
        final Ingredient input = Ingredient.fromJson(json.getAsJsonObject("input"));
        final BlockState renderState = BlockStateHelpers.fromJson(json, "display");
        final float growthModifier = GsonHelpers.getAsFloat(json, "growthModifier");
        final Set<String> categories = deserializeCategories(json);
        final int lightLevel = GsonHelpers.getAsInt(json, "lightLevel", -1);

        if (growthModifier <= -1) {
            throw new IllegalArgumentException(
                "Soil " + id + " has an invalid growth modifier. It must be greater than -1.");
        }

        return new SoilInfo(id, input, renderState, growthModifier, categories, lightLevel);
    }

    @Override
    public @Nullable SoilInfo fromNetwork(ResourceLocation id, ExtendedBuffer buffer) throws IOException {
        try {
            final Ingredient input = Ingredient.fromNetwork(buffer);
            final BlockState renderState = buffer.readBlockState();
            final float growthModifier = buffer.readFloat();

            final Set<String> categories = new HashSet<>();
            buffer.readStringCollection(categories);

            final int lightLevel = buffer.readVarIntFromBuffer();

            return new SoilInfo(id, input, renderState, growthModifier, categories, lightLevel);
        } catch (final Exception e) {
            throw new IllegalStateException("Failed to read soil info from packet buffer.", e);
        }
    }

    @Override
    public void toNetwork(ExtendedBuffer buffer, SoilInfo recipe) throws IOException {
        try {
            recipe.getIngredient()
                .toNetwork(buffer);
            buffer.writeBlockState(recipe.getRenderState());
            buffer.writeFloat(recipe.getGrowthModifier());
            buffer.writeStringCollection(recipe.getCategories());
            buffer.writeVarIntToBuffer(recipe.getLightLevel());
        } catch (final Exception e) {
            throw new IllegalStateException("Failed to write soil to the packet buffer.", e);
        }
    }

    /**
     * A helper method to deserialize soil categories from a JSON element.
     *
     * @param json The JsonObject to read from.
     * @return A set of soil categories in lowercase.
     */
    private static Set<String> deserializeCategories(JsonObject json) {
        final Set<String> categories = new HashSet<>();

        if (json.has("categories")) {
            JsonElement element = json.get("categories");
            if (element.isJsonArray()) {
                for (final JsonElement entry : element.getAsJsonArray()) {
                    if (entry.isJsonPrimitive()) {
                        categories.add(
                            entry.getAsString()
                                .toLowerCase());
                    }
                }
            } else if (element.isJsonPrimitive()) {
                categories.add(
                    element.getAsString()
                        .toLowerCase());
            }
        }

        return categories;
    }
}
