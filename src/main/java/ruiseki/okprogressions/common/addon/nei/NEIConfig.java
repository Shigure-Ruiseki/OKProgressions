package ruiseki.okprogressions.common.addon.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.event.NEIRegisterHandlerInfosEvent;
import codechicken.nei.recipe.HandlerInfo;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ruiseki.okcore.addon.nei.IRecipeHandlerBase;
import ruiseki.okprogressions.Reference;
import ruiseki.okprogressions.common.init.ModBlocks;

public class NEIConfig implements IConfigureNEI {

    @SubscribeEvent
    public void registerHandlerInfo(NEIRegisterHandlerInfosEvent event) {
        event.registerHandlerInfo(
            new HandlerInfo.Builder(BotanyCropsRecipeHandler.UID, Reference.MOD_NAME, Reference.MOD_ID)
                .setDisplayStack(ModBlocks.BOTANY_POT.newItemStack())
                .setHeight(64)
                .setWidth(166)
                .build());
    }

    @Override
    public void loadConfig() {
        registerHandler(new BotanyCropsRecipeHandler());
        API.addRecipeCatalyst(ModBlocks.BOTANY_POT.newItemStack(), BotanyCropsRecipeHandler.UID);
        API.addRecipeCatalyst(ModBlocks.HOPPER_BOTANY_POT.newItemStack(), BotanyCropsRecipeHandler.UID);
    }

    protected static void registerHandler(IRecipeHandlerBase handler) {
        handler.prepare();
        API.registerRecipeHandler(handler);
        API.registerUsageHandler(handler);
    }

    @Override
    public String getName() {
        return Reference.MOD_NAME;
    }

    @Override
    public String getVersion() {
        return Reference.VERSION;
    }
}
