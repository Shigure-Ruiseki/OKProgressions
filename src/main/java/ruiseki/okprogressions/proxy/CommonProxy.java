package ruiseki.okprogressions.proxy;

import net.minecraftforge.common.MinecraftForge;

import ruiseki.okcore.init.ModBase;
import ruiseki.okcore.network.PacketHandler;
import ruiseki.okcore.proxy.CommonProxyComponent;
import ruiseki.okprogressions.OKProgressions;
import ruiseki.okprogressions.common.network.PacketPlayerFalldamage;
import ruiseki.okprogressions.common.recipe.OreDictCropLoader;

public class CommonProxy extends CommonProxyComponent {

    @Override
    public ModBase getMod() {
        return OKProgressions._instance;
    }

    @Override
    public void registerPacketHandlers(PacketHandler packetHandler) {
        super.registerPacketHandlers(packetHandler);
        packetHandler.register(PacketPlayerFalldamage.class);
    }

    @Override
    public void registerEventHooks() {
        super.registerEventHooks();
        MinecraftForge.EVENT_BUS.register(OreDictCropLoader.INSTANCE);
    }
}
