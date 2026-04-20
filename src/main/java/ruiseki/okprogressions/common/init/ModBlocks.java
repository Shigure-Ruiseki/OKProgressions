package ruiseki.okprogressions.common.init;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.apache.logging.log4j.Level;

import ruiseki.okcore.block.IBlock;
import ruiseki.okprogressions.OKProgressions;
import ruiseki.okprogressions.common.block.cobblegen.BlockCobblegen;
import ruiseki.okprogressions.common.block.compressed.BlockBone;
import ruiseki.okprogressions.common.block.compressed.BlockCharcoal;
import ruiseki.okprogressions.common.block.compressed.BlockCompressed;
import ruiseki.okprogressions.common.block.compressed.BlockFlesh;
import ruiseki.okprogressions.common.block.compressed.BlockFlint;
import ruiseki.okprogressions.common.block.compressed.BlockNetherStar;
import ruiseki.okprogressions.common.block.growth.BlockGrowth;
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

    COMPRESSED_NETHER_BLOCK(
        (IBlock) new BlockCompressed("compressed_nether_block", Material.rock, Block.soundTypeStone, 1, 1)
            .setHardness(0.4F)
            .setResistance(10.0F)),
    DOUBLE_COMPRESSED_NETHER_BLOCK(
        (IBlock) new BlockCompressed("double_compressed_nether_block", Material.rock, Block.soundTypeStone, 1, 1)
            .setHardness(0.8F)
            .setResistance(20.0F)),
    TRIPLE_COMPRESSED_NETHER_BLOCK(
        (IBlock) new BlockCompressed("triple_compressed_nether_block", Material.rock, Block.soundTypeStone, 1, 1)
            .setHardness(1.2F)
            .setResistance(30.0F)),
    QUADRUPLE_COMPRESSED_NETHER_BLOCK(
        (IBlock) new BlockCompressed("quadruple_compressed_nether_block", Material.rock, Block.soundTypeStone, 1, 1)
            .setHardness(1.6F)
            .setResistance(40.0F)),
    QUINTUPLE_COMPRESSED_NETHER_BLOCK(
        (IBlock) new BlockCompressed("quintuple_compressed_nether_block", Material.rock, Block.soundTypeStone, 1, 1)
            .setHardness(2.0F)
            .setResistance(50.0F)),
    SEXTUPLE_COMPRESSED_NETHER_BLOCK(
        (IBlock) new BlockCompressed("sextuple_compressed_nether_block", Material.rock, Block.soundTypeStone, 1, 1)
            .setHardness(2.4F)
            .setResistance(60.0F)),
    SEPTUPLE_COMPRESSED_NETHER_BLOCK(
        (IBlock) new BlockCompressed("septuple_compressed_nether_block", Material.rock, Block.soundTypeStone, 1, 1)
            .setHardness(2.8F)
            .setResistance(70.0F)),
    OCTUPLE_COMPRESSED_NETHER_BLOCK(
        (IBlock) new BlockCompressed("octuple_compressed_nether_block", Material.rock, Block.soundTypeStone, 1, 1)
            .setHardness(3.2F)
            .setResistance(80.0F)),

    BONE_BLOCK(new BlockBone()),
    CHARCOAL_BLOCK(new BlockCharcoal()),
    FLESH_BLOCK(new BlockFlesh()),
    NETHER_STAR_BLOCK(new BlockNetherStar()),
    FLINT_BLOCK(new BlockFlint())

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
