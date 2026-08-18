package ruiseki.okprogressions.common.block.machine.miner;

import ruiseki.okcore.config.extendedconfig.BlockContainerConfig;
import ruiseki.okprogressions.OKProgressions;

public class BlockMinerConfig extends BlockContainerConfig {

    /**
     * The unique instance.
     */
    public static BlockMinerConfig _instance;

    /**
     * Make a new instance.
     */
    public BlockMinerConfig() {
        super(OKProgressions._instance, true, "block_miner", null, config -> new BlockMiner());
    }
}
