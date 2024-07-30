package com.huypham.trademe.container;

import com.huypham.trademe.helper.DevLog;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ExchangeBlockMenu extends AbstractContainerMenu {
    private final Container container;

    public ExchangeBlockMenu(int containerId, Inventory playerInv, Container container) {
        super(Containers.EXCHANGE_BLOCK_MENU.get(), containerId);
        this.container = container;

        addSlot(new Slot(this.container, 0, 36, 20));
        addSlot(new Slot(this.container, 1, 108, 20){
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return false;
            }
        });

        // place items on inventory player
        for (int i=9; i<=17; i++){
            addSlot(new Slot(playerInv, i, (i-9)*18, 93));
        }
        for (int i=18; i<=26; i++){
            addSlot(new Slot(playerInv, i, (i-18)*18, 111));
        }
        for (int i=27; i<=35; i++){
            addSlot(new Slot(playerInv, i, (i-27)*18, 129));
        }

        // ... hotbar
        for (int i=0; i<=8; i++){
            addSlot(new Slot(playerInv, i, (i)*18, 151));
        }
    }

    @Override
    public void slotsChanged(Container pContainer) {
        DevLog.print(this, "slotsChanged");
        super.slotsChanged(pContainer);
    }

    @Override
    public void removed(Player pPlayer) {
        if (pPlayer instanceof ServerPlayer){
            DevLog.print(this, "removed");
            super.removed(pPlayer);
        }
    }


    @Override
    protected Slot addSlot(Slot pSlot) {
        DevLog.print(this, "addSlot");
        return super.addSlot(pSlot);
    }



    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        DevLog.print(this, "quickMoveStack");
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
