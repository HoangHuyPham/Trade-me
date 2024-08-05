package com.huypham.trademe.client.screen;

import com.huypham.trademe.Main;
import com.huypham.trademe.container.ExchangeBlockMenu;
import com.huypham.trademe.helper.Utils;
import com.huypham.trademe.item.Items;
import com.huypham.trademe.message.MSGExchangeItem;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderTooltipEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ExchangeBlockScreen extends AbstractContainerScreen<ExchangeBlockMenu> {
    private static final ResourceLocation EXCHANGE_MENU = new ResourceLocation(Main.MODID, "textures/gui/exchange_block.png");
    private static final ResourceLocation EXCHANGE_MENU_SUB = new ResourceLocation(Main.MODID, "textures/gui/exchange_block_sub.png");
    private static final ResourceLocation TRADE_BTN = new ResourceLocation(Main.MODID, "textures/gui/trade_btn.png");
    private static final ResourceLocation INFO_BTN = new ResourceLocation(Main.MODID, "textures/gui/info_btn.png");

    private boolean isInfo = false;

    final List<ItemStack[]> itemStackMap = new ArrayList<>();
    int[][] exchangeRatio;


    final ImageWidget ratioInfo = new ImageWidget(10, 20, 18, 18, INFO_BTN) {
        @Override
        public void onClick(double pMouseX, double pMouseY) {
            super.onClick(pMouseX, pMouseY);
            isInfo = !isInfo;
        }

        @Nullable
        @Override
        public Tooltip getTooltip() {
            return Tooltip.create(Component.translatable("tradme.ratio.info"));
        }
    };

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
        exchangeRatio = Utils.getExchangeTicketRatio();
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
        this.ratioInfo.render(guiGraphics, mouseX, mouseY, partialTicks);

        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }


    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        pGuiGraphics.blit(EXCHANGE_MENU, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        if (isInfo){
            pGuiGraphics.blit(EXCHANGE_MENU_SUB, this.leftPos -this.imageWidth/2, this.topPos, 0, 0, this.imageWidth/2, 140);
            pGuiGraphics.drawString(Minecraft.getInstance().font, String.format("%d E > %d Ticket", exchangeRatio[3][0], exchangeRatio[3][1]), this.leftPos -this.imageWidth/2+5, this.topPos+10, 0xFF55FF);
            pGuiGraphics.drawString(Minecraft.getInstance().font, String.format("%d R > %d Ticket", exchangeRatio[2][0], exchangeRatio[2][1]), this.leftPos -this.imageWidth/2+5, this.topPos+30, 0x55FFFF);
            pGuiGraphics.drawString(Minecraft.getInstance().font, String.format("%d U > %d Ticket", exchangeRatio[1][0], exchangeRatio[1][1]), this.leftPos -this.imageWidth/2+5, this.topPos+50, 0xFFFF55);
            pGuiGraphics.drawString(Minecraft.getInstance().font, String.format("%d C > %d Ticket", exchangeRatio[0][0], exchangeRatio[0][1]), this.leftPos -this.imageWidth/2+5, this.topPos+70, 0xFFFFFF);

            pGuiGraphics.renderItem(new ItemStack(Items.TICKET_ITEM.get(), exchangeRatio[4][0]), this.leftPos -this.imageWidth/2+10, this.topPos+90);
            pGuiGraphics.renderItemDecorations(Minecraft.getInstance().font, new ItemStack(Items.TICKET_ITEM.get(), exchangeRatio[4][0]), this.leftPos -this.imageWidth/2+10, this.topPos+90);

            pGuiGraphics.drawString(Minecraft.getInstance().font, String.format(">"), this.leftPos -this.imageWidth/2+30, this.topPos+95, 0x55FF55);

            pGuiGraphics.renderItem(new ItemStack(Items.LUCKY_BLOCK_ITEM.get(), exchangeRatio[4][1]), this.leftPos -this.imageWidth/2+40, this.topPos+90);
            pGuiGraphics.renderItemDecorations(Minecraft.getInstance().font, new ItemStack(Items.TICKET_ITEM.get(), exchangeRatio[4][0]), this.leftPos -this.imageWidth/2+10, this.topPos+90);
        }
    }

    private void updateWidgetPositions() {
        trade1.setPosition(42 + this.leftPos, 46 + this.topPos);
        trade2.setPosition(42 + this.leftPos, 71 + this.topPos);
        trade3.setPosition(122 + this.leftPos, 46 + this.topPos);
        trade4.setPosition(122 + this.leftPos, 71 + this.topPos);
        ratioInfo.setPosition(10 + this.leftPos, 20 + this.topPos);
        this.addWidget(trade1);
        this.addWidget(trade2);
        this.addWidget(trade3);
        this.addWidget(trade4);
        this.addWidget(ratioInfo);
    }

}
