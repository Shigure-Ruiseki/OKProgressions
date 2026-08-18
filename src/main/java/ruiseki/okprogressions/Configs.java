package ruiseki.okprogressions;

import ruiseki.okcore.config.ConfigHandler;
import ruiseki.okprogressions.common.block.botanypot.BlockBotanyPotConfig;
import ruiseki.okprogressions.common.block.botanypot.BlockHopperBotanyPotConfig;
import ruiseki.okprogressions.common.block.cobblegen.BlockCobblegenConfig;
import ruiseki.okprogressions.common.block.cobblegen.BlockDiamondCobblegenConfig;
import ruiseki.okprogressions.common.block.cobblegen.BlockEmeraldCobblegenConfig;
import ruiseki.okprogressions.common.block.cobblegen.BlockGoldCobblegenConfig;
import ruiseki.okprogressions.common.block.cobblegen.BlockIronCobblegenConfig;
import ruiseki.okprogressions.common.block.compressed.BlockBoneConfig;
import ruiseki.okprogressions.common.block.compressed.BlockCharcoalConfig;
import ruiseki.okprogressions.common.block.compressed.BlockFlintConfig;
import ruiseki.okprogressions.common.block.compressed.BlockNetherStarConfig;
import ruiseki.okprogressions.common.block.growth.BlockGrowthTier1Config;
import ruiseki.okprogressions.common.block.growth.BlockGrowthTier2Config;
import ruiseki.okprogressions.common.block.growth.BlockGrowthTier3Config;
import ruiseki.okprogressions.common.block.machine.miner.BlockMinerConfig;
import ruiseki.okprogressions.common.block.machine.placer.BlockPlacerConfig;
import ruiseki.okprogressions.common.block.machine.user.BlockUserConfig;
import ruiseki.okprogressions.common.block.misc.BlockOreEnderConfig;
import ruiseki.okprogressions.common.block.misc.BlockStoneTorchConfig;
import ruiseki.okprogressions.common.block.reinforced.BlockReinforcedGlassConfig;
import ruiseki.okprogressions.common.block.reinforced.BlockReinforcedObsidianConfig;
import ruiseki.okprogressions.common.data.crop.CropSerializerConfig;
import ruiseki.okprogressions.common.data.crop.CropTypeConfig;
import ruiseki.okprogressions.common.data.soil.SoilSerializerConfig;
import ruiseki.okprogressions.common.data.soil.SoilTypeConfig;
import ruiseki.okprogressions.common.item.apple.ItemDiamondAppleConfig;
import ruiseki.okprogressions.common.item.apple.ItemEmeraldAppleConfig;
import ruiseki.okprogressions.common.item.apple.ItemIronAppleConfig;
import ruiseki.okprogressions.common.item.apple.ItemRedstoneAppleConfig;
import ruiseki.okprogressions.common.item.charm.ItemClimbingGloveConfig;
import ruiseki.okprogressions.common.item.misc.ItemEnderDustConfig;
import ruiseki.okprogressions.common.item.misc.ItemEnderSackConfig;
import ruiseki.okprogressions.common.item.misc.ItemMyceliumSeedsConfig;
import ruiseki.okprogressions.common.item.misc.ItemStoneStickConfig;
import ruiseki.okprogressions.common.item.misc.ItemTinyCharcoalConfig;
import ruiseki.okprogressions.common.item.misc.ItemTinyCoalConfig;
import ruiseki.okprogressions.common.item.tool.ItemBirthdayPickaxeConfig;
import ruiseki.okprogressions.common.item.tool.ItemDiamondPaxelConfig;
import ruiseki.okprogressions.common.item.tool.ItemGoldenPaxelConfig;
import ruiseki.okprogressions.common.item.tool.ItemIronPaxelConfig;
import ruiseki.okprogressions.common.item.tool.ItemStonePaxelConfig;
import ruiseki.okprogressions.common.item.tool.ItemWoodenPaxelConfig;

public class Configs {

    public static void register(ConfigHandler configHandler) {

        // Apple
        configHandler.add(new ItemIronAppleConfig());
        configHandler.add(new ItemEmeraldAppleConfig());
        configHandler.add(new ItemDiamondAppleConfig());
        configHandler.add(new ItemRedstoneAppleConfig());

        // Misc
        configHandler.add(new ItemMyceliumSeedsConfig());
        configHandler.add(new ItemEnderSackConfig());
        configHandler.add(new ItemTinyCharcoalConfig());
        configHandler.add(new ItemTinyCoalConfig());
        configHandler.add(new ItemStoneStickConfig());
        configHandler.add(new ItemEnderDustConfig());

        // Charm
        configHandler.add(new ItemClimbingGloveConfig());

        // Tool
        configHandler.add(new ItemBirthdayPickaxeConfig());
        configHandler.add(new ItemWoodenPaxelConfig());
        configHandler.add(new ItemStonePaxelConfig());
        configHandler.add(new ItemIronPaxelConfig());
        configHandler.add(new ItemGoldenPaxelConfig());
        configHandler.add(new ItemDiamondPaxelConfig());

        // Cobblegen
        configHandler.add(new BlockCobblegenConfig());
        configHandler.add(new BlockIronCobblegenConfig());
        configHandler.add(new BlockGoldCobblegenConfig());
        configHandler.add(new BlockDiamondCobblegenConfig());
        configHandler.add(new BlockEmeraldCobblegenConfig());

        // Botany Pot
        configHandler.add(new BlockBotanyPotConfig());
        configHandler.add(new BlockHopperBotanyPotConfig());

        // Growth
        configHandler.add(new BlockGrowthTier1Config());
        configHandler.add(new BlockGrowthTier2Config());
        configHandler.add(new BlockGrowthTier3Config());

        // Ore
        configHandler.add(new BlockOreEnderConfig());

        // Misc
        configHandler.add(new BlockStoneTorchConfig());

        // Reinforced
        configHandler.add(new BlockReinforcedObsidianConfig());
        configHandler.add(new BlockReinforcedGlassConfig());

        // Compressed
        configHandler.add(new BlockBoneConfig());
        configHandler.add(new BlockCharcoalConfig());
        configHandler.add(new BlockFlintConfig());
        configHandler.add(new BlockNetherStarConfig());

        // Machine
        configHandler.add(new BlockMinerConfig());
        configHandler.add(new BlockPlacerConfig());
        configHandler.add(new BlockUserConfig());

        // Recipe
        configHandler.add(new SoilSerializerConfig());
        configHandler.add(new SoilTypeConfig());
        configHandler.add(new CropSerializerConfig());
        configHandler.add(new CropTypeConfig());
    }
}
