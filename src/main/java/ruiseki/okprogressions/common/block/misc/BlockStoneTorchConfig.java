package ruiseki.okprogressions.common.block.misc;

import ruiseki.okcore.block.BlockTorchBase;
import ruiseki.okcore.config.extendedconfig.BlockConfig;
import ruiseki.okprogressions.OKProgressions;

public class BlockStoneTorchConfig extends BlockConfig {

    /**
     * The unique instance.
     */
    public static BlockStoneTorchConfig _instance;

    /**
     * Make a new instance.
     */
    public BlockStoneTorchConfig() {
        super(OKProgressions._instance, true, "stone_torch", null, config -> new BlockTorchBase());
    }

    @Override
    public String getOreDictionaryId() {
        return "torch";
    }
}
