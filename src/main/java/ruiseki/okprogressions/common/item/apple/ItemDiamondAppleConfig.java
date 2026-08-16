package ruiseki.okprogressions.common.item.apple;

import ruiseki.okcore.config.extendedconfig.ItemConfig;
import ruiseki.okprogressions.OKProgressions;

public class ItemDiamondAppleConfig extends ItemConfig {

    /**
     * The unique instance.
     */
    public static ItemDiamondAppleConfig _instance;

    /**
     * Make a new instance.
     */
    public ItemDiamondAppleConfig() {
        super(OKProgressions._instance, true, "diamond_apple", null, ItemDiamondApple.class);
    }
}
