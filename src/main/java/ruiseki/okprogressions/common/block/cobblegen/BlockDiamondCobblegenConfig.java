package ruiseki.okprogressions.common.block.cobblegen;

import ruiseki.okcore.config.ConfigurableProperty;
import ruiseki.okcore.config.extendedconfig.BlockContainerConfig;
import ruiseki.okprogressions.OKProgressions;

public class BlockDiamondCobblegenConfig extends BlockContainerConfig {

    /**
     * The unique instance.
     */
    public static BlockDiamondCobblegenConfig _instance;

    /**
     * The generation interval in ticks for each cobblestone produced.
     */
    @ConfigurableProperty(
        category = "machine.cobblegen",
        comment = "The cycle each generated (in ticks)",
        minimalValue = 1)
    public static int diamondCobbleGenCycle = 5;

    /**
     * The maximum capacity of cobblestone that can be stored in the generator.
     */
    @ConfigurableProperty(
        category = "machine.cobblegen",
        comment = "The max amount cobblestone can store in block",
        minimalValue = 1)
    public static int diamondCobbleGenStackSize = 64;

    /**
     * Make a new instance.
     */
    public BlockDiamondCobblegenConfig() {
        super(
            OKProgressions._instance,
            true,
            "diamond_cobblegen",
            null,
            config -> new BlockCobblegenBase(diamondCobbleGenCycle, diamondCobbleGenStackSize));
    }
}
