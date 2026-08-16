package ruiseki.okprogressions.common.block.compressed;

import ruiseki.okcore.config.extendedconfig.BlockConfig;
import ruiseki.okprogressions.OKProgressions;

public class BlockFlintConfig extends BlockConfig {

    /**
     * The unique instance.
     */
    public static BlockFlintConfig _instance;

    /**
     * Make a new instance.
     */
    public BlockFlintConfig() {
        super(OKProgressions._instance, true, "flint_block", null, BlockFlint.class);
    }

    @Override
    public String getOreDictionaryId() {
        return "blockFlint";
    }
}
