package com.huypham.trademe.enchantment;

import com.huypham.trademe.Main;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Enchantments {
    private static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Main.MODID);

    public static final RegistryObject<Enchantment> MARK_OF_DEATH_ENCHANTMENT = ENCHANTMENTS.register("mark_of_death_enchantment", MarkOfDeathEnchantment::new);

    public static final RegistryObject<Enchantment> INFINITY_FIREWORK_ENCHANTMENT = ENCHANTMENTS.register("infinity_firework_enchantment", InfinityFireworkEnchantment::new);

    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }


}
