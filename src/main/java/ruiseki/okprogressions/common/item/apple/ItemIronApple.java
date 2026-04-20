package ruiseki.okprogressions.common.item.apple;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

import com.mojang.realmsclient.gui.ChatFormatting;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ruiseki.okcore.item.ItemFoodOK;
import ruiseki.okprogressions.OKPCreativeTab;

public class ItemIronApple extends ItemFoodOK {

    public ItemIronApple(String name, int amount, float saturation, boolean isWolfFood) {
        super(name, amount, saturation, isWolfFood);
        this.setCreativeTab(OKPCreativeTab.INSTANCE);
        this.setAlwaysEdible();
    }

    public ItemIronApple(int amount, float saturation, boolean isWolfFood) {
        this("iron_apple", amount, saturation, isWolfFood);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.epic;
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            player.addPotionEffect(new PotionEffect(Potion.resistance.id, 300, 2));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        tooltip.add(ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.iron_apple_1").getFormattedText());
        tooltip.add(ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.iron_apple_2").getFormattedText());
    }
}
