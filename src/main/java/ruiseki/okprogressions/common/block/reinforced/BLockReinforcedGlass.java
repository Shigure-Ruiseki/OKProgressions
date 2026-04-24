package ruiseki.okprogressions.common.block.reinforced;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
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

import ruiseki.okcore.block.IBlockTooltipProvider;
import ruiseki.okprogressions.OKPCreativeTab;

public class BLockReinforcedGlass extends BlockGlass implements IBlockTooltipProvider {

    public BLockReinforcedGlass() {
        super(Material.glass, false);
        this.setHardness(20.0F);
        this.setResistance(2000.0F);
        this.setHarvestLevel("pickaxe", 1);
        this.setCreativeTab(OKPCreativeTab.INSTANCE);
        this.setStepSound(Block.soundTypeGlass);
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
    public int quantityDropped(Random random) {
        return 1;
    }

    @Override
    public MapColor getMapColor(int meta) {
        return MapColor.blackColor;
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List<String> list, boolean b) {
        list.add(ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.glass_1").getFormattedText());
    }
}
