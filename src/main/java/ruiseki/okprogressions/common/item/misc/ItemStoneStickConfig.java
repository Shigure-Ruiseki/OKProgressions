package ruiseki.okprogressions.common.item.misc;

import ruiseki.okcore.config.extendedconfig.ItemConfig;
import ruiseki.okcore.item.ItemBase;
import ruiseki.okprogressions.OKProgressions;

public class ItemStoneStickConfig extends ItemConfig {

    /**
     * The unique instance.
     */
    public static ItemStoneStickConfig _instance;

    /**
     * Make a new instance.
     */
    public ItemStoneStickConfig() {
        super(OKProgressions._instance, true, "stone_stick", null, config -> new ItemBase());
    }
}
