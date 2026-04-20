package ruiseki.okprogressions.common.item.apple;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ruiseki.okprogressions.Reference;

public class ItemGoldenApple extends ItemIronApple {

    public ItemGoldenApple() {
        super("golden_apple", 4, 1.0F, false);
        this.setTextureName(Reference.PREFIX_MOD + "golden_apple");
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            player.addPotionEffect(new PotionEffect(Potion.resistance.id, 3000, 0));
            player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 225, 0));
            player.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 2400, 0));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {}
}
