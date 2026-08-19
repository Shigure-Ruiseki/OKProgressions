package ruiseki.okprogressions.common.item.misc;

import ruiseki.okcore.config.extendedconfig.ItemConfig;
import ruiseki.okcore.item.ItemBase;
import ruiseki.okprogressions.OKProgressions;

public class ItemTinyCharcoalConfig extends ItemConfig {

    /**
     * The unique instance.
     */
    public static ItemTinyCharcoalConfig _instance;

    /**
     * Make a new instance.
     */
    public ItemTinyCharcoalConfig() {
        super(OKProgressions._instance, true, "tiny_charcoal", null, config -> new ItemBase());
    }

}
