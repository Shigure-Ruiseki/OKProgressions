package ruiseki.okprogressions.proxy;

import ruiseki.okcore.client.key.IKeyRegistry;
import ruiseki.okcore.init.ModBase;
import ruiseki.okcore.proxy.ClientProxyComponent;
import ruiseki.okprogressions.OKProgressions;
import ruiseki.okprogressions.client.renderer.tile.BotanyPotTESR;
import ruiseki.okprogressions.common.block.botanypot.TEBotanyPot;

public class ClientProxy extends ClientProxyComponent {

    public ClientProxy() {
        super(new CommonProxy());
    }

    @Override
    public ModBase getMod() {
        return OKProgressions._instance;
    }

    @Override
    public void registerKeyBindings(IKeyRegistry keyRegistry) {
        super.registerKeyBindings(keyRegistry);
    }

    @Override
    public void registerEventHooks() {
        super.registerEventHooks();
    }

    @Override
    public void registerRenderers() {
        registerRenderer(TEBotanyPot.class, new BotanyPotTESR());

        super.registerRenderers();
    }
}
