package com.huypham.trademe.tab.creative;

import com.huypham.trademe.Main;
import com.huypham.trademe.enchantment.Enchantments;
import com.huypham.trademe.item.Items;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
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
                        output.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(Enchantments.MARK_OF_DEATH_ENCHANTMENT.get(), 1)));
                        output.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(Enchantments.MARK_OF_DEATH_ENCHANTMENT.get(), 2)));
                        output.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(Enchantments.MARK_OF_DEATH_ENCHANTMENT.get(), 3)));
                        output.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(Enchantments.MARK_OF_DEATH_ENCHANTMENT.get(), 4)));
                        output.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(Enchantments.MARK_OF_DEATH_ENCHANTMENT.get(), 5)));
                        output.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(Enchantments.MARK_OF_DEATH_ENCHANTMENT.get(), 6)));
                        output.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(Enchantments.MARK_OF_DEATH_ENCHANTMENT.get(), 7)));
                        output.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(Enchantments.MARK_OF_DEATH_ENCHANTMENT.get(), 8)));
                        output.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(Enchantments.MARK_OF_DEATH_ENCHANTMENT.get(), 9)));
                        output.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(Enchantments.MARK_OF_DEATH_ENCHANTMENT.get(), 10)));
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        TABS.register(eventBus);
    }
}
