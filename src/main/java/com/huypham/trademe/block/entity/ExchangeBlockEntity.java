package com.huypham.trademe.block.entity;

import com.huypham.trademe.Main;
import com.huypham.trademe.container.ExchangeBlockMenu;
import com.huypham.trademe.helper.DevLog;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class ExchangeBlockEntity extends BlockEntity implements MenuProvider, Container {
    final int MAX_STACK = 64;
    public ItemStackHandler itemStackHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            setChanged();
        }
    };

    public ExchangeBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntities.EXCHANGE_BLOCK_ENTITY.get(), pPos, pBlockState);
    }


    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemStackHandler.deserializeNBT(pTag.getCompound("inventory"));
        DevLog.print(this, "load>>" + pTag);
    }


    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemStackHandler.serializeNBT());
        super.saveAdditional(pTag);
        DevLog.print(this, "saveAdditional>>" + pTag);
    }


    @Override
    public Component getDisplayName() {
        return Component.translatable(Main.MODID.concat(".").concat("exchange_block_entity"));
    }


    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        if (!(pPlayer instanceof ServerPlayer)) {
            return null;
        }
        return new ExchangeBlockMenu(pContainerId, pPlayerInventory, this);
    }


    @Override
    public int getContainerSize() {
        return itemStackHandler.getSlots();
    }

    @Override
    public boolean isEmpty() {
        return itemStackHandler.getSlots() <= 0;
    }

    @Override
    public ItemStack getItem(int pSlot) {
        return itemStackHandler.getStackInSlot(pSlot);
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        DevLog.print(this, "removeItem>>" + itemStackHandler.getStackInSlot(pSlot));
        if (pSlot == 0){
            itemStackHandler.extractItem(1, getItem(1).getCount(), false);
        } else if (pSlot == 1) {
            ItemStack inputStack = getItem(0);
            int count = inputStack.getCount();
            int countAfterConduct = 0;

            if (inputStack.is(com.huypham.trademe.item.Items.TICKET_ITEM.get())){
                // 12 item -> 1 coin
                countAfterConduct = count - (pAmount * 15);
                //bugs
                itemStackHandler.setStackInSlot(0, new ItemStack(getItem(0).getItem(), countAfterConduct));
            }else{
                switch (inputStack.getItem().getRarity(inputStack)){
                    case EPIC -> {
                        // 1 item -> 5 coin
                        countAfterConduct = count - (pAmount / 5);
                        itemStackHandler.setStackInSlot(0, new ItemStack(getItem(0).getItem(), countAfterConduct));
                    }

                    case COMMON -> {
                        // 12 item -> 1 coin
                        countAfterConduct = count - (pAmount * 12);
                        itemStackHandler.setStackInSlot(0, new ItemStack(getItem(0).getItem(), countAfterConduct));
                    }

                    case UNCOMMON -> {
                        // 1 item -> 1 coin
                        countAfterConduct = count - (pAmount);
                        itemStackHandler.setStackInSlot(0, new ItemStack(getItem(0).getItem(), countAfterConduct));
                    }

                    case RARE -> {
                        // 1 item -> 2 coin
                        countAfterConduct = count - (pAmount / 2);
                        itemStackHandler.setStackInSlot(0, new ItemStack(getItem(0).getItem(), countAfterConduct));
                    }

                    default -> {
                        setItem(0, ItemStack.EMPTY);
                    }
                }
            }
        }

        return itemStackHandler.extractItem(pSlot, pAmount, false);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        DevLog.print(this, "removeItemNoUpdate>>" + pSlot);
        return itemStackHandler.extractItem(pSlot, itemStackHandler.getStackInSlot(pSlot).getCount(), true);
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        DevLog.print(this, "setItem:slot>>" + pSlot + "stack>>" + pStack);
        if (!pStack.isEmpty()) { // Removing should be ignored
            pStack = handleExchangeItem(pSlot, pStack);
        }
        itemStackHandler.setStackInSlot(pSlot, pStack);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    @Override
    public void clearContent() {
        DevLog.print(this, "clearContent>>");
        removeItem(0, getItem(0).getCount());
        removeItem(1, getItem(1).getCount());
    }

    private ItemStack handleExchangeItem(int pSlot, ItemStack pStack) {
        ItemStack temp = pStack.copyAndClear();

        if (pSlot == 0) {
            if (
                    !temp.is(Items.DIRT) &&
                            !temp.is(Items.GRASS) &&
                            !temp.is(Items.GRASS_BLOCK) &&
                            !temp.is(Items.COBBLESTONE) &&
                            !temp.is(Items.SAND)
            ) {
                int count = temp.getCount();
                int returnCount = 0;

                if (temp.is(com.huypham.trademe.item.Items.TICKET_ITEM.get())){
                    // 12 item -> 1 coin
                    returnCount = Math.floorDiv(count, 15);
                    System.out.println("return count>>" + returnCount);
                    if (returnCount >= MAX_STACK) {
                        returnCount = MAX_STACK - Math.floorMod(MAX_STACK, 15);
                    }
                    setItem(1, new ItemStack(com.huypham.trademe.item.Items.LUCKY_BLOCK_ITEM.get(), returnCount));
                }else{
                    switch (temp.getItem().getRarity(temp)) {
                        case EPIC -> {
                            // 1 item -> 5 coin
                            returnCount = count * 5;
                            if (returnCount >= MAX_STACK) {
                                returnCount = MAX_STACK - Math.floorMod(MAX_STACK, 5);
                            }
                            setItem(1, new ItemStack(com.huypham.trademe.item.Items.TICKET_ITEM.get(), returnCount));
                        }

                        case COMMON -> {
                            // 12 item -> 1 coin
                            returnCount = Math.floorDiv(count, 12);
                            System.out.println("return count>>" + returnCount);
                            if (returnCount >= MAX_STACK) {
                                returnCount = MAX_STACK - Math.floorMod(MAX_STACK, 12);
                            }
                            setItem(1, new ItemStack(com.huypham.trademe.item.Items.TICKET_ITEM.get(), returnCount));
                        }

                        case UNCOMMON -> {
                            // 1 item -> 1 coin
                            returnCount = count;
                            if (returnCount >= MAX_STACK) {
                                returnCount = MAX_STACK - Math.floorMod(MAX_STACK, 1);
                            }
                            setItem(1, new ItemStack(com.huypham.trademe.item.Items.TICKET_ITEM.get(), returnCount));
                        }

                        case RARE -> {
                            // 1 item -> 2 coin
                            returnCount = count * 2;
                            if (returnCount >= MAX_STACK) {
                                returnCount = MAX_STACK - Math.floorMod(MAX_STACK, 2);
                            }
                            setItem(1, new ItemStack(com.huypham.trademe.item.Items.TICKET_ITEM.get(), returnCount));
                        }

                        default -> {
                            setItem(1, ItemStack.EMPTY);
                        }
                    }
                }
            }
        }
        return temp;
    }
}
