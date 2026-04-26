package ruiseki.okprogressions.common.block.reinforced;

import java.util.List;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.mojang.realmsclient.gui.ChatFormatting;

import ruiseki.okcore.block.BlockOK;
import ruiseki.okcore.block.IBlockTooltipProvider;
import ruiseki.okprogressions.OKPCreativeTab;

public class BlockReinforcedObsidian extends BlockOK implements IBlockTooltipProvider {

    public BlockReinforcedObsidian() {
        super("reinforced_obsidian", Material.rock);
        this.setHardness(20.0F);
        this.setResistance(2000.0F);
        this.setHarvestLevel("pickaxe", 1);
        this.setCreativeTab(OKPCreativeTab.INSTANCE);
    }

    @Override
    public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity) {
        return !(entity instanceof EntityWither);
    }

    @Override
    public void onBlockExploded(World world, int x, int y, int z, Explosion explosion) {}

    @Override
    public boolean canDropFromExplosion(Explosion explosionIn) {
        return false;
    }

    @Override
    public MapColor getMapColor(int meta) {
        return MapColor.obsidianColor;
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List<String> list, boolean b) {
        list.add(ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.obsidian_1").getFormattedText());
    }
}
