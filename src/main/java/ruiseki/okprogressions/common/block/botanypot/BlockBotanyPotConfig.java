package ruiseki.okprogressions.common.block.botanypot;

import ruiseki.okcore.config.extendedconfig.BlockContainerConfig;
import ruiseki.okprogressions.OKProgressions;

public class BlockBotanyPotConfig extends BlockContainerConfig {

    /**
     * The unique instance.
     */
    public static BlockBotanyPotConfig _instance;

    /**
     * Make a new instance.
     */
    public BlockBotanyPotConfig() {
        super(OKProgressions._instance, true, "botany_pot", null, config -> new BlockBotanyPotBase(false));
    }
}
