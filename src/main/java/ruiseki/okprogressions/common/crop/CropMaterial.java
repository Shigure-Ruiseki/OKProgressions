package ruiseki.okprogressions.common.crop;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import ruiseki.okcore.json.AbstractJsonMaterial;
import ruiseki.okcore.json.ItemJson;
import ruiseki.okprogressions.common.soil.SoilMaterial;

public class CropMaterial extends AbstractJsonMaterial {

    public ItemStack stack;
    public int growthTicks;
    public int lightLevel = -1;
    public List<String> categories = new ArrayList<>();
    public Block displayBlock;
    public int displayMeta;
    public List<HarvestMaterial> results = new ArrayList<>();

    public int getGrowthTicksForSoil(SoilMaterial soil) {
        final float requiredGrowthTicks = this.growthTicks;
        final float growthModifier = soil.growthModifier;
        if (growthModifier > -1) {
            return MathHelper.floor_double(requiredGrowthTicks * (1 + growthModifier * -1));
        }

        return -1;
    }

    @Override
    public void read(JsonObject json) {
        ItemJson itemJson = ItemJson.parseItemString(getString(json, "seed", "minecraft:dirt,1,0"));
        this.stack = ItemJson.resolveItemStack(itemJson);

        this.growthTicks = getInt(json, "growthTicks", 1200);

        this.lightLevel = getInt(json, "lightLevel", -1);

        this.categories.clear();
        if (json.has("categories") && json.get("categories")
            .isJsonArray()) {
            for (JsonElement el : json.getAsJsonArray("categories")) {
                if (!el.isJsonNull()) this.categories.add(el.getAsString());
            }
        }

        this.displayBlock = Block.getBlockFromName(getString(json, "displayBlock", "minecraft:dirt"));
        this.displayMeta = getInt(json, "displayMeta", 0);

        this.results.clear();
        if (json.has("results") && json.get("results")
            .isJsonArray()) {
            for (JsonElement el : json.getAsJsonArray("results")) {
                if (el.isJsonObject()) {
                    HarvestMaterial entry = new HarvestMaterial();
                    entry.read(el.getAsJsonObject());
                    this.results.add(entry);
                }
            }
        }

        captureUnknownProperties(
            json,
            "seed",
            "growthTicks",
            "lightLevel",
            "categories",
            "displayBlock",
            "displayMeta",
            "results");
    }

    @Override
    public void write(JsonObject json) {
        json.addProperty("seed", ItemJson.toItemString(stack));

        json.addProperty("growthTicks", this.growthTicks);

        if (this.lightLevel != -1) {
            json.addProperty("lightLevel", this.lightLevel);
        }

        if (!this.categories.isEmpty()) {
            JsonArray array = new JsonArray();
            for (String cat : this.categories) {
                array.add(new JsonPrimitive(cat));
            }
            json.add("categories", array);
        }

        String blockName = Block.blockRegistry.getNameForObject(this.displayBlock);
        json.addProperty("displayBlock", blockName);

        json.addProperty("displayMeta", displayMeta);

        if (!this.results.isEmpty()) {
            JsonArray array = new JsonArray();
            for (HarvestMaterial entry : this.results) {
                JsonObject entryJson = new JsonObject();
                entry.write(entryJson);
                array.add(entryJson);
            }
            json.add("results", array);
        }

        writeUnknownProperties(json);
    }
}
