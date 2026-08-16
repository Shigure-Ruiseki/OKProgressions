package ruiseki.okprogressions.common.item.charm;

import ruiseki.okcore.config.extendedconfig.ItemConfig;
import ruiseki.okprogressions.OKProgressions;

public class ItemClimbingGloveConfig extends ItemConfig {

    /**
     * The unique instance.
     */
    public static ItemClimbingGloveConfig _instance;

    /**
     * Make a new instance.
     */
    public ItemClimbingGloveConfig() {
        super(OKProgressions._instance, true, "climbing_glove", null, ItemClimbingGlove.class);
    }
}
