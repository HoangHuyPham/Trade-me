package com.huypham.trademe.message;

import com.huypham.trademe.container.ExchangeBlockMenu;
import com.huypham.trademe.helper.Utils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class MSGExchangeItem {
    public int id;

    public MSGExchangeItem(int id) {
        this.id = id;
    }

    public MSGExchangeItem() {
    }

    public static MSGExchangeItem decode(FriendlyByteBuf buf) {
        return new MSGExchangeItem(buf.readInt());
    }

    public static void encode(MSGExchangeItem message, FriendlyByteBuf buf) {
        buf.writeInt(message.id);
    }

    private static void handleTrade(ItemStack need, ItemStack result, Inventory inventory){

        int needCount = need.getCount();
        int temp = needCount;

        if (getSizeAllSlotMatch(need, inventory) >= needCount){
            if (inventory.getFreeSlot() > 0){
                for (int i: findAllSlotMatch(need, inventory)){
                    if (temp <= 0)
                        break;

                    int count = inventory.getItem(i).getCount();

                    if (temp <= count){
                        inventory.removeItem(i, temp);
                        temp -= temp;
                    }else{
                        inventory.removeItem(i, count);
                        temp -= count;
                    }
                }

                inventory.add(result);
            }
        }

    }

    private static List<Integer> findAllSlotMatch(ItemStack itemStack, Inventory inventory){
        List<Integer> all = new ArrayList<>();
        for(int i = 0; i < inventory.items.size(); ++i) {
            if (!inventory.items.get(i).isEmpty() && ItemStack.isSameItemSameTags(itemStack, inventory.items.get(i))) {
                all.add(i);
            }
        }
        return all;
    }

    private static int getSizeAllSlotMatch(ItemStack itemStack, Inventory inventory){
        int size = 0;
        for(int i = 0; i < inventory.items.size(); ++i) {
            if (!inventory.items.get(i).isEmpty() && ItemStack.isSameItemSameTags(itemStack, inventory.items.get(i))) {
                size += inventory.items.get(i).getCount();
            }
        }
        return size;
    }


    public static void handle(MSGExchangeItem message, Supplier<NetworkEvent.Context> context) {
        context.get().setPacketHandled(true);
        context.get().enqueueWork(() -> {
            ServerPlayer sender = context.get().getSender();
            if (sender.containerMenu instanceof ExchangeBlockMenu){
                Inventory inventory = sender.getInventory();
                List<ItemStack[]> exchangeTable = Utils.getExchangeTable();

                switch (message.id){
                    case 1 ->{
                        handleTrade(exchangeTable.get(0)[0], exchangeTable.get(0)[1], inventory);
                    }
                    case 2 ->{
                        handleTrade(exchangeTable.get(1)[0], exchangeTable.get(1)[1], inventory);
                    }
                    case 3 ->{
                        handleTrade(exchangeTable.get(2)[0], exchangeTable.get(2)[1], inventory);
                    }
                    case 4 ->{
                        handleTrade(exchangeTable.get(3)[0], exchangeTable.get(3)[1], inventory);
                    }
                }

                inventory.setChanged();
            }
        });
    }

}
