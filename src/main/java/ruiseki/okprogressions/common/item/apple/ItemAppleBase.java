package ruiseki.okprogressions.common.item.apple;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

import ruiseki.okcore.config.configurable.ConfigurableItemFood;
import ruiseki.okcore.config.extendedconfig.ExtendedConfig;
import ruiseki.okcore.config.extendedconfig.ItemConfig;

public abstract class ItemAppleBase extends ConfigurableItemFood {

    public ItemAppleBase(ExtendedConfig<ItemConfig> eConfig, int amount, float saturation, boolean isWolfFood) {
        super(eConfig, amount, saturation, isWolfFood);
        this.setAlwaysEdible();
    }

    @Override
    public boolean hasEffect(ItemStack stack, int pass) {
        return true;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.epic;
    }
}
