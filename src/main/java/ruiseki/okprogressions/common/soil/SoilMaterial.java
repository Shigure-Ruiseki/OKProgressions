package ruiseki.okprogressions.common.soil;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import ruiseki.okcore.json.AbstractJsonMaterial;
import ruiseki.okcore.json.ItemJson;

public class SoilMaterial extends AbstractJsonMaterial {

    public ItemStack stack;
    public Block displayBlock;
    public int displayMeta;
    public float growthModifier;
    public List<String> categories = new ArrayList<>();

    @Override
    public void read(JsonObject json) {
        ItemJson itemJson = ItemJson.parseItemString(getString(json, "stack", "minecraft:dirt,1,0"));
        this.stack = ItemJson.resolveItemStack(itemJson);

        this.displayBlock = Block.getBlockFromName(getString(json, "displayBlock", "minecraft:dirt"));
        this.displayMeta = getInt(json, "displayMeta", 0);

        this.growthModifier = getFloat(json, "growthModifier", 1.0f);

        this.categories.clear();
        if (json.has("categories") && json.get("categories")
            .isJsonArray()) {
            JsonArray array = json.getAsJsonArray("categories");
            for (JsonElement element : array) {
                this.categories.add(element.getAsString());
            }
        }

        captureUnknownProperties(json, "stack", "displayBlock", "displayMeta", "growthModifier", "categories");
    }

    @Override
    public void write(JsonObject json) {
        json.addProperty("stack", ItemJson.toItemString(stack));

        String blockName = Block.blockRegistry.getNameForObject(this.displayBlock);
        json.addProperty("displayBlock", blockName);

        json.addProperty("displayMeta", displayMeta);

        json.addProperty("growthModifier", this.growthModifier);

        if (this.categories != null && !this.categories.isEmpty()) {
            JsonArray array = new JsonArray();
            for (String category : this.categories) {
                array.add(new JsonPrimitive(category));
            }
            json.add("categories", array);
        }

        writeUnknownProperties(json);
    }
}
