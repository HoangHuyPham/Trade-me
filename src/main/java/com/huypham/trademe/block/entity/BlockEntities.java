package com.huypham.trademe.block.entity;

import com.huypham.trademe.Main;
import com.huypham.trademe.block.custom.Blocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntities {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Main.MODID);

    public static final RegistryObject<BlockEntityType<ExchangeBlockEntity>> EXCHANGE_BLOCK_ENTITY = BLOCK_ENTITIES.register("exchange_block_entity", ()->BlockEntityType.Builder.of(ExchangeBlockEntity::new, Blocks.EXCHANGE_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
