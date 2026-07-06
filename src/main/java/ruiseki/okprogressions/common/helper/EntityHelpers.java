package ruiseki.okprogressions.common.helper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.util.ForgeDirection;

public class EntityHelpers {

    public static void setEntityFacing(EntityLivingBase entity, ForgeDirection currentFacing) {
        float yaw = 0;
        float pitch = 0;

        switch (currentFacing) {
            case DOWN:
                pitch = 90F;
                yaw = 0F;
                break;
            case UP:
                pitch = -90F;
                yaw = 0F;
                break;
            case NORTH:
                pitch = 0F;
                yaw = 180F;
                break;
            case SOUTH:
                pitch = 0F;
                yaw = 0F;
                break;
            case WEST:
                pitch = 0F;
                yaw = 90F;
                break;
            case EAST:
                pitch = 0F;
                yaw = 270F;
                break;
            default:
                break;
        }

        entity.rotationYaw = yaw;
        entity.rotationPitch = pitch;

        entity.prevRotationYaw = yaw;
        entity.prevRotationPitch = pitch;

        entity.rotationYawHead = yaw;
        entity.prevRotationYawHead = yaw;
    }
}
