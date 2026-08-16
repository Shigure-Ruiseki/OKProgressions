package ruiseki.okprogressions.common.item.misc;

import ruiseki.okcore.config.extendedconfig.ItemConfig;
import ruiseki.okprogressions.OKProgressions;

public class ItemEnderSackConfig extends ItemConfig {

    /**
     * The unique instance.
     */
    public static ItemEnderSackConfig _instance;

    /**
     * Make a new instance.
     */
    public ItemEnderSackConfig() {
        super(OKProgressions._instance, true, "ender_sack", null, ItemEnderSack.class);
    }
}
