package ruiseki.okprogressions.common.block.machine.user;

import ruiseki.okcore.config.extendedconfig.BlockContainerConfig;
import ruiseki.okprogressions.OKProgressions;

public class BlockUserConfig extends BlockContainerConfig {

    /**
     * The unique instance.
     */
    public static BlockUserConfig _instance;

    /**
     * Make a new instance.
     */
    public BlockUserConfig() {
        super(OKProgressions._instance, true, "block_user", null, config -> new BlockUser());
    }
}
