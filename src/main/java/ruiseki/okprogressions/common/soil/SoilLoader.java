package ruiseki.okprogressions.common.soil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import org.apache.logging.log4j.Level;

import ruiseki.okcore.init.IInitListener;
import ruiseki.okprogressions.OKProgressions;
import ruiseki.okprogressions.Reference;

public class SoilLoader implements IInitListener {

    private static final File configDir = new File("config/" + Reference.MOD_ID + "/soils.json");

    @Override
    public void onInit(Step step) {
        if (step != Step.POSTINIT) return;

        SoilReader reader = new SoilReader(configDir);

        try {
            File[] files = configDir.listFiles();
            if (!configDir.exists() || files == null || files.length == 0) {
                List<SoilMaterial> defaults = createDefaults();
                try {
                    new SoilWriter(configDir).write(defaults);
                } catch (IOException e) {
                    OKProgressions.okLog(Level.ERROR, "Failed to write default config soils: " + e.getMessage());
                }
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
        dirt.stack = new ItemStack(Blocks.dirt);
        dirt.displayBlock = Blocks.dirt;
        dirt.displayMeta = 0;
        dirt.growthModifier = 0f;
        dirt.categories = Collections.singletonList("dirt");
        list.add(dirt);

        SoilMaterial end_stone = new SoilMaterial();
        end_stone.stack = new ItemStack(Blocks.end_stone);
        end_stone.displayBlock = Blocks.end_stone;
        end_stone.displayMeta = 0;
        end_stone.growthModifier = -0.5f;
        end_stone.categories = Collections.singletonList("end_stone");
        list.add(end_stone);

        SoilMaterial farmland = new SoilMaterial();
        farmland.stack = new ItemStack(Blocks.farmland);
        farmland.displayBlock = Blocks.farmland;
        farmland.displayMeta = 7;
        farmland.growthModifier = 0.15f;
        farmland.categories = Arrays.asList("dirt", "farmland");
        list.add(farmland);

        SoilMaterial grass = new SoilMaterial();
        grass.stack = new ItemStack(Blocks.grass);
        grass.displayBlock = Blocks.grass;
        grass.displayMeta = 0;
        grass.growthModifier = 0.05f;
        grass.categories = Arrays.asList("dirt", "grass");
        list.add(grass);

        SoilMaterial mycelium = new SoilMaterial();
        mycelium.stack = new ItemStack(Blocks.mycelium);
        mycelium.displayBlock = Blocks.mycelium;
        mycelium.displayMeta = 0;
        mycelium.growthModifier = 0.05f;
        mycelium.categories = Arrays.asList("dirt", "mushroom");
        list.add(mycelium);

        SoilMaterial netherrack = new SoilMaterial();
        netherrack.stack = new ItemStack(Blocks.netherrack);
        netherrack.displayBlock = Blocks.netherrack;
        netherrack.displayMeta = 0;
        netherrack.growthModifier = 0f;
        netherrack.categories = Collections.singletonList("nether");
        list.add(netherrack);

        SoilMaterial podzol = new SoilMaterial();
        podzol.stack = new ItemStack(Blocks.dirt, 1, 2);
        podzol.displayBlock = Blocks.dirt;
        podzol.displayMeta = 2;
        podzol.growthModifier = 0.05f;
        podzol.categories = Arrays.asList("dirt", "grass", "podzol", "mushroom");
        list.add(podzol);

        SoilMaterial red_sand = new SoilMaterial();
        red_sand.stack = new ItemStack(Blocks.sand, 1, 1);
        red_sand.displayBlock = Blocks.sand;
        red_sand.displayMeta = 1;
        red_sand.growthModifier = 0f;
        red_sand.categories = Arrays.asList("sand", "red_sand");
        list.add(red_sand);

        SoilMaterial sand = new SoilMaterial();
        sand.stack = new ItemStack(Blocks.sand);
        sand.displayBlock = Blocks.sand;
        sand.displayMeta = 0;
        sand.growthModifier = 0f;
        sand.categories = Collections.singletonList("sand");
        list.add(sand);

        SoilMaterial soul_sand = new SoilMaterial();
        soul_sand.stack = new ItemStack(Blocks.soul_sand);
        soul_sand.displayBlock = Blocks.soul_sand;
        soul_sand.displayMeta = 0;
        soul_sand.growthModifier = -0.3f;
        soul_sand.categories = Arrays.asList("soul_sand", "nether");
        list.add(soul_sand);

        SoilMaterial water = new SoilMaterial();
        water.stack = new ItemStack(Items.water_bucket);
        water.displayBlock = Blocks.water;
        water.displayMeta = 0;
        water.growthModifier = 0f;
        water.categories = Arrays.asList("water", "fluid", "liquid");
        list.add(water);

        SoilMaterial lava = new SoilMaterial();
        lava.stack = new ItemStack(Items.lava_bucket);
        lava.displayBlock = Blocks.lava;
        lava.displayMeta = 0;
        lava.growthModifier = 0f;
        lava.categories = Arrays.asList("lava", "fluid", "liquid");
        list.add(lava);

        return list;
    }
}
