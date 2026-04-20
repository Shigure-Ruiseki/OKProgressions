package ruiseki.okprogressions.common.item.misc;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

import com.mojang.realmsclient.gui.ChatFormatting;

import ruiseki.okcore.item.ItemOK;
import ruiseki.okprogressions.OKPCreativeTab;

public class ItemMyceliumSeeds extends ItemOK {

    public ItemMyceliumSeeds() {
        super("mycelium_seeds");
        this.setCreativeTab(OKPCreativeTab.INSTANCE);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ) {
        Block block = world.getBlock(x,y,z);
        if (block == Blocks.dirt || block == Blocks.grass) {
            world.setBlock(x, y, z, Blocks.mycelium);
            world.playSoundEffect((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D, "step.grass", 1.0F, 1.0F);
            if (!player.capabilities.isCreativeMode) {
                stack.stackSize--;
            }
            return true;
        }
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean flag) {
        list.add(ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.seeds").getFormattedText());
    }
}
