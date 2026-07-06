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
        if (worldObj.isRemote) return;

        verifyUuid(worldObj);
        if (fakePlayer == null) {
            fakePlayer = PlayerHelpers.initFakePlayer((WorldServer) worldObj, this.uuid, "block_miner");
            if (fakePlayer == null) {
                OKProgressions.okLog("Fake player failed to init ");
                return;
            }
        }
        tryEquipItem();

        BlockPos start = getPos().offset(this.direction);
        if (targetPos == null || targetPos != start) targetPos = start; // not sure if this is needed

        if (isRunning()) {
            if (!isCurrentlyMining) {
                if (!worldObj.isAirBlock(targetPos.x, targetPos.y, targetPos.z)) {
                    isCurrentlyMining = true;
                    curBlockDamage = 0;
                } else {
                    isCurrentlyMining = false;
                    resetProgress(targetPos.x, targetPos.y, targetPos.z);
                }
            }
        } else {
            if (isCurrentlyMining) {
                isCurrentlyMining = false;
                resetProgress(targetPos.x, targetPos.y, targetPos.z);
            }
        }

        if (isCurrentlyMining) {
            Block targetBlock = targetPos.getBlock(worldObj);
            int targetMeta = targetPos.getBlockMetadata(worldObj);
            curBlockDamage += targetBlock
                .getPlayerRelativeBlockHardness(fakePlayer.get(), worldObj, targetPos.x, targetPos.y, targetPos.z);
            if (curBlockDamage >= 1.0f) {
                isCurrentlyMining = false;
                resetProgress(targetPos.x, targetPos.y, targetPos.z);
                FakePlayer player = fakePlayer.get();
                if (player != null) {
                    player.theItemInWorldManager.tryHarvestBlock(targetPos.x, targetPos.y, targetPos.z);
                }
            } else {
                worldObj.destroyBlockInWorldPartially(
                    uuid.hashCode(),
                    targetPos.x,
                    targetPos.y,
                    targetPos.z,
                    (int) (curBlockDamage * 10.0F) - 1);
            }
        }
    }

    private void verifyUuid(World world) {
        if (uuid == null) {
            uuid = UUID.randomUUID();
            onSendUpdate();
        }
    }

    private void tryEquipItem() {
        FakePlayer player = fakePlayer.get();
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
        if (isCurrentlyMining && uuid != null) {
            resetProgress(x, y, z);
        }
    }

    private void resetProgress(int x, int y, int z) {
        if (uuid != null) {
            worldObj.destroyBlockInWorldPartially(uuid.hashCode(), x, y, z, -1);
            curBlockDamage = 0;
        }
    }
}
