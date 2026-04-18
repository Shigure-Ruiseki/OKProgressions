package ruiseki.okprogressions.common.init;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.apache.logging.log4j.Level;

import ruiseki.okcore.block.BlockOK;
import ruiseki.okcore.block.IBlock;
import ruiseki.okprogressions.OKProgressions;
import ruiseki.okprogressions.common.block.BlockCobblegen;
import ruiseki.okprogressions.common.block.BlockGrowth;
import ruiseki.okprogressions.config.ModConfig;

public enum ModBlocks {

    // spotless: off

    COBBLE_GEN(new BlockCobblegen("cobblegen", ModConfig.cobbleGenCycle, ModConfig.cobbleGenStackSize)),
    IRON_COBBLE_GEN(
        new BlockCobblegen("iron_cobblegen", ModConfig.ironCobbleGenCycle, ModConfig.ironCobbleGenStackSize)),
    DIAMOND_COBBLE_GEN(
        new BlockCobblegen("diamond_cobblegen", ModConfig.diamondCobbleGenCycle, ModConfig.diamondCobbleGenStackSize)),
    BLAZE_COBBLE_GEN(
        new BlockCobblegen("blaze_cobblegen", ModConfig.blazeCobbleGenCycle, ModConfig.blazeCobbleGenStackSize)),
    EMERALD_COBBLE_GEN(
        new BlockCobblegen("emerald_cobblegen", ModConfig.emeraldCobbleGenCycle, ModConfig.emeraldCobbleGenStackSize)),
    GROWTH(new BlockGrowth("growth", 1, 4, 2)),
    GROWTH_UPGRADE(new BlockGrowth("growth_upgrade", 2, 6, 5)),
    GROWTH_UPGRADE_TWO(new BlockGrowth("growth_upgrade_two", 3, 8, 10)),
    BONE_BLOCK(new BlockOK("bone_block", Material.rock).setTextureName("bone_block")),

    ;

    // spotless: on

    public static final ModBlocks[] VALUES = values();

    public static void preInit() {
        for (ModBlocks block : VALUES) {
            if (block.block == null) {
                continue;
            }
            try {
                block.block.init();
                OKProgressions.okLog(Level.INFO, "Successfully initialized " + block.name());
            } catch (Exception e) {
                OKProgressions.okLog(Level.ERROR, "Failed to initialize block: +" + block.name());
            }
        }
    }

    private final IBlock block;

    ModBlocks(IBlock block) {
        this.block = block;
    }

    public Block getBlock() {
        return block.getBlock();
    }

    public Item getItem() {
        return block != null ? Item.getItemFromBlock(getBlock()) : null;
    }

    public ItemStack newItemStack() {
        return newItemStack(1);
    }

    public ItemStack newItemStack(int count) {
        return newItemStack(count, 0);
    }

    public ItemStack newItemStack(int count, int meta) {
        return block != null ? new ItemStack(this.getBlock(), count, meta) : null;
    }
}
