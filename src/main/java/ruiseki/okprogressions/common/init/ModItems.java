package ruiseki.okprogressions.common.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.apache.logging.log4j.Level;

import ruiseki.okcore.item.IItem;
import ruiseki.okprogressions.OKProgressions;
import ruiseki.okprogressions.common.item.apple.ItemDiamondApple;
import ruiseki.okprogressions.common.item.apple.ItemEmeraldApple;
import ruiseki.okprogressions.common.item.apple.ItemGoldenApple;
import ruiseki.okprogressions.common.item.apple.ItemIronApple;
import ruiseki.okprogressions.common.item.apple.ItemRedstoneApple;
import ruiseki.okprogressions.common.item.misc.ItemMyceliumSeeds;

public enum ModItems {

    // spotless: off

    MYCELIUM_SEEDS(new ItemMyceliumSeeds()),
    IRON_APPLE(new ItemIronApple()),
    GOLDEN_APPLE(new ItemGoldenApple()),
    DIAMOND_APPLE(new ItemDiamondApple()),
    EMERALD_APPLE(new ItemEmeraldApple()),
    REDSTONE_APPLE(new ItemRedstoneApple()),

    ;

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

    public Item getBlock() {
        return item.getItem();
    }

    public ItemStack newItemStack() {
        return newItemStack(1);
    }

    public ItemStack newItemStack(int count) {
        return newItemStack(count, 0);
    }

    public ItemStack newItemStack(int count, int meta) {
        return item != null ? new ItemStack(this.getBlock(), count, meta) : null;
    }
}
