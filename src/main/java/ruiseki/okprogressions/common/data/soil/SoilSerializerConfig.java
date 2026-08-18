package ruiseki.okprogressions.common.data.soil;

import ruiseki.okcore.config.extendedconfig.RecipeConfig;
import ruiseki.okprogressions.OKProgressions;

public class SoilSerializerConfig extends RecipeConfig<SoilInfo> {

    /**
     * The unique instance.
     */
    public static SoilSerializerConfig _instance;

    public SoilSerializerConfig() {
        super(OKProgressions._instance, true, "soil", null, config -> new SoilSerializer());
    }
}
