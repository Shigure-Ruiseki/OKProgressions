package ruiseki.okprogressions;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.Level;

import com.gtnewhorizon.gtnhlib.client.model.loading.ModelRegistry;

import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import ruiseki.okcore.config.ConfigHandler;
import ruiseki.okcore.config.extendedconfig.BlockItemConfigReference;
import ruiseki.okcore.helper.ItemHelpers;
import ruiseki.okcore.helper.MinecraftHelpers;
import ruiseki.okcore.init.ItemCreativeTab;
import ruiseki.okcore.init.ModBaseVersionable;
import ruiseki.okcore.proxy.ICommonProxy;
import ruiseki.okprogressions.common.addon.nei.Mods;
import ruiseki.okprogressions.common.addon.nei.NEIConfig;
import ruiseki.okprogressions.common.block.cobblegen.BlockCobblegenConfig;
import ruiseki.okprogressions.common.block.compressed.BlockCharcoalConfig;
import ruiseki.okprogressions.common.item.misc.ItemTinyCharcoalConfig;
import ruiseki.okprogressions.common.item.misc.ItemTinyCoalConfig;
import ruiseki.okprogressions.common.world.WorldGen;

@Mod(
    modid = Reference.MOD_ID,
    name = Reference.MOD_NAME,
    version = Reference.MOD_VERSION,
    dependencies = Reference.MOD_DEPENDENCIES,
    guiFactory = Reference.GUI_FACTORY)
public class OKProgressions extends ModBaseVersionable {

    @SidedProxy(serverSide = Reference.PROXY_COMMON, clientSide = Reference.PROXY_CLIENT)
    public static ICommonProxy proxy;

    @Mod.Instance(Reference.MOD_ID)
    public static OKProgressions _instance;

    public OKProgressions() {
        super(Reference.MOD_ID, Reference.MOD_NAME, Reference.MOD_VERSION);

        addInitListeners(new WorldGen());
    }

    @Override
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        if (MinecraftHelpers.isClientSide()) {
            ModelRegistry.registerModid(Reference.MOD_ID);
        }

        GameRegistry.registerFuelHandler(new IFuelHandler() {

            @Override
            public int getBurnTime(ItemStack fuel) {
                if (ItemHelpers.areItemsEqual(fuel, new ItemStack(ItemTinyCoalConfig._instance.getInstance()))) {
                    return 200;
                }
                if (ItemHelpers.areItemsEqual(fuel, new ItemStack(ItemTinyCharcoalConfig._instance.getInstance()))) {
                    return 200;
                }
                if (ItemHelpers.areItemsEqual(fuel, new ItemStack(BlockCharcoalConfig._instance.getItemInstance()))) {
                    return 16000;
                }
                return 0;
            }
        });

        if ((MinecraftHelpers.isClientSide() && Mods.NotEnoughItems.isModLoaded())) {
            NEIConfig config = new NEIConfig();
            MinecraftForge.EVENT_BUS.register(config);
            config.loadConfig();
        }
    }

    @Override
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        super.onServerStarting(event);
    }

    @Override
    @Mod.EventHandler
    public void onServerStarted(FMLServerStartedEvent event) {
        super.onServerStarted(event);
    }

    @Override
    @Mod.EventHandler
    public void onServerStopping(FMLServerStoppingEvent event) {
        super.onServerStopping(event);
    }

    @Override
    @Mod.EventHandler
    public void onServerStopped(FMLServerStoppedEvent event) {
        super.onServerStopped(event);
    }

    @Override
    public CreativeTabs constructDefaultCreativeTab() {
        return new ItemCreativeTab(this, new BlockItemConfigReference(BlockCobblegenConfig.class));
    }

    @Override
    public ICommonProxy getProxy() {
        return proxy;
    }

    @Override
    public void onGeneralConfigsRegister(ConfigHandler configHandler) {
        configHandler.add(new GeneralConfig());
    }

    @Override
    public void onMainConfigsRegister(ConfigHandler configHandler) {
        Configs.register(configHandler);
    }

    /**
     * Log a new info message for this mod.
     *
     * @param message The message to show.
     */
    public static void okLog(String message) {
        OKProgressions._instance.log(Level.INFO, message);
    }

    /**
     * Log a new message of the given level for this mod.
     *
     * @param level   The level in which the message must be shown.
     * @param message The message to show.
     */
    public static void okLog(Level level, String message) {
        OKProgressions._instance.log(level, message);
    }

    public static void okLog(Level level, String message, Object... params) {
        OKProgressions._instance.log(level, message, params);
    }
}
