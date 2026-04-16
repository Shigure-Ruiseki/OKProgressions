package ruiseki.okprogressions;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ruiseki.okcore.helper.LangHelpers;
import ruiseki.okprogressions.common.init.ModBlocks;

public class OKPCreativeTab extends CreativeTabs {

    public static final OKPCreativeTab INSTANCE = new OKPCreativeTab();

    public OKPCreativeTab() {
        super("okprogressions");
    }

    @Override
    public Item getTabIconItem() {
        return ModBlocks.COBBLE_GEN.getItem();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getTranslatedTabLabel() {
        return LangHelpers.localize("creativetab." + getTabLabel());
    }
}
