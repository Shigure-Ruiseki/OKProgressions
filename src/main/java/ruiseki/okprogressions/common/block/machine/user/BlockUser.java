package ruiseki.okprogressions.common.block.machine.user;

import net.minecraft.block.material.Material;

import ruiseki.okcore.config.extendedconfig.BlockConfig;
import ruiseki.okcore.config.extendedconfig.ExtendedConfig;
import ruiseki.okprogressions.common.block.machine.BlockMachine;

public class BlockUser extends BlockMachine {

    private static BlockUser _instance = null;

    /**
     * Get the unique instance.
     *
     * @return The instance.
     */
    public static BlockUser getInstance() {
        return _instance;
    }

    public BlockUser(ExtendedConfig<BlockConfig> eConfig) {
        super(eConfig, Material.iron, TEBlockUser.class);
        this.isDirection = true;
    }
}
