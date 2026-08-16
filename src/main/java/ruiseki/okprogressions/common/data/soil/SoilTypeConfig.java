package ruiseki.okprogressions.common.data.soil;

import ruiseki.okcore.config.configurable.ConfigurableRecipeType;
import ruiseki.okcore.config.extendedconfig.ExtendedConfig;
import ruiseki.okcore.config.extendedconfig.RecipeTypeConfig;
import ruiseki.okprogressions.OKProgressions;

public class SoilTypeConfig extends RecipeTypeConfig<SoilInfo> {

    /**
     * The unique instance.
     */
    public static SoilTypeConfig _instance;

    public SoilTypeConfig() {
        super(OKProgressions._instance, true, "soil", null, SoilType.class);
    }

    public static class SoilType extends ConfigurableRecipeType<SoilInfo> {

        private static SoilType _instance = null;

        /**
         * Get the unique instance.
         *
         * @return The instance.
         */
        public static SoilType getInstance() {
            return _instance;
        }

        public SoilType(ExtendedConfig<RecipeTypeConfig<SoilInfo>> eConfig) {
            super(eConfig);
        }
    }

}
