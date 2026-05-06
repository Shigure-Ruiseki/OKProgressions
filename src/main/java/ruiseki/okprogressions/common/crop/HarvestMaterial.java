package ruiseki.okprogressions.common.crop;

import net.minecraft.item.ItemStack;

import com.google.gson.JsonObject;

import ruiseki.okcore.json.AbstractJsonMaterial;
import ruiseki.okcore.json.ItemJson;

public class HarvestMaterial extends AbstractJsonMaterial {

    public ItemStack stack;
    public float chance;
    public int minRolls;
    public int maxRolls;

    public HarvestMaterial() {}

    public static HarvestMaterial createMaterial(ItemStack stack, float chance, int minRolls, int maxRolls) {
        HarvestMaterial material = new HarvestMaterial();
        material.stack = stack;
        material.chance = chance;
        material.minRolls = minRolls;
        material.maxRolls = maxRolls;
        return material;
    }

    @Override
    public void read(JsonObject json) {
        ItemJson itemJson = ItemJson.parseItemString(getString(json, "stack", "minecraft:dirt,1,0"));
        this.stack = ItemJson.resolveItemStack(itemJson);

        this.chance = getFloat(json, "chance", 1.0f);

        this.minRolls = getInt(json, "minRolls", 1);

        this.maxRolls = getInt(json, "maxRolls", 1);

        captureUnknownProperties(json, "stack", "chance", "minRolls", "maxRolls");
    }

    @Override
    public void write(JsonObject json) {
        json.addProperty("stack", ItemJson.toItemString(stack));
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
}
