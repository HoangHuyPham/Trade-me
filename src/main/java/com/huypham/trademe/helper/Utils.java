package com.huypham.trademe.helper;

import com.huypham.trademe.Main;
import com.huypham.trademe.config.TradeMeConfig;
import net.minecraft.nbt.TagParser;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

import java.util.ArrayList;
import java.util.List;
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

    public static List<ItemStack[]> getExchangeTable() {
        List<ItemStack[]> res = new ArrayList<>();

        List<String> exchange1 = TradeMeConfig.SERVER.exchange1.get();
        List<String> exchange2 = TradeMeConfig.SERVER.exchange2.get();
        List<String> exchange3 = TradeMeConfig.SERVER.exchange3.get();
        List<String> exchange4 = TradeMeConfig.SERVER.exchange4.get();

        try {
            ItemStack[] trade1 = new ItemStack[]{ItemStack.of(TagParser.parseTag(exchange1.get(0))), ItemStack.of(TagParser.parseTag(exchange1.get(1)))};

            ItemStack[] trade2 = new ItemStack[]{ItemStack.of(TagParser.parseTag(exchange2.get(0))), ItemStack.of(TagParser.parseTag(exchange2.get(1)))};

            ItemStack[] trade3 = new ItemStack[]{ItemStack.of(TagParser.parseTag(exchange3.get(0))), ItemStack.of(TagParser.parseTag(exchange3.get(1)))};

            ItemStack[] trade4 = new ItemStack[]{ItemStack.of(TagParser.parseTag(exchange4.get(0))), ItemStack.of(TagParser.parseTag(exchange4.get(1)))};

            res.add(trade1);
            res.add(trade2);
            res.add(trade3);
            res.add(trade4);
        }catch (Exception e){
            DevLog.print(Utils.class, "has err>>"+e);
        }


        return res;
    }


    public static int[][] getExchangeTicketRatio(){
        int[][] exchangeTicketRatio = new int[5][5];
        // common ratio
        List<Integer> commonRatio = TradeMeConfig.SERVER.exchangeCommon.get();
        exchangeTicketRatio[0][0] = commonRatio.get(0);
        exchangeTicketRatio[0][1] = commonRatio.get(1);

        // uncommon ratio
        List<Integer> unCommonRatio = TradeMeConfig.SERVER.exchangeUnCommon.get();
        exchangeTicketRatio[1][0] = unCommonRatio.get(0);
        exchangeTicketRatio[1][1] = unCommonRatio.get(1);

        // rare ratio
        List<Integer> rareRatio = TradeMeConfig.SERVER.exchangeRare.get();
        exchangeTicketRatio[2][0] = rareRatio.get(0);
        exchangeTicketRatio[2][1] = rareRatio.get(1);

        // epic ratio
        List<Integer> epicRatio = TradeMeConfig.SERVER.exchangeEpic.get();
        exchangeTicketRatio[3][0] = epicRatio.get(0);
        exchangeTicketRatio[3][1] = epicRatio.get(1);

        // ticket -> lucky block
        int ticketRatio = TradeMeConfig.SERVER.exchangeTicket.get();
        exchangeTicketRatio[4][0] = ticketRatio;
        exchangeTicketRatio[4][1] = 1;

        return exchangeTicketRatio;
    }
}
