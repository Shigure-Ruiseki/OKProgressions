package ruiseki.okprogressions.common.crop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;

import ruiseki.okcore.init.IRegistry;
import ruiseki.okcore.inventory.ItemStackKey;

public class CropRegistry implements IRegistry {

    private static final Map<ItemStackKey, CropMaterial> ITEM_MAP = new HashMap<>();

    public static void register(CropMaterial material) {
        if (material == null || !material.validate()
            || material.getStack() == null
            || material.getStack()
                .getItem() == null)
            return;
        ITEM_MAP.put(ItemStackKey.of(material.getStack()), material);
    }

    public static CropMaterial getByStack(ItemStack stack) {
        if (stack == null) return null;
        return ITEM_MAP.get(ItemStackKey.of(stack));
    }

    public static void clear() {
        ITEM_MAP.clear();
    }

    public static int size() {
        return ITEM_MAP.size();
    }

    public Collection<CropMaterial> getSoils() {
        return new ArrayList<>(ITEM_MAP.values());
    }
}
