package ruiseki.okprogressions.common.soil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;

import ruiseki.okcore.json.AbstractJsonReader;

public class SoilReader extends AbstractJsonReader<List<SoilMaterial>> {

    public SoilReader(File path) {
        super(path);
    }

    @Override
    public List<SoilMaterial> read() throws IOException {
        List<SoilMaterial> materials = new ArrayList<>();
        if (path.isDirectory()) {
            for (File file : listJsonFiles(path)) materials.addAll(readFile(file));
        } else if (path.exists()) {
            materials.addAll(readFile(path));
        }
        this.cache = materials;
        rebuildIndex();
        return materials;
    }

    @Override
    protected List<SoilMaterial> readFile(JsonElement root, File file) {
        List<SoilMaterial> list = new ArrayList<>();
        if (root.isJsonArray()) {
            for (JsonElement e : root.getAsJsonArray()) {
                if (e.isJsonObject()) {
                    SoilMaterial m = new SoilMaterial();
                    m.setSourceFile(file);
                    m.read(e.getAsJsonObject());
                    list.add(m);
                }
            }
        } else if (root.isJsonObject()) {
            SoilMaterial m = new SoilMaterial();
            m.setSourceFile(file);
            m.read(root.getAsJsonObject());
            list.add(m);
        }
        return list;
    }

    @Override
    protected void rebuildIndex() {
        super.rebuildIndex();
        if (this.cache == null) return;

        for (SoilMaterial m : this.cache) {
            if (m.inputItem != null) {
                index.put(m.inputItem, m);
            }
        }
    }
}
