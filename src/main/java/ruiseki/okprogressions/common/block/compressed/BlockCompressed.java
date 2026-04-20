package ruiseki.okprogressions.common.block.compressed;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.world.Explosion;

import ruiseki.okcore.block.BlockOK;
import ruiseki.okprogressions.OKPCreativeTab;

public class BlockCompressed extends BlockOK {

    private int meta;
    private int least_quantity;
    private int most_quantity;

    public BlockCompressed(String name, Material material, SoundType soundType, int meta, int least_quantity,
        int most_quantity) {
        super(name, material);
        this.meta = meta;
        this.least_quantity = least_quantity;
        this.most_quantity = most_quantity;
        this.setHarvestLevel("pickaxe", 1);
        this.setStepSound(soundType);
        this.setCreativeTab(OKPCreativeTab.INSTANCE);
        hasSubtypes = true;
        isOpaque = false;
    }

    public BlockCompressed(String name, Material material, SoundType soundType, int least_quantity, int most_quantity) {
        this(name, material, soundType, 0, least_quantity, most_quantity);
    }

    protected BlockCompressed(String name, Material material, SoundType soundType) {
        this(name, material, soundType, 1, 1);
    }

    @Override
    public boolean canDropFromExplosion(Explosion explosionIn) {
        return false;
    }

    @Override
    public int damageDropped(int meta) {
        return this.meta;
    }

    @Override
    public int quantityDropped(int meta, int fortune, Random random) {
        if (this.least_quantity >= this.most_quantity) return this.least_quantity;
        return this.least_quantity + random.nextInt(this.most_quantity - this.least_quantity + fortune + 1);
    }
}
