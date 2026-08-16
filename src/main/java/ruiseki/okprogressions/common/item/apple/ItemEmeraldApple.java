package ruiseki.okprogressions.common.item.apple;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

import com.mojang.realmsclient.gui.ChatFormatting;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ruiseki.okcore.config.extendedconfig.ExtendedConfig;
import ruiseki.okcore.config.extendedconfig.ItemConfig;

public class ItemEmeraldApple extends ItemAppleBase {

    private static ItemEmeraldApple _instance = null;

    /**
     * Get the unique instance.
     *
     * @return The instance.
     */
    public static ItemEmeraldApple getInstance() {
        return _instance;
    }

    public ItemEmeraldApple(ExtendedConfig<ItemConfig> eConfig) {
        super(eConfig, 4, 1.0F, false);
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 300, 0));
            player.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 300, 0));
            player.addPotionEffect(new PotionEffect(Potion.field_76443_y.id, 120, 0));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.emerald_apple_1").getFormattedText());
        tooltip.add(ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.emerald_apple_2").getFormattedText());
        tooltip.add(ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.emerald_apple_3").getFormattedText());
    }
}
