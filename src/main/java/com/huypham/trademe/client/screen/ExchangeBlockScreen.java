package com.huypham.trademe.client.screen;

import com.huypham.trademe.Main;
import com.huypham.trademe.container.ExchangeBlockMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ExchangeBlockScreen extends AbstractContainerScreen<ExchangeBlockMenu> {
    private static final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation(Main.MODID, "textures/gui/exchange_block.png");

    public ExchangeBlockScreen(ExchangeBlockMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.titleLabelX = 10;
        this.imageWidth = 162;
        this.imageHeight = 169;
        this.inventoryLabelX = 10;
        this.leftPos = 0;
        this.topPos = 0;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, BACKGROUND_LOCATION);
        guiGraphics.blit(BACKGROUND_LOCATION,this.leftPos,this.topPos,0, 0, this.imageWidth, this.imageHeight);

    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        guiGraphics.drawString(Minecraft.getInstance().font, "Exchange table", 10, 46, 0xFFFFFF);
        guiGraphics.drawString(Minecraft.getInstance().font, "1 Epic -> 5 ticket", 10, 66, 0xD445EB);
        guiGraphics.drawString(Minecraft.getInstance().font, "1 Rare -> 2 ticket", 10, 78, 0x44E0E0);
        guiGraphics.drawString(Minecraft.getInstance().font, "1 Uncommon -> 1 ticket", 10, 90, 0xC1E044);
        guiGraphics.drawString(Minecraft.getInstance().font, "12 Common -> 1 ticket", 10, 102, 0xFFFFFF);
        guiGraphics.drawString(Minecraft.getInstance().font, "P/s: Grass, sand, dirt,", 10, 114, 0xFFFFFF);
        guiGraphics.drawString(Minecraft.getInstance().font, "cobblestone is trash!", 10, 126, 0xFFFFFF);
        guiGraphics.drawString(Minecraft.getInstance().font, "[15 ticket -> lucky block]", 10, 138, 0x48A7EB);
    }
}
