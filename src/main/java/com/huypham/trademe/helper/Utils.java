package com.huypham.trademe.helper;

import com.huypham.trademe.Main;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

import java.util.Random;

public class Utils {
    private static final Random RANDOM = new Random();

    public static Item getRandomItem(MinecraftServer server) {
        var items = Main.items;
        Item item = items.get(RANDOM.nextInt(items.size()));;
        ItemStack itemStack = new ItemStack(item);
        // non creative item
        if (item.getRarity(itemStack) == Rarity.EPIC) {
            return getRandomItem(server);
        } else
            return item;
    }
}
