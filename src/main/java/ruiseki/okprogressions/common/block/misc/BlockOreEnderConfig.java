package ruiseki.okprogressions.common.block.misc;

import ruiseki.okcore.config.extendedconfig.BlockConfig;
import ruiseki.okprogressions.OKProgressions;

public class BlockOreEnderConfig extends BlockConfig {

    /**
     * The unique instance.
     */
    public static BlockOreEnderConfig _instance;

    /**
     * Make a new instance.
     */
    public BlockOreEnderConfig() {
        super(OKProgressions._instance, true, "ender_ore", null, BlockOreEnder.class);
    }

    @Override
    public String getOreDictionaryId() {
        return "oreEnderOre";
    }
}
