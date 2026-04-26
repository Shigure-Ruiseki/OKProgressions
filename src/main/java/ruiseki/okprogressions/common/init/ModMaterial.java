package ruiseki.okprogressions.common.init;

import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

public class ModMaterial {

    public static final Item.ToolMaterial BIRTHDAY = EnumHelper
        .addToolMaterial("birthdayToolMaterial", 4, 3061, 100.0F, 10.0F, 22);

    public static final Item.ToolMaterial PWOOD = EnumHelper
        .addToolMaterial("pwoodToolMaterial", 0, 59, 2.0F, 5.0F, 15);
    public static final Item.ToolMaterial PSTONE = EnumHelper
        .addToolMaterial("pstoneToolMaterial", 1, 131, 4.0F, 7.0F, 5);
    public static final Item.ToolMaterial PIRON = EnumHelper
        .addToolMaterial("pironToolMaterial", 2, 250, 6.0F, 8.0F, 14);
    public static final Item.ToolMaterial PGOLD = EnumHelper
        .addToolMaterial("pgoldToolMaterial", 0, 32, 5.0F, 1.2F, 22);
    public static final Item.ToolMaterial PDIAMOND = EnumHelper
        .addToolMaterial("pdiamondToolMaterial", 3, 1561, 8.0F, 10.0F, 10);
}
