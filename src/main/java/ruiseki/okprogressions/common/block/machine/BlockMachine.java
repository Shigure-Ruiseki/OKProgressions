package ruiseki.okprogressions.common.block.machine;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.jetbrains.annotations.Nullable;

import ruiseki.okcore.block.BlockOK;
import ruiseki.okcore.block.property.BlockPropertyReg;
import ruiseki.okcore.block.property.BlockStateProperties;
import ruiseki.okcore.block.property.DirectionProperty;
import ruiseki.okcore.helper.BlockStateHelpers;
import ruiseki.okcore.helper.DirectionHelpers;

public class BlockMachine extends BlockOK {

    @BlockPropertyReg
    public static final DirectionProperty DIRECTION = BlockStateProperties.FACING;

    protected boolean isDirection;

    protected BlockMachine(String name, @Nullable Class<? extends TEMachine> teClass, Material mat) {
        super(name, teClass, mat);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
        super.onBlockPlacedBy(world, x, y, z, player, stack);
        if (!isDirection) return;
        ForgeDirection facing = DirectionHelpers.yawToDirection6(player);
        BlockStateHelpers.set(world, x, y, z, DIRECTION, facing);
    }

    @Override
    public boolean isKeepNBTOnDrop() {
        return false;
    }
}
