package ruiseki.okprogressions.common.block.machine;

import java.lang.ref.WeakReference;
import java.util.UUID;

import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.ForgeDirection;

import org.apache.logging.log4j.Level;

import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.ModularScreen;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;

import lombok.experimental.Delegate;
import ruiseki.okcore.block.IBlockDirection;
import ruiseki.okcore.datastructure.BlockPos;
import ruiseki.okcore.enums.RedstoneMode;
import ruiseki.okcore.helper.EntityHelpers;
import ruiseki.okcore.helper.PlayerHelpers;
import ruiseki.okcore.item.handler.IItemHandler;
import ruiseki.okcore.persist.nbt.NBTPersist;
import ruiseki.okcore.tileentity.IRedstoneMode;
import ruiseki.okcore.tileentity.TileEntityOK;
import ruiseki.okprogressions.OKProgressions;
import ruiseki.okprogressions.Reference;

public class TEMachine extends TileEntityOK
    implements IBlockDirection, IRedstoneMode, TileEntityOK.ITickingTile, IGuiHolder<PosGuiData> {

    @NBTPersist
    protected ForgeDirection direction = ForgeDirection.NORTH;

    @Delegate
    @SuppressWarnings("all")
    protected final ITickingTile tickingTileComponent = new TickingTileComponent(this);

    @NBTPersist
    protected RedstoneMode redstoneMode = RedstoneMode.ALWAYS_ON;

    @NBTPersist
    protected int speed = 1;
    @NBTPersist
    protected int timer;

    public TEMachine() {}

    @Override
    public ForgeDirection getDirection(IBlockAccess world, int x, int y, int z) {
        return direction;
    }

    @Override
    public void setDirection(World world, int x, int y, int z, ForgeDirection direction) {
        this.direction = direction;
        this.markDirty();
        this.onSendUpdate();

    }

    public ForgeDirection getDirection() {
        return direction;
    }

    public boolean isRunning() {
        if (!this.isValid()) return false;
        return this.canRunInWorld(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
    }

    public boolean isValid() {
        return worldObj != null && !this.isInvalid()
            && this.getPos()
                .isLoaded(worldObj);
    }

    @Override
    public ModularScreen createScreen(PosGuiData data, ModularPanel mainPanel) {
        return new ModularScreen(Reference.MOD_ID, mainPanel);
    }

    @Override
    public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
        return new MachinePanel<>(this, data, syncManager, settings);
    }

    @Override
    public RedstoneMode getRedstoneMode() {
        return redstoneMode;
    }

    @Override
    public void setRedstoneMode(RedstoneMode mode) {
        this.redstoneMode = mode;
        this.markDirty();
        this.onSendUpdate();
    }

    protected boolean updateTimerIsZero() {
        timer -= this.getSpeed();
        if (timer < 0) {
            timer = 0;
        }
        return timer == 0;
    }

    public int getSpeed() {
        return speed;
    }

    public static void tryEquipItem(ItemStack item, WeakReference<FakePlayer> fp) {
        if (fp == null) {
            return;
        }

        FakePlayer player = fp.get();
        if (player == null) {
            return;
        }

        player.inventory.setInventorySlotContents(player.inventory.currentItem, item);
    }

    public static void syncEquippedItem(IItemHandler handler, WeakReference<FakePlayer> fp, int slot) {
        if (fp == null) {
            return;
        }

        FakePlayer player = fp.get();
        if (player == null) {
            return;
        }

        handler.extractItem(slot, 64, false);
        handler.insertItem(slot, player.getHeldItem(), false);
    }

    public static boolean leftClickBlock(final WeakReference<FakePlayer> fp, final World world, final BlockPos pos,
        final ForgeDirection side) {

        final FakePlayer fakePlayer = fp.get();
        if (fakePlayer == null) {
            return false;
        }

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        if (!pos.isLoaded(world) || pos.isAirBlock(world)) {
            return false;
        }

        try {
            fakePlayer.theItemInWorldManager.onBlockClicked(x, y, z, side.ordinal());
            return fakePlayer.theItemInWorldManager.tryHarvestBlock(x, y, z);
        } catch (Exception e) {
            OKProgressions
                .okLog(Level.WARN, "Failed to break block via FakePlayer at [%d, %d, %d]: %s", x, y, z, e.getMessage());
            return false;
        }
    }

    public static boolean rightClickBlock(final WeakReference<FakePlayer> fp, final World world, final BlockPos pos,
        final ForgeDirection side) {
        FakePlayer fakePlayer = fp.get();

        if (fakePlayer == null || world == null) {
            return false;
        }

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        if (!pos.isLoaded(world)) {
            return false;
        }

        try {
            ItemStack heldItem = fakePlayer.getHeldItem();
            float hitX = 0.5F;
            float hitY = 0.5F;
            float hitZ = 0.5F;

            boolean success = fakePlayer.theItemInWorldManager
                .activateBlockOrUseItem(fakePlayer, world, heldItem, x, y, z, side.ordinal(), hitX, hitY, hitZ);

            if (success) {
                fakePlayer.inventory.markDirty();
            }

            return success;
        } catch (Exception e) {
            OKProgressions.okLog(
                Level.WARN,
                "Failed to right-click block via FakePlayer at [%d, %d, %d]: %s",
                x,
                y,
                z,
                e.getMessage());
            return false;
        }
    }

    public static boolean tryHarvestBlock(final WeakReference<FakePlayer> fp, final World world, final BlockPos pos) {
        FakePlayer fakePlayer = fp.get();
        if (fakePlayer == null) {
            return false;
        }

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        if (!pos.isLoaded(world) || pos.isAirBlock(world)) {
            return false;
        }

        try {
            return fakePlayer.theItemInWorldManager.tryHarvestBlock(x, y, z);
        } catch (Exception e) {
            OKProgressions.okLog(
                Level.WARN,
                "Failed to harvest block via FakePlayer at [%d, %d, %d]: %s",
                x,
                y,
                z,
                e.getMessage());
            return false;
        }
    }

    public WeakReference<FakePlayer> setupBeforeTrigger(WorldServer sw, String name, UUID uuid) {
        WeakReference<FakePlayer> fp = PlayerHelpers.initFakePlayer(sw, uuid, name);
        if (fp == null) {
            OKProgressions.okLog(Level.ERROR, "Fake player failed to init " + name + " " + uuid);
            return null;
        }

        FakePlayer fakePlayer = fp.get();
        if (fakePlayer == null) {
            return null;
        }

        // fake player facing the same direction as tile. for throwables
        fakePlayer.setPosition(
            this.getPos()
                .getX(),
            this.getPos()
                .getY(),
            this.getPos()
                .getZ()); // seems to help interact() mob drops like milk
        EntityHelpers.setEntityFacing(fakePlayer, this.direction);
        return fp;
    }

    public WeakReference<FakePlayer> setupBeforeTrigger(WorldServer sw, String name) {
        return setupBeforeTrigger(sw, name, UUID.randomUUID());
    }

    protected UUID verifyUuid(UUID uuid) {
        if (uuid == null) {
            uuid = UUID.randomUUID();
            onSendUpdate();
        }
        return uuid;
    }
}
