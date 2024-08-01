package com.huypham.trademe.sound;

import com.huypham.trademe.Main;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class Sounds {

        private static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, Main.MODID);

        public static final RegistryObject<SoundEvent> MARK_OF_DEATH_LAST = SOUNDS.register("death_mark_last", ()->SoundEvent.createFixedRangeEvent(new ResourceLocation(Main.MODID, "death_mark_last"), 10f));


        public static final SoundType LUCKY_BLOCK_SOUND = new SoundType(1f, 1f,
                SoundType.GLASS.getBreakSound(),
                SoundType.METAL.getStepSound(),
                SoundType.GLASS.getPlaceSound(),
                SoundType.METAL.getHitSound(),
                SoundType.METAL.getFallSound()
        );

        public static void register(IEventBus eventBus) {
                SOUNDS.register(eventBus);
        }
}
