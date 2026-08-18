package ruiseki.okprogressions.common.block.growth;

import ruiseki.okcore.config.ConfigurableProperty;
import ruiseki.okcore.config.ConfigurableTypeCategory;
import ruiseki.okcore.config.extendedconfig.BlockContainerConfig;
import ruiseki.okprogressions.OKProgressions;

public class BlockGrowthTier2Config extends BlockContainerConfig {

    /**
     * The unique instance.
     */
    public static BlockGrowthTier2Config _instance;

    /**
     * The growth tick interval (in seconds).
     */
    @ConfigurableProperty(
        category = ConfigurableTypeCategory.MACHINE,
        comment = "Tick interval in seconds to attempt growth tick",
        minimalValue = 1)
    public static int growthIntervalTier2 = 2;

    /**
     * The radius in blocks affected by the growth accelerator.
     */
    @ConfigurableProperty(
        category = ConfigurableTypeCategory.MACHINE,
        comment = "Radius of affected crops/blocks",
        minimalValue = 1)
    public static int growthRadiusTier2 = 6;

    /**
     * The vertical range (up/down) in blocks affected.
     */
    @ConfigurableProperty(
        category = ConfigurableTypeCategory.MACHINE,
        comment = "Vertical height range (above and below) affected",
        minimalValue = 1)
    public static int growthHeightTier2 = 5;

    /**
     * Make a new instance.
     */
    public BlockGrowthTier2Config() {
        super(
            OKProgressions._instance,
            true,
            "growth_upgrade",
            null,
            config -> new BlockGrowthBase(growthIntervalTier2, growthRadiusTier2, growthHeightTier2));
    }
}
