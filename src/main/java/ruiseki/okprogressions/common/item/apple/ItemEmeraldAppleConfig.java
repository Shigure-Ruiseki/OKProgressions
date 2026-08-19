package ruiseki.okprogressions.common.item.apple;

import ruiseki.okcore.config.extendedconfig.ItemConfig;
import ruiseki.okprogressions.OKProgressions;

public class ItemEmeraldAppleConfig extends ItemConfig {

    /**
     * The unique instance.
     */
    public static ItemEmeraldAppleConfig _instance;

    /**
     * Make a new instance.
     */
    public ItemEmeraldAppleConfig() {
        super(OKProgressions._instance, true, "emerald_apple", null, config -> new ItemEmeraldApple());
    }
}
