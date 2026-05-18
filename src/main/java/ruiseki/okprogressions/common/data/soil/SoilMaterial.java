package ruiseki.okprogressions.common.data.soil;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

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
public class SoilMaterial extends AbstractRecipeMaterial<SoilInfo> implements IRecipeType<SoilInfo> {

    public static String SOIL_KEY = "okprogressions:soil";

    private ResourceLocation id;
    private BlockMaterial displayBlock;
    private float growthModifier;
    private List<String> categories = new ArrayList<>();

    @Override
    public String getTypeKey() {
        return SOIL_KEY;
    }

    @Override
    public void fromJson(ResourceLocation id, JsonObject json) {
        this.id = id;
        if (json.has("input")) {
            JsonElement resultElement = json.get("input");
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

        this.growthModifier = AbstractJsonMaterial.getFloat(json, "growthModifier", 0.0f);

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
    }

    @Override
    public boolean validate() {
        if (this.displayBlock == null || !this.displayBlock.validate()) {
            OKCore.okLog("Soil display is missing or invalid!");
            return false;
        }

        if (this.growthModifier < -1.0f) {
            OKCore.okLog("Growth modifier cannot be less than -1.0!");
            return false;
        }
        return super.validate();
    }

    @Override
    public List<SoilInfo> getRecipes() {
        List<String> categories = new ArrayList<>(this.categories);
        SoilInfo info = new SoilInfo(
            this.id,
            this.result.toStack(),
            this.displayBlock.toStack(),
            this.growthModifier,
            categories);
        return List.of(info);
    }

    public BlockMaterial getBlock() {
        return displayBlock;
    }

    public float getGrowthModifier() {
        return growthModifier;
    }

    public List<String> getCategories() {
        return categories;
    }

    @Override
    public boolean isForgeRecipe() {
        return true;
    }

    @Override
    public Class<? extends IRecipe> getRecipeClass() {
        return SoilInfo.class;
    }
}
