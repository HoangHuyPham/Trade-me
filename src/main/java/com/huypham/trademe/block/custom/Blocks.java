package com.huypham.trademe.block.custom;

import com.huypham.trademe.Main;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class Blocks {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, Main.MODID);

    public static final RegistryObject<Block> EXCHANGE_BLOCK = BLOCKS.register("exchange_block", ExchangeBlock::new);

    public static final RegistryObject<Block> LUCKY_BLOCK = BLOCKS.register("lucky_block", LuckyBlock::new);

    public static final RegistryObject<Block> ANVIL_BLOCK_REVAMP = BLOCKS.register("anvil_revamp_block", ()->new AnvilRevampBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(5.0F, 1200.0F).requiresCorrectToolForDrops().sound(SoundType.ANVIL).pushReaction(PushReaction.BLOCK)));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
