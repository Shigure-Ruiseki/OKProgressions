package ruiseki.okprogressions.common.item.tool;

import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.collect.Sets;
import com.mojang.realmsclient.gui.ChatFormatting;

import ruiseki.okcore.item.IItem;
import ruiseki.okprogressions.OKPCreativeTab;
import ruiseki.okprogressions.Reference;
import ruiseki.okprogressions.common.init.ModMaterial;

public class ItemBirthdayPickaxe extends ItemTool implements IItem {

    private static final Set<Block> effective_against = Sets.newHashSet(
        Blocks.activator_rail,
        Blocks.coal_ore,
        Blocks.cobblestone,
        Blocks.detector_rail,
        Blocks.diamond_block,
        Blocks.diamond_ore,
        Blocks.double_stone_slab,
        Blocks.golden_rail,
        Blocks.gold_block,
        Blocks.gold_ore,
        Blocks.ice,
        Blocks.iron_block,
        Blocks.iron_ore,
        Blocks.lapis_block,
        Blocks.lapis_ore,
        Blocks.lit_redstone_ore,
        Blocks.mossy_cobblestone,
        Blocks.netherrack,
        Blocks.packed_ice,
        Blocks.rail,
        Blocks.redstone_ore,
        Blocks.sandstone,
        Blocks.stone,
        Blocks.stone_slab,
        Blocks.stone_button,
        Blocks.stone_pressure_plate);

    public ItemBirthdayPickaxe() {
        super(2.0F, ModMaterial.BIRTHDAY, effective_against);
        this.setMaxDamage(6521);
        this.maxStackSize = 1;
        this.setHarvestLevel("pickaxe", 4);

        this.setUnlocalizedName(this.getName());
        this.setTextureName(Reference.PREFIX_MOD + this.getName());
        this.setCreativeTab(OKPCreativeTab.INSTANCE);
    }

    @Override
    public Item getItem() {
        return this;
    }

    @Override
    public String getName() {
        return "birthday_pickaxe";
    }

    @Override
    public boolean func_150897_b(Block block) {
        return block == Blocks.obsidian || super.func_150897_b(block);
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta) {
        if (block == Blocks.obsidian) {
            return this.efficiencyOnProperMaterial;
        }
        Material material = block.getMaterial();
        return material != Material.iron && material != Material.anvil && material != Material.rock
            ? super.getDigSpeed(stack, block, meta)
            : this.efficiencyOnProperMaterial;
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        ItemStack mat = new ItemStack(Blocks.obsidian);
        return OreDictionary.itemMatches(mat, repair, false) || super.getIsRepairable(toRepair, repair);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        tooltip.add(ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.birthday_1").getFormattedText());
        tooltip.add(ChatFormatting.YELLOW + new ChatComponentTranslation("tooltip.birthday_2").getFormattedText());
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            int targetX = x;
            int targetY = y;
            int targetZ = z;

            if (side == 0) --targetY;
            if (side == 1) ++targetY;
            if (side == 2) --targetZ;
            if (side == 3) ++targetZ;
            if (side == 4) --targetX;
            if (side == 5) ++targetX;

            if (!player.canPlayerEdit(targetX, targetY, targetZ, side, stack)) {
                return false;
            }

            if (world.isAirBlock(targetX, targetY, targetZ)) {
                if (player.getCommandSenderName()
                    .equalsIgnoreCase("darkosto")) {
                    player.addChatMessage(
                        new ChatComponentText(
                            "HAPPY BIRTHDAY DARKOSTO " + EnumChatFormatting.GREEN + EnumChatFormatting.BOLD));

                    EntityFireworkRocket firework = new EntityFireworkRocket(
                        world,
                        player.posX,
                        player.posY,
                        player.posZ,
                        null);
                    world.spawnEntityInWorld(firework);
                }

                world.setBlock(targetX, targetY, targetZ, Blocks.cake);
                stack.damageItem(854, player);
                return true;
            }
        }
        return false;
    }
}
