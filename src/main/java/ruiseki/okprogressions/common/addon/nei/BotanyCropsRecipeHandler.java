package ruiseki.okprogressions.common.addon.nei;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import ruiseki.okcore.addon.nei.PositionedStackAdv;
import ruiseki.okcore.addon.nei.RecipeHandlerBase;
import ruiseki.okprogressions.common.block.botanypot.BlockBotanyPot;
import ruiseki.okprogressions.common.data.crop.CropInfo;
import ruiseki.okprogressions.common.data.crop.HarvestInfo;
import ruiseki.okprogressions.common.data.soil.SoilInfo;
import ruiseki.okprogressions.common.helper.BotanyPotHelpers;

public class BotanyCropsRecipeHandler extends RecipeHandlerBase {

    public static final String UID = "botany.crops";

    @Override
    public String getRecipeID() {
        return UID;
    }

    @Override
    public String getGuiTexture() {
        return "okcore:textures/gui/nei/neiBlank.png";
    }

    @Override
    public String getRecipeName() {
        return "Crops";
    }

    @Override
    public void loadTransferRects() {
        this.addTransferRect(0, 0, 0, 0);
    }

    @Override
    public void loadAllRecipes() {
        super.loadAllRecipes();
        for (CropInfo crop : BotanyPotHelpers.getCrops()) {
            for (SoilInfo soil : BotanyPotHelpers.getSoils()) {
                if (isCompatible(crop, soil)) {
                    addAllResults(crop, soil);
                }
            }
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        super.loadCraftingRecipes(result);
        for (CropInfo crop : BotanyPotHelpers.getCrops()) {
            for (SoilInfo soil : BotanyPotHelpers.getSoils()) {
                if (isCompatible(crop, soil)) {
                    for (HarvestInfo hm : crop.getResults()) {
                        if (NEIServerUtils.areStacksSameTypeCrafting(hm.getStack(), result)) {
                            addAllResults(crop, soil);
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        if (ingredient != null && ingredient.getItem() != null) {
            Block block = Block.getBlockFromItem(ingredient.getItem());
            if (block instanceof BlockBotanyPot) {
                loadAllRecipes();
                return;
            }
        }

        for (CropInfo crop : BotanyPotHelpers.getCrops()) {
            for (SoilInfo soil : BotanyPotHelpers.getSoils()) {
                if (isCompatible(crop, soil)) {
                    if (NEIServerUtils.areStacksSameTypeCrafting(crop.getStack(), ingredient)
                        || NEIServerUtils.areStacksSameTypeCrafting(soil.getStack(), ingredient)) {
                        addAllResults(crop, soil);
                    }
                }
            }
        }
    }

    @Override
    public void drawBackground(int recipe) {
        super.drawBackground(recipe);

        drawItemSlot(22 - 1, 14 - 1);
        drawItemSlot(22 - 1, 32 - 1);

        for (int i = 0; i < 12; i++) {
            int col = i % 4;
            int row = i / 4;

            int x = 75 + (col * 18) - 1;
            int y = 5 + (row * 18) - 1;

            drawItemSlot(x, y);
        }
    }

    private void addAllResults(CropInfo crop, SoilInfo soil) {
        arecipes.add(new CachedBotanyCropsRecipe(crop.getStack(), soil.getStack(), crop.getResults()));
    }

    private boolean isCompatible(CropInfo crop, SoilInfo soil) {
        for (final String soilCategory : soil.getCategories()) {
            for (String cropCategory : crop.getCategories()) {
                if (soilCategory.equalsIgnoreCase(cropCategory)) {
                    return true;
                }
            }
        }
        return false;
    }

    public class CachedBotanyCropsRecipe extends CachedBaseRecipe {

        public List<PositionedStack> inputs = new ArrayList<>();
        public List<PositionedStack> outputs = new ArrayList<>();

        public CachedBotanyCropsRecipe(ItemStack crop, ItemStack soil, List<HarvestInfo> results) {
            if (crop != null) {
                inputs.add(new PositionedStack(crop, 22, 14));
            }

            if (soil != null) {
                inputs.add(new PositionedStack(soil, 22, 32));
            }

            if (results != null) {
                for (int i = 0; i < results.size() && i < 12; i++) {
                    HarvestInfo hm = results.get(i);
                    int col = i % 4;
                    int row = i / 4;
                    int x = 75 + (col * 18);
                    int y = 5 + (row * 18);

                    PositionedStackAdv stackAdv = new PositionedStackAdv(hm.getStack(), x, y);
                    stackAdv.addToTooltip("§7Chance: §f" + String.format("%.2f%%", hm.getChance() * 100));
                    if (hm.getMinRolls() != hm.getMaxRolls()) {
                        stackAdv.addToTooltip("§7Amount: §f" + hm.getMinRolls() + " - " + hm.getMaxRolls());
                    } else {
                        stackAdv.addToTooltip("§7Amount: §f" + hm.getMinRolls());
                    }

                    outputs.add(stackAdv);
                }
            }
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return inputs;
        }

        @Override
        public List<PositionedStack> getOtherStacks() {
            return outputs;
        }

        @Override
        public PositionedStack getResult() {
            return null;
        }

    }
}
