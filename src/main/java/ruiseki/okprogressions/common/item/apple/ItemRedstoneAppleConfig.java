package ruiseki.okprogressions.common.item.apple;

import ruiseki.okcore.config.extendedconfig.ItemConfig;
import ruiseki.okprogressions.OKProgressions;

public class ItemRedstoneAppleConfig extends ItemConfig {

    /**
     * The unique instance.
     */
    public static ItemRedstoneAppleConfig _instance;

    /**
     * Make a new instance.
     */
    public ItemRedstoneAppleConfig() {
        super(OKProgressions._instance, true, "redstone_apple", null, ItemRedstoneApple.class);
    }
}
