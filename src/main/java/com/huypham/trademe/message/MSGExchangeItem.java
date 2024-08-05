package com.huypham.trademe.message;

import com.huypham.trademe.container.ExchangeBlockMenu;
import com.huypham.trademe.helper.Utils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

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
        int slot = inventory.findSlotMatchingItem(need);
        int count = inventory.getItem(slot).getCount();
        int needCount = need.getCount();

        if (inventory.contains(need)){
            if (inventory.getFreeSlot() > 0){
                if (count >= needCount){
                    inventory.removeItem(slot, needCount);
                    inventory.add(result);
                }
            }
        }

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
