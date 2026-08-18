package ruiseki.okprogressions.common.data.crop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.gtnewhorizon.gtnhlib.blockstate.core.BlockState;

import ruiseki.okcore.helper.BlockStateHelpers;
import ruiseki.okcore.helper.GsonHelpers;
import ruiseki.okcore.network.ExtendedBuffer;
import ruiseki.okcore.recipe.IRecipeSerializer;
import ruiseki.okcore.recipe.ingredient.Ingredient;
import ruiseki.okprogressions.OKProgressions;

public class CropSerializer implements IRecipeSerializer<CropInfo> {

    @Override
    public CropInfo fromJson(ResourceLocation id, JsonObject json) {

        final Ingredient seed = Ingredient.fromJson(json.getAsJsonObject("seed"));
        final Set<String> validSoils = deserializeSoilInfo(id, json);
        final int growthTicks = GsonHelpers.getAsInt(json, "growthTicks");
        final List<HarvestEntry> results = deserializeCropEntries(id, json);
        final int lightLevel = GsonHelpers.getAsInt(json, "lightLevel", -1);
        final BlockState state = BlockStateHelpers.fromJson(json, "display");

        if (growthTicks <= 0) {
            throw new IllegalArgumentException(
                "Crop " + id + " has an invalid growth tick rate. It must use a positive integer.");
        }

        return new CropInfo(id, seed, validSoils, growthTicks, results, state, lightLevel);
    }

    @Override
    public @Nullable CropInfo fromNetwork(ResourceLocation id, ExtendedBuffer buf) throws IOException {
        try {
            final Ingredient seed = Ingredient.fromNetwork(buf);
            Set<String> validSoils = new HashSet<>();
            buf.readStringCollection(validSoils);
            final int growthTicks = buf.readInt();
            final List<HarvestEntry> results = new ArrayList<>();

            final int length = buf.readInt();

            for (int i = 0; i < length; i++) {
                results.add(HarvestEntry.deserialize(buf));
            }

            BlockState state = buf.readBlockState();

            final int lightLevel = buf.readVarIntFromBuffer();

            return new CropInfo(id, seed, validSoils, growthTicks, results, state, lightLevel);
        } catch (final Exception e) {
            throw new IllegalStateException("Failed to read crop info from packet buffer.", e);
        }
    }

    @Override
    public void toNetwork(ExtendedBuffer buffer, CropInfo info) throws IOException {

        try {
            info.getSeed()
                .toNetwork(buffer);
            buffer.writeStringCollection(info.getSoilCategories());
            buffer.writeInt(info.getGrowthTicks());
            buffer.writeInt(
                info.getResults()
                    .size());
            for (final HarvestEntry entry : info.getResults()) {
                HarvestEntry.serialize(buffer, entry);
            }

            buffer.writeBlockState(info.getDisplayState());

            buffer.writeVarIntToBuffer(info.getLightLevel());
        } catch (final Exception e) {
            throw new IllegalStateException("Failed to write crop to the packet buffer.", e);
        }
    }

    /**
     * A helper method to deserialize soil categories from an array.
     *
     * @param ownerId The Id of the SoilInfo currently being deserialized.
     * @param json    The JsonObject to read from.
     * @return A set of soil categories.
     */
    private static Set<String> deserializeSoilInfo(ResourceLocation ownerId, JsonObject json) {

        final Set<String> categories = new HashSet<>();

        if (json.has("categories") && json.get("categories")
            .isJsonArray()) {
            for (final JsonElement element : json.getAsJsonArray("categories")) {
                categories.add(
                    element.getAsString()
                        .toLowerCase());
            }
        }

        return categories;
    }

    /**
     * A helper method for reading crop harvest entries.
     *
     * @param ownerId The id of the CropInfo being deserialized.
     * @param json    The json data to read from.
     * @return A list of crop harvest entries.
     */
    private static List<HarvestEntry> deserializeCropEntries(ResourceLocation ownerId, JsonObject json) {
        final List<HarvestEntry> crops = new ArrayList<>();
        if (!json.has("results")) {
            OKProgressions
                .okLog(Level.ERROR, "The crop {} has no results array. This means it won't drop anything!", ownerId);
            return crops;
        }

        for (final JsonElement entry : json.getAsJsonArray("results")) {
            if (!entry.isJsonObject()) {
                OKProgressions.okLog(Level.ERROR, "Crop entry in {} is not a JsonObject.", ownerId);
            } else {
                final HarvestEntry cropEntry = HarvestEntry.deserialize(entry.getAsJsonObject());
                crops.add(cropEntry);
            }
        }

        return crops;
    }
}
