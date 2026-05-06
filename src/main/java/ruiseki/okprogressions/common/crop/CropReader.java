package ruiseki.okprogressions.common.crop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import ruiseki.okcore.json.AbstractJsonReader;

public class CropReader extends AbstractJsonReader<List<CropMaterial>> {

    public CropReader(File path) {
        super(path);
    }

    @Override
    public List<CropMaterial> read() throws IOException {
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
    protected List<CropMaterial> readFile(JsonElement root, File file) {
        List<CropMaterial> results = new ArrayList<>();
        if (root.isJsonArray()) {
            JsonArray array = root.getAsJsonArray();
            for (JsonElement e : array) {
                if (e.isJsonObject()) {
                    CropMaterial item = new CropMaterial();
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
