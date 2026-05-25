package ruiseki.okprogressions.common.data.crop;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import ruiseki.okcore.json.AbstractJsonMaterial;
import ruiseki.okcore.json.item.ItemMaterial;

public class HarvestMaterial extends AbstractJsonMaterial {

    public ItemMaterial stack;
    public float chance;
    public int minRolls;
    public int maxRolls;

    public HarvestMaterial() {}

    @Override
    public void read(JsonObject json) {
        if (json.has("output")) {
            JsonElement resultElement = json.get("output");
            if (resultElement.isJsonObject()) {
                JsonObject resultObj = resultElement.getAsJsonObject();
                this.stack = new ItemMaterial();
                this.stack.read(resultObj);
            }
        }

        this.chance = getFloat(json, "chance", 1.0f);

        this.minRolls = getInt(json, "minRolls", 1);

        this.maxRolls = getInt(json, "maxRolls", 1);

        captureUnknownProperties(json, "output", "chance", "minRolls", "maxRolls");
    }

    @Override
    public void write(JsonObject json) {
        if (this.stack != null) {
            JsonObject stackObj = new JsonObject();
            this.stack.write(stackObj);
            json.add("stack", stackObj);
        }
        json.addProperty("chance", this.chance);
        json.addProperty("minRolls", this.minRolls);
        json.addProperty("maxRolls", this.maxRolls);

        writeUnknownProperties(json);
    }

    @Override
    public boolean validate() {
        if (minRolls < 0 || maxRolls < 0) return false;
        return minRolls <= maxRolls;
    }

    public HarvestInfo toInfo() {
        return new HarvestInfo(this.stack.toStack(), this.chance, this.minRolls, this.maxRolls);
    }
}
