package ruiseki.okprogressions.common.block.cobblegen;

import ruiseki.okcore.config.extendedconfig.BlockConfig;
import ruiseki.okcore.config.extendedconfig.ExtendedConfig;

public class BlockCobblegen extends BlockCobblegenBase {

    private static BlockCobblegen _instance = null;

    /**
     * Get the unique instance.
     *
     * @return The instance.
     */
    public static BlockCobblegen getInstance() {
        return _instance;
    }

    public BlockCobblegen(ExtendedConfig<BlockConfig> eConfig) {
        super(eConfig, BlockCobblegenConfig.cobbleGenCycle, BlockCobblegenConfig.cobbleGenStackSize);
    }
}
