package com.huypham.trademe.helper;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.Random;

public class Utils {
    private static final Random RANDOM = new Random();

    public static MinecraftServer getServer() {
        return DistExecutor.safeCallWhenOn(Dist.DEDICATED_SERVER, () -> ServerLifecycleHooks::getCurrentServer);
    }

    public static Item getRandomItem(MinecraftServer server) {
        var itemRegistry = server.registryAccess().registryOrThrow(Registries.ITEM);
        var items = itemRegistry.keySet().toArray(new ResourceLocation[0]);
        ResourceLocation randomItemKey = items[RANDOM.nextInt(items.length)];
        Item item = itemRegistry.get(randomItemKey);
        ItemStack itemStack = new ItemStack(item);

        // non creative item
        if (item.getRarity(itemStack) == Rarity.EPIC) {
            return getRandomItem(server);
        } else
            return item;
    }
}
