package ruiseki.okprogressions.common.block.growth;

import ruiseki.okcore.config.extendedconfig.BlockConfig;
import ruiseki.okcore.config.extendedconfig.ExtendedConfig;

public class BlockGrowthTier2 extends BlockGrowthBase {

    private static BlockGrowthTier2 _instance = null;

    /**
     * Get the unique instance.
     *
     * @return The instance.
     */
    public static BlockGrowthTier2 getInstance() {
        return _instance;
    }

    public BlockGrowthTier2(ExtendedConfig<BlockConfig> eConfig) {
        super(
            eConfig,
            BlockGrowthTier2Config.growthIntervalTier2,
            BlockGrowthTier2Config.growthRadiusTier2,
            BlockGrowthTier2Config.growthHeightTier2);
    }
}
