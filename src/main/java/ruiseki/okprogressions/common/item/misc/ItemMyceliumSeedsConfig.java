package ruiseki.okprogressions.common.item.misc;

import ruiseki.okcore.config.extendedconfig.ItemConfig;
import ruiseki.okprogressions.OKProgressions;

public class ItemMyceliumSeedsConfig extends ItemConfig {

    /**
     * The unique instance.
     */
    public static ItemMyceliumSeedsConfig _instance;

    /**
     * Make a new instance.
     */
    public ItemMyceliumSeedsConfig() {
        super(OKProgressions._instance, true, "mycelium_seeds", null, ItemMyceliumSeeds.class);
    }
}
