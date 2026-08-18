package ruiseki.okprogressions.common.block.compressed;

import ruiseki.okcore.config.extendedconfig.BlockConfig;
import ruiseki.okprogressions.OKProgressions;

public class BlockNetherStarConfig extends BlockConfig {

    /**
     * The unique instance.
     */
    public static BlockNetherStarConfig _instance;

    /**
     * Make a new instance.
     */
    public BlockNetherStarConfig() {
        super(OKProgressions._instance, true, "nether_star_block", null, config -> new BlockNetherStar());
    }
}
