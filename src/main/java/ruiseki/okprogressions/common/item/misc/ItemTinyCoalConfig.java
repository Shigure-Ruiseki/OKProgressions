package ruiseki.okprogressions.common.item.misc;

import ruiseki.okcore.config.extendedconfig.ItemConfig;
import ruiseki.okcore.item.ItemBase;
import ruiseki.okprogressions.OKProgressions;

public class ItemTinyCoalConfig extends ItemConfig {

    /**
     * The unique instance.
     */
    public static ItemTinyCoalConfig _instance;

    /**
     * Make a new instance.
     */
    public ItemTinyCoalConfig() {
        super(OKProgressions._instance, true, "tiny_coal", null, config -> new ItemBase());
    }
}
