package ruiseki.okprogressions.common.block.cobblegen;

import ruiseki.okcore.config.extendedconfig.BlockConfig;
import ruiseki.okcore.config.extendedconfig.ExtendedConfig;

public class BlockEmeraldCobblegen extends BlockCobblegenBase {

    private static BlockEmeraldCobblegen _instance = null;

    /**
     * Get the unique instance.
     *
     * @return The instance.
     */
    public static BlockEmeraldCobblegen getInstance() {
        return _instance;
    }

    public BlockEmeraldCobblegen(ExtendedConfig<BlockConfig> eConfig) {
        super(
            eConfig,
            BlockEmeraldCobblegenConfig.emeraldCobbleGenCycle,
            BlockEmeraldCobblegenConfig.emeraldCobbleGenStackSize);
    }
}
