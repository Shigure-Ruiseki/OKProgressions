package ruiseki.okprogressions.common.block.growth;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import com.mojang.realmsclient.gui.ChatFormatting;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ruiseki.okcore.block.BlockOK;
import ruiseki.okprogressions.OKPCreativeTab;
import ruiseki.okprogressions.config.ModConfig;

public class BlockGrowth extends BlockOK {

    private final int range;
    private final int rangeY;
    private final int growthLvl;

    public BlockGrowth(String name, int growthLvl, int range, int rangeY) {
        super(name, TEGrowth.class, Material.iron);
        this.setTickRandomly(true);
        this.setHardness(8.0F);
        this.setResistance(1000.0F);
        this.setLightLevel(7.0F / 15.0F);
        this.setHarvestLevel("pickaxe", 1);
        this.setCreativeTab(OKPCreativeTab.INSTANCE);
        this.setStepSound(Block.soundTypeMetal);
        this.isOpaque = false;

        this.growthLvl = growthLvl;
        this.range = range;
        this.rangeY = rangeY;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean flag) {
        if (this.growthLvl == 1) {
            list.add(ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.growth_1").getFormattedText());
            list.add(ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.growth_2").getFormattedText());
            list.add(ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.growth_3").getFormattedText());
        }

        if (this.growthLvl == 2) {
            list.add(
                ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.growthupgrade_1").getFormattedText());
            list.add(
                ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.growthupgrade_2").getFormattedText());
            list.add(
                ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.growthupgrade_3").getFormattedText());
            list.add(
                ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.growthupgrade_4").getFormattedText());
            list.add(
                ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.growthupgrade_5").getFormattedText());
        }

        if (this.growthLvl == 3) {
            list.add(
                ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.growthupgrade2_1").getFormattedText());
            list.add(
                ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.growthupgrade2_2").getFormattedText());
            list.add(
                ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.growthupgrade2_3").getFormattedText());
            list.add(
                ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.growthupgrade2_4").getFormattedText());
            list.add(
                ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.growthupgrade2_5").getFormattedText());
        }
    }

    @Override
    public void updateTick(World worldIn, int x, int y, int z, Random random) {
        super.updateTick(worldIn, x, y, z, random);
        growCropsNearby(worldIn, x, y, z, random);
    }

    private int getGrowthCrystalTickRate() {
        int tickRate = this.growthLvl == 1 ? ModConfig.growthTicks
            : this.growthLvl == 2 ? ModConfig.upgradeGrowthTicks
                : this.growthLvl == 3 ? ModConfig.upgradeTwoGrowthTicks : 40;

        return tickRate * 40;
    }

    private int getGrowthCrystalTickRate(double distanceCoefficient) {
        int tickRate = this.growthLvl == 1 ? ModConfig.growthTicks
            : this.growthLvl == 2 ? ModConfig.upgradeGrowthTicks
                : this.growthLvl == 3 ? ModConfig.upgradeTwoGrowthTicks : 40;
        return (int) (distanceCoefficient * tickRate * 40);
    }

    public void growCropsNearby(World world, int xO, int yO, int zO, Random random) {
        if (world.isRemote) return;

        for (int xD = -range; xD <= range; xD++) {
            for (int yD = -rangeY; yD <= rangeY; yD++) {
                for (int zD = -range; zD <= range; zD++) {

                    int x = xO + xD;
                    int y = yO + yD;
                    int z = zO + zD;

                    Block block = world.getBlock(x, y, z);

                    if (block == this) continue;

                    double distance = Math.sqrt(xD * xD + yD * yD + zD * zD);
                    distance = Math.max(1D, distance);
                    double coeff = 1D - (1D / distance);
                    if (block instanceof IGrowable) {
                        block.updateTick(world, x, y, z, random);
                    }

                    if (!world.scheduledUpdatesAreImmediate) {
                        world.scheduleBlockUpdate(x, y, z, block, getGrowthCrystalTickRate(coeff));
                    }
                }
            }
        }

        world.scheduleBlockUpdate(xO, yO, zO, this, getGrowthCrystalTickRate());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        if (!ModConfig.partical) return;
        if (rand.nextInt(ModConfig.particalTicks) != 0) return;

        List<int[]> validBlocks = new ArrayList<>();

        for (int xAxis = -range; xAxis <= range; xAxis++) {
            for (int zAxis = -range; zAxis <= range; zAxis++) {
                for (int yAxis = -rangeY; yAxis <= rangeY; yAxis++) {

                    int tx = x + xAxis;
                    int ty = y + yAxis;
                    int tz = z + zAxis;

                    Block block = world.getBlock(tx, ty, tz);

                    if (block instanceof IGrowable || block == Blocks.mycelium
                        || block == Blocks.cactus
                        || block == Blocks.reeds) {

                        validBlocks.add(new int[] { tx, ty, tz });
                    }
                }
            }
        }
        if (validBlocks.isEmpty()) return;
        int[] pos = validBlocks.get(rand.nextInt(validBlocks.size()));

        int tx = pos[0];
        int ty = pos[1];
        int tz = pos[2];

        double px = x + 0.5D;
        double py = y + 1.5D;
        double pz = z + 0.5D;

        double vx = (tx + 0.5D - px) * 0.2D;
        double vy = (ty + 0.5D - py) * 0.2D;
        double vz = (tz + 0.5D - pz) * 0.2D;

        world.spawnParticle("enchantmenttable", px, py, pz, vx, vy, vz);
    }

    @Override
    public boolean canDropFromExplosion(Explosion explosionIn) {
        return false;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TEGrowth().setStats(growthLvl, range, rangeY);
    }
}
