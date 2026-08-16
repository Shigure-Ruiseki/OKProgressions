package ruiseki.okprogressions.common.block.botanypot;

import ruiseki.okcore.config.extendedconfig.BlockConfig;
import ruiseki.okcore.config.extendedconfig.ExtendedConfig;

public class BlockHopperBotanyPot extends BlockBotanyPotBase {

    private static BlockHopperBotanyPot _instance = null;

    /**
     * Get the unique instance.
     *
     * @return The instance.
     */
    public static BlockHopperBotanyPot getInstance() {
        return _instance;
    }

    public BlockHopperBotanyPot(ExtendedConfig<BlockConfig> eConfig) {
        super(eConfig, true);
    }
}
