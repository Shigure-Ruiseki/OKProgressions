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
    private UUID uuid;

    @NBTPersist
    private int buildSpeed = 1;

    @NBTPersist
    public int TIMER_FULL = 1;

    private WeakReference<FakePlayer> fakePlayer;

    public TEBlockPlacer() {
        super(9);
        this.setSlotsForBoth();
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return Block.getBlockFromItem(stack.getItem()) != null;
    }

    public boolean isFuelBurning() {
        return this.timer > 0 && this.timer < TIMER_FULL;
    }

    private void verifyFakePlayer(WorldServer w) {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        if (fakePlayer == null) {
            fakePlayer = FakePlayerHelpers.initFakePlayer(
                w,
                this.uuid,
                this.getBlockType()
                    .getLocalizedName());
            if (fakePlayer == null) {
                OKProgressions.okLog(Level.ERROR, "Fake player failed to init ");
            }
        }
    }

    @Override
    protected void doUpdate() {
        super.doUpdate();
        shiftAllUp();
        boolean trigger = false;
        if (!isRunning()) {
            markDirty();
            return;
        }

        if (this.worldObj instanceof WorldServer worldServer) {
            verifyFakePlayer(worldServer);
        }

        ItemStack stack = getStackInSlot(0);
        if (stack == null) {
            timer = TIMER_FULL;
        } else {
            timer -= buildSpeed;
            if (timer <= 0) {
                timer = TIMER_FULL;
                trigger = true;
            }
        }

        if (trigger) {
            if (fakePlayer != null) {
                EntityHelpers.setEntityFacing(fakePlayer.get(), this.getDirection());
                BlockPos placePos = getPos().offset(this.getDirection());
                if (this.worldObj.isAirBlock(placePos.getX(), placePos.getY(), placePos.getZ())) {
                    PlaceBlockHelpers.buildStackAsPlayer(this.worldObj, fakePlayer.get(), placePos, stack);
                }
            }
        }
        this.markDirty();
    }

    @Override
    public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
        return super.buildUI(data, syncManager, settings);
    }
}
