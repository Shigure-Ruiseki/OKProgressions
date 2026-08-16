package ruiseki.okprogressions.common.item.misc;

import ruiseki.okcore.config.configurable.ConfigurableItem;
import ruiseki.okcore.config.configurable.IConfigurable;
import ruiseki.okcore.config.extendedconfig.ItemConfig;
import ruiseki.okprogressions.OKProgressions;

public class ItemTinyCoalConfig extends ItemConfig {

    /**
     * The unique instance.
     */
    public static ItemTinyCoalConfig _instance;

    /**
     * Make a new instance.
     */
    public ItemTinyCoalConfig() {
        super(OKProgressions._instance, true, "tiny_coal", null, null);
    }

    @Override
    protected IConfigurable<ItemConfig> initSubInstance() {
        return new ConfigurableItem(this);
    }
}
