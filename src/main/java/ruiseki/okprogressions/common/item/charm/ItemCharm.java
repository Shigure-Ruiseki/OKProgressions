package ruiseki.okprogressions.common.item.charm;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import baubles.api.BaubleType;
import baubles.api.expanded.BaubleItemHelper;
import baubles.api.expanded.IBaubleExpanded;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ruiseki.okcore.config.configurable.ConfigurableItem;
import ruiseki.okcore.config.extendedconfig.ExtendedConfig;
import ruiseki.okcore.config.extendedconfig.ItemConfig;
import ruiseki.okcore.entity.EntityDoppleganger;
import ruiseki.okcore.helper.LangHelpers;
import ruiseki.okcore.item.IItemToggle;
import ruiseki.okprogressions.common.addon.nei.Mods;

@Optional.InterfaceList({
    @Optional.Interface(modid = "Baubles|Expanded", iface = "baubles.api.expanded.IBaubleExpanded"),
    @Optional.Interface(modid = "Baubles", iface = "baubles.api.IBauble"), })
public abstract class ItemCharm extends ConfigurableItem implements IBaubleExpanded, IItemToggle {

    public ItemCharm(ExtendedConfig<ItemConfig> eConfig, int durability) {
        super(eConfig);
        this.setMaxDamage(durability);
    }

    @Override
    public void toggle(EntityPlayer player, ItemStack held) {
        setOn(held, !isOn(held));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack, int pass) {
        return canTick(stack);
    }

    public boolean canTick(ItemStack stack) {
        return isOn(stack) && (stack.getItemDamage() < stack.getMaxDamage());
    }

    public void damageCharm(EntityPlayer living, ItemStack stack) {
        if (!living.capabilities.isCreativeMode && !living.worldObj.isRemote) {
            stack.damageItem(1, living);
            if (stack.stackSize <= 0 && living instanceof EntityPlayerMP playerMP) {
                playerMP.sendContainerToPlayer(living.inventoryContainer);
            }
        }
    }

    @Override
    @Optional.Method(modid = "Baubles|Expanded")
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (!EntityDoppleganger.isTruePlayer(player)) return itemStack;

        if (Mods.Baubles.isModLoaded()) {
            if (canEquip(itemStack, player)) {
                BaubleItemHelper.onBaubleRightClick(itemStack, world, player);
            }
        }

        return itemStack;
    }

    @Override
    @Optional.Method(modid = "Baubles|Expanded")
    public String[] getBaubleTypes(ItemStack itemstack) {
        return new String[] { "charm" };
    }

    @Override
    @Optional.Method(modid = "Baubles")
    public BaubleType getBaubleType(ItemStack itemstack) {
        return null;
    }

    @Override
    @Optional.Method(modid = "Baubles")
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    @Optional.Method(modid = "Baubles")
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    @Optional.Method(modid = "Baubles")
    public boolean canEquip(ItemStack stack, EntityLivingBase player) {
        return true;
    }

    @Override
    @Optional.Method(modid = "Baubles")
    public boolean canUnequip(ItemStack stack, EntityLivingBase player) {
        return true;
    }

    @Override
    @Optional.Method(modid = "Baubles")
    public void onWornTick(ItemStack stack, EntityLivingBase entity) {
        if (entity instanceof EntityPlayer player) onTick(stack, player);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isHeld) {
        if (entity instanceof EntityPlayer player) onTick(stack, player);
    }

    public abstract void onTick(ItemStack stack, EntityPlayer player);

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean flag) {
        String onoff = this.isOn(stack) ? "on" : "off";
        list.add(LangHelpers.localize("tooltip.charm.info") + LangHelpers.localize("tooltip.charm." + onoff));
        super.addInformation(stack, player, list, flag);
    }
}
