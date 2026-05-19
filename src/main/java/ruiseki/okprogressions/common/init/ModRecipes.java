package ruiseki.okprogressions.common.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import ruiseki.okcore.init.IInitListener;

public class ModRecipes implements IInitListener {

    @Override
    public void onInit(Step step) {
        if (step == Step.POSTINIT) {
            blockRecipes();
            itemRecipes();
            fuelRecipes();
        }
    }

    public static void blockRecipes() {

    }

    public static void itemRecipes() {

    }

    private void fuelRecipes() {
        GameRegistry.registerFuelHandler(new IFuelHandler() {

            @Override
            public int getBurnTime(ItemStack fuel) {
                Item item = fuel.getItem();

                if (item == ModBlocks.CHARCOAL_BLOCK.getItem()) return 16000;

                return 0;
            }
        });
    }
}
