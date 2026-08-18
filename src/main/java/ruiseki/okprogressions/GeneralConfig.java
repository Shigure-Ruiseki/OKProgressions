package ruiseki.okprogressions;

import org.apache.logging.log4j.Level;

import ruiseki.okcore.config.ConfigurableProperty;
import ruiseki.okcore.config.ConfigurableType;
import ruiseki.okcore.config.ConfigurableTypeCategory;
import ruiseki.okcore.config.extendedconfig.DummyConfig;
import ruiseki.okcore.init.ModBase;
import ruiseki.okcore.tracking.Versions;

public class GeneralConfig extends DummyConfig {

    /**
     * The current mod version, will be used to check if the player's config isn't out of date and
     * warn the player accordingly.
     */
    @ConfigurableProperty(
        category = ConfigurableTypeCategory.CORE,
        comment = "Config version for " + Reference.MOD_NAME + ".\nDO NOT EDIT MANUALLY!")
    public static String version = Reference.MOD_VERSION;

    /**
     * If the debug mode should be enabled. @see Debug
     */
    @ConfigurableProperty(
        category = ConfigurableTypeCategory.CORE,
        comment = "Set 'true' to enable development debug mode. This will result in a lower performance!",
        requiresMcRestart = true)
    public static boolean debug = false;

    /**
     * If the recipe loader should crash when finding invalid recipes.
     */
    @ConfigurableProperty(
        category = ConfigurableTypeCategory.CORE,
        comment = "If the recipe loader should crash when finding invalid recipes.",
        requiresMcRestart = true)
    public static boolean crashOnInvalidRecipe = false;

    /**
     * If mod compatibility loader should crash hard if errors occur in that process.
     */
    @ConfigurableProperty(
        category = ConfigurableTypeCategory.CORE,
        comment = "If mod compatibility loader should crash hard if errors occur in that process.",
        requiresMcRestart = true)
    public static boolean crashOnModCompatCrash = false;

    /**
     * If the version checker should be enabled.
     */
    @ConfigurableProperty(
        category = ConfigurableTypeCategory.CORE,
        comment = "If the version checker should be enabled.")
    public static boolean versionChecker = true;

    /**
     * Base growth ticks interval.
     */
    @ConfigurableProperty(
        category = ConfigurableTypeCategory.CORE,
        comment = "Base growth tick interval.",
        minimalValue = 1)
    public static int growthTicks = 30;

    /**
     * Growth ticks interval for Tier 1 Upgrade.
     */
    @ConfigurableProperty(
        category = ConfigurableTypeCategory.CORE,
        comment = "Growth tick interval for Tier 1 Upgrade.",
        minimalValue = 1)
    public static int upgradeGrowthTicks = 40;

    /**
     * Growth ticks interval for Tier 2 Upgrade.
     */
    @ConfigurableProperty(
        category = ConfigurableTypeCategory.CORE,
        comment = "Growth tick interval for Tier 2 Upgrade.",
        minimalValue = 1)
    public static int upgradeTwoGrowthTicks = 30;

    /**
     * Enable particle effects.
     */
    @ConfigurableProperty(
        category = ConfigurableTypeCategory.CORE,
        comment = "Set to 'true' to enable particle effects.")
    public static boolean particle = true;

    /**
     * Interval in ticks between spawning particles.
     */
    @ConfigurableProperty(
        category = ConfigurableTypeCategory.CORE,
        comment = "Interval in ticks between spawning particles.",
        minimalValue = 1)
    public static int particleTicks = 750;

    /**
     * The type of this config.
     */
    public static ConfigurableType TYPE = ConfigurableType.DUMMY;

    /**
     * Create a new instance.
     */
    public GeneralConfig() {
        super(OKProgressions._instance, true, "general", null);
    }

    @Override
    public void onRegistered() {
        // Check version of config file
        if (!version.equals(Reference.MOD_VERSION)) {
            getMod().log(
                Level.WARN,
                "The config file of " + Reference.MOD_NAME
                    + " is out of date and might cause problems, please remove it so it can be regenerated.");
        }

        getMod().putGenericReference(ModBase.REFKEY_CRASH_ON_INVALID_RECIPE, GeneralConfig.crashOnInvalidRecipe);
        getMod().putGenericReference(ModBase.REFKEY_DEBUGCONFIG, GeneralConfig.debug);
        getMod().putGenericReference(ModBase.REFKEY_CRASH_ON_MODCOMPAT_CRASH, GeneralConfig.crashOnModCompatCrash);

        if (versionChecker) {
            Versions.registerMod(getMod(), OKProgressions._instance, Reference.VERSION_URL);
        }
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
