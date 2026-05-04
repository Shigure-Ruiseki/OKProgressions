package ruiseki.okprogressions.common.soil;

import ruiseki.okcore.json.AbstractJsonWriter;

import java.io.File;
import java.util.List;

public class SoilWriter extends AbstractJsonWriter<List<SoilMaterial>> {
    public SoilWriter(File path) {
        super(path);
    }
}
