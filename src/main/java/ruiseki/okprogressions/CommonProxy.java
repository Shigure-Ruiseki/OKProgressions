package ruiseki.okprogressions;

import ruiseki.okcore.init.ModBase;
import ruiseki.okcore.network.PacketHandler;
import ruiseki.okcore.proxy.CommonProxyComponent;
import ruiseki.okprogressions.common.network.PacketPlayerFalldamage;

public class CommonProxy extends CommonProxyComponent {

    @Override
    public ModBase getMod() {
        return OKProgressions.instance;
    }

    @Override
    public void registerPacketHandlers(PacketHandler packetHandler) {
        super.registerPacketHandlers(packetHandler);
        packetHandler.register(PacketPlayerFalldamage.class);
    }

    @Override
    public void registerEventHooks() {
        super.registerEventHooks();
    }
}
