package ruiseki.okprogressions.common.data.soil;

import ruiseki.okcore.config.extendedconfig.RecipeTypeConfig;
import ruiseki.okcore.recipe.IRecipeType;
import ruiseki.okprogressions.OKProgressions;

public class SoilTypeConfig extends RecipeTypeConfig<SoilInfo> {

    /**
     * The unique instance.
     */
    public static SoilTypeConfig _instance;

    public SoilTypeConfig() {
        super(OKProgressions._instance, true, "soil", null, config -> new IRecipeType<SoilInfo>() {});
    }

}
