package ruiseki.okprogressions.common.block.growth;

import ruiseki.okcore.config.extendedconfig.BlockConfig;
import ruiseki.okcore.config.extendedconfig.ExtendedConfig;

public class BlockGrowthTier1 extends BlockGrowthBase {

    private static BlockGrowthTier1 _instance = null;

    /**
     * Get the unique instance.
     *
     * @return The instance.
     */
    public static BlockGrowthTier1 getInstance() {
        return _instance;
    }

    public BlockGrowthTier1(ExtendedConfig<BlockConfig> eConfig) {
        super(
            eConfig,
            BlockGrowthTier1Config.growthIntervalTier1,
            BlockGrowthTier1Config.growthRadiusTier1,
            BlockGrowthTier1Config.growthHeightTier1);
    }
}
