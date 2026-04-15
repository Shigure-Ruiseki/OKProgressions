package ruiseki.okprogresstion;

import ruiseki.okcore.client.key.IKeyRegistry;
import ruiseki.okcore.init.ModBase;
import ruiseki.okcore.proxy.ClientProxyComponent;

public class ClientProxy extends ClientProxyComponent {

    public ClientProxy() {
        super(new CommonProxy());
    }

    @Override
    public ModBase getMod() {
        return OKProgression.instance;
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
