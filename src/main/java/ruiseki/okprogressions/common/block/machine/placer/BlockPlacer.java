package ruiseki.okprogressions.common.block.machine.placer;

import net.minecraft.block.material.Material;

import ruiseki.okcore.config.extendedconfig.BlockConfig;
import ruiseki.okcore.config.extendedconfig.ExtendedConfig;
import ruiseki.okprogressions.common.block.machine.BlockMachine;

public class BlockPlacer extends BlockMachine {

    private static BlockPlacer _instance = null;

    /**
     * Get the unique instance.
     *
     * @return The instance.
     */
    public static BlockPlacer getInstance() {
        return _instance;
    }

    public BlockPlacer(ExtendedConfig<BlockConfig> eConfig) {
        super(eConfig, Material.iron, TEBlockPlacer.class);
        this.isDirection = true;
    }
}
