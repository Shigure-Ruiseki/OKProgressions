package ruiseki.okprogressions.common.item.tool;

import ruiseki.okcore.config.extendedconfig.ItemConfig;
import ruiseki.okprogressions.OKProgressions;
import ruiseki.okprogressions.common.init.OKProgressionsMaterial;

public class ItemIronPaxelConfig extends ItemConfig {

    /**
     * The unique instance.
     */
    public static ItemIronPaxelConfig _instance;

    /**
     * Make a new instance.
     */
    public ItemIronPaxelConfig() {
        super(
            OKProgressions._instance,
            true,
            "iron_paxel",
            null,
            config -> new ItemPaxel(OKProgressionsMaterial.PIRON));
    }
}
