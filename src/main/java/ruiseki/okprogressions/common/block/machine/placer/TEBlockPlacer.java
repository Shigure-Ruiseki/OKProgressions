package ruiseki.okprogressions.common.block.machine.placer;

import java.lang.ref.WeakReference;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;

import org.apache.logging.log4j.Level;

import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;

import ruiseki.okcore.datastructure.BlockPos;
import ruiseki.okcore.persist.nbt.NBTPersist;
import ruiseki.okprogressions.OKProgressions;
import ruiseki.okprogressions.common.block.machine.TEMachineInventory;
import ruiseki.okprogressions.common.helper.EntityHelpers;
import ruiseki.okprogressions.common.helper.FakePlayerHelpers;
import ruiseki.okprogressions.common.helper.PlaceBlockHelpers;

public class TEBlockPlacer extends TEMachineInventory {

    @NBTPersist
    private UUID uuid = UUID.randomUUID();

    private static final int BUILD_SPEED = 1;
    public static final int TIMER_FULL = 1;

    private WeakReference<FakePlayer> fakePlayer;

    public TEBlockPlacer() {
        super(9);
        this.inventory.setSlotsForBoth();
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (stack == null) return false;
        return Block.getBlockFromItem(stack.getItem()) != null;
    }

    public boolean isFuelBurning() {
        return this.timer > 0 && this.timer < TIMER_FULL;
    }

    private void verifyFakePlayer(WorldServer w) {
        if (this.fakePlayer == null || this.fakePlayer.get() == null) {
            this.fakePlayer = FakePlayerHelpers.initFakePlayer(
                w,
                this.uuid,
                this.getBlockType()
                    .getLocalizedName());
            if (this.fakePlayer == null || this.fakePlayer.get() == null) {
                OKProgressions.okLog(Level.ERROR, "Fake player failed to init");
            }
        }
    }

    @Override
    protected void doUpdate() {
        super.doUpdate();
        shiftAllUp();

        if (this.worldObj == null || this.worldObj.isRemote) {
            return;
        }

        if (!isRunning()) {
            markDirty();
            return;
        }

        if (this.worldObj instanceof WorldServer worldServer) {
            verifyFakePlayer(worldServer);
        }

        boolean trigger = false;
        ItemStack stack = getStackInSlot(0);
        if (stack == null) {
            this.timer = TIMER_FULL;
        } else {
            this.timer -= BUILD_SPEED;
            if (this.timer <= 0) {
                this.timer = TIMER_FULL;
                trigger = true;
            }
        }

        if (trigger && this.fakePlayer != null && this.fakePlayer.get() != null) {
            FakePlayer player = this.fakePlayer.get();
            EntityHelpers.setEntityFacing(player, this.getDirection());
            BlockPos placePos = getPos().offset(this.getDirection());

            if (this.worldObj.isAirBlock(placePos.getX(), placePos.getY(), placePos.getZ())) {
                ItemStack itemForPlacement = stack.copy();
                boolean success = PlaceBlockHelpers
                    .buildStackAsPlayer(this.worldObj, player, placePos, itemForPlacement);

                if (success || itemForPlacement.stackSize != stack.stackSize) {
                    if (itemForPlacement.stackSize <= 0) {
                        this.setInventorySlotContents(0, null);
                    } else {
                        this.setInventorySlotContents(0, itemForPlacement);
                    }
                }
            }
        }
        this.markDirty();
    }

    @Override
    public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
        return new BlockPlacerPanel(this, data, syncManager, settings);
    }
}
