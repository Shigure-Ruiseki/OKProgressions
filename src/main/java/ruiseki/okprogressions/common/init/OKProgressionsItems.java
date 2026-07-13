package ruiseki.okprogressions.common.init;

import java.util.function.Supplier;

import net.minecraft.item.Item;

import ruiseki.okcore.item.IItem;
import ruiseki.okcore.item.ItemOK;
import ruiseki.okcore.registries.DeferredRegister;
import ruiseki.okcore.registries.RegistryObject;
import ruiseki.okcore.tag.Registries;
import ruiseki.okprogressions.OKPCreativeTab;
import ruiseki.okprogressions.Reference;
import ruiseki.okprogressions.common.item.apple.ItemDiamondApple;
import ruiseki.okprogressions.common.item.apple.ItemEmeraldApple;
import ruiseki.okprogressions.common.item.apple.ItemIronApple;
import ruiseki.okprogressions.common.item.apple.ItemRedstoneApple;
import ruiseki.okprogressions.common.item.charm.ItemClimbingGlove;
import ruiseki.okprogressions.common.item.misc.ItemEnderSack;
import ruiseki.okprogressions.common.item.misc.ItemMyceliumSeeds;
import ruiseki.okprogressions.common.item.tool.ItemBirthdayPickaxe;
import ruiseki.okprogressions.common.item.tool.ItemPaxel;

public final class OKProgressionsItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Reference.MOD_ID);

    public static final RegistryObject<Item> MYCELIUM_SEEDS = register(
        "mycelium_seeds",
        () -> true,
        ItemMyceliumSeeds::new);
    public static final RegistryObject<Item> IRON_APPLE = register("iron_apple", () -> true, ItemIronApple::new);
    public static final RegistryObject<Item> DIAMOND_APPLE = register(
        "diamond_apple",
        () -> true,
        ItemDiamondApple::new);
    public static final RegistryObject<Item> EMERALD_APPLE = register(
        "emerald_apple",
        () -> true,
        ItemEmeraldApple::new);
    public static final RegistryObject<Item> REDSTONE_APPLE = register(
        "redstone_apple",
        () -> true,
        ItemRedstoneApple::new);

    public static final RegistryObject<Item> BIRTHDAY_PICKAXE = register(
        "birthday_pickaxe",
        () -> true,
        ItemBirthdayPickaxe::new);

    public static final RegistryObject<Item> WOODEN_PAXEL = register(
        "wooden_paxel",
        () -> true,
        () -> (IItem) new ItemPaxel(OKProgressionsMaterial.PWOOD).setTextureName(Reference.PREFIX_MOD + "wooden_paxel"));

    public static final RegistryObject<Item> STONE_PAXEL = register(
        "stone_paxel",
        () -> true,
        () -> (IItem) new ItemPaxel(OKProgressionsMaterial.PSTONE).setTextureName(Reference.PREFIX_MOD + "stone_paxel"));

    public static final RegistryObject<Item> IRON_PAXEL = register(
        "iron_paxel",
        () -> true,
        () -> (IItem) new ItemPaxel(OKProgressionsMaterial.PIRON).setTextureName(Reference.PREFIX_MOD + "iron_paxel"));

    public static final RegistryObject<Item> GOLDEN_PAXEL = register(
        "golden_paxel",
        () -> true,
        () -> (IItem) new ItemPaxel(OKProgressionsMaterial.PGOLD).setTextureName(Reference.PREFIX_MOD + "golden_paxel"));

    public static final RegistryObject<Item> DIAMOND_PAXEL = register(
        "diamond_paxel",
        () -> true,
        () -> (IItem) new ItemPaxel(OKProgressionsMaterial.PDIAMOND).setTextureName(Reference.PREFIX_MOD + "diamond_paxel"));

    public static final RegistryObject<Item> STONE_STICK = register(
        "stone_stick",
        () -> true,
        () -> (IItem) new ItemOK().setTextureName(Reference.PREFIX_MOD + "stone_stick")
            .setCreativeTab(OKPCreativeTab.INSTANCE));

    public static final RegistryObject<Item> ENDER_DUST = register(
        "ender_dust",
        () -> true,
        () -> (IItem) new ItemOK().setTextureName(Reference.PREFIX_MOD + "ender_dust")
            .setCreativeTab(OKPCreativeTab.INSTANCE));

    public static final RegistryObject<Item> CLIMBING_GLOVE = register(
        "climbing_glove",
        () -> true,
        ItemClimbingGlove::new);
    public static final RegistryObject<Item> ENDER_SACK = register("ender_sack", () -> true, ItemEnderSack::new);

    public static final RegistryObject<Item> TINY_COAL = register(
        "tiny_coal",
        () -> true,
        () -> (IItem) new ItemOK().setTextureName(Reference.PREFIX_MOD + "tiny_coal")
            .setCreativeTab(OKPCreativeTab.INSTANCE));

    public static final RegistryObject<Item> TINY_CHARCOAL = register(
        "tiny_charcoal",
        () -> true,
        () -> (IItem) new ItemOK().setTextureName(Reference.PREFIX_MOD + "tiny_charcoal")
            .setCreativeTab(OKPCreativeTab.INSTANCE));

    private static RegistryObject<Item> register(String name, Supplier<Boolean> configCondition,
        Supplier<IItem> itemSupplier) {
        if (!configCondition.get()) {
            return RegistryObject.empty();
        }

        return ITEMS.register(
            name,
            () -> itemSupplier.get()
                .get());
    }

    public static void register() {
        ITEMS.register();
    }

    private OKProgressionsItems() {}
}
