package ruiseki.okprogressions.common.block.growth;

import ruiseki.okcore.config.ConfigurableProperty;
import ruiseki.okcore.config.ConfigurableTypeCategory;
import ruiseki.okcore.config.extendedconfig.BlockContainerConfig;
import ruiseki.okprogressions.OKProgressions;

public class BlockGrowthTier1Config extends BlockContainerConfig {

    /**
     * The unique instance.
     */
    public static BlockGrowthTier1Config _instance;

    /**
     * The growth tick interval (in seconds).
     */
    @ConfigurableProperty(
        category = ConfigurableTypeCategory.MACHINE,
        comment = "Tick interval in seconds to attempt growth tick",
        minimalValue = 1)
    public static int growthIntervalTier1 = 1;

    /**
     * The radius in blocks affected by the growth accelerator.
     */
    @ConfigurableProperty(
        category = ConfigurableTypeCategory.MACHINE,
        comment = "Radius of affected crops/blocks",
        minimalValue = 1)
    public static int growthRadiusTier1 = 4;

    /**
     * The vertical range (up/down) in blocks affected.
     */
    @ConfigurableProperty(
        category = ConfigurableTypeCategory.MACHINE,
        comment = "Vertical height range (above and below) affected",
        minimalValue = 1)
    public static int growthHeightTier1 = 2;

    /**
     * Make a new instance.
     */
    public BlockGrowthTier1Config() {
        super(
            OKProgressions._instance,
            true,
            "growth",
            null,
            config -> new BlockGrowthBase(growthIntervalTier1, growthRadiusTier1, growthHeightTier1));
    }
}
