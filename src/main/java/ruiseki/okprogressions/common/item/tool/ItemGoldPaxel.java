package ruiseki.okprogressions.common.item.tool;

import ruiseki.okcore.config.extendedconfig.ExtendedConfig;
import ruiseki.okcore.config.extendedconfig.ItemConfig;
import ruiseki.okprogressions.common.init.OKProgressionsMaterial;

public class ItemGoldPaxel extends ItemPaxel {

    private static ItemGoldPaxel _instance = null;

    /**
     * Get the unique instance.
     *
     * @return The instance.
     */
    public static ItemGoldPaxel getInstance() {
        return _instance;
    }

    public ItemGoldPaxel(ExtendedConfig<ItemConfig> eConfig) {
        super(eConfig, OKProgressionsMaterial.PGOLD);
    }
}
