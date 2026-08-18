package ruiseki.okprogressions.common.block.botanypot;

import ruiseki.okcore.config.extendedconfig.BlockContainerConfig;
import ruiseki.okprogressions.OKProgressions;

public class BlockHopperBotanyPotConfig extends BlockContainerConfig {

    /**
     * The unique instance.
     */
    public static BlockHopperBotanyPotConfig _instance;

    /**
     * Make a new instance.
     */
    public BlockHopperBotanyPotConfig() {
        super(OKProgressions._instance, true, "hopper_botany_pot", null, config -> new BlockBotanyPotBase(true));
    }
}
