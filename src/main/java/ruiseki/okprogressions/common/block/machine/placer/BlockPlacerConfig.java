package ruiseki.okprogressions.common.block.machine.placer;

import ruiseki.okcore.config.extendedconfig.BlockContainerConfig;
import ruiseki.okprogressions.OKProgressions;

public class BlockPlacerConfig extends BlockContainerConfig {

    /**
     * The unique instance.
     */
    public static BlockPlacerConfig _instance;

    /**
     * Make a new instance.
     */
    public BlockPlacerConfig() {
        super(OKProgressions._instance, true, "block_placer", null, BlockPlacer.class);
    }
}
