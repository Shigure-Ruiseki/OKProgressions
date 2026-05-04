package ruiseki.okprogressions.common.soil;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import ruiseki.okcore.json.AbstractJsonMaterial;

public class SoilMaterial extends AbstractJsonMaterial {

    public String inputItem;
    public String displayBlock;
    public int displayMeta;
    public float growthModifier;
    public Set<String> categories = new HashSet<>();

    @Override
    public void read(JsonObject json) {
        this.inputItem = getString(json, "input", "minecraft:dirt");
        this.displayBlock = getString(json, "display", "minecraft:dirt");
        this.displayMeta = getInt(json, "meta", 0);
        this.growthModifier = getFloat(json, "growthModifier", 1.0f);

        this.categories.clear();
        if (json.has("categories") && json.get("categories")
            .isJsonArray()) {
            JsonArray array = json.getAsJsonArray("categories");
            for (JsonElement element : array) {
                this.categories.add(element.getAsString());
            }
        }

        captureUnknownProperties(json, "input", "display", "meta", "growthModifier", "categories");
    }

    @Override
    public void write(JsonObject json) {
        json.addProperty("input", this.inputItem);
        json.addProperty("display", this.displayBlock);
        json.addProperty("meta", this.displayMeta);
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

    @Override
    public boolean validate() {
        if (growthModifier < 0) {
            logValidationError("Growth modifier của " + inputItem + " không được âm!");
            return false;
        }
        return true;
    }
}
