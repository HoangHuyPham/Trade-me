package com.huypham.trademe.config;

import com.huypham.trademe.helper.DevLog;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class TradeMeConfig {
    public static final ForgeConfigSpec SERVER_SPEC;
    public static final TradeMeConfig SERVER;
    public final ForgeConfigSpec.ConfigValue<List<String>> exchange1;
    public final ForgeConfigSpec.ConfigValue<List<String>> exchange2;
    public final ForgeConfigSpec.ConfigValue<List<String>> exchange3;
    public final ForgeConfigSpec.ConfigValue<List<String>> exchange4;

    static {
        {
            final Pair<TradeMeConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(TradeMeConfig::new);
            SERVER_SPEC = specPair.getRight();
            SERVER = specPair.getLeft();
        }
    }

    public TradeMeConfig(ForgeConfigSpec.Builder builder) {
        List<String> exchange1List = new ArrayList<>();
        exchange1List.add(new ItemStack(Items.NETHERITE_INGOT, 1).serializeNBT().toString());
        exchange1List.add("{Count:5b,id:\"trademe:ticket_item\"}");

        List<String> exchange2List = new ArrayList<>();
        exchange2List.add(new ItemStack(Items.DIAMOND, 1).serializeNBT().toString());
        exchange2List.add("{Count:3b,id:\"trademe:ticket_item\"}");

        List<String> exchange3List = new ArrayList<>();
        exchange3List.add(new ItemStack(Items.LAPIS_LAZULI, 1).serializeNBT().toString());
        exchange3List.add("{Count:3b,id:\"trademe:ticket_item\"}");

        List<String> exchange4List = new ArrayList<>();
        exchange4List.add(new ItemStack(Items.EMERALD, 5).serializeNBT().toString());
        exchange4List.add("{Count:1b,id:\"trademe:ticket_item\"}");


        builder.comment("Trade me server config");
        builder.push("TradeList");
        builder.comment("First item will be used to trade into Second item");
        builder.push("Exchage1");
        exchange1 = builder.define("item", exchange1List);
        builder.pop();
        builder.push("Exchage2");
        exchange2 = builder.define("item", exchange2List);
        builder.pop();
        builder.push("Exchage3");
        exchange3 = builder.define("item", exchange2List);
        builder.pop();
        builder.push("Exchage4");
        exchange4 = builder.define("item", exchange2List);

        builder.pop();
    }
}



