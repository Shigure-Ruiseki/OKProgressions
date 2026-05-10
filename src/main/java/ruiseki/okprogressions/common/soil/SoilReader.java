package ruiseki.okprogressions.common.soil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import ruiseki.okcore.json.AbstractJsonReader;

public class SoilReader extends AbstractJsonReader<List<SoilMaterial>> {

    public SoilReader(File path) {
        super(path);
    }

    @Override
    public List<SoilMaterial> read() throws IOException {
        this.cache = new ArrayList<>();
        if (path.exists()) {
            if (path.isDirectory()) {
                for (File f : listJsonFiles(path)) {
                    this.cache.addAll(readFile(f));
                }
            } else {
                this.cache.addAll(readFile(path));
            }
        }

        return cache;
    }

    @Override
    protected List<SoilMaterial> readFile(JsonElement root, File file) {
        List<SoilMaterial> results = new ArrayList<>();
        if (root.isJsonArray()) {
            JsonArray array = root.getAsJsonArray();
            for (JsonElement e : array) {
                if (e.isJsonObject()) {
                    SoilMaterial item = new SoilMaterial();
                    item.setSourceFile(file);
                    item.read(e.getAsJsonObject());
                    if (item.validate()) {
                        results.add(item);
                    }
                }
            }
        }
        return results;
    }
}
