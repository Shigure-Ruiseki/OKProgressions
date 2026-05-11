package ruiseki.okprogressions.config;

import com.gtnewhorizon.gtnhlib.config.Config;
import com.gtnewhorizon.gtnhlib.config.ConfigException;
import com.gtnewhorizon.gtnhlib.config.ConfigurationManager;

import ruiseki.okprogressions.Reference;

@Config.LangKey("config.generalConfig")
@Config(modid = Reference.MOD_ID, configSubDirectory = Reference.MOD_ID, category = "general")
public class ModConfig {

    public static void registerConfig() throws ConfigException {
        ConfigurationManager.registerConfig(ModConfig.class);
    }

    @Config.DefaultInt(40)
    @Config.RangeInt(min = 1)
    public static int cobbleGenCycle;

    @Config.DefaultInt(32)
    @Config.RangeInt(min = 1)
    public static int cobbleGenStackSize;

    @Config.DefaultInt(20)
    @Config.RangeInt(min = 1)
    public static int ironCobbleGenCycle;

    @Config.DefaultInt(64)
    @Config.RangeInt(min = 1)
    public static int ironCobbleGenStackSize;

    @Config.DefaultInt(10)
    @Config.RangeInt(min = 1)
    public static int goldCobbleGenCycle;

    @Config.DefaultInt(64)
    @Config.RangeInt(min = 1)
    public static int goldCobbleGenStackSize;

    @Config.DefaultInt(5)
    @Config.RangeInt(min = 1)
    public static int diamondCobbleGenCycle;

    @Config.DefaultInt(64)
    @Config.RangeInt(min = 1)
    public static int diamondCobbleGenStackSize;

    @Config.DefaultInt(1)
    @Config.RangeInt(min = 1)
    public static int emeraldCobbleGenCycle;

    @Config.DefaultInt(64)
    @Config.RangeInt(min = 1)
    public static int emeraldCobbleGenStackSize;

    @Config.DefaultInt(30)
    @Config.RangeInt(min = 1)
    public static int growthTicks;

    @Config.DefaultInt(40)
    @Config.RangeInt(min = 1)
    public static int upgradeGrowthTicks;

    @Config.DefaultInt(30)
    @Config.RangeInt(min = 1)
    public static int upgradeTwoGrowthTicks;

    @Config.DefaultBoolean(true)
    public static boolean partical;

    @Config.DefaultInt(750)
    @Config.RangeInt(min = 1)
    public static int particalTicks;
}
