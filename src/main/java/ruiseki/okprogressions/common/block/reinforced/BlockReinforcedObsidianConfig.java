package ruiseki.okprogressions.common.block.reinforced;

import ruiseki.okcore.config.extendedconfig.BlockConfig;
import ruiseki.okprogressions.OKProgressions;

public class BlockReinforcedObsidianConfig extends BlockConfig {

    /**
     * The unique instance.
     */
    public static BlockReinforcedObsidianConfig _instance;

    /**
     * Make a new instance.
     */
    public BlockReinforcedObsidianConfig() {
        super(OKProgressions._instance, true, "reinforced_obsidian", null, BlockReinforcedObsidian.class);
    }
}
