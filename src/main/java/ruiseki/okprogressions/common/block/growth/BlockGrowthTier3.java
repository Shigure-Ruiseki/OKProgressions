package ruiseki.okprogressions.common.block.growth;

import ruiseki.okcore.config.extendedconfig.BlockConfig;
import ruiseki.okcore.config.extendedconfig.ExtendedConfig;

public class BlockGrowthTier3 extends BlockGrowthBase {

    private static BlockGrowthTier3 _instance = null;

    /**
     * Get the unique instance.
     *
     * @return The instance.
     */
    public static BlockGrowthTier3 getInstance() {
        return _instance;
    }

    public BlockGrowthTier3(ExtendedConfig<BlockConfig> eConfig) {
        super(
            eConfig,
            BlockGrowthTier3Config.growthIntervalTier3,
            BlockGrowthTier3Config.growthRadiusTier3,
            BlockGrowthTier3Config.growthHeightTier3);
    }
}
