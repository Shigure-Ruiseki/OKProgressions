package ruiseki.okprogressions.common.data.crop;

import java.io.IOException;

import net.minecraft.item.ItemStack;

import com.google.gson.JsonObject;

import ruiseki.okcore.helper.GsonHelpers;
import ruiseki.okcore.network.ExtendedBuffer;
import ruiseki.okcore.recipe.type.crafting.shaped.ShapedRecipe;

public class HarvestEntry {

    /**
     * The chance that the entry should happen.
     */
    private final float chance;

    /**
     * The item to give.
     */
    private final ItemStack item;

    /**
     * The lowest amount of the item to give.
     */
    private final int minRolls;

    /**
     * The maximum amount of the item to give.
     */
    private final int maxRolls;

    public HarvestEntry(float chance, ItemStack item, int minRolls, int maxRolls) {

        this.chance = chance;
        this.item = item;
        this.minRolls = minRolls;
        this.maxRolls = maxRolls;

        if (minRolls < 0 || maxRolls < 0) {

            throw new IllegalArgumentException("Rolls must not be negative!");
        }

        if (minRolls > maxRolls) {

            throw new IllegalArgumentException("Min rolls must not be greater than max rolls!");
        }
    }

    /**
     * Gets the chance for the entry to happen.
     *
     * @return The chance for the entry to happen.
     */
    public float getChance() {

        return this.chance;
    }

    /**
     * Gets the item to give from this entry.
     *
     * @return The item to give.
     */
    public ItemStack getItem() {

        return this.item;
    }

    /**
     * Gets the minimum amount of items to give.
     *
     * @return The minimum amount of items to give.
     */
    public int getMinRolls() {

        return this.minRolls;
    }

    /**
     * Gets the maximum amount of items to give.
     *
     * @return The maximum amount of items to give.
     */
    public int getMaxRolls() {

        return this.maxRolls;
    }

    /**
     * Deserializes a harvest entry from a Json Object.
     *
     * @param json The Json object to read from.
     * @return The deserialized harvest entry.
     */
    public static HarvestEntry deserialize(JsonObject json) {

        final float chance = GsonHelpers.getAsFloat(json, "chance");
        final ItemStack item = ShapedRecipe.itemFromJson(json.getAsJsonObject("output"));
        final int minRolls = GsonHelpers.getAsInt(json, "minRolls");
        final int maxRolls = GsonHelpers.getAsInt(json, "maxRolls");

        return new HarvestEntry(chance, item, minRolls, maxRolls);
    }

    public static HarvestEntry deserialize(ExtendedBuffer buf) throws IOException {

        final float chance = buf.readFloat();
        final ItemStack item = buf.readItemStackFromBuffer();
        final int min = buf.readInt();
        final int max = buf.readInt();

        return new HarvestEntry(chance, item, min, max);
    }

    public static void serialize(ExtendedBuffer buffer, HarvestEntry info) throws IOException {
        buffer.writeFloat(info.getChance());
        buffer.writeItemStackToBuffer(info.getItem());
        buffer.writeInt(info.getMinRolls());
        buffer.writeInt(info.getMaxRolls());
    }
}
