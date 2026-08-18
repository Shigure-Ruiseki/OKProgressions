package ruiseki.okprogressions.common.item.apple;

import ruiseki.okcore.config.extendedconfig.ItemConfig;
import ruiseki.okprogressions.OKProgressions;

public class ItemIronAppleConfig extends ItemConfig {

    /**
     * The unique instance.
     */
    public static ItemIronAppleConfig _instance;

    /**
     * Make a new instance.
     */
    public ItemIronAppleConfig() {
        super(OKProgressions._instance, true, "iron_apple", null, config -> new ItemIronApple());
    }
}
