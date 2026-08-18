package ruiseki.okprogressions.common.block.cobblegen;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import com.mojang.realmsclient.gui.ChatFormatting;

import ruiseki.okcore.block.BlockTile;
import ruiseki.okcore.datastructure.BlockPos;
import ruiseki.okcore.helper.TileHelpers;

public class BlockCobblegenBase extends BlockTile {

    private final int cycleUpdate;
    private final int maxStackSize;

    public BlockCobblegenBase(int cycleUpdate, int maxStackSize) {
        super(Material.iron, TileCobblegen.class);
        this.setHardness(1.0F);
        this.setHarvestLevel("pickaxe", 1);
        this.setResistance(1000.0F);
        this.setLightLevel(0.5F);
        this.setLightOpacity(1);
        this.setStepSound(soundTypeStone);
        this.cycleUpdate = cycleUpdate;
        this.maxStackSize = maxStackSize;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean flag) {
        Block blockRaw = Block.getBlockFromItem(stack.getItem());
        if (blockRaw instanceof BlockCobblegenBase block) {
            list.add(
                ChatFormatting.YELLOW
                    + new ChatComponentTranslation("tooltip.cobblegen_1", block.maxStackSize).getFormattedText());

            list.add(
                ChatFormatting.YELLOW
                    + new ChatComponentTranslation("tooltip.cobblegen_2", block.cycleUpdate).getFormattedText());
        }
        list.add(ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.cobblegen_3").getFormattedText());
        list.add(ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.cobblegen_4").getFormattedText());
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        TileCobblegen cobblegen = (TileCobblegen) super.createTileEntity(world, metadata);
        cobblegen.setCycleUpdate(cycleUpdate);
        cobblegen.setMaxStackSize(maxStackSize);
        return cobblegen;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float subX,
        float subY, float subZ) {
        if (world.isRemote) {
            return true;
        }
        TileHelpers.getTileEntity(world, new BlockPos(x, y, z), TileCobblegen.class)
            .ifPresent(tile -> {
                if (!player.isSneaking()) {
                    ItemStack stack = tile.outputInventory.getAndRemoveSlot(0);
                    if (stack != null) {
                        if (!player.inventory.addItemStackToInventory(stack)) {
                            ForgeHooks.onPlayerTossEvent(player, stack, false);
                        } else if (player instanceof EntityPlayerMP) {
                            ((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
                        }
                    }
                } else {
                    ItemStack stack = tile.outputInventory.getStackInSlot(0);
                    player.addChatComponentMessage(
                        new ChatComponentText(
                            Blocks.cobblestone.getLocalizedName() + " x " + (stack == null ? 0 : stack.stackSize)));
                }
            });
        return true;
    }
}
