package ruiseki.okprogressions.common.block.botanypot;

import ruiseki.okcore.config.extendedconfig.BlockConfig;
import ruiseki.okcore.config.extendedconfig.ExtendedConfig;

public class BlockBotanyPot extends BlockBotanyPotBase {

    private static BlockBotanyPot _instance = null;

    /**
     * Get the unique instance.
     *
     * @return The instance.
     */
    public static BlockBotanyPot getInstance() {
        return _instance;
    }

    public BlockBotanyPot(ExtendedConfig<BlockConfig> eConfig) {
        super(eConfig, false);
    }
}
