package ruiseki.okprogressions.common.block.cobblegen;

import ruiseki.okcore.config.ConfigurableProperty;
import ruiseki.okcore.config.ConfigurableTypeCategory;
import ruiseki.okcore.config.extendedconfig.BlockContainerConfig;
import ruiseki.okprogressions.OKProgressions;

public class BlockCobblegenConfig extends BlockContainerConfig {

    /**
     * The unique instance.
     */
    public static BlockCobblegenConfig _instance;

    /**
     * The generation interval in ticks for each cobblestone produced.
     */
    @ConfigurableProperty(
        category = ConfigurableTypeCategory.MACHINE,
        comment = "The cycle each generated (in ticks)",
        minimalValue = 1)
    public static int cobbleGenCycle = 40;

    /**
     * The maximum capacity of cobblestone that can be stored in the generator.
     */
    @ConfigurableProperty(
        category = ConfigurableTypeCategory.MACHINE,
        comment = "The max amount cobblestone can store in block",
        minimalValue = 1)
    public static int cobbleGenStackSize = 32;

    /**
     * Make a new instance.
     */
    public BlockCobblegenConfig() {
        super(OKProgressions._instance, true, "cobblegen", null, BlockCobblegen.class);
    }
}
