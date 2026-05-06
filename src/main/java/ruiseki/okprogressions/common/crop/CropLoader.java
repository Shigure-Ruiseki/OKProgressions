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

    private static final File configDir = new File("config/" + Reference.MOD_ID + "/crops.json");

    @Override
    public void onInit(Step step) {
        if (step != Step.POSTINIT) return;

        CropReader reader = new CropReader(configDir);
        try {
            File[] files = configDir.listFiles();
            if (!configDir.exists() || files == null || files.length == 0) {
                List<CropMaterial> defaults = createDefaults();
                try {
                    new CropWriter(configDir).write(defaults);
                } catch (IOException e) {
                    OKProgressions.okLog(Level.ERROR, "Failed to write default config soils: " + e.getMessage());
                }
            }

            List<CropMaterial> materials = reader.read();
            for (CropMaterial material : materials) {
                CropRegistry.register(material);
            }

            OKProgressions.okLog("Loaded " + CropRegistry.size() + " crop types.");
        } catch (IOException e) {
            OKProgressions.okLog(Level.ERROR, "Could not load soil configs!" + e);
        }
    }

    private static List<CropMaterial> createDefaults() {
        List<CropMaterial> list = new ArrayList<>();

        CropMaterial allium = new CropMaterial();
        allium.stack = new ItemStack(Blocks.red_flower, 1, 2);
        allium.growthTicks = 1200;
        allium.displayBlock = Blocks.red_flower;
        allium.displayMeta = 2;
        allium.categories = Collections.singletonList("dirt");
        allium.results = Arrays.asList(
            HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower, 1, 2), 0.75f, 1, 1),
            HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower, 1, 2), 0.05f, 1, 2));
        list.add(allium);

        CropMaterial azure_bluet = new CropMaterial();
        azure_bluet.stack = new ItemStack(Blocks.red_flower, 1, 3);
        azure_bluet.growthTicks = 1200;
        azure_bluet.displayBlock = Blocks.red_flower;
        azure_bluet.displayMeta = 3;
        azure_bluet.categories = Collections.singletonList("dirt");
        azure_bluet.results = Arrays.asList(
            HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower, 1, 3), 0.75f, 1, 1),
            HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower, 1, 3), 0.05f, 1, 2));
        list.add(azure_bluet);

        CropMaterial blue_orchid = new CropMaterial();
        blue_orchid.stack = new ItemStack(Blocks.red_flower, 1, 1);
        blue_orchid.growthTicks = 1200;
        blue_orchid.displayBlock = Blocks.red_flower;
        blue_orchid.displayMeta = 1;
        blue_orchid.categories = Collections.singletonList("dirt");
        blue_orchid.results = Arrays.asList(
            HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower, 1, 1), 0.75f, 1, 1),
            HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower, 1, 1), 0.05f, 1, 2));
        list.add(blue_orchid);

        CropMaterial brown_mushroom = new CropMaterial();
        brown_mushroom.stack = new ItemStack(Blocks.brown_mushroom);
        brown_mushroom.growthTicks = 1600;
        brown_mushroom.displayBlock = Blocks.brown_mushroom;
        brown_mushroom.displayMeta = 0;
        brown_mushroom.results = Arrays.asList(
            HarvestMaterial.createMaterial(new ItemStack(Blocks.brown_mushroom), 0.75f, 1, 1),
            HarvestMaterial.createMaterial(new ItemStack(Blocks.brown_mushroom), 0.05f, 1, 2));
        list.add(brown_mushroom);

        CropMaterial cactus = new CropMaterial();
        cactus.stack = new ItemStack(Blocks.cactus);
        cactus.growthTicks = 1600;
        cactus.displayBlock = Blocks.cactus;
        cactus.displayMeta = 0;
        cactus.categories = Collections.singletonList("sand");
        cactus.results = Collections
            .singletonList(HarvestMaterial.createMaterial(new ItemStack(Blocks.cactus), 0.75f, 1, 1));
        list.add(cactus);

        CropMaterial carrot = new CropMaterial();
        carrot.stack = new ItemStack(Items.carrot);
        carrot.growthTicks = 1100;
        carrot.displayBlock = Blocks.carrots;
        carrot.displayMeta = 7;
        carrot.categories = Collections.singletonList("dirt");
        carrot.results = Arrays.asList(
            HarvestMaterial.createMaterial(new ItemStack(Items.carrot), 0.75f, 1, 1),
            HarvestMaterial.createMaterial(new ItemStack(Items.carrot), 0.05f, 1, 2));
        list.add(carrot);

        CropMaterial dandelion = new CropMaterial();
        dandelion.stack = new ItemStack(Blocks.yellow_flower);
        dandelion.growthTicks = 1200;
        dandelion.displayBlock = Blocks.yellow_flower;
        dandelion.displayMeta = 0;
        dandelion.categories = Collections.singletonList("dirt");
        dandelion.results = Arrays.asList(
            HarvestMaterial.createMaterial(new ItemStack(Blocks.yellow_flower), 0.75f, 1, 1),
            HarvestMaterial.createMaterial(new ItemStack(Blocks.yellow_flower), 0.05f, 1, 2));
        list.add(dandelion);

        CropMaterial fern = new CropMaterial();
        fern.stack = new ItemStack(Blocks.tallgrass, 1, 2);
        fern.growthTicks = 600;
        fern.displayBlock = Blocks.tallgrass;
        fern.displayMeta = 0;
        fern.categories = Collections.singletonList("dirt");
        fern.results = Arrays.asList(
            HarvestMaterial.createMaterial(new ItemStack(Blocks.tallgrass, 1, 2), 0.75f, 1, 1),
            HarvestMaterial.createMaterial(new ItemStack(Blocks.tallgrass, 1, 2), 0.05f, 1, 2));
        list.add(fern);

        CropMaterial grass = new CropMaterial();
        grass.stack = new ItemStack(Blocks.tallgrass, 1, 1);
        grass.growthTicks = 600;
        grass.displayBlock = Blocks.tallgrass;
        grass.displayMeta = 1;
        grass.categories = Collections.singletonList("dirt");
        grass.results = Arrays.asList(
            HarvestMaterial.createMaterial(new ItemStack(Blocks.tallgrass, 1, 1), 0.75f, 1, 1),
            HarvestMaterial.createMaterial(new ItemStack(Blocks.tallgrass, 1, 1), 0.05f, 1, 2));
        list.add(grass);

        CropMaterial melon = new CropMaterial();
        melon.stack = new ItemStack(Items.melon_seeds);
        melon.growthTicks = 1800;
        melon.displayBlock = Blocks.melon_block;
        melon.displayMeta = 0;
        melon.categories = Collections.singletonList("dirt");
        melon.results = Arrays.asList(
            HarvestMaterial.createMaterial(new ItemStack(Items.melon), 0.75f, 1, 1),
            HarvestMaterial.createMaterial(new ItemStack(Items.melon_seeds), 0.05f, 1, 2));
        list.add(melon);

        CropMaterial nether_wart = new CropMaterial();
        nether_wart.stack = new ItemStack(Items.nether_wart);
        nether_wart.growthTicks = 1600;
        nether_wart.displayBlock = Blocks.nether_wart;
        nether_wart.displayMeta = 2;
        nether_wart.categories = Collections.singletonList("nether");
        nether_wart.results = Arrays.asList(
            HarvestMaterial.createMaterial(new ItemStack(Items.nether_wart), 0.75f, 1, 3),
            HarvestMaterial.createMaterial(new ItemStack(Items.nether_wart), 0.05f, 1, 3));
        list.add(nether_wart);

        CropMaterial orange_tulip = new CropMaterial();
        orange_tulip.stack = new ItemStack(Blocks.red_flower, 1, 5);
        orange_tulip.growthTicks = 1200;
        orange_tulip.displayBlock = Blocks.red_flower;
        orange_tulip.displayMeta = 5;
        orange_tulip.categories = Collections.singletonList("dirt");
        orange_tulip.results = Arrays.asList(
            HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower, 1, 5), 0.75f, 1, 1),
            HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower, 1, 5), 0.05f, 1, 2));
        list.add(orange_tulip);

        CropMaterial oxeye_daisy = new CropMaterial();
        oxeye_daisy.stack = new ItemStack(Blocks.red_flower, 1, 8);
        oxeye_daisy.growthTicks = 1200;
        oxeye_daisy.displayBlock = Blocks.red_flower;
        oxeye_daisy.displayMeta = 8;
        oxeye_daisy.categories = Collections.singletonList("dirt");
        oxeye_daisy.results = Arrays.asList(
            HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower, 1, 8), 0.75f, 1, 1),
            HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower, 1, 8), 0.05f, 1, 2));
        list.add(oxeye_daisy);

        CropMaterial peony = new CropMaterial();
        peony.stack = new ItemStack(Blocks.double_plant, 1, 5);
        peony.growthTicks = 1200;
        peony.displayBlock = Blocks.double_plant;
        peony.displayMeta = 5;
        peony.categories = Collections.singletonList("dirt");
        peony.results = Arrays.asList(
            HarvestMaterial.createMaterial(new ItemStack(Blocks.double_plant, 1, 5), 0.75f, 1, 1),
            HarvestMaterial.createMaterial(new ItemStack(Blocks.double_plant, 1, 5), 0.05f, 1, 2));
        list.add(peony);

        CropMaterial pink_tulip = new CropMaterial();
        pink_tulip.stack = new ItemStack(Blocks.red_flower, 1, 7);
        pink_tulip.growthTicks = 1200;
        pink_tulip.displayBlock = Blocks.red_flower;
        pink_tulip.displayMeta = 7;
        pink_tulip.categories = Collections.singletonList("dirt");
        pink_tulip.results = Arrays.asList(
            HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower, 1, 7), 0.75f, 1, 1),
            HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower, 1, 7), 0.05f, 1, 2));
        list.add(pink_tulip);

        CropMaterial poppy = new CropMaterial();
        poppy.stack = new ItemStack(Blocks.red_flower);
        poppy.growthTicks = 1200;
        poppy.displayBlock = Blocks.red_flower;
        poppy.displayMeta = 0;
        poppy.categories = Collections.singletonList("dirt");
        poppy.results = Arrays.asList(
            HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower), 0.75f, 1, 1),
            HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower), 0.05f, 1, 2));
        list.add(poppy);

        CropMaterial potato = new CropMaterial();
        potato.stack = new ItemStack(Items.potato);
        potato.growthTicks = 900;
        potato.displayBlock = Blocks.potatoes;
        potato.displayMeta = 7;
        potato.categories = Collections.singletonList("dirt");
        potato.results = Arrays.asList(
            HarvestMaterial.createMaterial(new ItemStack(Items.potato), 0.75f, 1, 1),
            HarvestMaterial.createMaterial(new ItemStack(Items.potato), 0.05f, 1, 2));
        list.add(potato);

        CropMaterial pumpkin = new CropMaterial();
        pumpkin.stack = new ItemStack(Items.pumpkin_seeds);
        pumpkin.growthTicks = 1800;
        pumpkin.displayBlock = Blocks.pumpkin;
        pumpkin.displayMeta = 0;
        pumpkin.categories = Collections.singletonList("dirt");
        pumpkin.results = Arrays.asList(
            HarvestMaterial.createMaterial(new ItemStack(Blocks.pumpkin), 0.75f, 1, 1),
            HarvestMaterial.createMaterial(new ItemStack(Items.pumpkin_seeds), 0.05f, 1, 2));
        list.add(pumpkin);

        CropMaterial red_mushroom = new CropMaterial();
        red_mushroom.stack = new ItemStack(Blocks.red_mushroom);
        red_mushroom.growthTicks = 1600;
        red_mushroom.displayBlock = Blocks.red_mushroom;
        red_mushroom.displayMeta = 0;
        red_mushroom.categories = Collections.singletonList("mushroom");
        red_mushroom.results = Arrays.asList(
            HarvestMaterial.createMaterial(new ItemStack(Blocks.red_mushroom), 0.75f, 1, 1),
            HarvestMaterial.createMaterial(new ItemStack(Blocks.red_mushroom), 0.05f, 1, 2));
        list.add(red_mushroom);

        CropMaterial red_tulip = new CropMaterial();
        red_tulip.stack = new ItemStack(Blocks.red_flower, 1, 4);
        red_tulip.growthTicks = 1200;
        red_tulip.displayBlock = Blocks.red_flower;
        red_tulip.displayMeta = 4;
        red_tulip.categories = Collections.singletonList("dirt");
        red_tulip.results = Arrays.asList(
            HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower, 1, 4), 0.75f, 1, 1),
            HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower, 1, 4), 0.05f, 1, 2));
        list.add(red_tulip);

        CropMaterial sugar_cane = new CropMaterial();
        sugar_cane.stack = new ItemStack(Items.reeds);
        sugar_cane.growthTicks = 800;
        sugar_cane.displayBlock = Blocks.reeds;
        sugar_cane.displayMeta = 0;
        sugar_cane.categories = Collections.singletonList("sand");
        sugar_cane.results = Collections
            .singletonList(HarvestMaterial.createMaterial(new ItemStack(Items.reeds), 0.75f, 1, 1));
        list.add(sugar_cane);

        CropMaterial sunflower = new CropMaterial();
        sunflower.stack = new ItemStack(Blocks.double_plant);
        sunflower.growthTicks = 1200;
        sunflower.displayBlock = Blocks.double_plant;
        sunflower.displayMeta = 0;
        sunflower.categories = Collections.singletonList("dirt");
        sunflower.results = Collections
            .singletonList(HarvestMaterial.createMaterial(new ItemStack(Blocks.double_plant), 0.75f, 1, 1));
        list.add(sunflower);

        CropMaterial wheat_seeds = new CropMaterial();
        wheat_seeds.stack = new ItemStack(Items.wheat_seeds);
        wheat_seeds.growthTicks = 1000;
        wheat_seeds.displayBlock = Blocks.wheat;
        wheat_seeds.displayMeta = 7;
        wheat_seeds.categories = Collections.singletonList("dirt");
        wheat_seeds.results = Arrays.asList(
            HarvestMaterial.createMaterial(new ItemStack(Items.wheat), 0.75f, 1, 1),
            HarvestMaterial.createMaterial(new ItemStack(Items.wheat), 0.05f, 1, 2),
            HarvestMaterial.createMaterial(new ItemStack(Items.wheat_seeds), 0.05f, 1, 2));
        list.add(wheat_seeds);

        CropMaterial white_tulip = new CropMaterial();
        white_tulip.stack = new ItemStack(Blocks.red_flower, 1, 6);
        white_tulip.growthTicks = 1200;
        white_tulip.displayBlock = Blocks.red_flower;
        white_tulip.displayMeta = 6;
        white_tulip.categories = Collections.singletonList("dirt");
        white_tulip.results = Arrays.asList(
            HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower, 1, 6), 0.75f, 1, 1),
            HarvestMaterial.createMaterial(new ItemStack(Blocks.red_flower, 1, 6), 0.05f, 1, 2));
        list.add(white_tulip);

        CropMaterial acacia = new CropMaterial();
        acacia.stack = new ItemStack(Blocks.sapling, 1, 4);
        acacia.growthTicks = 2400;
        acacia.displayBlock = Blocks.sapling;
        acacia.displayMeta = 4;
        acacia.categories = Collections.singletonList("dirt");
        acacia.results = Arrays.asList(
            HarvestMaterial.createMaterial(new ItemStack(Blocks.log2), 1f, 2, 4),
            HarvestMaterial.createMaterial(new ItemStack(Items.stick), 0.1f, 1, 2),
            HarvestMaterial.createMaterial(new ItemStack(Blocks.sapling, 1, 4), 0.15f, 1, 1));
        list.add(acacia);

        CropMaterial birch = new CropMaterial();
        birch.stack = new ItemStack(Blocks.sapling, 1, 2);
        birch.growthTicks = 2400;
        birch.displayBlock = Blocks.sapling;
        birch.displayMeta = 2;
        birch.categories = Collections.singletonList("dirt");
        birch.results = Arrays.asList(
            HarvestMaterial.createMaterial(new ItemStack(Blocks.log), 1f, 2, 2),
            HarvestMaterial.createMaterial(new ItemStack(Items.stick), 0.1f, 1, 2),
            HarvestMaterial.createMaterial(new ItemStack(Blocks.sapling, 1, 2), 0.15f, 1, 1));
        list.add(birch);

        CropMaterial dark_oak = new CropMaterial();
        dark_oak.stack = new ItemStack(Blocks.sapling, 1, 5);
        dark_oak.growthTicks = 2400;
        dark_oak.displayBlock = Blocks.sapling;
        dark_oak.displayMeta = 5;
        dark_oak.categories = Collections.singletonList("dirt");
        dark_oak.results = Arrays.asList(
            HarvestMaterial.createMaterial(new ItemStack(Blocks.log2, 1, 1), 1f, 2, 4),
            HarvestMaterial.createMaterial(new ItemStack(Items.stick), 0.1f, 1, 2),
            HarvestMaterial.createMaterial(new ItemStack(Blocks.sapling, 1, 5), 0.15f, 1, 1));
        list.add(dark_oak);

        CropMaterial jungle = new CropMaterial();
        jungle.stack = new ItemStack(Blocks.sapling, 1, 3);
        jungle.growthTicks = 2400;
        jungle.displayBlock = Blocks.sapling;
        jungle.displayMeta = 3;
        jungle.categories = Collections.singletonList("dirt");
        jungle.results = Arrays.asList(
            HarvestMaterial.createMaterial(new ItemStack(Blocks.log), 1f, 2, 3),
            HarvestMaterial.createMaterial(new ItemStack(Items.stick), 0.1f, 1, 2),
            HarvestMaterial.createMaterial(new ItemStack(Items.dye, 1, 3), 0.05f, 1, 1),
            HarvestMaterial.createMaterial(new ItemStack(Blocks.sapling, 1, 3), 0.15f, 1, 1));
        list.add(jungle);

        CropMaterial oak = new CropMaterial();
        oak.stack = new ItemStack(Blocks.sapling);
        oak.growthTicks = 2400;
        oak.displayBlock = Blocks.sapling;
        oak.displayMeta = 0;
        oak.categories = Collections.singletonList("dirt");
        oak.results = Arrays.asList(
            HarvestMaterial.createMaterial(new ItemStack(Blocks.log), 1f, 2, 1),
            HarvestMaterial.createMaterial(new ItemStack(Items.stick), 0.1f, 1, 2),
            HarvestMaterial.createMaterial(new ItemStack(Items.apple), 0.05f, 1, 1),
            HarvestMaterial.createMaterial(new ItemStack(Blocks.sapling), 0.15f, 1, 1));
        list.add(oak);

        CropMaterial spruce = new CropMaterial();
        spruce.stack = new ItemStack(Blocks.sapling, 1, 1);
        spruce.growthTicks = 2400;
        spruce.displayBlock = Blocks.sapling;
        spruce.displayMeta = 1;
        spruce.categories = Collections.singletonList("dirt");
        spruce.results = Arrays.asList(
            HarvestMaterial.createMaterial(new ItemStack(Blocks.log, 1, 1), 1f, 2, 1),
            HarvestMaterial.createMaterial(new ItemStack(Items.stick), 0.1f, 1, 2),
            HarvestMaterial.createMaterial(new ItemStack(Blocks.sapling, 1, 1), 0.15f, 1, 1));
        list.add(spruce);

        return list;
    }
}
