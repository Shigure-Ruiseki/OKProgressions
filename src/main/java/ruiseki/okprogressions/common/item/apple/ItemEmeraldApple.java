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
import ruiseki.okprogressions.Reference;

public class ItemEmeraldApple extends ItemIronApple {

    public ItemEmeraldApple() {
        super("emerald_apple", 4, 1.0F, false);
        this.setTextureName(Reference.PREFIX_MOD + "emerald_apple");
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
        tooltip.add(ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.emerald_apple_1").getFormattedText());
        tooltip.add(ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.emerald_apple_2").getFormattedText());
        tooltip.add(ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.emerald_apple_3").getFormattedText());
    }
}
