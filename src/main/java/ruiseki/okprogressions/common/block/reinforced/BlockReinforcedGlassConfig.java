package ruiseki.okprogressions.common.block.reinforced;

import ruiseki.okcore.config.extendedconfig.BlockConfig;
import ruiseki.okprogressions.OKProgressions;

public class BlockReinforcedGlassConfig extends BlockConfig {

    /**
     * The unique instance.
     */
    public static BlockReinforcedGlassConfig _instance;

    /**
     * Make a new instance.
     */
    public BlockReinforcedGlassConfig() {
        super(OKProgressions._instance, true, "reinforced_glass", null, BlockReinforcedGlass.class);
    }
}
