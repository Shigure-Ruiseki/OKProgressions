package ruiseki.okprogressions.common.block.cobblegen;

import ruiseki.okcore.config.extendedconfig.BlockConfig;
import ruiseki.okcore.config.extendedconfig.ExtendedConfig;

public class BlockDiamondCobblegen extends BlockCobblegenBase {

    private static BlockDiamondCobblegen _instance = null;

    /**
     * Get the unique instance.
     *
     * @return The instance.
     */
    public static BlockDiamondCobblegen getInstance() {
        return _instance;
    }

    public BlockDiamondCobblegen(ExtendedConfig<BlockConfig> eConfig) {
        super(
            eConfig,
            BlockDiamondCobblegenConfig.diamondCobbleGenCycle,
            BlockDiamondCobblegenConfig.diamondCobbleGenStackSize);
    }
}
