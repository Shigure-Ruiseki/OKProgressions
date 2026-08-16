package ruiseki.okprogressions.common.item.misc;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import ruiseki.okcore.config.configurable.ConfigurableItem;
import ruiseki.okcore.config.extendedconfig.ExtendedConfig;
import ruiseki.okcore.config.extendedconfig.ItemConfig;
import ruiseki.okcore.helper.LangHelpers;
import ruiseki.okprogressions.Reference;

public class ItemEnderSack extends ConfigurableItem {

    private static ItemEnderSack _instance = null;

    /**
     * Get the unique instance.
     *
     * @return The instance.
     */
    public static ItemEnderSack getInstance() {
        return _instance;
    }

    public ItemEnderSack(ExtendedConfig<ItemConfig> eConfig) {
        super(eConfig);
        this.setMaxStackSize(1);
        this.setTextureName(Reference.PREFIX_MOD + "ender_sack");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        player.displayGUIChest(player.getInventoryEnderChest());
        if (!world.isRemote) {
            String soundName = world.rand.nextDouble() > 0.5 ? "mob.endermen.portal" : "random.chestopen";
            world.playSoundAtEntity(player, soundName, 0.5F, 1.0F);
        }
        return stack;
    }

    @Override
    public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List<String> list, boolean p_77624_4_) {
        list.add(LangHelpers.localize("tooltip.ender_sack"));
    }
}
