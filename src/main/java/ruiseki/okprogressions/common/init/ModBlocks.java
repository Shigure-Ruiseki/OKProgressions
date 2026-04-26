package ruiseki.okprogressions.common.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.apache.logging.log4j.Level;

import ruiseki.okcore.block.IBlock;
import ruiseki.okprogressions.OKProgressions;
import ruiseki.okprogressions.common.block.BlockOreEnder;
import ruiseki.okprogressions.common.block.BlockStoneTorch;
import ruiseki.okprogressions.common.block.cobblegen.BlockCobblegen;
import ruiseki.okprogressions.common.block.compressed.BlockBone;
import ruiseki.okprogressions.common.block.compressed.BlockCharcoal;
import ruiseki.okprogressions.common.block.compressed.BlockFlint;
import ruiseki.okprogressions.common.block.compressed.BlockNetherStar;
import ruiseki.okprogressions.common.block.growth.BlockGrowth;
import ruiseki.okprogressions.common.block.reinforced.BLockReinforcedGlass;
import ruiseki.okprogressions.common.block.reinforced.BlockReinforcedObsidian;
import ruiseki.okprogressions.config.ModConfig;

public enum ModBlocks {

    // spotless: off

    COBBLE_GEN(new BlockCobblegen("cobblegen", ModConfig.cobbleGenCycle, ModConfig.cobbleGenStackSize)),
    IRON_COBBLE_GEN(
        new BlockCobblegen("iron_cobblegen", ModConfig.ironCobbleGenCycle, ModConfig.ironCobbleGenStackSize)),
    GOLD_COBBLE_GEN(
        new BlockCobblegen("gold_cobblegen", ModConfig.goldCobbleGenCycle, ModConfig.goldCobbleGenStackSize)),
    DIAMOND_COBBLE_GEN(
        new BlockCobblegen("diamond_cobblegen", ModConfig.diamondCobbleGenCycle, ModConfig.diamondCobbleGenStackSize)),
    EMERALD_COBBLE_GEN(
        new BlockCobblegen("emerald_cobblegen", ModConfig.emeraldCobbleGenCycle, ModConfig.emeraldCobbleGenStackSize)),

    GROWTH(new BlockGrowth("growth", 1, 4, 2)),
    GROWTH_UPGRADE(new BlockGrowth("growth_upgrade", 2, 6, 5)),
    GROWTH_UPGRADE_TWO(new BlockGrowth("growth_upgrade_two", 3, 8, 10)),

    BONE_BLOCK(new BlockBone()),
    CHARCOAL_BLOCK(new BlockCharcoal()),
    NETHER_STAR_BLOCK(new BlockNetherStar()),
    FLINT_BLOCK(new BlockFlint()),

    STONE_TORCH(new BlockStoneTorch()),

    REINFORCED_OBSIDIAN(new BlockReinforcedObsidian()),
    REINFORCED_GLASS(new BLockReinforcedGlass()),

    ORE_ENDER(new BlockOreEnder()),

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
