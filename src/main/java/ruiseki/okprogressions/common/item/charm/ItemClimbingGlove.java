package ruiseki.okprogressions.common.item.charm;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import ruiseki.okcore.helper.LangHelpers;
import ruiseki.okprogressions.OKProgressions;
import ruiseki.okprogressions.Reference;
import ruiseki.okprogressions.common.network.PacketPlayerFalldamage;

public class ItemClimbingGlove extends ItemCharm {

    private static final int TICKS_FALLDIST_SYNC = 22;
    private static final double CLIMB_SPEED = 0.288D;

    public ItemClimbingGlove() {
        super("climbing_glove", 6000);
        setTextureName(Reference.PREFIX_MOD + "climbing_glove");
    }

    @Override
    public void onTick(ItemStack stack, EntityPlayer player) {
        if (!this.canTick(stack)) return;
        boolean isTouchingWall = player.isCollidedHorizontally || checkWallCollision(player);

        if (isTouchingWall) {
            World world = player.getEntityWorld();
            tryMakeEntityClimb(world, player, CLIMB_SPEED);
            damageCharm(player, stack);
            if (world.isRemote && player instanceof EntityPlayer && player.ticksExisted % TICKS_FALLDIST_SYNC == 0) {
                world.playSound(player.posX, player.posY, player.posZ, "step.ladder", 0.5F, 1.0F, false);
            }
        }
    }

    private boolean checkWallCollision(EntityPlayer player) {
        return !player.worldObj.getCollidingBoundingBoxes(player, player.boundingBox.expand(0.1D, 0.0D, 0.1D))
            .isEmpty();
    }

    public static void tryMakeEntityClimb(World worldIn, EntityLivingBase entity, double climbSpeed) {
        if (entity.isSneaking()) {
            entity.motionY = 0.0D;
        } else if (entity.moveForward > 0.0F && entity.motionY < climbSpeed) {
            entity.motionY = climbSpeed;
        }
        if (worldIn.isRemote && entity instanceof EntityPlayer && entity.ticksExisted % TICKS_FALLDIST_SYNC == 0) {
            OKProgressions.instance.getPacketHandler()
                .sendToServer(new PacketPlayerFalldamage());
        }
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean flag) {
        list.add(LangHelpers.localize("tooltip.climbing_glove"));
        super.addInformation(stack, player, list, flag);
    }
}
