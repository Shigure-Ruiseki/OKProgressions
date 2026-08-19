package ruiseki.okprogressions.common.data.crop;

import ruiseki.okcore.config.extendedconfig.RecipeTypeConfig;
import ruiseki.okcore.recipe.IRecipeType;
import ruiseki.okprogressions.OKProgressions;

public class CropTypeConfig extends RecipeTypeConfig<CropInfo> {

    /**
     * The unique instance.
     */
    public static CropTypeConfig _instance;

    public CropTypeConfig() {
        super(OKProgressions._instance, true, "crop", null, config -> new IRecipeType<CropInfo>() {});
    }
}
