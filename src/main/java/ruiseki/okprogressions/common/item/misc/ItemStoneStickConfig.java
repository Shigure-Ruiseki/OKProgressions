package ruiseki.okprogressions.common.item.misc;

import ruiseki.okcore.config.configurable.ConfigurableItem;
import ruiseki.okcore.config.configurable.IConfigurable;
import ruiseki.okcore.config.extendedconfig.ItemConfig;
import ruiseki.okprogressions.OKProgressions;

public class ItemStoneStickConfig extends ItemConfig {

    /**
     * The unique instance.
     */
    public static ItemStoneStickConfig _instance;

    /**
     * Make a new instance.
     */
    public ItemStoneStickConfig() {
        super(OKProgressions._instance, true, "stone_stick", null, null);
    }

    @Override
    protected IConfigurable<ItemConfig> initSubInstance() {
        return new ConfigurableItem(this);
    }
}
