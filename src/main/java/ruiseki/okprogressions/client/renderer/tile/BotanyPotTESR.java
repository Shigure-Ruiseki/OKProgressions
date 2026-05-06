package ruiseki.okprogressions.client.renderer.tile;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ruiseki.okprogressions.common.block.botanypot.TEBotanyPot;

@SideOnly(Side.CLIENT)
public class BotanyPotTESR extends TileEntitySpecialRenderer {

    private final RenderBlocks renderBlocks = new RenderBlocks();

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks) {
        if (!(te instanceof TEBotanyPot pot)) return;

        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);

        GL11.glDisable(GL11.GL_LIGHTING);

        if (pot.getSoil() != null && pot.getSoil().displayBlock != null) {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.5f, 0.4f, 0.5f);
            GL11.glScalef(0.625f, 0.1f, 0.625f);
            this.renderBlock(pot.getSoil().displayBlock, pot.getSoil().displayMeta);
            GL11.glPopMatrix();
        }

        if (pot.getCrop() != null && pot.getCrop().displayBlock != null) {
            GL11.glPushMatrix();

            GL11.glTranslated(0.5, 0.45, 0.5);

            float currentTicks = pot.getCurrentGrowthTicks() + (pot.isGrowing() ? partialTicks : 0);
            float progress = currentTicks / (float) Math.max(1, pot.getTotalGrowthTicks());
            if (progress > 1.0f) progress = 1.0f;

            float scale = 0.25f + (progress * 0.75f);
            float finalScale = scale * 0.625f;

            GL11.glScalef(finalScale, finalScale, finalScale);

            GL11.glTranslatef(0.0f, 0.45f, 0.0f);
            this.renderBlock(pot.getCrop().displayBlock, pot.getCrop().displayMeta);

            GL11.glPopMatrix();
        }

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    private void renderBlock(Block block, int meta) {
        if (block == null) return;

        this.renderBlocks.blockAccess = Minecraft.getMinecraft().theWorld;
        this.bindTexture(TextureMap.locationBlocksTexture);

        GL11.glPushMatrix();
        this.renderBlocks.renderBlockAsItem(block, meta, 1.0f);
        GL11.glPopMatrix();
    }
}
