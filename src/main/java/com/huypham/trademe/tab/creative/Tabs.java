package com.huypham.trademe.tab.creative;

import com.huypham.trademe.Main;
import com.huypham.trademe.item.Items;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class Tabs {
    private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Main.MODID);

    public static final RegistryObject<CreativeModeTab> GENERAL_TAB = TABS.register("general_tab", () ->
            CreativeModeTab.builder()
                    .title(Component.translatable(Main.MODID.concat(".").concat("general_tab")))
                    .icon(()->Items.EXCHANGE_BLOCK_ITEM.get().getDefaultInstance())
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(Items.EXCHANGE_BLOCK_ITEM.get());
                        output.accept(Items.TICKET_ITEM.get());
                        output.accept(Items.LUCKY_BLOCK_ITEM.get());
                        output.accept(Items.ANVIL_REVAMP_BLOCK_ITEM.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        TABS.register(eventBus);
    }
}
