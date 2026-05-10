package ruiseki.okprogressions.common.crop;

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

public class CropLoader implements IInitListener {

    private static final File configFolder = new File("config/" + Reference.MOD_ID + "/crops/");

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
                    CropReader reader = new CropReader(file);
                    List<CropMaterial> materials = reader.read();
                    if (materials != null) {
                        for (CropMaterial material : materials) {
                            CropRegistry.register(material);
                        }
                    }
                }
            }

            OKProgressions.okLog(
                "Loaded " + CropRegistry.size() + " crop types from " + (files != null ? files.length : 0) + " files.");

        } catch (IOException e) {
            OKProgressions.okLog(Level.ERROR, "Could not load crop configs! " + e.getMessage());
        }
    }

    private static void generateDefaultConfigsIfMissing() throws IOException {
        File cropsFile = new File(configFolder, "crops.json");
        if (!cropsFile.exists()) {
            writeGroupToFile(cropsFile, createDefaults());
        }

        File treesFile = new File(configFolder, "trees.json");
        if (!treesFile.exists()) {
            writeGroupToFile(treesFile, createTrees());
        }
    }

    private static void writeGroupToFile(File targetFile, List<CropMaterial> materials) throws IOException {
        if (materials == null || materials.isEmpty()) return;
        new CropWriter(targetFile).write(materials);
        OKProgressions.okLog("Generated default config file: " + targetFile.getName());
    }

    private static List<CropMaterial> createDefaults() {
        List<CropMaterial> list = new ArrayList<>();
        // Allium
        CropMaterial allium = new CropMaterial();
        allium.setStack(new ItemStack(Blocks.red_flower, 1, 2));
        allium.setGrowthTicks(1200);
        allium.setDisplayBlock(Blocks.red_flower);
        allium.setDisplayMeta(2);
        allium.setCategories(Collections.singletonList("dirt"));
        allium.setResults(
            Arrays.asList(
                HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower, 1, 2), 0.75f, 1, 1),
                HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower, 1, 2), 0.05f, 1, 2)));
        list.add(allium);
        // Azure Bluet
        CropMaterial azureBluet = new CropMaterial();
        azureBluet.setStack(new ItemStack(Blocks.red_flower, 1, 3));
        azureBluet.setGrowthTicks(1200);
        azureBluet.setDisplayBlock(Blocks.red_flower);
        azureBluet.setDisplayMeta(3);
        azureBluet.setCategories(Collections.singletonList("dirt"));
        azureBluet.setResults(
            Arrays.asList(
                HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower, 1, 3), 0.75f, 1, 1),
                HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower, 1, 3), 0.05f, 1, 2)));
        list.add(azureBluet);
        // Blue Orchid
        CropMaterial blueOrchid = new CropMaterial();
        blueOrchid.setStack(new ItemStack(Blocks.red_flower, 1, 1));
        blueOrchid.setGrowthTicks(1200);
        blueOrchid.setDisplayBlock(Blocks.red_flower);
        blueOrchid.setDisplayMeta(1);
        blueOrchid.setCategories(Collections.singletonList("dirt"));
        blueOrchid.setResults(
            Arrays.asList(
                HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower, 1, 1), 0.75f, 1, 1),
                HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower, 1, 1), 0.05f, 1, 2)));
        list.add(blueOrchid);
        // Brown Mushroom
        CropMaterial brownMushroom = new CropMaterial();
        brownMushroom.setStack(new ItemStack(Blocks.brown_mushroom));
        brownMushroom.setGrowthTicks(1600);
        brownMushroom.setDisplayBlock(Blocks.brown_mushroom);
        brownMushroom.setDisplayMeta(0);
        brownMushroom.setResults(
            Arrays.asList(
                HarvestMaterial.createMaterial(new ItemStack(Blocks.brown_mushroom), 0.75f, 1, 1),
                HarvestMaterial.createMaterial(new ItemStack(Blocks.brown_mushroom), 0.05f, 1, 2)));
        list.add(brownMushroom);

        // Cactus
        CropMaterial cactus = new CropMaterial();
        cactus.setStack(new ItemStack(Blocks.cactus));
        cactus.setGrowthTicks(1600);
        cactus.setDisplayBlock(Blocks.cactus);
        cactus.setDisplayMeta(0);
        cactus.setCategories(Collections.singletonList("sand"));
        cactus.setResults(
            Collections.singletonList(HarvestMaterial.createMaterial(new ItemStack(Blocks.cactus), 0.75f, 1, 1)));
        list.add(cactus);

        // Carrot
        CropMaterial carrot = new CropMaterial();
        carrot.setStack(new ItemStack(Items.carrot));
        carrot.setGrowthTicks(1100);
        carrot.setDisplayBlock(Blocks.carrots);
        carrot.setDisplayMeta(7);
        carrot.setCategories(Collections.singletonList("dirt"));
        carrot.setResults(
            Arrays.asList(
                HarvestMaterial.createMaterial(new ItemStack(Items.carrot), 0.75f, 1, 1),
                HarvestMaterial.createMaterial(new ItemStack(Items.carrot), 0.05f, 1, 2)));
        list.add(carrot);

        // Dandelion
        CropMaterial dandelion = new CropMaterial();
        dandelion.setStack(new ItemStack(Blocks.yellow_flower));
        dandelion.setGrowthTicks(1200);
        dandelion.setDisplayBlock(Blocks.yellow_flower);
        dandelion.setDisplayMeta(0);
        dandelion.setCategories(Collections.singletonList("dirt"));
        dandelion.setResults(
            Arrays.asList(
                HarvestMaterial.createMaterial(new ItemStack(Blocks.yellow_flower), 0.75f, 1, 1),
                HarvestMaterial.createMaterial(new ItemStack(Blocks.yellow_flower), 0.05f, 1, 2)));
        list.add(dandelion);

        // Fern
        CropMaterial fern = new CropMaterial();
        fern.setStack(new ItemStack(Blocks.tallgrass, 1, 2));
        fern.setGrowthTicks(600);
        fern.setDisplayBlock(Blocks.tallgrass);
        fern.setDisplayMeta(0);
        fern.setCategories(Collections.singletonList("dirt"));
        fern.setResults(
            Arrays.asList(
                HarvestMaterial.createMaterial(new ItemStack(Blocks.tallgrass, 1, 2), 0.75f, 1, 1),
                HarvestMaterial.createMaterial(new ItemStack(Blocks.tallgrass, 1, 2), 0.05f, 1, 2)));
        list.add(fern);

        // Grass
        CropMaterial grass = new CropMaterial();
        grass.setStack(new ItemStack(Blocks.tallgrass, 1, 1));
        grass.setGrowthTicks(600);
        grass.setDisplayBlock(Blocks.tallgrass);
        grass.setDisplayMeta(1);
        grass.setCategories(Collections.singletonList("dirt"));
        grass.setResults(
            Arrays.asList(
                HarvestMaterial.createMaterial(new ItemStack(Blocks.tallgrass, 1, 1), 0.75f, 1, 1),
                HarvestMaterial.createMaterial(new ItemStack(Blocks.tallgrass, 1, 1), 0.05f, 1, 2)));
        list.add(grass);

        // Melon
        CropMaterial melon = new CropMaterial();
        melon.setStack(new ItemStack(Items.melon_seeds));
        melon.setGrowthTicks(1800);
        melon.setDisplayBlock(Blocks.melon_block);
        melon.setDisplayMeta(0);
        melon.setCategories(Collections.singletonList("dirt"));
        melon.setResults(
            Arrays.asList(
                HarvestMaterial.createMaterial(new ItemStack(Items.melon), 0.75f, 1, 1),
                HarvestMaterial.createMaterial(new ItemStack(Items.melon_seeds), 0.05f, 1, 2)));
        list.add(melon);

        // Nether Wart
        CropMaterial netherWart = new CropMaterial();
        netherWart.setStack(new ItemStack(Items.nether_wart));
        netherWart.setGrowthTicks(1600);
        netherWart.setDisplayBlock(Blocks.nether_wart);
        netherWart.setDisplayMeta(2);
        netherWart.setCategories(Collections.singletonList("nether"));
        netherWart.setResults(
            Arrays.asList(
                HarvestMaterial.createMaterial(new ItemStack(Items.nether_wart), 0.75f, 1, 3),
                HarvestMaterial.createMaterial(new ItemStack(Items.nether_wart), 0.05f, 1, 3)));
        list.add(netherWart);

        // Orange Tulip
        CropMaterial orangeTulip = new CropMaterial();
        orangeTulip.setStack(new ItemStack(Blocks.red_flower, 1, 5));
        orangeTulip.setGrowthTicks(1200);
        orangeTulip.setDisplayBlock(Blocks.red_flower);
        orangeTulip.setDisplayMeta(5);
        orangeTulip.setCategories(Collections.singletonList("dirt"));
        orangeTulip.setResults(
            Arrays.asList(
                HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower, 1, 5), 0.75f, 1, 1),
                HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower, 1, 5), 0.05f, 1, 2)));
        list.add(orangeTulip);

        // Oxeye Daisy
        CropMaterial oxeyeDaisy = new CropMaterial();
        oxeyeDaisy.setStack(new ItemStack(Blocks.red_flower, 1, 8));
        oxeyeDaisy.setGrowthTicks(1200);
        oxeyeDaisy.setDisplayBlock(Blocks.red_flower);
        oxeyeDaisy.setDisplayMeta(8);
        oxeyeDaisy.setCategories(Collections.singletonList("dirt"));
        oxeyeDaisy.setResults(
            Arrays.asList(
                HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower, 1, 8), 0.75f, 1, 1),
                HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower, 1, 8), 0.05f, 1, 2)));
        list.add(oxeyeDaisy);

        // Peony
        CropMaterial peony = new CropMaterial();
        peony.setStack(new ItemStack(Blocks.double_plant, 1, 5));
        peony.setGrowthTicks(1200);
        peony.setDisplayBlock(Blocks.double_plant);
        peony.setDisplayMeta(5);
        peony.setCategories(Collections.singletonList("dirt"));
        peony.setResults(
            Arrays.asList(
                HarvestMaterial.createMaterial(new ItemStack(Blocks.double_plant, 1, 5), 0.75f, 1, 1),
                HarvestMaterial.createMaterial(new ItemStack(Blocks.double_plant, 1, 5), 0.05f, 1, 2)));
        list.add(peony);

        // Pink Tulip
        CropMaterial pinkTulip = new CropMaterial();
        pinkTulip.setStack(new ItemStack(Blocks.red_flower, 1, 7));
        pinkTulip.setGrowthTicks(1200);
        pinkTulip.setDisplayBlock(Blocks.red_flower);
        pinkTulip.setDisplayMeta(7);
        pinkTulip.setCategories(Collections.singletonList("dirt"));
        pinkTulip.setResults(
            Arrays.asList(
                HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower, 1, 7), 0.75f, 1, 1),
                HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower, 1, 7), 0.05f, 1, 2)));
        list.add(pinkTulip);

        // Poppy
        CropMaterial poppy = new CropMaterial();
        poppy.setStack(new ItemStack(Blocks.red_flower));
        poppy.setGrowthTicks(1200);
        poppy.setDisplayBlock(Blocks.red_flower);
        poppy.setDisplayMeta(0);
        poppy.setCategories(Collections.singletonList("dirt"));
        poppy.setResults(
            Arrays.asList(
                HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower), 0.75f, 1, 1),
                HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower), 0.05f, 1, 2)));
        list.add(poppy);

        // Potato
        CropMaterial potato = new CropMaterial();
        potato.setStack(new ItemStack(Items.potato));
        potato.setGrowthTicks(900);
        potato.setDisplayBlock(Blocks.potatoes);
        potato.setDisplayMeta(7);
        potato.setCategories(Collections.singletonList("dirt"));
        potato.setResults(
            Arrays.asList(
                HarvestMaterial.createMaterial(new ItemStack(Items.potato), 0.75f, 1, 1),
                HarvestMaterial.createMaterial(new ItemStack(Items.potato), 0.05f, 1, 2)));
        list.add(potato);

        // Pumpkin
        CropMaterial pumpkin = new CropMaterial();
        pumpkin.setStack(new ItemStack(Items.pumpkin_seeds));
        pumpkin.setGrowthTicks(1800);
        pumpkin.setDisplayBlock(Blocks.pumpkin);
        pumpkin.setDisplayMeta(0);
        pumpkin.setCategories(Collections.singletonList("dirt"));
        pumpkin.setResults(
            Arrays.asList(
                HarvestMaterial.createMaterial(new ItemStack(Blocks.pumpkin), 0.75f, 1, 1),
                HarvestMaterial.createMaterial(new ItemStack(Items.pumpkin_seeds), 0.05f, 1, 2)));
        list.add(pumpkin);

        // Red Mushroom
        CropMaterial redMushroom = new CropMaterial();
        redMushroom.setStack(new ItemStack(Blocks.red_mushroom));
        redMushroom.setGrowthTicks(1600);
        redMushroom.setDisplayBlock(Blocks.red_mushroom);
        redMushroom.setDisplayMeta(0);
        redMushroom.setCategories(Collections.singletonList("mushroom"));
        redMushroom.setResults(
            Arrays.asList(
                HarvestMaterial.createMaterial(new ItemStack(Blocks.red_mushroom), 0.75f, 1, 1),
                HarvestMaterial.createMaterial(new ItemStack(Blocks.red_mushroom), 0.05f, 1, 2)));
        list.add(redMushroom);

        // Red Tulip
        CropMaterial redTulip = new CropMaterial();
        redTulip.setStack(new ItemStack(Blocks.red_flower, 1, 4));
        redTulip.setGrowthTicks(1200);
        redTulip.setDisplayBlock(Blocks.red_flower);
        redTulip.setDisplayMeta(4);
        redTulip.setCategories(Collections.singletonList("dirt"));
        redTulip.setResults(
            Arrays.asList(
                HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower, 1, 4), 0.75f, 1, 1),
                HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower, 1, 4), 0.05f, 1, 2)));
        list.add(redTulip);

        // Sugar Cane
        CropMaterial sugarCane = new CropMaterial();
        sugarCane.setStack(new ItemStack(Items.reeds));
        sugarCane.setGrowthTicks(800);
        sugarCane.setDisplayBlock(Blocks.reeds);
        sugarCane.setDisplayMeta(0);
        sugarCane.setCategories(Collections.singletonList("sand"));
        sugarCane.setResults(
            Collections.singletonList(HarvestMaterial.createMaterial(new ItemStack(Items.reeds), 0.75f, 1, 1)));
        list.add(sugarCane);

        // Sunflower
        CropMaterial sunflower = new CropMaterial();
        sunflower.setStack(new ItemStack(Blocks.double_plant));
        sunflower.setGrowthTicks(1200);
        sunflower.setDisplayBlock(Blocks.double_plant);
        sunflower.setDisplayMeta(0);
        sunflower.setCategories(Collections.singletonList("dirt"));
        sunflower.setResults(
            Collections.singletonList(HarvestMaterial.createMaterial(new ItemStack(Blocks.double_plant), 0.75f, 1, 1)));
        list.add(sunflower);

        // Wheat Seeds
        CropMaterial wheatSeeds = new CropMaterial();
        wheatSeeds.setStack(new ItemStack(Items.wheat_seeds));
        wheatSeeds.setGrowthTicks(1000);
        wheatSeeds.setDisplayBlock(Blocks.wheat);
        wheatSeeds.setDisplayMeta(7);
        wheatSeeds.setCategories(Collections.singletonList("dirt"));
        wheatSeeds.setResults(
            Arrays.asList(
                HarvestMaterial.createMaterial(new ItemStack(Items.wheat), 0.75f, 1, 1),
                HarvestMaterial.createMaterial(new ItemStack(Items.wheat), 0.05f, 1, 2),
                HarvestMaterial.createMaterial(new ItemStack(Items.wheat_seeds), 0.05f, 1, 2)));
        list.add(wheatSeeds);

        // White Tulip
        CropMaterial whiteTulip = new CropMaterial();
        whiteTulip.setStack(new ItemStack(Blocks.red_flower, 1, 6));
        whiteTulip.setGrowthTicks(1200);
        whiteTulip.setDisplayBlock(Blocks.red_flower);
        whiteTulip.setDisplayMeta(6);
        whiteTulip.setCategories(Collections.singletonList("dirt"));
        whiteTulip.setResults(
            Arrays.asList(
                HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower, 1, 6), 0.75f, 1, 1),
                HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower, 1, 6), 0.05f, 1, 2)));
        list.add(whiteTulip);

        return list;
    }

    private static List<CropMaterial> createTrees() {
        List<CropMaterial> list = new ArrayList<>();

        // Acacia Sapling
        CropMaterial acacia = new CropMaterial();
        acacia.setStack(new ItemStack(Blocks.sapling, 1, 4));
        acacia.setGrowthTicks(2400);
        acacia.setDisplayBlock(Blocks.sapling);
        acacia.setDisplayMeta(4);
        acacia.setCategories(Collections.singletonList("dirt"));
        acacia.setResults(
            Arrays.asList(
                HarvestMaterial.createMaterial(new ItemStack(Blocks.log2), 1f, 2, 4),
                HarvestMaterial.createMaterial(new ItemStack(Items.stick), 0.1f, 1, 2),
                HarvestMaterial.createMaterial(new ItemStack(Blocks.sapling, 1, 4), 0.15f, 1, 1)));
        list.add(acacia);

        // Birch Sapling
        CropMaterial birch = new CropMaterial();
        birch.setStack(new ItemStack(Blocks.sapling, 1, 2));
        birch.setGrowthTicks(2400);
        birch.setDisplayBlock(Blocks.sapling);
        birch.setDisplayMeta(2);
        birch.setCategories(Collections.singletonList("dirt"));
        birch.setResults(
            Arrays.asList(
                HarvestMaterial.createMaterial(new ItemStack(Blocks.log), 1f, 2, 2),
                HarvestMaterial.createMaterial(new ItemStack(Items.stick), 0.1f, 1, 2),
                HarvestMaterial.createMaterial(new ItemStack(Blocks.sapling, 1, 2), 0.15f, 1, 1)));
        list.add(birch);

        // Dark Oak Sapling
        CropMaterial darkOak = new CropMaterial();
        darkOak.setStack(new ItemStack(Blocks.sapling, 1, 5));
        darkOak.setGrowthTicks(2400);
        darkOak.setDisplayBlock(Blocks.sapling);
        darkOak.setDisplayMeta(5);
        darkOak.setCategories(Collections.singletonList("dirt"));
        darkOak.setResults(
            Arrays.asList(
                HarvestMaterial.createMaterial(new ItemStack(Blocks.log2, 1, 1), 1f, 2, 4),
                HarvestMaterial.createMaterial(new ItemStack(Items.stick), 0.1f, 1, 2),
                HarvestMaterial.createMaterial(new ItemStack(Blocks.sapling, 1, 5), 0.15f, 1, 1)));
        list.add(darkOak);

        // Jungle Sapling
        CropMaterial jungle = new CropMaterial();
        jungle.setStack(new ItemStack(Blocks.sapling, 1, 3));
        jungle.setGrowthTicks(2400);
        jungle.setDisplayBlock(Blocks.sapling);
        jungle.setDisplayMeta(3);
        jungle.setCategories(Collections.singletonList("dirt"));
        jungle.setResults(
            Arrays.asList(
                HarvestMaterial.createMaterial(new ItemStack(Blocks.log), 1f, 2, 3),
                HarvestMaterial.createMaterial(new ItemStack(Items.stick), 0.1f, 1, 2),
                HarvestMaterial.createMaterial(new ItemStack(Items.dye, 1, 3), 0.05f, 1, 1),
                HarvestMaterial.createMaterial(new ItemStack(Blocks.sapling, 1, 3), 0.15f, 1, 1)));
        list.add(jungle);

        // Oak Sapling
        CropMaterial oak = new CropMaterial();
        oak.setStack(new ItemStack(Blocks.sapling));
        oak.setGrowthTicks(2400);
        oak.setDisplayBlock(Blocks.sapling);
        oak.setDisplayMeta(0);
        oak.setCategories(Collections.singletonList("dirt"));
        oak.setResults(
            Arrays.asList(
                HarvestMaterial.createMaterial(new ItemStack(Blocks.log), 1f, 1, 2),
                HarvestMaterial.createMaterial(new ItemStack(Items.stick), 0.1f, 1, 2),
                HarvestMaterial.createMaterial(new ItemStack(Items.apple), 0.05f, 1, 1),
                HarvestMaterial.createMaterial(new ItemStack(Blocks.sapling), 0.15f, 1, 1)));
        list.add(oak);

        // Spruce Sapling
        CropMaterial spruce = new CropMaterial();
        spruce.setStack(new ItemStack(Blocks.sapling, 1, 1));
        spruce.setGrowthTicks(2400);
        spruce.setDisplayBlock(Blocks.sapling);
        spruce.setDisplayMeta(1);
        spruce.setCategories(Collections.singletonList("dirt"));
        spruce.setResults(
            Arrays.asList(
                HarvestMaterial.createMaterial(new ItemStack(Blocks.log, 1, 1), 1f, 2, 1),
                HarvestMaterial.createMaterial(new ItemStack(Items.stick), 0.1f, 1, 2),
                HarvestMaterial.createMaterial(new ItemStack(Blocks.sapling, 1, 1), 0.15f, 1, 1)));
        list.add(spruce);

        return list;
    }
}
