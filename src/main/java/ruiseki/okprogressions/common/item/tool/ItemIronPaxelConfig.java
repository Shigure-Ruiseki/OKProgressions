package ruiseki.okprogressions.common.item.tool;

import ruiseki.okcore.config.extendedconfig.ItemConfig;
import ruiseki.okprogressions.OKProgressions;

public class ItemIronPaxelConfig extends ItemConfig {

    /**
     * The unique instance.
     */
    public static ItemIronPaxelConfig _instance;

    /**
     * Make a new instance.
     */
    public ItemIronPaxelConfig() {
        super(OKProgressions._instance, true, "iron_paxel", null, ItemIronPaxel.class);
    }
}
