package ruiseki.okprogressions.common.item.tool;

import ruiseki.okcore.config.extendedconfig.ExtendedConfig;
import ruiseki.okcore.config.extendedconfig.ItemConfig;
import ruiseki.okprogressions.common.init.OKProgressionsMaterial;

public class ItemIronPaxel extends ItemPaxel {

    private static ItemIronPaxel _instance = null;

    /**
     * Get the unique instance.
     *
     * @return The instance.
     */
    public static ItemIronPaxel getInstance() {
        return _instance;
    }

    public ItemIronPaxel(ExtendedConfig<ItemConfig> eConfig) {
        super(eConfig, OKProgressionsMaterial.PIRON);
    }
}
