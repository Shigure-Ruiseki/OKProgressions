package ruiseki.okprogressions.common.addon.nei;

import java.util.Locale;

import com.gtnewhorizon.gtnhlib.util.data.IMod;

import cpw.mods.fml.common.Loader;

public enum Mods implements IMod {

    NotEnoughItems("NotEnoughItems"),;

    public final String modid;
    public final String resourceDomain;
    private Boolean loaded;

    Mods(String modid) {
        this.modid = modid;
        this.resourceDomain = modid != null ? modid.toLowerCase(Locale.ENGLISH) : null;
    }

    @Override
    public boolean isModLoaded() {
        if (loaded == null) {
            if (modid != null) {
                loaded = Loader.isModLoaded(modid);
            } else loaded = false;
        }
        return loaded;
    }

    @Override
    public String getID() {
        return modid;
    }

    @Override
    public String getResourceLocation() {
        return resourceDomain;
    }
}
