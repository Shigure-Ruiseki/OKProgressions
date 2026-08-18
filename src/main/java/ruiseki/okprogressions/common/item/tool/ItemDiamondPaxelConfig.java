package ruiseki.okprogressions.common.item.tool;

import ruiseki.okcore.config.extendedconfig.ItemConfig;
import ruiseki.okprogressions.OKProgressions;
import ruiseki.okprogressions.common.init.OKProgressionsMaterial;

public class ItemDiamondPaxelConfig extends ItemConfig {

    /**
     * The unique instance.
     */
    public static ItemDiamondPaxelConfig _instance;

    /**
     * Make a new instance.
     */
    public ItemDiamondPaxelConfig() {
        super(
            OKProgressions._instance,
            true,
            "diamond_paxel",
            null,
            config -> new ItemPaxel(OKProgressionsMaterial.PDIAMOND));
    }
}
