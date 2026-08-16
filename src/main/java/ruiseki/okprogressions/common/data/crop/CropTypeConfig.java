package ruiseki.okprogressions.common.data.crop;

import ruiseki.okcore.config.configurable.ConfigurableRecipeType;
import ruiseki.okcore.config.extendedconfig.ExtendedConfig;
import ruiseki.okcore.config.extendedconfig.RecipeTypeConfig;
import ruiseki.okprogressions.OKProgressions;

public class CropTypeConfig extends RecipeTypeConfig<CropInfo> {

    /**
     * The unique instance.
     */
    public static CropTypeConfig _instance;

    public CropTypeConfig() {
        super(OKProgressions._instance, true, "crop", null, CropType.class);
    }

    public static class CropType extends ConfigurableRecipeType<CropInfo> {

        private static CropType _instance = null;

        /**
         * Get the unique instance.
         *
         * @return The instance.
         */
        public static CropType getInstance() {
            return _instance;
        }

        public CropType(ExtendedConfig<RecipeTypeConfig<CropInfo>> eConfig) {
            super(eConfig);
        }
    }

}
