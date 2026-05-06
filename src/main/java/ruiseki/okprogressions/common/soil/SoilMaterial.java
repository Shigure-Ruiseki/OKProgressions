package ruiseki.okprogressions.common.soil;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import ruiseki.okcore.json.AbstractJsonMaterial;

public class SoilMaterial extends AbstractJsonMaterial {

    public String inputItem;
    public float growthModifier;
    public List<String> categories = new ArrayList<>();

    @Override
    public void read(JsonObject json) {
        this.inputItem = getString(json, "input", "minecraft:dirt");
        this.growthModifier = getFloat(json, "growthModifier", 1.0f);

        this.categories.clear();
        if (json.has("categories") && json.get("categories")
            .isJsonArray()) {
            JsonArray array = json.getAsJsonArray("categories");
            for (JsonElement element : array) {
                this.categories.add(element.getAsString());
            }
        }

        captureUnknownProperties(json, "input", "growthModifier", "categories");
    }

    @Override
    public void write(JsonObject json) {
        json.addProperty("input", this.inputItem);
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
