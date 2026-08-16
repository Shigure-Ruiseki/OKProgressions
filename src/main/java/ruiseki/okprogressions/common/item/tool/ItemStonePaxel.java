package ruiseki.okprogressions.common.item.tool;

import ruiseki.okcore.config.extendedconfig.ExtendedConfig;
import ruiseki.okcore.config.extendedconfig.ItemConfig;
import ruiseki.okprogressions.common.init.OKProgressionsMaterial;

public class ItemStonePaxel extends ItemPaxel {

    private static ItemStonePaxel _instance = null;

    /**
     * Get the unique instance.
     *
     * @return The instance.
     */
    public static ItemStonePaxel getInstance() {
        return _instance;
    }

    public ItemStonePaxel(ExtendedConfig<ItemConfig> eConfig) {
        super(eConfig, OKProgressionsMaterial.PSTONE);
    }
}
