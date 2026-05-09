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

    private ItemStack stack;
    private Block displayBlock;
    private int displayMeta;
    private float growthModifier;
    private List<String> categories = new ArrayList<>();

    public void setStack(ItemStack stack) {
        this.stack = stack;
    }

    public ItemStack getStack() {
        return stack;
    }

    public Block getDisplayBlock() {
        return displayBlock;
    }

    public void setDisplayBlock(Block displayBlock) {
        this.displayBlock = displayBlock;
    }

    public int getDisplayMeta() {
        return displayMeta;
    }

    public void setDisplayMeta(int displayMeta) {
        this.displayMeta = displayMeta;
    }

    public float getGrowthModifier() {
        return growthModifier;
    }

    public void setGrowthModifier(float growthModifier) {
        this.growthModifier = growthModifier;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

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
