package com.huypham.trademe.container;

import com.huypham.trademe.Main;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuTypes {
    private static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Main.MODID);

    public static final RegistryObject<MenuType<ExchangeBlockMenu>> EXCHANGE_BLOCK_MENU = MENU_TYPES.register("exchange_block_menu", ()-> new MenuType<>((pContainerId, pPlayerInventory) -> new ExchangeBlockMenu(pContainerId, pPlayerInventory, new SimpleContainer(2)), FeatureFlags.DEFAULT_FLAGS));

    public static final RegistryObject<MenuType<AnvilRevampMenu>> ANVIL_REVAMP_BLOCK_MENU = MENU_TYPES.register("anvil_revamp_block_menu",()-> new MenuType<>((AnvilRevampMenu::new), FeatureFlags.DEFAULT_FLAGS));

    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}
