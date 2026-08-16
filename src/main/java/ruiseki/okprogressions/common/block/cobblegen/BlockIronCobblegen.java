package ruiseki.okprogressions.common.block.cobblegen;

import ruiseki.okcore.config.extendedconfig.BlockConfig;
import ruiseki.okcore.config.extendedconfig.ExtendedConfig;

public class BlockIronCobblegen extends BlockCobblegenBase {

    private static BlockIronCobblegen _instance = null;

    /**
     * Get the unique instance.
     *
     * @return The instance.
     */
    public static BlockIronCobblegen getInstance() {
        return _instance;
    }

    public BlockIronCobblegen(ExtendedConfig<BlockConfig> eConfig) {
        super(eConfig, BlockIronCobblegenConfig.ironCobbleGenCycle, BlockIronCobblegenConfig.ironCobbleGenStackSize);
    }
}
