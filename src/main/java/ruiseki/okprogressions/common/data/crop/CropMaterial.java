package ruiseki.okprogressions.common.data.crop;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.Level;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import ruiseki.okcore.OKCore;
import ruiseki.okcore.data.loader.recipes.AbstractRecipeMaterial;
import ruiseki.okcore.data.loader.recipes.IRecipeType;
import ruiseki.okcore.data.loader.recipes.RecipeData;
import ruiseki.okcore.json.AbstractJsonMaterial;
import ruiseki.okcore.json.block.BlockMaterial;
import ruiseki.okcore.json.item.ItemMaterial;

@RecipeData
public class CropMaterial extends AbstractRecipeMaterial<CropInfo> implements IRecipeType<CropInfo> {

    public static String CROP_KEY = "okprogressions:crop";

    private BlockMaterial displayBlock;
    private int growthTicks;
    private int lightLevel = -1;
    private List<String> categories = new ArrayList<>();
    private List<HarvestMaterial> results = new ArrayList<>();

    @Override
    public String getTypeKey() {
        return CROP_KEY;
    }

    @Override
    public void fromJson(ResourceLocation id, JsonObject json) {
        super.fromJson(id, json);
        if (json.has("seed")) {
            JsonElement resultElement = json.get("seed");
            if (resultElement.isJsonObject()) {
                JsonObject resultObj = resultElement.getAsJsonObject();
                this.result = new ItemMaterial();
                this.result.read(resultObj);
            }
        }

        if (json.has("display") && json.get("display")
            .isJsonObject()) {
            this.displayBlock = new BlockMaterial();
            this.displayBlock.read(json.getAsJsonObject("display"));
        }

        this.growthTicks = AbstractJsonMaterial.getInt(json, "growthTicks", 1200);

        this.lightLevel = AbstractJsonMaterial.getInt(json, "lightLevel", -1);

        this.categories.clear();
        if (json.has("categories") && json.get("categories")
            .isJsonArray()) {
            for (JsonElement el : json.getAsJsonArray("categories")) {
                if (el != null && el.isJsonPrimitive()
                    && el.getAsJsonPrimitive()
                        .isString()) {
                    this.categories.add(el.getAsString());
                }
            }
        }

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
    }

    @Override
    public List<CropInfo> getRecipes() {
        List<String> categories = new ArrayList<>(this.categories);
        List<HarvestInfo> results = new ArrayList<>();
        for (HarvestMaterial material : this.results) {
            if (!material.validate()) continue;
            results.add(material.toInfo());
        }

        CropInfo info = new CropInfo(
            this.id,
            this.result.toStack(),
            this.displayBlock.toStack(),
            this.growthTicks,
            this.lightLevel,
            categories,
            results);
        return List.of(info);
    }

    @Override
    public boolean validate() {
        if (!super.validate()) {
            return false;
        }

        if (this.displayBlock == null || !this.displayBlock.validate()) {
            OKCore.okLog("Crop display is missing or invalid!");
            return false;
        }

        if (this.results.isEmpty()) {
            OKCore.okLog(Level.ERROR, "CropMaterial [{}]: At least one harvest result is required!", id);
            return false;
        }

        for (HarvestMaterial material : this.results) {
            if (material == null || !material.validate()) {
                OKCore.okLog(Level.ERROR, "CropMaterial [{}]: Contains an invalid harvest result entry!", id);
                return false;
            }
        }

        if (this.growthTicks <= 0) {
            OKCore.okLog(
                Level.WARN,
                "CropMaterial [{}]: growthTicks is <= 0 ({}), forcing default to 1200.",
                id,
                this.growthTicks);
            return false;
        }

        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CropMaterial other)) return false;

        if (growthTicks != other.growthTicks) return false;
        if (lightLevel != other.lightLevel) return false;
        if (!Objects.equals(id, other.id)) return false;
        if (!Objects.equals(result, other.result)) return false;
        if (!Objects.equals(displayBlock, other.displayBlock)) return false;
        if (!Objects.equals(categories, other.categories)) return false;
        return Objects.equals(results, other.results);
    }

    @Override
    public int hashCode() {
        int hash = Objects.hashCode(id);
        hash = 31 * hash + Objects.hashCode(result);
        hash = 31 * hash + Objects.hashCode(displayBlock);
        hash = 31 * hash + growthTicks;
        hash = 31 * hash + lightLevel;
        hash = 31 * hash + Objects.hashCode(categories);
        hash = 31 * hash + Objects.hashCode(results);
        return hash;
    }

    @Override
    public String toString() {
        return "CropMaterial{" + "id="
            + id
            + ", result="
            + result
            + ", displayBlock="
            + displayBlock
            + ", growthTicks="
            + growthTicks
            + ", lightLevel="
            + lightLevel
            + ", categories="
            + categories
            + ", results="
            + results
            + '}';
    }

    @Override
    public boolean isForgeRecipe() {
        return true;
    }

    @Override
    public Class<? extends IRecipe> getRecipeClass() {
        return CropInfo.class;
    }
}
