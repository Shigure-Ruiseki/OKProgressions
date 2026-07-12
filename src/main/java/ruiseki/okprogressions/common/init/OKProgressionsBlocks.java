package ruiseki.okprogressions.common.init;

import java.util.function.Supplier;

import net.minecraft.block.Block;

import ruiseki.okcore.Reference;
import ruiseki.okcore.block.IBlock;
import ruiseki.okcore.registries.DeferredRegister;
import ruiseki.okcore.registries.RegistryObject;
import ruiseki.okcore.tag.Registries;
import ruiseki.okprogressions.common.block.BlockOreEnder;
import ruiseki.okprogressions.common.block.BlockStoneTorch;
import ruiseki.okprogressions.common.block.botanypot.BlockBotanyPot;
import ruiseki.okprogressions.common.block.cobblegen.BlockCobblegen;
import ruiseki.okprogressions.common.block.compressed.BlockBone;
import ruiseki.okprogressions.common.block.compressed.BlockCharcoal;
import ruiseki.okprogressions.common.block.compressed.BlockFlint;
import ruiseki.okprogressions.common.block.compressed.BlockNetherStar;
import ruiseki.okprogressions.common.block.growth.BlockGrowth;
import ruiseki.okprogressions.common.block.machine.miner.BlockMiner;
import ruiseki.okprogressions.common.block.machine.placer.BlockPlacer;
import ruiseki.okprogressions.common.block.reinforced.BLockReinforcedGlass;
import ruiseki.okprogressions.common.block.reinforced.BlockReinforcedObsidian;
import ruiseki.okprogressions.config.ModConfig;

public final class OKProgressionsBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, Reference.MOD_ID);

    public static final RegistryObject<Block> COBBLE_GEN = register(
        "cobblegen",
        () -> true,
        () -> new BlockCobblegen(ModConfig.cobbleGenCycle, ModConfig.cobbleGenStackSize));

    public static final RegistryObject<Block> IRON_COBBLE_GEN = register(
        "iron_cobblegen",
        () -> true,
        () -> new BlockCobblegen(ModConfig.ironCobbleGenCycle, ModConfig.ironCobbleGenStackSize));

    public static final RegistryObject<Block> GOLD_COBBLE_GEN = register(
        "gold_cobblegen",
        () -> true,
        () -> new BlockCobblegen(ModConfig.goldCobbleGenCycle, ModConfig.goldCobbleGenStackSize));

    public static final RegistryObject<Block> DIAMOND_COBBLE_GEN = register(
        "diamond_cobblegen",
        () -> true,
        () -> new BlockCobblegen(ModConfig.diamondCobbleGenCycle, ModConfig.diamondCobbleGenStackSize));

    public static final RegistryObject<Block> EMERALD_COBBLE_GEN = register(
        "emerald_cobblegen",
        () -> true,
        () -> new BlockCobblegen(ModConfig.emeraldCobbleGenCycle, ModConfig.emeraldCobbleGenStackSize));

    public static final RegistryObject<Block> GROWTH = register("growth", () -> true, () -> new BlockGrowth(1, 4, 2));

    public static final RegistryObject<Block> GROWTH_UPGRADE = register(
        "growth_upgrade",
        () -> true,
        () -> new BlockGrowth(2, 6, 5));

    public static final RegistryObject<Block> GROWTH_UPGRADE_TWO = register(
        "growth_upgrade_two",
        () -> true,
        () -> new BlockGrowth(3, 8, 10));

    public static final RegistryObject<Block> BONE_BLOCK = register("bone_block", () -> true, BlockBone::new);

    public static final RegistryObject<Block> CHARCOAL_BLOCK = register(
        "charcoal_block",
        () -> true,
        BlockCharcoal::new);

    public static final RegistryObject<Block> NETHER_STAR_BLOCK = register(
        "nether_star_block",
        () -> true,
        BlockNetherStar::new);

    public static final RegistryObject<Block> FLINT_BLOCK = register("flint_block", () -> true, BlockFlint::new);

    public static final RegistryObject<Block> STONE_TORCH = register("stone_torch", () -> true, BlockStoneTorch::new);

    public static final RegistryObject<Block> REINFORCED_OBSIDIAN = register(
        "reinforced_obsidian",
        () -> true,
        BlockReinforcedObsidian::new);

    public static final RegistryObject<Block> REINFORCED_GLASS = register(
        "reinforced_glass",
        () -> true,
        BLockReinforcedGlass::new);

    public static final RegistryObject<Block> ORE_ENDER = register("ender_ore", () -> true, BlockOreEnder::new);

    public static final RegistryObject<Block> BOTANY_POT = register("botany_pot", () -> true, BlockBotanyPot::new);

    public static final RegistryObject<Block> HOPPER_BOTANY_POT = register(
        "hopper_botany_pot",
        () -> true,
        () -> new BlockBotanyPot(true));

    public static final RegistryObject<Block> BLOCK_MINER = register("block_miner", () -> true, BlockMiner::new);

    public static final RegistryObject<Block> BLOCK_PLACER = register("block_placer", () -> true, BlockPlacer::new);

    private static RegistryObject<Block> register(String name, Supplier<Boolean> configCondition,
        Supplier<IBlock> blockSupplier) {
        if (!configCondition.get()) {
            return RegistryObject.empty();
        }

        return BLOCKS.register(
            name,
            () -> blockSupplier.get()
                .get());
    }

    public static void register() {
        BLOCKS.register();
    }

    private OKProgressionsBlocks() {}
}
