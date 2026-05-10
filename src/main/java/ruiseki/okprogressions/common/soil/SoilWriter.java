package ruiseki.okprogressions.common.soil;

import java.io.File;
import java.util.List;

import ruiseki.okcore.json.AbstractJsonWriter;

public class SoilWriter extends AbstractJsonWriter<List<SoilMaterial>> {

    public SoilWriter(File path) {
        super(path);
    }
}
