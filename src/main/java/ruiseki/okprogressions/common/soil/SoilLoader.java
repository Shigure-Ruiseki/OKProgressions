package ruiseki.okprogressions.common.soil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import org.apache.logging.log4j.Level;

import ruiseki.okcore.init.IInitListener;
import ruiseki.okcore.json.ItemJson;
import ruiseki.okprogressions.OKProgressions;
import ruiseki.okprogressions.Reference;

public class SoilLoader implements IInitListener {

    private static final File configDir = new File("config/" + Reference.MOD_ID + "/soils.json");

    @Override
    public void onInit(Step step) {
        if (step != Step.POSTINIT) return;

        SoilReader reader = new SoilReader(configDir);
        SoilWriter writer = new SoilWriter(configDir);

        try {
            File[] files = configDir.listFiles();
            if (!configDir.exists() || files == null || files.length == 0) {
                List<SoilMaterial> defaults = createDefaults();
                writer.write(defaults);
            }

            List<SoilMaterial> materials = reader.read();
            for (SoilMaterial material : materials) {
                SoilRegistry.register(material);
            }

            OKProgressions.okLog("Loaded " + SoilRegistry.size() + " soil types.");

        } catch (IOException e) {
            OKProgressions.okLog(Level.ERROR, "Could not load soil configs!" + e);
        }
    }

    private static List<SoilMaterial> createDefaults() {
        List<SoilMaterial> list = new ArrayList<>();

        SoilMaterial dirt = new SoilMaterial();
        dirt.inputItem = ItemJson.toItemString(new ItemStack(Blocks.dirt));
        dirt.growthModifier = 0f;
        dirt.categories = Collections.singletonList("dirt");
        list.add(dirt);

        SoilMaterial end_stone = new SoilMaterial();
        end_stone.inputItem = ItemJson.toItemString(new ItemStack(Blocks.end_stone));
        end_stone.growthModifier = -0.5f;
        end_stone.categories = Collections.singletonList("end_stone");
        list.add(end_stone);

        SoilMaterial farmland = new SoilMaterial();
        farmland.inputItem = ItemJson.toItemString(new ItemStack(Blocks.farmland, 1, 7));
        farmland.growthModifier = 0.15f;
        farmland.categories = Arrays.asList("dirt", "farmland");
        list.add(farmland);

        SoilMaterial grass = new SoilMaterial();
        grass.inputItem = ItemJson.toItemString(new ItemStack(Blocks.grass));
        grass.growthModifier = 0.05f;
        grass.categories = Arrays.asList("dirt", "grass");
        list.add(grass);

        SoilMaterial mycelium = new SoilMaterial();
        mycelium.inputItem = ItemJson.toItemString(new ItemStack(Blocks.mycelium));
        mycelium.growthModifier = 0.05f;
        mycelium.categories = Arrays.asList("dirt", "mushroom");
        list.add(mycelium);

        SoilMaterial netherrack = new SoilMaterial();
        netherrack.inputItem = ItemJson.toItemString(new ItemStack(Blocks.netherrack));
        netherrack.growthModifier = 0f;
        netherrack.categories = Collections.singletonList("nether");
        list.add(netherrack);

        SoilMaterial podzol = new SoilMaterial();
        podzol.inputItem = ItemJson.toItemString(new ItemStack(Blocks.dirt, 1, 2));
        podzol.growthModifier = 0.05f;
        podzol.categories = Arrays.asList("dirt", "grass", "podzol", "mushroom");
        list.add(podzol);

        SoilMaterial red_sand = new SoilMaterial();
        red_sand.inputItem = ItemJson.toItemString(new ItemStack(Blocks.sand, 1, 1));
        red_sand.growthModifier = 0f;
        red_sand.categories = Arrays.asList("sand", "red_sand");
        list.add(red_sand);

        SoilMaterial sand = new SoilMaterial();
        sand.inputItem = ItemJson.toItemString(new ItemStack(Blocks.sand));
        sand.growthModifier = 0f;
        sand.categories = Collections.singletonList("sand");
        list.add(sand);

        SoilMaterial soul_sand = new SoilMaterial();
        soul_sand.inputItem = ItemJson.toItemString(new ItemStack(Blocks.soul_sand));
        soul_sand.growthModifier = -0.3f;
        soul_sand.categories = Arrays.asList("soul_sand", "nether");
        list.add(soul_sand);

        return list;
    }
}
