package ruiseki.okprogressions.common.item.tool;

import ruiseki.okcore.config.extendedconfig.ExtendedConfig;
import ruiseki.okcore.config.extendedconfig.ItemConfig;
import ruiseki.okprogressions.common.init.OKProgressionsMaterial;

public class ItemDiamondPaxel extends ItemPaxel {

    private static ItemDiamondPaxel _instance = null;

    /**
     * Get the unique instance.
     *
     * @return The instance.
     */
    public static ItemDiamondPaxel getInstance() {
        return _instance;
    }

    public ItemDiamondPaxel(ExtendedConfig<ItemConfig> eConfig) {
        super(eConfig, OKProgressionsMaterial.PDIAMOND);
    }
}
