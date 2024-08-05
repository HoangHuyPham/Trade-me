package com.huypham.trademe.effect;

import com.huypham.trademe.Main;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Effects {
    private static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Main.MODID);

    public static final RegistryObject<MobEffect> MARK_OF_DEATH = EFFECTS.register("mark_of_death", MarkOfDeathEffect::new);

    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }


}
