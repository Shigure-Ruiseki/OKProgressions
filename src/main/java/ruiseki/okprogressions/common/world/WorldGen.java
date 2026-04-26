package ruiseki.okprogressions.common.world;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import ruiseki.okcore.init.IInitListener;
import ruiseki.okcore.world.gen.SimpleMinableWorldGenerator;
import ruiseki.okcore.world.gen.WorldGenMinableExtended;
import ruiseki.okprogressions.OKProgressions;
import ruiseki.okprogressions.common.init.ModBlocks;

public class WorldGen implements IInitListener {

    @Override
    public void onInit(Step step) {
        if (step == Step.INIT) {
            init();
        }
    }

    public static void init() {
        List<WorldGenMinableExtended> generators = new ArrayList<>();
        generators.add(
            new WorldGenMinableExtended(
                ModBlocks.ORE_ENDER.getBlock(), // block
                0, // meta
                6, // blocksPerVein
                4, // veinsPerChunk
                10, // startY
                40, // endY
                Blocks.stone // replaceTarget
            ));

        SimpleMinableWorldGenerator oreGen = new SimpleMinableWorldGenerator(OKProgressions.instance, generators);

        GameRegistry.registerWorldGenerator(oreGen, 0);
    }
}
