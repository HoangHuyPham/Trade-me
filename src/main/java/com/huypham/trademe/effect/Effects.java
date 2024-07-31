package com.huypham.trademe.effect;

import com.huypham.trademe.Main;
import com.huypham.trademe.block.custom.Blocks;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandExceptionType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Effects {
    private static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Main.MODID);

    public static final RegistryObject<MobEffect> MARK_OF_DEATH = EFFECTS.register("mark_of_death", MarkOfDeathEffect::new);

    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }


}
