package ruiseki.okprogressions.common.block.botanypot;

import ruiseki.okcore.config.extendedconfig.BlockContainerConfig;
import ruiseki.okprogressions.OKProgressions;

public class BlockHopperBotanyConfig extends BlockContainerConfig {

    /**
     * The unique instance.
     */
    public static BlockHopperBotanyConfig _instance;

    /**
     * Make a new instance.
     */
    public BlockHopperBotanyConfig() {
        super(OKProgressions._instance, true, "hopper_botany_pot", null, BlockHopperBotanyPot.class);
    }
}
