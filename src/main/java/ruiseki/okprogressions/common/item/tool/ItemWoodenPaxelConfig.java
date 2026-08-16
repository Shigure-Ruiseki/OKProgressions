package ruiseki.okprogressions.common.item.tool;

import ruiseki.okcore.config.extendedconfig.ItemConfig;
import ruiseki.okprogressions.OKProgressions;

public class ItemWoodenPaxelConfig extends ItemConfig {

    /**
     * The unique instance.
     */
    public static ItemWoodenPaxelConfig _instance;

    /**
     * Make a new instance.
     */
    public ItemWoodenPaxelConfig() {
        super(OKProgressions._instance, true, "wooden_paxel", null, ItemWoodenPaxel.class);
    }
}
