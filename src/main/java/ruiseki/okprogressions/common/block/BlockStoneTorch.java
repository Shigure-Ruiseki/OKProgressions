package ruiseki.okprogressions.common.block;

import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.item.ItemStack;

import ruiseki.okcore.block.IBlock;
import ruiseki.okcore.recipe.IOreDictEntry;
import ruiseki.okprogressions.OKPCreativeTab;
import ruiseki.okprogressions.Reference;

public class BlockStoneTorch extends BlockTorch implements IBlock, IOreDictEntry {

    public BlockStoneTorch() {
        super();
        this.setBlockTextureName(Reference.PREFIX_MOD + "stone_torch");
        this.setCreativeTab(OKPCreativeTab.INSTANCE);
        this.setLightLevel(1.0F);
    }

    @Override
    public boolean isHasSubtypes() {
        return false;
    }

    @Override
    public Map<String, ItemStack> getOreMappings() {
        return Map.of("torch", new ItemStack(this));
    }

    @Override
    public Block get() {
        return this;
    }
}
