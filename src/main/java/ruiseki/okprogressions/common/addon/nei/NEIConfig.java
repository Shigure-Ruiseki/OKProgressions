package ruiseki.okprogressions.common.addon.nei;

import net.minecraft.item.ItemStack;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.event.NEIRegisterHandlerInfosEvent;
import codechicken.nei.recipe.HandlerInfo;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ruiseki.okcore.addon.nei.IRecipeHandlerBase;
import ruiseki.okprogressions.Reference;
import ruiseki.okprogressions.common.block.botanypot.BlockBotanyPot;
import ruiseki.okprogressions.common.block.botanypot.BlockHopperBotanyPot;

public class NEIConfig implements IConfigureNEI {

    @SubscribeEvent
    public void registerHandlerInfo(NEIRegisterHandlerInfosEvent event) {
        event.registerHandlerInfo(
            new HandlerInfo.Builder(BotanyCropsRecipeHandler.UID, Reference.MOD_NAME, Reference.MOD_ID)
                .setDisplayStack(new ItemStack(BlockBotanyPot.getInstance()))
                .setHeight(64)
                .setWidth(166)
                .build());
    }

    @Override
    public void loadConfig() {
        registerHandler(new BotanyCropsRecipeHandler());
        API.addRecipeCatalyst(new ItemStack(BlockBotanyPot.getInstance()), BotanyCropsRecipeHandler.UID);
        API.addRecipeCatalyst(new ItemStack(BlockHopperBotanyPot.getInstance()), BotanyCropsRecipeHandler.UID);
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
        return Reference.MOD_VERSION;
    }
}
