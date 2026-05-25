package ruiseki.okprogressions.common.data.soil;

import static ruiseki.okprogressions.common.data.soil.SoilType.SOIL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import ruiseki.okcore.datastructure.BlockStack;
import ruiseki.okcore.json.AbstractJsonMaterial;
import ruiseki.okcore.json.block.BlockMaterial;
import ruiseki.okcore.json.item.ItemMaterial;
import ruiseki.okcore.network.ExtendedBuffer;
import ruiseki.okcore.recipe.IRecipeSerializer;
import ruiseki.okcore.recipe.RecipeData;

@RecipeData
public class SoilSerializer implements IRecipeSerializer<SoilInfo> {

    @Override
    public String getTypeKey() {
        return SOIL;
    }

    @Override
    public SoilInfo fromJson(ResourceLocation id, JsonObject json) {
        ItemStack input = null;
        if (json.has("input")) {
            JsonElement resultElement = json.get("input");
            if (resultElement.isJsonObject()) {
                JsonObject resultObj = resultElement.getAsJsonObject();
                ItemMaterial material = new ItemMaterial();
                material.read(resultObj);
                if (material.validate()) input = material.toStack();
            }
        }

        BlockStack displayBlock = null;
        if (json.has("display") && json.get("display")
            .isJsonObject()) {
            BlockMaterial material = new BlockMaterial();
            material.read(json.getAsJsonObject("display"));
            if (material.validate()) displayBlock = material.toStack();
        }

        float growthModifier = AbstractJsonMaterial.getFloat(json, "growthModifier", 0.0f);

        List<String> categories = new ArrayList<>();
        if (json.has("categories") && json.get("categories")
            .isJsonArray()) {
            for (JsonElement el : json.getAsJsonArray("categories")) {
                if (el != null && el.isJsonPrimitive()
                    && el.getAsJsonPrimitive()
                        .isString()) {
                    categories.add(el.getAsString());
                }
            }
        }

        return new SoilInfo(id, input, displayBlock, growthModifier, categories);
    }

    @Override
    public @Nullable SoilInfo fromNetwork(ResourceLocation id, ExtendedBuffer buffer) throws IOException {
        ItemStack input = buffer.readItemStackFromBuffer();

        BlockStack displayBlock = buffer.readBlockStack();

        float growthModifier = buffer.readFloat();

        int catSize = buffer.readInt();
        List<String> categories = new ArrayList<>(catSize);
        for (int i = 0; i < catSize; i++) {
            categories.add(buffer.readString());
        }

        return new SoilInfo(id, input, displayBlock, growthModifier, categories);
    }

    @Override
    public void toNetwork(ExtendedBuffer buffer, SoilInfo recipe) throws IOException {
        buffer.writeItemStackToBuffer(recipe.getStack());

        buffer.writeBlockStack(recipe.getDisplayBlock());

        buffer.writeFloat(recipe.getGrowthModifier());

        List<String> categories = recipe.getCategories();
        buffer.writeInt(categories.size());
        for (String cat : categories) {
            buffer.writeString(cat);
        }
    }
}
