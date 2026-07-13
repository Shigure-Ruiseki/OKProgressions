package ruiseki.okprogressions.common.block.machine.user;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.common.registry.GameRegistry;
import ruiseki.okprogressions.common.block.machine.BlockMachine;

public class BlockUser extends BlockMachine {

    public BlockUser() {
        super(Material.iron);
        this.isDirection = true;
    }

    @Override
    public void registerTileEntity(String name) {
        GameRegistry.registerTileEntity(TEBlockUser.class, name + "TileEntity");
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TEBlockUser();
    }
}
