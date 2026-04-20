package ruiseki.okprogressions.common.block.compressed;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.mojang.realmsclient.gui.ChatFormatting;

import ruiseki.okcore.block.BlockOK;
import ruiseki.okprogressions.OKPCreativeTab;

public class BlockNetherStar extends BlockOK {

    public BlockNetherStar() {
        super("netherstar_block", Material.iron);
        this.setHardness(3.0F);
        this.setHarvestLevel("pickaxe", 1);
        this.setResistance(2000.0F);
        this.setLightOpacity(1);
        this.setStepSound(soundTypeStone);
        this.setCreativeTab(OKPCreativeTab.INSTANCE);
        this.isFullSize = this.isOpaque = false;
    }

    @Override
    public boolean isBeaconBase(IBlockAccess worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {
        return true;
    }

    @Override
    public boolean canDropFromExplosion(Explosion explosionIn) {
        return false;
    }

    @Override
    public void onFallenUpon(World worldIn, int x, int y, int z, Entity entity, float fallDistance) {
        entity.fallDistance = fallDistance * 3.0F;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean flag) {
        list.add(ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.netherstar_1").getFormattedText());
    }
}
