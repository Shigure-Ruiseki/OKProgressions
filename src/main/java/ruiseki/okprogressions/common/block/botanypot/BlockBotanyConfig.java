package ruiseki.okprogressions.common.block.botanypot;

import ruiseki.okcore.config.extendedconfig.BlockContainerConfig;
import ruiseki.okprogressions.OKProgressions;

public class BlockBotanyConfig extends BlockContainerConfig {

    /**
     * The unique instance.
     */
    public static BlockBotanyConfig _instance;

    /**
     * Make a new instance.
     */
    public BlockBotanyConfig() {
        super(OKProgressions._instance, true, "botany_pot", null, BlockBotanyPot.class);
    }
}
