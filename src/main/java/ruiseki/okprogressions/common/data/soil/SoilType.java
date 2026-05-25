package ruiseki.okprogressions.common.data.soil;

import ruiseki.okcore.recipe.IRecipeType;
import ruiseki.okcore.recipe.RecipeData;

@RecipeData
public class SoilType implements IRecipeType<SoilInfo> {

    public static String SOIL = "okprogressions:soil";

    @Override
    public String getTypeKey() {
        return SOIL;
    }
}
