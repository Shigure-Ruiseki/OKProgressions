package ruiseki.okprogressions.common.crop;

import java.io.File;
import java.util.List;

import ruiseki.okcore.json.AbstractJsonWriter;

public class CropWriter extends AbstractJsonWriter<List<CropMaterial>> {

    public CropWriter(File path) {
        super(path);
    }
}
