package ruiseki.okprogressions.common.block.machine.miner;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

import org.apache.logging.log4j.Level;

import ruiseki.okcore.config.extendedconfig.BlockConfig;
import ruiseki.okcore.config.extendedconfig.ExtendedConfig;
import ruiseki.okcore.helper.TileHelpers;
import ruiseki.okprogressions.OKProgressions;
import ruiseki.okprogressions.common.block.machine.BlockMachine;

public class BlockMiner extends BlockMachine {

    private static BlockMiner _instance = null;

    /**
     * Get the unique instance.
     *
     * @return The instance.
     */
    public static BlockMiner getInstance() {
        return _instance;
    }

    public BlockMiner(ExtendedConfig<BlockConfig> eConfig) {
        super(eConfig, Material.iron, TEBlockMiner.class);
        this.isDirection = true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block blockBroken, int meta) {
        try {
            TEBlockMiner tile = TileHelpers.getSafeTile(world, x, y, z, TEBlockMiner.class);
            if (tile != null) {
                tile.breakBlock(world, x, y, z);
            }
        } catch (Exception e) {
            OKProgressions.okLog(Level.ERROR, "Block miner: tile entity deleted before block breaking", e);
        }
        super.breakBlock(world, x, y, z, blockBroken, meta);
    }
}
