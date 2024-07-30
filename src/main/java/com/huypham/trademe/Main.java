package com.huypham.trademe;

import com.huypham.trademe.block.custom.Blocks;
import com.huypham.trademe.block.entity.BlockEntities;
import com.huypham.trademe.client.screen.AnvilRevampScreen;
import com.huypham.trademe.client.screen.ExchangeBlockScreen;
import com.huypham.trademe.container.Containers;
import com.huypham.trademe.helper.Utils;
import com.huypham.trademe.item.Items;
import com.huypham.trademe.tab.creative.Tabs;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Main.MODID)
public class Main
{
    public static final String MODID = "trademe";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Main()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        Blocks.register(modEventBus);
        Items.register(modEventBus);
        Tabs.register(modEventBus);
        BlockEntities.register(modEventBus);
        Containers.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }


    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
            event.enqueueWork(
                    ()->{
                        MenuScreens.register(Containers.EXCHANGE_BLOCK_MENU.get(), ExchangeBlockScreen::new);
                        MenuScreens.register(Containers.ANVIL_REVAMP_BLOCK_MENU.get(), AnvilRevampScreen::new);
                    }
            );

        }

    }
}
