package ruiseki.okprogressions.common.item.tool;

import ruiseki.okcore.config.extendedconfig.ItemConfig;
import ruiseki.okprogressions.OKProgressions;

public class ItemBirthdayPickaxeConfig extends ItemConfig {

    /**
     * The unique instance.
     */
    public static ItemBirthdayPickaxeConfig _instance;

    /**
     * Make a new instance.
     */
    public ItemBirthdayPickaxeConfig() {
        super(OKProgressions._instance, true, "birthday_pickaxe", null, config -> new ItemBirthdayPickaxe());
    }
}
