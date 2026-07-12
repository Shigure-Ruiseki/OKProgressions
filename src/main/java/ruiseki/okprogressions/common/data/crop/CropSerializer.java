package ruiseki.okprogressions.common.data.crop;

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

public class CropSerializer implements IRecipeSerializer<CropInfo> {

    public static final CropSerializer INSTANCE = new CropSerializer();

    @Override
    public CropInfo fromJson(ResourceLocation id, JsonObject json) {
        ItemMaterial resultMaterial = new ItemMaterial();
        BlockMaterial displayMaterial = new BlockMaterial();
        List<String> categories = new ArrayList<>();
        List<HarvestInfo> results = new ArrayList<>();

        if (json.has("seed") && json.get("seed")
            .isJsonObject()) {
            resultMaterial.read(json.getAsJsonObject("seed"));
        }

        if (json.has("display") && json.get("display")
            .isJsonObject()) {
            displayMaterial.read(json.getAsJsonObject("display"));
        }

        int growthTicks = AbstractJsonMaterial.getInt(json, "growthTicks", 1200);
        int lightLevel = AbstractJsonMaterial.getInt(json, "lightLevel", -1);

        if (json.has("categories") && json.get("categories")
            .isJsonArray()) {
            for (JsonElement el : json.getAsJsonArray("categories")) {
                if (el.isJsonPrimitive() && el.getAsJsonPrimitive()
                    .isString()) {
                    categories.add(el.getAsString());
                }
            }
        }

        if (json.has("results") && json.get("results")
            .isJsonArray()) {
            for (JsonElement el : json.getAsJsonArray("results")) {
                if (el.isJsonObject()) {
                    HarvestMaterial entry = new HarvestMaterial();
                    entry.read(el.getAsJsonObject());
                    if (entry.validate()) {
                        results.add(entry.toInfo());
                    }
                }
            }
        }

        return new CropInfo(
            id,
            resultMaterial.toStack(),
            displayMaterial.toStack(),
            growthTicks,
            lightLevel,
            categories,
            results);
    }

    @Override
    public @Nullable CropInfo fromNetwork(ResourceLocation id, ExtendedBuffer buffer) throws IOException {
        ItemStack stack = buffer.readItemStackFromBuffer();
        BlockStack displayBlock = buffer.readBlockStack();
        int growthTicks = buffer.readInt();
        int lightLevel = buffer.readInt();

        int catSize = buffer.readInt();
        List<String> categories = new ArrayList<>(catSize);
        for (int i = 0; i < catSize; i++) {
            categories.add(buffer.readString());
        }

        int resSize = buffer.readInt();
        List<HarvestInfo> results = new ArrayList<>(resSize);
        for (int i = 0; i < resSize; i++) {
            HarvestInfo info = new HarvestInfo();
            info.fromNetwork(buffer);
            results.add(info);
        }

        return new CropInfo(id, stack, displayBlock, growthTicks, lightLevel, categories, results);
    }

    @Override
    public void toNetwork(ExtendedBuffer buffer, CropInfo recipe) throws IOException {
        buffer.writeItemStackToBuffer(recipe.getStack());

        buffer.writeBlockStack(recipe.getDisplayBlock());

        buffer.writeInt(recipe.getGrowthTicks());

        buffer.writeInt(recipe.getLightLevel());

        List<String> categories = recipe.getCategories();
        buffer.writeInt(categories.size());
        for (String cat : categories) {
            buffer.writeString(cat);
        }

        List<HarvestInfo> results = recipe.getResults();
        buffer.writeInt(results.size());
        for (HarvestInfo info : results) {
            info.toNetwork(buffer);
        }
    }
}
