package ruiseki.okprogresstion;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ruiseki.okcore.helper.LangHelpers;

public class OKBCreativeTab extends CreativeTabs {

    public static final OKBCreativeTab INSTANCE = new OKBCreativeTab();

    public OKBCreativeTab() {
        super("okprogresstion");
    }

    @Override
    public Item getTabIconItem() {
        return Items.apple;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getTranslatedTabLabel() {
        return LangHelpers.localize("creativetab." + getTabLabel());
    }
}
