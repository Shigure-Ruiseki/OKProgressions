package ruiseki.okprogressions.common.item.tool;

import ruiseki.okcore.config.extendedconfig.ExtendedConfig;
import ruiseki.okcore.config.extendedconfig.ItemConfig;
import ruiseki.okprogressions.common.init.OKProgressionsMaterial;

public class ItemWoodenPaxel extends ItemPaxel {

    private static ItemWoodenPaxel _instance = null;

    /**
     * Get the unique instance.
     *
     * @return The instance.
     */
    public static ItemWoodenPaxel getInstance() {
        return _instance;
    }

    public ItemWoodenPaxel(ExtendedConfig<ItemConfig> eConfig) {
        super(eConfig, OKProgressionsMaterial.PWOOD);
    }
}
