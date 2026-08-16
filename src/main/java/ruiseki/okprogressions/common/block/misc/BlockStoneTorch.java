package ruiseki.okprogressions.common.block.misc;

import ruiseki.okcore.config.configurable.ConfigurableBlockTorch;
import ruiseki.okcore.config.extendedconfig.BlockConfig;
import ruiseki.okcore.config.extendedconfig.ExtendedConfig;

public class BlockStoneTorch extends ConfigurableBlockTorch {

    private static BlockStoneTorch _instance = null;

    /**
     * Get the unique instance.
     *
     * @return The instance.
     */
    public static BlockStoneTorch getInstance() {
        return _instance;
    }

    public BlockStoneTorch(ExtendedConfig<BlockConfig> eConfig) {
        super(eConfig);
    }

}
