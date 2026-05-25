package ruiseki.okprogressions.common.data.crop;

import ruiseki.okcore.recipe.IRecipeType;
import ruiseki.okcore.recipe.RecipeData;

@RecipeData
public class CropType implements IRecipeType<CropInfo> {

    public static final String CROP = "okprogressions:crop";

    @Override
    public String getTypeKey() {
        return CROP;
    }
}
