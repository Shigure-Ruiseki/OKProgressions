package ruiseki.okprogressions.common.item.charm;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ruiseki.okcore.helper.LangHelpers;
import ruiseki.okcore.item.IItemToggle;
import ruiseki.okcore.item.ItemBauble;
import ruiseki.okprogressions.OKPCreativeTab;

@Optional.InterfaceList({
    @Optional.Interface(modid = "Baubles|Expanded", iface = "baubles.api.expanded.IBaubleExpanded"),
    @Optional.Interface(modid = "Baubles", iface = "baubles.api.IBauble"), })
public abstract class ItemCharm extends ItemBauble implements IItemToggle {

    public ItemCharm(int durability) {
        super();
        this.setMaxDamage(durability);
        this.setCreativeTab(OKPCreativeTab.INSTANCE);
    }

    @Override
    public void toggle(EntityPlayer player, ItemStack held) {
        setOn(held, !isOn(held));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return canTick(stack);
    }

    public boolean canTick(ItemStack stack) {
        return isOn(stack) && (stack.getItemDamage() < stack.getMaxDamage());
    }

    public void damageCharm(EntityPlayer living, ItemStack stack) {
        if (!living.capabilities.isCreativeMode && !living.worldObj.isRemote) {
            stack.damageItem(1, living);
            if (living instanceof EntityPlayerMP playerMP) {
                playerMP.sendContainerToPlayer(living.inventoryContainer);
            }
        }
    }

    @Override
    public String[] getBaubleTypes(ItemStack itemstack) {
        return new String[] { "charm" };
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
