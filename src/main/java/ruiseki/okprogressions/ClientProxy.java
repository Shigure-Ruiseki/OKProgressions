package ruiseki.okprogressions;

import ruiseki.okcore.client.key.IKeyRegistry;
import ruiseki.okcore.init.ModBase;
import ruiseki.okcore.proxy.ClientProxyComponent;

public class ClientProxy extends ClientProxyComponent {

    public ClientProxy() {
        super(new CommonProxy());
    }

    @Override
    public ModBase getMod() {
        return OKProgressions.instance;
    }

    @Override
    public void registerKeyBindings(IKeyRegistry keyRegistry) {
        super.registerKeyBindings(keyRegistry);
    }

    @Override
    public void registerEventHooks() {
        super.registerEventHooks();
    }
}
