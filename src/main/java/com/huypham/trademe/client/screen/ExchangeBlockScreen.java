package com.huypham.trademe.client.screen;

import com.huypham.trademe.Main;
import com.huypham.trademe.container.ExchangeBlockMenu;
import com.huypham.trademe.helper.Utils;
import com.huypham.trademe.message.MSGExchangeItem;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageWidget;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ExchangeBlockScreen extends AbstractContainerScreen<ExchangeBlockMenu> {
    private static final ResourceLocation EXCHANGE_MENU = new ResourceLocation(Main.MODID, "textures/gui/exchange_block.png");
    private static final ResourceLocation TRADE_BTN = new ResourceLocation(Main.MODID, "textures/gui/trade_btn.png");

    final List<ItemStack[]> itemStackMap = new ArrayList<>();

    final ImageWidget trade1 = new ImageWidget(42, 46, 20, 20, TRADE_BTN) {
        @Override
        public void onClick(double pMouseX, double pMouseY) {
            super.onClick(pMouseX, pMouseY);
            handleTrade(1);
        }


    };
    final ImageWidget trade2 = new ImageWidget(42, 46 + 25, 20, 20, TRADE_BTN) {
        @Override
        public void onClick(double pMouseX, double pMouseY) {
            super.onClick(pMouseX, pMouseY);
            handleTrade(2);
        }
    };
    final ImageWidget trade3 = new ImageWidget(122, 46, 20, 20, TRADE_BTN) {
        @Override
        public void onClick(double pMouseX, double pMouseY) {
            super.onClick(pMouseX, pMouseY);
            handleTrade(3);
        }
    };
    final ImageWidget trade4 = new ImageWidget(122, 46 + 25, 20, 20, TRADE_BTN) {
        @Override
        public void onClick(double pMouseX, double pMouseY) {
            super.onClick(pMouseX, pMouseY);
            handleTrade(4);
        }
    };

    private void handleTrade(int id) {
        Main.sendMSGToServer(new MSGExchangeItem(id));
    }

    public ExchangeBlockScreen(ExchangeBlockMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.titleLabelX = 10;
        this.titleLabelY = 5;
        this.imageWidth = 182;  // padding left/right = 10
        this.imageHeight = 166 + 20 + 10; // padding top 20 / bottom 10
        this.inventoryLabelX = 10;
        this.inventoryLabelY = 100;
    }

    @Override
    protected void init() {
        super.init();
        itemStackMap.clear();
        itemStackMap.addAll(Utils.getExchangeTable());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, EXCHANGE_MENU);
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        updateWidgetPositions();

        this.trade1.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.trade2.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.trade3.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.trade4.render(guiGraphics, mouseX, mouseY, partialTicks);

        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }


    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        pGuiGraphics.blit(EXCHANGE_MENU, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    private void updateWidgetPositions() {
        trade1.setPosition(42 + this.leftPos, 46 + this.topPos);
        trade2.setPosition(42 + this.leftPos, 71 + this.topPos);
        trade3.setPosition(122 + this.leftPos, 46 + this.topPos);
        trade4.setPosition(122 + this.leftPos, 71 + this.topPos);
        this.addWidget(trade1);
        this.addWidget(trade2);
        this.addWidget(trade3);
        this.addWidget(trade4);
    }

    private void renderItemAndAmount(GuiGraphics guiGraphics, ItemStack itemStack, int x, int y) {
        guiGraphics.renderItem(itemStack, this.leftPos + x+1, this.topPos + y+1);
        guiGraphics.renderItemDecorations(this.font, itemStack, this.leftPos + x+1, this.topPos + y+1);
    }

}
