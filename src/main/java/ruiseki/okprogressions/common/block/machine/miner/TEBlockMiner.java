package ruiseki.okprogressions.common.block.machine.miner;

import java.lang.ref.WeakReference;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;

import ruiseki.okcore.datastructure.BlockPos;
import ruiseki.okcore.helper.PlayerHelpers;
import ruiseki.okcore.persist.nbt.NBTPersist;
import ruiseki.okprogressions.OKProgressions;
import ruiseki.okprogressions.common.block.machine.TEMachineInventory;

public class TEBlockMiner extends TEMachineInventory {

    @NBTPersist
    private UUID uuid;

    @NBTPersist
    private boolean isCurrentlyMining;

    @NBTPersist
    private float curBlockDamage;

    @NBTPersist
    private BlockPos targetPos = null;

    private WeakReference<FakePlayer> fakePlayer;

    public TEBlockMiner() {
        super(0);
    }

    @Override
    protected void doUpdate() {
        if (this.worldObj == null || this.worldObj.isRemote) {
            return;
        }

        this.uuid = verifyUuid(this.uuid);
        if (this.fakePlayer == null || this.fakePlayer.get() == null) {
            this.fakePlayer = PlayerHelpers.initFakePlayer((WorldServer) this.worldObj, this.uuid, "block_miner");
            if (this.fakePlayer == null || this.fakePlayer.get() == null) {
                OKProgressions.okLog("Fake player failed to init");
                return;
            }
        }

        tryEquipItem();

        BlockPos start = getPos().offset(this.direction);
        if (this.targetPos == null || !this.targetPos.equals(start)) {
            this.targetPos = start;
        }

        if (isRunning()) {
            if (!this.isCurrentlyMining) {
                if (!this.worldObj.isAirBlock(this.targetPos.x, this.targetPos.y, this.targetPos.z)) {
                    this.isCurrentlyMining = true;
                    this.curBlockDamage = 0;
                } else {
                    this.isCurrentlyMining = false;
                    resetProgress(this.targetPos.x, this.targetPos.y, this.targetPos.z);
                }
            }
        } else {
            if (this.isCurrentlyMining) {
                this.isCurrentlyMining = false;
                resetProgress(this.targetPos.x, this.targetPos.y, this.targetPos.z);
            }
        }

        if (this.isCurrentlyMining) {
            Block targetBlock = this.targetPos.getBlock(this.worldObj);
            FakePlayer player = this.fakePlayer.get();

            if (player != null && targetBlock != null) {
                this.curBlockDamage += targetBlock.getPlayerRelativeBlockHardness(
                    player,
                    this.worldObj,
                    this.targetPos.x,
                    this.targetPos.y,
                    this.targetPos.z);

                if (this.curBlockDamage >= 1.0f) {
                    this.isCurrentlyMining = false;
                    resetProgress(this.targetPos.x, this.targetPos.y, this.targetPos.z);
                    player.theItemInWorldManager.tryHarvestBlock(this.targetPos.x, this.targetPos.y, this.targetPos.z);
                } else {
                    this.worldObj.destroyBlockInWorldPartially(
                        this.uuid.hashCode(),
                        this.targetPos.x,
                        this.targetPos.y,
                        this.targetPos.z,
                        (int) (this.curBlockDamage * 10.0F) - 1);
                }
            }
        }
    }

    private void tryEquipItem() {
        FakePlayer player = this.fakePlayer.get();
        if (player != null && player.getHeldItem() == null) {
            ItemStack unbreakingPickaxe = new ItemStack(Items.diamond_pickaxe, 1);
            unbreakingPickaxe.addEnchantment(Enchantment.efficiency, 3);

            NBTTagCompound tag = new NBTTagCompound();
            tag.setBoolean("Unbreakable", true);
            unbreakingPickaxe.setTagCompound(tag);

            player.setCurrentItemOrArmor(0, unbreakingPickaxe);
        }
    }

    public void breakBlock(World worldIn, int x, int y, int z) {
        if (this.isCurrentlyMining && this.uuid != null) {
            resetProgress(x, y, z);
        }
    }

    private void resetProgress(int x, int y, int z) {
        if (this.uuid != null && this.worldObj != null) {
            this.worldObj.destroyBlockInWorldPartially(this.uuid.hashCode(), x, y, z, -1);
            this.curBlockDamage = 0;
        }
    }
}
