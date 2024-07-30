package com.huypham.trademe.item;

import com.huypham.trademe.Main;
import com.huypham.trademe.block.custom.Blocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Items {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Main.MODID);

    public static final RegistryObject<Item> EXCHANGE_BLOCK_ITEM = ITEMS.register("exchange_block", ()->new BlockItem(Blocks.EXCHANGE_BLOCK.get(), new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> LUCKY_BLOCK_ITEM = ITEMS.register("lucky_block", ()->new BlockItem(Blocks.LUCKY_BLOCK.get(), new Item.Properties().stacksTo(64).rarity(Rarity.UNCOMMON)){
        @Override
        public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
            pTooltip.add(Component.translatable("item.trademe.lucky_block.tooltip"));
            super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        }
    });

    public static final RegistryObject<Item> TICKET_ITEM = ITEMS.register("ticket_item", ()->new Item(new Item.Properties().stacksTo(64).rarity(Rarity.UNCOMMON)){
        @Override
        public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
            pTooltip.add(Component.translatable("item.trademe.ticket_item.tooltip"));
            super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        }
    });

    public static final RegistryObject<Item> ANVIL_REVAMP_BLOCK_ITEM = ITEMS.register("anvil_revamp_block",  ()->new BlockItem(Blocks.ANVIL_BLOCK_REVAMP.get(), new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)){
        @Override
        public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
            pTooltip.add(Component.translatable("item.trademe.anvil_revamp_block.tooltip"));
            super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        }
    });


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
