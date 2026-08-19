package ruiseki.okprogressions.common.block.machine.placer;

import net.minecraft.block.material.Material;

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

    public BlockPlacer() {
        super(Material.iron, TEBlockPlacer.class);
        this.isDirection = true;
    }
}
