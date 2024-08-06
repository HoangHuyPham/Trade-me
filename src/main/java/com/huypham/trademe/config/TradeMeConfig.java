package com.huypham.trademe.config;

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

    public final ForgeConfigSpec.ConfigValue<List<Integer>> exchangeCommon;
    public final ForgeConfigSpec.ConfigValue<List<Integer>> exchangeUnCommon;
    public final ForgeConfigSpec.ConfigValue<List<Integer>> exchangeRare;
    public final ForgeConfigSpec.ConfigValue<List<Integer>> exchangeEpic;
    public final ForgeConfigSpec.IntValue exchangeTicket;

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
        exchange1List.add("{Count:10b,id:\"trademe:ticket_item\"}");

        List<String> exchange2List = new ArrayList<>();
        exchange2List.add(new ItemStack(Items.DIAMOND, 1).serializeNBT().toString());
        exchange2List.add("{Count:2b,id:\"trademe:ticket_item\"}");

        List<String> exchange3List = new ArrayList<>();
        exchange3List.add(new ItemStack(Items.LAPIS_LAZULI, 1).serializeNBT().toString());
        exchange3List.add("{Count:2b,id:\"trademe:ticket_item\"}");

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
        exchange3 = builder.define("item", exchange3List);
        builder.pop();
        builder.push("Exchage4");
        exchange4 = builder.define("item", exchange4List);
        builder.pop();
        builder.pop();

        builder.push("ExchangeTicketRatio");
        builder.comment("This is ratio when exchange item into ticket");
        builder.comment("Left number is number of common items need to exchange into ticket (right number)");
        builder.comment("Example [12, 1]; 12 is left number, 1 is right number");

        ArrayList<Integer> commonRatio = new ArrayList<>();
        commonRatio.add(12);
        commonRatio.add(1);

        ArrayList<Integer> unCommonRatio = new ArrayList<>();
        unCommonRatio.add(1);
        unCommonRatio.add(1);

        ArrayList<Integer> rareRatio = new ArrayList<>();
        rareRatio.add(1);
        rareRatio.add(2);

        ArrayList<Integer> epicRatio = new ArrayList<>();
        epicRatio.add(1);
        epicRatio.add(5);

        exchangeCommon = builder.define("Common", commonRatio);
        exchangeUnCommon = builder.define("Uncommon", unCommonRatio);
        exchangeRare = builder.define("Rare", rareRatio);
        exchangeEpic = builder.define("Epic", epicRatio);

        builder.comment("This is number of ticket to exchange into lucky block");
        exchangeTicket = builder.defineInRange("Ticket", 15, 1, 64);
    }
}



