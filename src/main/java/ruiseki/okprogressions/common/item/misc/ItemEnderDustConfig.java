package ruiseki.okprogressions.common.item.misc;

import ruiseki.okcore.config.extendedconfig.ItemConfig;
import ruiseki.okcore.item.ItemBase;
import ruiseki.okprogressions.OKProgressions;

public class ItemEnderDustConfig extends ItemConfig {

    /**
     * The unique instance.
     */
    public static ItemEnderDustConfig _instance;

    /**
     * Make a new instance.
     */
    public ItemEnderDustConfig() {
        super(OKProgressions._instance, true, "ender_dust", null, config -> new ItemBase());
    }
}
