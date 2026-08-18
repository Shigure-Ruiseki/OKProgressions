package ruiseki.okprogressions.common.item.tool;

import ruiseki.okcore.config.extendedconfig.ItemConfig;
import ruiseki.okprogressions.OKProgressions;
import ruiseki.okprogressions.common.init.OKProgressionsMaterial;

public class ItemStonePaxelConfig extends ItemConfig {

    /**
     * The unique instance.
     */
    public static ItemStonePaxelConfig _instance;

    /**
     * Make a new instance.
     */
    public ItemStonePaxelConfig() {
        super(
            OKProgressions._instance,
            true,
            "stone_paxel",
            null,
            config -> new ItemPaxel(OKProgressionsMaterial.PSTONE));
    }
}
