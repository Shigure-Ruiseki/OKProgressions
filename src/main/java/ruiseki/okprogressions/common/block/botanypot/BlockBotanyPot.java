package ruiseki.okprogressions.common.block.botanypot;

import java.util.List;
import java.util.Random;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

import ruiseki.okcore.block.BlockOK;
import ruiseki.okcore.block.IBlockTooltipProvider;
import ruiseki.okcore.helper.TileHelpers;

public class BlockBotanyPot extends BlockOK implements IGrowable, IBlockTooltipProvider {

    private final boolean hopper;

    public BlockBotanyPot() {
        this(false);
    }

    protected BlockBotanyPot(boolean hopper) {
        super("botany_pot", TEBotanyPot.class, Material.circuits);
        this.hopper = hopper;
    }

    public boolean isHopper() {
        return this.hopper;
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List<String> list, boolean b) {
        list.add(this.isHopper() ? new ChatComponentTranslation("tooltip.pot.normal").getFormattedText() : new ChatComponentTranslation("tooltip.pot.hopper").getFormattedText());
    }

    // canGrow
    @Override
    public boolean func_149851_a(World worldIn, int x, int y, int z, boolean isClient) {
        TEBotanyPot tile = TileHelpers.getSafeTile(worldIn, x, y, z, TEBotanyPot.class);
        if (tile != null) {
            return false;
        }
        return false;
    }

    // canUseBonemeal
    @Override
    public boolean func_149852_a(World worldIn, Random random, int x, int y, int z) {
        return false;
    }

    // grow
    @Override
    public void func_149853_b(World worldIn, Random random, int x, int y, int z) {

    }
}
