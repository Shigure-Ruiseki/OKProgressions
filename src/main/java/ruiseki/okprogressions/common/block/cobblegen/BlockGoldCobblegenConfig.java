package ruiseki.okprogressions.common.block.cobblegen;

import ruiseki.okcore.config.ConfigurableProperty;
import ruiseki.okcore.config.extendedconfig.BlockContainerConfig;
import ruiseki.okprogressions.OKProgressions;

public class BlockGoldCobblegenConfig extends BlockContainerConfig {

    /**
     * The unique instance.
     */
    public static BlockGoldCobblegenConfig _instance;

    /**
     * The generation interval in ticks for each cobblestone produced.
     */
    @ConfigurableProperty(
        category = "machine.cobblegen",
        comment = "The cycle each generated (in ticks)",
        minimalValue = 1)
    public static int goldCobbleGenCycle = 10;

    /**
     * The maximum capacity of cobblestone that can be stored in the generator.
     */
    @ConfigurableProperty(
        category = "machine.cobblegen",
        comment = "The max amount cobblestone can store in block",
        minimalValue = 1)
    public static int goldCobbleGenStackSize = 64;

    /**
     * Make a new instance.
     */
    public BlockGoldCobblegenConfig() {
        super(
            OKProgressions._instance,
            true,
            "gold_cobblegen",
            null,
            config -> new BlockCobblegenBase(goldCobbleGenCycle, goldCobbleGenStackSize));
    }
}
