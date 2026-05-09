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

    private static final File configFolder = new File("config/" + Reference.MOD_ID + "/soils/");

    @Override
    public void onInit(Step step) {
        if (step != Step.POSTINIT) return;

        try {
            if (!configFolder.exists()) {
                configFolder.mkdirs();
            }

            generateDefaultConfigsIfMissing();
            File[] files = configFolder.listFiles((dir, name) -> name.endsWith(".json"));
            if (files != null) {
                for (File file : files) {
                    SoilReader reader = new SoilReader(file);
                    List<SoilMaterial> materials = reader.read();
                    for (SoilMaterial material : materials) {
                        SoilRegistry.register(material);
                    }
                }
            }

            OKProgressions.okLog(
                "Loaded " + SoilRegistry.size() + " soil types from " + (files != null ? files.length : 0) + " files.");

        } catch (IOException e) {
            OKProgressions.okLog(Level.ERROR, "Could not load soil configs! " + e.getMessage());
        }

    }

    private static void generateDefaultConfigsIfMissing() throws IOException {
        File treesFile = new File(configFolder, "soils.json");
        if (!treesFile.exists()) {
            writeGroupToFile(treesFile, createDefaults());
        }
    }

    private static void writeGroupToFile(File targetFile, List<SoilMaterial> materials) throws IOException {
        if (materials == null || materials.isEmpty()) return;
        new SoilWriter(targetFile).write(materials);
        OKProgressions.okLog("Generated default config file: " + targetFile.getName());
    }

    private static List<SoilMaterial> createDefaults() {
        List<SoilMaterial> list = new ArrayList<>();
        SoilMaterial dirt = new SoilMaterial();

        dirt.setStack(new ItemStack(Blocks.dirt));
        dirt.setDisplayBlock(Blocks.dirt);
        dirt.setDisplayMeta(0);
        dirt.setGrowthModifier(0f);
        dirt.setCategories(Collections.singletonList("dirt"));
        list.add(dirt);

        SoilMaterial endStone = new SoilMaterial();
        endStone.setStack(new ItemStack(Blocks.end_stone));
        endStone.setDisplayBlock(Blocks.end_stone);
        endStone.setDisplayMeta(0);
        endStone.setGrowthModifier(-0.5f);
        endStone.setCategories(Collections.singletonList("end_stone"));
        list.add(endStone);

        SoilMaterial farmland = new SoilMaterial();
        farmland.setStack(new ItemStack(Blocks.farmland));
        farmland.setDisplayBlock(Blocks.farmland);
        farmland.setDisplayMeta(7);
        farmland.setGrowthModifier(0.15f);
        farmland.setCategories(Arrays.asList("dirt", "farmland"));
        list.add(farmland);

        SoilMaterial grass = new SoilMaterial();
        grass.setStack(new ItemStack(Blocks.grass));
        grass.setDisplayBlock(Blocks.grass);
        grass.setDisplayMeta(0);
        grass.setGrowthModifier(0.05f);
        grass.setCategories(Arrays.asList("dirt", "grass"));
        list.add(grass);

        SoilMaterial mycelium = new SoilMaterial();
        mycelium.setStack(new ItemStack(Blocks.mycelium));
        mycelium.setDisplayBlock(Blocks.mycelium);
        mycelium.setDisplayMeta(0);
        mycelium.setGrowthModifier(0.05f);
        mycelium.setCategories(Arrays.asList("dirt", "mushroom"));
        list.add(mycelium);

        SoilMaterial netherrack = new SoilMaterial();
        netherrack.setStack(new ItemStack(Blocks.netherrack));
        netherrack.setDisplayBlock(Blocks.netherrack);
        netherrack.setDisplayMeta(0);
        netherrack.setGrowthModifier(0f);
        netherrack.setCategories(Collections.singletonList("nether"));
        list.add(netherrack);

        SoilMaterial podzol = new SoilMaterial();
        podzol.setStack(new ItemStack(Blocks.dirt, 1, 2));
        podzol.setDisplayBlock(Blocks.dirt);
        podzol.setDisplayMeta(2);
        podzol.setGrowthModifier(0.05f);
        podzol.setCategories(Arrays.asList("dirt", "grass", "podzol", "mushroom"));
        list.add(podzol);

        SoilMaterial redSand = new SoilMaterial();
        redSand.setStack(new ItemStack(Blocks.sand, 1, 1));
        redSand.setDisplayBlock(Blocks.sand);
        redSand.setDisplayMeta(1);
        redSand.setGrowthModifier(0f);
        redSand.setCategories(Arrays.asList("sand", "red_sand"));
        list.add(redSand);

        SoilMaterial sand = new SoilMaterial();
        sand.setStack(new ItemStack(Blocks.sand));
        sand.setDisplayBlock(Blocks.sand);
        sand.setDisplayMeta(0);
        sand.setGrowthModifier(0f);
        sand.setCategories(Collections.singletonList("sand"));
        list.add(sand);

        SoilMaterial soulSand = new SoilMaterial();
        soulSand.setStack(new ItemStack(Blocks.soul_sand));
        soulSand.setDisplayBlock(Blocks.soul_sand);
        soulSand.setDisplayMeta(0);
        soulSand.setGrowthModifier(-0.3f);
        soulSand.setCategories(Arrays.asList("soul_sand", "nether"));
        list.add(soulSand);

        SoilMaterial water = new SoilMaterial();
        water.setStack(new ItemStack(Items.water_bucket));
        water.setDisplayBlock(Blocks.water);
        water.setDisplayMeta(0);
        water.setGrowthModifier(0f);
        water.setCategories(Arrays.asList("water", "fluid", "liquid"));
        list.add(water);

        SoilMaterial lava = new SoilMaterial();
        lava.setStack(new ItemStack(Items.lava_bucket));
        lava.setDisplayBlock(Blocks.lava);
        lava.setDisplayMeta(0);
        lava.setGrowthModifier(0f);
        lava.setCategories(Arrays.asList("lava", "fluid", "liquid"));
        list.add(lava);

        return list;
    }
}
