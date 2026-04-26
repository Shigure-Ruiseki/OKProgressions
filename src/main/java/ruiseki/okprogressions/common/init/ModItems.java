package ruiseki.okprogressions.common.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.apache.logging.log4j.Level;

import ruiseki.okcore.item.IItem;
import ruiseki.okcore.item.ItemOK;
import ruiseki.okprogressions.OKPCreativeTab;
import ruiseki.okprogressions.OKProgressions;
import ruiseki.okprogressions.Reference;
import ruiseki.okprogressions.common.item.apple.ItemDiamondApple;
import ruiseki.okprogressions.common.item.apple.ItemEmeraldApple;
import ruiseki.okprogressions.common.item.apple.ItemIronApple;
import ruiseki.okprogressions.common.item.apple.ItemRedstoneApple;
import ruiseki.okprogressions.common.item.misc.ItemMyceliumSeeds;
import ruiseki.okprogressions.common.item.tool.ItemBirthdayPickaxe;
import ruiseki.okprogressions.common.item.tool.ItemPaxel;

public enum ModItems {

    // spotless: off

    MYCELIUM_SEEDS(new ItemMyceliumSeeds()),
    IRON_APPLE(new ItemIronApple()),
    DIAMOND_APPLE(new ItemDiamondApple()),
    EMERALD_APPLE(new ItemEmeraldApple()),
    REDSTONE_APPLE(new ItemRedstoneApple()),

    BIRTHDAY_PICKAXE(new ItemBirthdayPickaxe()),

    WOODEN_PAXEL(
        (IItem) new ItemPaxel("wooden_paxel", ModMaterial.PWOOD).setTextureName(Reference.PREFIX_MOD + "wooden_paxel")),
    STONE_PAXEL(
        (IItem) new ItemPaxel("stone_paxel", ModMaterial.PSTONE).setTextureName(Reference.PREFIX_MOD + "stone_paxel")),
    IRON_PAXEL(
        (IItem) new ItemPaxel("iron_paxel", ModMaterial.PIRON).setTextureName(Reference.PREFIX_MOD + "iron_paxel")),
    GOLDEN_PAXEL(
        (IItem) new ItemPaxel("golden_paxel", ModMaterial.PGOLD).setTextureName(Reference.PREFIX_MOD + "golden_paxel")),
    DIAMOND_PAXEL((IItem) new ItemPaxel("diamond_paxel", ModMaterial.PDIAMOND)
        .setTextureName(Reference.PREFIX_MOD + "diamond_paxel")),

    STONE_STICK((IItem) new ItemOK("stone_stick").setTextureName(Reference.PREFIX_MOD + "stone_stick")
        .setCreativeTab(OKPCreativeTab.INSTANCE)),

    ENDER_DUST((IItem) new ItemOK("ender_dust").setTextureName(Reference.PREFIX_MOD + "ender_dust")
        .setCreativeTab(OKPCreativeTab.INSTANCE)),;

    // spotless: on

    public static final ModItems[] VALUES = values();

    public static void preInit() {
        for (ModItems item : VALUES) {
            if (item.item == null) {
                continue;
            }
            try {
                item.item.init();
                OKProgressions.okLog(Level.INFO, "Successfully initialized " + item.name());
            } catch (Exception e) {
                OKProgressions.okLog(Level.ERROR, "Failed to initialize item: +" + item.name());
            }
        }
    }

    private final IItem item;

    ModItems(IItem block) {
        this.item = block;
    }

    public Item getItem() {
        return item.getItem();
    }

    public ItemStack newItemStack() {
        return newItemStack(1);
    }

    public ItemStack newItemStack(int count) {
        return newItemStack(count, 0);
    }

    public ItemStack newItemStack(int count, int meta) {
        return item != null ? new ItemStack(this.getItem(), count, meta) : null;
    }
}
