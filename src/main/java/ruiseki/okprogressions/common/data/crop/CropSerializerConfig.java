package ruiseki.okprogressions.common.data.crop;

import ruiseki.okcore.config.extendedconfig.RecipeConfig;
import ruiseki.okprogressions.OKProgressions;

public class CropSerializerConfig extends RecipeConfig<CropInfo> {

    /**
     * The unique instance.
     */
    public static CropSerializerConfig _instance;

    public CropSerializerConfig() {
        super(OKProgressions._instance, true, "crop", null, CropSerializer.class);
    }
}
