package ruiseki.okprogressions.common.soil;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class SoilInfo {
    public ItemStack inputItem;
    public Block renderBlock;
    public int renderMeta;
    public float growthModifier;
    public String[] categories;

    public SoilInfo(ItemStack item, Block block, int meta, float modifier, String[] cats) {
        this.inputItem = item;
        this.renderBlock = block;
        this.renderMeta = meta;
        this.growthModifier = modifier;
        this.categories = cats;
    }
}
