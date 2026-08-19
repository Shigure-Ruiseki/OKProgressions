package ruiseki.okprogressions.common.item.tool;

import ruiseki.okcore.config.extendedconfig.ItemConfig;
import ruiseki.okprogressions.OKProgressions;
import ruiseki.okprogressions.common.init.OKProgressionsMaterial;

public class ItemGoldenPaxelConfig extends ItemConfig {

    /**
     * The unique instance.
     */
    public static ItemGoldenPaxelConfig _instance;

    /**
     * Make a new instance.
     */
    public ItemGoldenPaxelConfig() {
        super(
            OKProgressions._instance,
            true,
            "golden_paxel",
            null,
            config -> new ItemPaxel(OKProgressionsMaterial.PGOLD));
    }
}
