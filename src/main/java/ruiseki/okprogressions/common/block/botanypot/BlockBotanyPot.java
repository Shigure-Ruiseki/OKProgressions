package ruiseki.okprogressions.common.block.botanypot;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import ruiseki.okcore.block.BlockOK;
import ruiseki.okcore.block.IBlockTooltipProvider;
import ruiseki.okcore.datastructure.BlockPos;
import ruiseki.okcore.helper.InventoryHelpers;
import ruiseki.okcore.helper.TileHelpers;
import ruiseki.okprogressions.OKPCreativeTab;
import ruiseki.okprogressions.common.crop.CropMaterial;
import ruiseki.okprogressions.common.crop.CropRegistry;
import ruiseki.okprogressions.common.helper.BotanyPotHelpers;
import ruiseki.okprogressions.common.soil.SoilMaterial;
import ruiseki.okprogressions.common.soil.SoilRegistry;

public class BlockBotanyPot extends BlockOK implements IGrowable, IBlockTooltipProvider {

    private final boolean hopper;

    public BlockBotanyPot(String name) {
        this(false, name);
    }

    public BlockBotanyPot(boolean hopper, String name) {
        super(name, TEBotanyPot.class, Material.circuits);
        this.hopper = hopper;
        this.setHardness(1.25F);
        this.setResistance(4.2F);
        this.setStepSound(soundTypeStone);
        this.setBlockBounds(0.125F, 0.0F, 0.125F, 0.875F, 0.5F, 0.875F);
        this.setCreativeTab(OKPCreativeTab.INSTANCE);
        this.isFullSize = this.isOpaque = false;
    }

    public boolean isHopper() {
        return this.hopper;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
        float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        }

        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile instanceof TEBotanyPot pot) {
            ItemStack heldItem = player.getHeldItem();

            if (player.isSneaking()) {
                CropMaterial crop = pot.getCrop();

                if (crop != null) {
                    if (pot.canSetCrop(null)) {
                        ItemStack cropStack = pot.getCropStack();
                        if (cropStack != null) {
                            InventoryHelpers.dropItems(world, cropStack, new BlockPos(x, y, z));
                        }
                        pot.setCrop(null, null);
                        return true;
                    }
                } else {
                    SoilMaterial soil = pot.getSoil();
                    if (soil != null) {
                        ItemStack soilStack = pot.getSoilStack();
                        if (soilStack != null && pot.canSetSoil(null)) {
                            pot.setSoil(null, null);
                            InventoryHelpers.dropItems(world, soilStack, new BlockPos(x, y, z));
                            return true;
                        }
                    }
                }
            } else {
                if (heldItem != null) {
                    if (pot.getSoil() == null) {
                        SoilMaterial soilForStack = SoilRegistry.getByStack(heldItem);
                        if (soilForStack != null && pot.canSetSoil(soilForStack)) {
                            ItemStack inStack = heldItem.copy();
                            inStack.stackSize = 1;
                            pot.setSoil(soilForStack, inStack);
                            if (!player.capabilities.isCreativeMode) {
                                heldItem.stackSize--;
                            }
                            return true;
                        }
                    } else if (pot.getCrop() == null) {
                        CropMaterial cropForStack = CropRegistry.getByStack(heldItem);
                        if (cropForStack != null && BotanyPotHelpers.isSoilValidForCrop(pot.getSoil(), cropForStack)
                            && pot.canSetCrop(cropForStack)) {
                            ItemStack inStack = heldItem.copy();
                            inStack.stackSize = 1;
                            pot.setCrop(cropForStack, inStack);
                            if (!player.capabilities.isCreativeMode) {
                                heldItem.stackSize--;
                            }
                            return true;
                        }
                    }
                }

                if (!this.isHopper() && pot.isDoneGrowing() && pot.getCrop() != null && pot.canHarvest()) {
                    List<ItemStack> drops = BotanyPotHelpers.generateDrop(world.rand, pot.getCrop());
                    for (ItemStack stack : drops) {
                        InventoryHelpers.dropItems(world, stack, new BlockPos(x, y, z));
                    }
                    pot.resetGrowthTime();
                    return true;
                }
            }
        }

        return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block blockBroken, int meta) {
        super.breakBlock(world, x, y, z, blockBroken, meta);
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List<String> list, boolean b) {
        list.add(
            this.isHopper() ? new ChatComponentTranslation("tooltip.pot.hopper").getFormattedText()
                : new ChatComponentTranslation("tooltip.pot.normal").getFormattedText());
    }

    // canGrow
    @Override
    public boolean func_149851_a(World worldIn, int x, int y, int z, boolean isClient) {
        TEBotanyPot pot = TileHelpers.getSafeTile(worldIn, x, y, z, TEBotanyPot.class);
        if (pot != null) {
            return pot.hasSoilAndCrop() && !pot.isDoneGrowing();
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
        TileHelpers.getTileEntity(worldIn, new BlockPos(x, y, z), TEBotanyPot.class)
            .ifPresent(pot -> pot.addGrowth((random.nextInt(15 - 3 + 1) + 3) * 20));
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TEBotanyPot pot) {
            return pot.isDoneGrowing() ? 15 : 0;
        }
        return 0;
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TEBotanyPot pot) {
            int light = 0;
            if (pot.getCrop() != null) light = Math.max(
                light,
                pot.getCrop()
                    .getLightLevel());
            return light;
        }
        return 0;
    }
}
