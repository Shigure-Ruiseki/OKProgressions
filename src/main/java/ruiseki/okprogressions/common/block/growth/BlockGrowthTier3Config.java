package ruiseki.okprogressions.common.block.growth;

import ruiseki.okcore.config.ConfigurableProperty;
import ruiseki.okcore.config.ConfigurableTypeCategory;
import ruiseki.okcore.config.extendedconfig.BlockContainerConfig;
import ruiseki.okprogressions.OKProgressions;

public class BlockGrowthTier3Config extends BlockContainerConfig {

    /**
     * The unique instance.
     */
    public static BlockGrowthTier3Config _instance;

    /**
     * The growth tick interval (in seconds).
     */
    @ConfigurableProperty(
        category = ConfigurableTypeCategory.MACHINE,
        comment = "Tick interval in seconds to attempt growth tick",
        minimalValue = 1)
    public static int growthIntervalTier3 = 3;

    /**
     * The radius in blocks affected by the growth accelerator.
     */
    @ConfigurableProperty(
        category = ConfigurableTypeCategory.MACHINE,
        comment = "Radius of affected crops/blocks",
        minimalValue = 1)
    public static int growthRadiusTier3 = 8;

    /**
     * The vertical range (up/down) in blocks affected.
     */
    @ConfigurableProperty(
        category = ConfigurableTypeCategory.MACHINE,
        comment = "Vertical height range (above and below) affected",
        minimalValue = 1)
    public static int growthHeightTier3 = 10;

    /**
     * Make a new instance.
     */
    public BlockGrowthTier3Config() {
        super(
            OKProgressions._instance,
            true,
            "growth_upgrade_two",
            null,
            config -> new BlockGrowthBase(growthIntervalTier3, growthRadiusTier3, growthHeightTier3));
    }
}
