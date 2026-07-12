package ruiseki.okprogressions.common.block.machine.miner;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.registry.GameRegistry;
import ruiseki.okcore.helper.TileHelpers;
import ruiseki.okprogressions.OKProgressions;
import ruiseki.okprogressions.common.block.machine.BlockMachine;

public class BlockMiner extends BlockMachine {

    public BlockMiner() {
        super(Material.iron);
        this.isDirection = true;
    }

    @Override
    public void registerTileEntity(String name) {
        GameRegistry.registerTileEntity(TEBlockMiner.class, name + "TileEntity");
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TEBlockMiner();
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
