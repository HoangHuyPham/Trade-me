package com.huypham.trademe.container;

import com.huypham.trademe.helper.DevLog;
import com.huypham.trademe.helper.Utils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ExchangeBlockMenu extends AbstractContainerMenu {

    final int resultSlot = 11;
    final int[][] exchangeTicketRatio;

    public ExchangeBlockMenu(int containerId, Inventory playerInv) {
        this(containerId, playerInv, new ItemStackHandler(4));
    }

    public ExchangeBlockMenu(int containerId, Inventory playerInv, ItemStackHandler container) {
        super(MenuTypes.EXCHANGE_BLOCK_MENU.get(), containerId);

        this.exchangeTicketRatio = Utils.getExchangeTicketRatio();
        // Virtual container
        Container[] virtualTrade = new SimpleContainer[]{
                new SimpleContainer(2),new SimpleContainer(2),new SimpleContainer(2),new SimpleContainer(2)
        };

        List<ItemStack[]> itemStackMap = Utils.getExchangeTable();

        for (int index = 0; index < itemStackMap.size(); index++) {
            virtualTrade[index].setItem(0, itemStackMap.get(index)[0]);
            virtualTrade[index].setItem(1, itemStackMap.get(index)[1]);
        }

        addSlot(new SlotView(virtualTrade[0], 0, 18, 46));
        addSlot(new SlotView(virtualTrade[0], 1, 18 + 48, 46));

        addSlot(new SlotView(virtualTrade[1], 0, 18, 46 + 26));
        addSlot(new SlotView(virtualTrade[1], 1, 18 + 48, 46 + 26));

        addSlot(new SlotView(virtualTrade[2], 0, 98, 46));
        addSlot(new SlotView(virtualTrade[3], 1, 98 + 48, 46));

        addSlot(new SlotView(virtualTrade[3], 0, 98, 46 + 26));
        addSlot(new SlotView(virtualTrade[3], 1, 98 + 48, 46 + 26));


        // container this
        addSlot(new SlotItemHandler(container, 0, 37 + 1, 20 + 1){
            @Override
            public void set(@NotNull ItemStack stack) {

                super.set(stack);
            }
        });
        addSlot(new SlotItemHandler(container, 1, 55 + 1, 20 + 1));
        addSlot(new SlotItemHandler(container, 2, 73 + 1, 20 + 1));
        addSlot(new SlotItemHandler(container, 3, 127 + 1, 20 + 1) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return false;
            }

            @Override
            public boolean mayPickup(Player playerIn) {
                return this.hasItem();
            }

        });

        // place items on inventory player
        for (int i = 9; i <= 17; i++) {
            addSlot(new Slot(playerInv, i, (i - 9) * 18 + 10 + 1, 110 + 1));
        }
        for (int i = 18; i <= 26; i++) {
            addSlot(new Slot(playerInv, i, (i - 18) * 18 + 10 + 1, 128 + 1));
        }
        for (int i = 27; i <= 35; i++) {
            addSlot(new Slot(playerInv, i, (i - 27) * 18 + 10 + 1, 146 + 1));
        }

        // ... hotbar
        for (int i = 0; i <= 8; i++) {
            addSlot(new Slot(playerInv, i, (i) * 18 + 10 + 1, 168 + 1));
        }
    }




    @Override
    public void slotsChanged(Container pContainer) {
        DevLog.print(this, "slotsChanged");
        super.slotsChanged(pContainer);
    }

    @Override
    public void removed(Player pPlayer) {
        if (pPlayer instanceof ServerPlayer) {
            DevLog.print(this, "removed");
            super.removed(pPlayer);
        }
    }



    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        // The quick moved slot stack
        ItemStack quickMovedStack = ItemStack.EMPTY;
        // The quick moved slot
        Slot quickMovedSlot = this.slots.get(i);

        if (quickMovedSlot instanceof SlotView || i == resultSlot){
            return ItemStack.EMPTY;
        }

        // If the slot is in the valid range and the slot is not empty
        if (quickMovedSlot != null && quickMovedSlot.hasItem()) {
            // Get the raw stack to move
            ItemStack rawStack = quickMovedSlot.getItem();
            // Set the slot stack to a copy of the raw stack
            quickMovedStack = rawStack.copy();

        /*
        The following quick move logic can be simplified to if in data inventory,
        try to move to player inventory/hotbar and vice versa for containers
        that cannot transform data (e.g. chests).
        */

            // If the quick move was performed on the data inventory result slot
            if (i == 0+7) {
                // Try to move the result slot into the player inventory/hotbar
                if (!this.moveItemStackTo(rawStack, 5+7, 41+7, true)) {
                    // If cannot move, no longer quick move
                    return ItemStack.EMPTY;
                }

                // Perform logic on result slot quick move
//                slot.onQuickCraft(rawStack, quickMovedStack);
            }
            // Else if the quick move was performed on the player inventory or hotbar slot
            else if (i >= 5+7 && i < 41+7) {
                // Try to move the inventory/hotbar slot into the data inventory input slots
                if (!this.moveItemStackTo(rawStack, 1+7, 5+7, false)) {
                    // If cannot move and in player inventory slot, try to move to hotbar
                    if (i < 32+7) {
                        if (!this.moveItemStackTo(rawStack, 32+7, 41+7, false)) {
                            // If cannot move, no longer quick move
                            return ItemStack.EMPTY;
                        }
                    }
                    // Else try to move hotbar into player inventory slot
                    else if (!this.moveItemStackTo(rawStack, 5+7, 32+7, false)) {
                        // If cannot move, no longer quick move
                        return ItemStack.EMPTY;
                    }
                }
            }
            // Else if the quick move was performed on the data inventory input slots, try to move to player inventory/hotbar
            else if (!this.moveItemStackTo(rawStack, 5+7, 41+7, false)) {
                // If cannot move, no longer quick move
                return ItemStack.EMPTY;
            }

            if (rawStack.isEmpty()) {
                // If the raw stack has completely moved out of the slot, set the slot to the empty stack
                quickMovedSlot.set(ItemStack.EMPTY);
            } else {
                // Otherwise, notify the slot that that the stack count has changed
                quickMovedSlot.setChanged();
            }

        /*
        The following if statement and Slot#onTake call can be removed if the
        menu does not represent a container that can transform stacks (e.g.
        chests).
        */
            if (rawStack.getCount() == quickMovedStack.getCount()) {
                // If the raw stack was not able to be moved to another slot, no longer quick move
                return ItemStack.EMPTY;
            }
            // Execute logic on what to do post move with the remaining stack
            quickMovedSlot.onTake(player, rawStack);
        }

        return quickMovedStack; // Return the slot stack
    }
    public int[][] getExchangeTicketRatio(){
        return this.exchangeTicketRatio;
    }

    @Override
    protected boolean moveItemStackTo(ItemStack pStack, int pStartIndex, int pEndIndex, boolean pReverseDirection) {

        boolean flag = false;
        int i = pStartIndex;
        if (pReverseDirection) {
            i = pEndIndex - 1;
        }

        if (pStack.isStackable()) {
            while(!pStack.isEmpty()) {
                if (i==resultSlot) {
                    if (pReverseDirection) {
                        --i;
                    } else {
                        ++i;
                    }
                };

                if (pReverseDirection) {
                    if (i < pStartIndex) {
                        break;
                    }
                } else if (i >= pEndIndex) {
                    break;
                }

                //from inventory
                Slot slot = this.slots.get(i);
                ItemStack itemstack = slot.getItem();
                if (!itemstack.isEmpty() && ItemStack.isSameItemSameTags(pStack, itemstack)) {
                    int j = itemstack.getCount() + pStack.getCount();
                    int maxSize = Math.min(slot.getMaxStackSize(), pStack.getMaxStackSize());
                    if (j <= maxSize) {
                        pStack.setCount(0);
                        itemstack.setCount(j);
                        slot.setByPlayer(itemstack);
                        flag = true;

                    } else if (itemstack.getCount() < maxSize) {
                        pStack.shrink(maxSize - itemstack.getCount());
                        itemstack.setCount(maxSize);
                        slot.setByPlayer(itemstack);
                        flag = true;
                    }
                }

                if (pReverseDirection) {
                    --i;
                } else {
                    ++i;
                }
            }
        }

        if (!pStack.isEmpty()) {
            if (pReverseDirection) {
                i = pEndIndex - 1;
            } else {
                i = pStartIndex;
            }

            while(true) {
                if (i==resultSlot) {
                    if (pReverseDirection) {
                        --i;
                    } else {
                        ++i;
                    }
                };

                if (pReverseDirection) {
                    if (i < pStartIndex) {
                        break;
                    }
                } else if (i >= pEndIndex) {
                    break;
                }

                Slot slot1 = this.slots.get(i);
                ItemStack itemstack1 = slot1.getItem();
                if (itemstack1.isEmpty() && slot1.mayPlace(pStack)) {
                    if (pStack.getCount() > slot1.getMaxStackSize()) {
                        slot1.setByPlayer(pStack.split(slot1.getMaxStackSize()));
                    } else {
                        slot1.setByPlayer(pStack.split(pStack.getCount()));
                    }

                    slot1.setChanged();
                    flag = true;
                    break;
                }

                if (pReverseDirection) {
                    --i;
                } else {
                    ++i;
                }
            }
        }



        return flag;
    }



    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    static class SlotView extends Slot{
        public SlotView(Container pContainer, int pSlot, int pX, int pY) {
            super(pContainer, pSlot, pX, pY);
        }

        @Override
        public boolean mayPickup(Player pPlayer) {
            return false;
        }

        @Override
        public boolean mayPlace(ItemStack pStack) {
            return false;
        }
    }
}
