package ruiseki.okprogressions.common.block.machine;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.cleanroommc.modularui.factory.GuiFactories;
import com.gtnewhorizon.gtnhlib.blockstate.core.BlockState;

import ruiseki.okcore.block.BlockTile;
import ruiseki.okcore.block.property.BlockProperty;
import ruiseki.okcore.block.property.DirectionProperty;
import ruiseki.okcore.datastructure.BlockPos;
import ruiseki.okcore.helper.DirectionHelpers;
import ruiseki.okcore.tileentity.TileEntityOK;

public abstract class BlockMachine extends BlockTile {

    @BlockProperty
    public static final DirectionProperty DIRECTION = DirectionProperty.facing();

    protected boolean isDirection;

    protected BlockMachine(Material material, Class<? extends TileEntityOK> tileEntity) {
        super(material, tileEntity);
        this.setHardness(3.0F);
        this.setResistance(5.0F);
        this.setStepSound(soundTypeMetal);
    }

    @Override
    public BlockState getStateForPlacement(World world, BlockPos pos, ForgeDirection facing, float hitX, float hitY,
        float hitZ, int meta, EntityLivingBase placer) {
        BlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer);
        if (isDirection) state.setPropertyValue(DIRECTION, DirectionHelpers.yawToDirection6(placer));
        return state;
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX,
        float subY, float subZ) {
        if (!worldIn.isRemote) {
            GuiFactories.tileEntity()
                .open(player, x, y, z);
        }
        return true;
    }

    @Override
    public boolean isKeepNBTOnDrop() {
        return false;
    }
}
