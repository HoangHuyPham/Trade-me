package com.huypham.trademe;

import com.huypham.trademe.block.custom.Blocks;
import com.huypham.trademe.block.entity.BlockEntities;
import com.huypham.trademe.client.screen.AnvilRevampScreen;
import com.huypham.trademe.client.screen.ExchangeBlockScreen;
import com.huypham.trademe.container.MenuTypes;
import com.huypham.trademe.effect.Effects;
import com.huypham.trademe.enchantment.Enchantments;
import com.huypham.trademe.helper.DevLog;
import com.huypham.trademe.item.Items;
import com.huypham.trademe.message.MSGExchangeItem;
import com.huypham.trademe.particle.MarkOfDeathLastParticle;
import com.huypham.trademe.particle.MarkOfDeathParticle;
import com.huypham.trademe.particle.Particles;
import com.huypham.trademe.sound.Sounds;
import com.huypham.trademe.tab.creative.Tabs;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mod(Main.MODID)
public class Main {
    public static final String MODID = "trademe";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final List<Item> items = new ArrayList<>();
    public static final HashMap<Integer, Registry<Item>> ITEM_REGISTRY = new HashMap<>();
    private static final String PROTOCOL_VERSION = "1";
    private static int packetsRegistered;
    public static final SimpleChannel MAIN_NETWORK = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MODID, "main_channel"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public Main() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        Blocks.register(modEventBus);
        Items.register(modEventBus);
        Tabs.register(modEventBus);
        BlockEntities.register(modEventBus);
        MenuTypes.register(modEventBus);
        Effects.register(modEventBus);
        Enchantments.register(modEventBus);
        Particles.register(modEventBus);
        Sounds.register(modEventBus);

        modEventBus.addListener(this::onSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static <MSG> void sendMSGToServer(MSG message) {
        MAIN_NETWORK.sendToServer(message);
    }



    void onSetup(FMLCommonSetupEvent event) {
        MAIN_NETWORK.registerMessage(packetsRegistered++, MSGExchangeItem.class, MSGExchangeItem::encode, MSGExchangeItem::decode, MSGExchangeItem::handle);
        DevLog.print(this, "All messages is registered");
    }

    @SubscribeEvent
    void onFMLLoadCompleteEvent(ServerStartedEvent event) {
        MinecraftServer server = event.getServer();
        ITEM_REGISTRY.put(0, server.registryAccess().registryOrThrow(Registries.ITEM));
        var items0 = ITEM_REGISTRY.put(0, server.registryAccess().registryOrThrow(Registries.ITEM)).keySet().toArray(new ResourceLocation[0]);
        for (var item : items0) {
            items.add(ITEM_REGISTRY.put(0, server.registryAccess().registryOrThrow(Registries.ITEM)).get(item));
        }
        DevLog.print(this, "Load item complete!");
    }

    @SubscribeEvent
    public void hi(PlayerInteractEvent.RightClickBlock event) {
        MAIN_NETWORK.sendToServer(new MSGExchangeItem(123));
    }


    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
            event.enqueueWork(
                    () -> {
                        MenuScreens.register(MenuTypes.EXCHANGE_BLOCK_MENU.get(), ExchangeBlockScreen::new);
                        MenuScreens.register(MenuTypes.ANVIL_REVAMP_BLOCK_MENU.get(), AnvilRevampScreen::new);
                    }
            );
        }

        @SubscribeEvent
        public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(Particles.MARK_OF_DEATH_PARTICLE.get(), MarkOfDeathParticle.Provider::new);
            event.registerSpriteSet(Particles.MARK_OF_DEATH_LAST_PARTICLE.get(), MarkOfDeathLastParticle.Provider::new);
        }
    }
}
