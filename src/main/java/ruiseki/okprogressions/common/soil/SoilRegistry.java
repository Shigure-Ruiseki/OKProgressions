package ruiseki.okprogressions.common.soil;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;

import ruiseki.okcore.init.IRegistry;
import ruiseki.okcore.inventory.ItemStackKey;
import ruiseki.okcore.json.ItemJson;

public class SoilRegistry implements IRegistry {

    private static final Map<ItemStackKey, SoilInfo> ITEM_MAP = new HashMap<>();

    public static void clear() {
        ITEM_MAP.clear();
    }

    public static void register(SoilMaterial material) {
        if (material == null || !material.validate()) return;

        ItemJson itemData = ItemJson.parseItemString(material.inputItem);
        ItemStack stack = ItemJson.resolveItemStack(itemData);

        if (stack == null || stack.getItem() == null) return;

        SoilInfo info = new SoilInfo(stack, material.growthModifier, material.categories.toArray(new String[0]));

        ITEM_MAP.put(ItemStackKey.of(stack), info);
    }

    public static SoilInfo findByStack(ItemStack stack) {
        if (stack == null) return null;
        return ITEM_MAP.get(ItemStackKey.of(stack));
    }

    public static int size() {
        return ITEM_MAP.size();
    }
}
