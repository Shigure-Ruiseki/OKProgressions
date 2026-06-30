package ruiseki.okprogressions.common.data.crop;

import java.io.IOException;
import java.util.Objects;

import net.minecraft.item.ItemStack;

import ruiseki.okcore.inventory.ItemStackKey;
import ruiseki.okcore.network.ExtendedBuffer;
import ruiseki.okcore.network.INetworkMaterial;

public class HarvestInfo implements INetworkMaterial {

    private ItemStack stack;
    private float chance;
    private int minRolls;
    private int maxRolls;

    public HarvestInfo() {}

    public HarvestInfo(ItemStack stack, float chance, int minRolls, int maxRolls) {
        this.stack = stack;
        this.chance = chance;
        this.minRolls = minRolls;
        this.maxRolls = maxRolls;
    }

    public ItemStack getStack() {
        return stack;
    }

    public float getChance() {
        return chance;
    }

    public int getMinRolls() {
        return minRolls;
    }

    public int getMaxRolls() {
        return maxRolls;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof HarvestInfo other)) return false;
        if (Float.compare(other.chance, chance) != 0) return false;
        if (minRolls != other.minRolls) return false;
        if (maxRolls != other.maxRolls) return false;

        ItemStackKey key1 = ItemStackKey.of(this.stack);
        ItemStackKey key2 = ItemStackKey.of(other.stack);
        return Objects.equals(key1, key2);
    }

    @Override
    public int hashCode() {
        ItemStackKey key = ItemStackKey.of(this.stack);
        int result = key != null ? key.hashCode() : 0;

        result = 31 * result + Float.floatToIntBits(chance);
        result = 31 * result + minRolls;
        result = 31 * result + maxRolls;
        return result;
    }

    @Override
    public String toString() {
        ItemStackKey key = ItemStackKey.of(this.stack);
        return "HarvestInfo{" + "item="
            + (key != null ? key.getItem() : "null")
            + ", meta="
            + (key != null ? key.getMeta() : 0)
            + ", size="
            + (stack != null ? stack.stackSize : 0)
            + ", chance="
            + chance
            + ", minRolls="
            + minRolls
            + ", maxRolls="
            + maxRolls
            + '}';
    }

    public void toNetwork(ExtendedBuffer buffer) throws IOException {
        buffer.writeItemStackToBuffer(this.stack);
        buffer.writeFloat(this.chance);
        buffer.writeInt(this.minRolls);
        buffer.writeInt(this.maxRolls);
    }

    public void fromNetwork(ExtendedBuffer buffer) throws IOException {
        this.stack = buffer.readItemStackFromBuffer();
        this.chance = buffer.readFloat();
        this.minRolls = buffer.readInt();
        this.maxRolls = buffer.readInt();
    }
}
