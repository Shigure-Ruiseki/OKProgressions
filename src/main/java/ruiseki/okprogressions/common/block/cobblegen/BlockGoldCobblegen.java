package ruiseki.okprogressions.common.block.cobblegen;

import ruiseki.okcore.config.extendedconfig.BlockConfig;
import ruiseki.okcore.config.extendedconfig.ExtendedConfig;

public class BlockGoldCobblegen extends BlockCobblegenBase {

    private static BlockGoldCobblegen _instance = null;

    /**
     * Get the unique instance.
     *
     * @return The instance.
     */
    public static BlockGoldCobblegen getInstance() {
        return _instance;
    }

    public BlockGoldCobblegen(ExtendedConfig<BlockConfig> eConfig) {
        super(eConfig, BlockGoldCobblegenConfig.goldCobbleGenCycle, BlockGoldCobblegenConfig.goldCobbleGenStackSize);
    }
}
