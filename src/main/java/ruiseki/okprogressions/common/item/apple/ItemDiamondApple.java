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

public class ItemDiamondApple extends ItemIronApple {

    public ItemDiamondApple() {
        super("diamond_apple", 4, 1.0F, false);
        this.setTextureName(Reference.PREFIX_MOD + "diamond_apple");
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 300, 0, true)); // 15
            player.addPotionEffect(new PotionEffect(Potion.resistance.id, 1200, 0, true)); // 60
            player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 300, 0, true));
            player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 1200, 0, true));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        tooltip.add(ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.apple_1").getFormattedText());
        tooltip.add(ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.apple_2").getFormattedText());
        tooltip.add(ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.apple_3").getFormattedText());
        tooltip.add(ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.apple_4").getFormattedText());
    }

}
