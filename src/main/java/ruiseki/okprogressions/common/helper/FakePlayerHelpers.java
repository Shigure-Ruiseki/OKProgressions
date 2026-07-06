package ruiseki.okprogressions.common.helper;

import java.lang.ref.WeakReference;
import java.util.UUID;

import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

import org.apache.logging.log4j.Level;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.FMLCommonHandler;
import ruiseki.okprogressions.OKProgressions;
import ruiseki.okprogressions.Reference;

public class FakePlayerHelpers {

    public static WeakReference<FakePlayer> initFakePlayer(WorldServer ws, UUID uname, String blockName) {
        GameProfile breakerProfile = new GameProfile(uname, Reference.MOD_NAME + ".fake_player." + blockName);
        WeakReference<FakePlayer> fakePlayer;
        try {
            fakePlayer = new WeakReference<>(FakePlayerFactory.get(ws, breakerProfile));
        } catch (Exception e) {
            OKProgressions.okLog(Level.ERROR, "Exception thrown trying to create fake player : ", e);
            fakePlayer = null;
        }

        if (fakePlayer == null) return null;

        FakePlayer player = fakePlayer.get();
        if (player == null) return null;

        player.onGround = true;

        player.playerNetServerHandler = new NetHandlerPlayServer(
            FMLCommonHandler.instance()
                .getMinecraftServerInstance(),
            new NetworkManager(false),
            player) {

            @Override
            public void sendPacket(Packet packetIn) {

            }
        };

        return fakePlayer;
    }
}
