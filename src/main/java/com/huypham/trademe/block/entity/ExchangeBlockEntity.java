package com.huypham.trademe.block.entity;

import com.huypham.trademe.Main;
import com.huypham.trademe.container.ExchangeBlockMenu;
import com.huypham.trademe.helper.DevLog;
import com.huypham.trademe.helper.Utils;
import com.huypham.trademe.item.Items;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExchangeBlockEntity extends BlockEntity implements MenuProvider {
    final int resultSlot = 3;

    public ItemStackHandler container = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            handleContentsChanged();
            setChanged();
        }

        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (slot == resultSlot && !simulate) {
                handleTakeResultSlot(amount);
            }
            return super.extractItem(slot, amount, simulate);
        }
    };

    public ExchangeBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntities.EXCHANGE_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    private void handleTakeResultSlot(int amount) {
//        System.out.println("handleTakeResultSlot>>>");
        if (hasTicketOnly()) {
            shrinkTicket(Utils.getExchangeTicketRatio()[4][0] * amount);
        } else {
            shrinkItem();
        }
    }

    private void handleContentsChanged() {
        System.out.println("handleContentsChanged>>>");
        if (hasTicketOnly()) {
            int totalTicket = getTotalTicket();
            int totalResult = Math.floorDiv(totalTicket, Utils.getExchangeTicketRatio()[4][0]);
            int maxTotalResult = this.container.getSlotLimit(resultSlot);

            if (totalResult == this.container.getStackInSlot(resultSlot).getCount()) {
                return;
            }

            if (totalResult > maxTotalResult) {
                totalResult = maxTotalResult;
            }

            this.container.setStackInSlot(resultSlot, new ItemStack(Items.LUCKY_BLOCK_ITEM.get(), totalResult));

        } else {
            int[] rarityCount = getRarityCount();
            int[] resultTicket = getResulTicket(rarityCount[0], rarityCount[1], rarityCount[2], rarityCount[3], Utils.getExchangeTicketRatio());
            int tickets = resultTicket[4];

            if (tickets == this.container.getStackInSlot(resultSlot).getCount()) {
                return;
            }

            this.container.setStackInSlot(resultSlot, new ItemStack(Items.TICKET_ITEM.get(), tickets));
        }
    }


    private int[] getResulTicket(int common, int uncommon, int rare, int epic, int[][] ratioTable) {
        int[] result = new int[5];
        int tickets = 0;

        // Process common items
        while (common >= ratioTable[0][0] && tickets < 64) {
            common -= ratioTable[0][0];
            tickets += ratioTable[0][1];
        }
        result[0] = common;

        // Process uncommon items
        while (uncommon >= ratioTable[1][0] && tickets < 64) {
            uncommon -= ratioTable[1][0];
            tickets += ratioTable[1][1];
        }
        result[1] = uncommon;

        // Process rare items
        while (rare >= ratioTable[2][0] && tickets < 64) {
            if (tickets + ratioTable[2][1] > 64) {
                break;
            }
            rare -= ratioTable[2][0];
            tickets += ratioTable[2][1];
        }
        result[2] = rare;

        // Process epic items
        while (epic >= ratioTable[3][0] && tickets < 64) {
            if (tickets + ratioTable[3][1] > 64) {
                break;
            }
            epic -= ratioTable[3][0];
            tickets += ratioTable[3][1];
        }
        result[3] = epic;

        // Total tickets
        result[4] = tickets;

        return result;
    }

    private void shrinkItem() {
        int[] rarityCount = getRarityCount();
        int[] resultTicket = getResulTicket(rarityCount[0], rarityCount[1], rarityCount[2], rarityCount[3], Utils.getExchangeTicketRatio());
        int remainCommon = resultTicket[0];
        int remainUnCommon = resultTicket[1];
        int remainRare = resultTicket[2];
        int remainEpic = resultTicket[3];
        int needCommon = rarityCount[0] - remainCommon;
        int needUnCommon = rarityCount[1] - remainUnCommon;
        int needRare = rarityCount[2] - remainRare;
        int needEpic = rarityCount[3] - remainEpic;

        shrinkItemHelper(Rarity.COMMON, needCommon);
        shrinkItemHelper(Rarity.UNCOMMON, needUnCommon);
        shrinkItemHelper(Rarity.RARE, needRare);
        shrinkItemHelper(Rarity.EPIC, needEpic);

    }

    // return remain items
    private void shrinkItemHelper(Rarity rarity, int amount) {
        int currentAmount = amount;
        if (amount <= 0)
            return;
        for (int i = 0; i < resultSlot; i++) {
            if (!container.getStackInSlot(i).is(Items.TICKET_ITEM.get()) && container.getStackInSlot(i).getRarity() == rarity){
                int countInSlot = container.getStackInSlot(i).getCount();
                if (currentAmount <= 0) return;
                if (currentAmount > container.getStackInSlot(i).getCount()){
                    container.getStackInSlot(i).shrink(countInSlot);
                    currentAmount -= countInSlot;
                }else{
                    container.getStackInSlot(i).shrink(currentAmount);
                }
            }
        }
    }

    private void shrinkTicket(int amount) {
        int temp = amount;
        for (int i = 0; i < resultSlot; i++) {
            if (container.getStackInSlot(i).is(Items.TICKET_ITEM.get())) {
                ItemStack itemStack = container.getStackInSlot(i);
                for (int am = temp; am > 0; am--) {
                    if (temp <= 0) return;
                    if (itemStack.getCount() > 0)
                        itemStack.shrink(1);
                    else {
                        temp = am;
                        break;
                    }
                }
            }
        }
    }



    private int[] getRarityCount() {
        int[] arr = new int[4];

        for (int i = 0; i < resultSlot; i++) {
            if (container.getStackInSlot(i).is(Items.TICKET_ITEM.get()))
                continue;

            switch (container.getStackInSlot(i).getRarity()) {
                case EPIC -> {
                    arr[3]+=container.getStackInSlot(i).getCount();
                }

                case RARE -> {
                    arr[2]+=container.getStackInSlot(i).getCount();
                }

                case UNCOMMON -> {
                    arr[1]+=container.getStackInSlot(i).getCount();
                }

                default -> {
                    arr[0]+=container.getStackInSlot(i).getCount();
                }
            }
        }

        return arr;
    }

    private int getTotalTicket() {
        int result = 0;
        for (int i = 0; i < resultSlot; i++) {
            if (this.container.getStackInSlot(i).is(Items.TICKET_ITEM.get())) {
                result += this.container.getStackInSlot(i).getCount();
            }
        }
        return result;
    }

    private boolean hasTicketOnly() {
        boolean result = true;
        for (int i = 0; i < 3; i++) {
            if (!this.container.getStackInSlot(i).is(Items.TICKET_ITEM.get()) && !this.container.getStackInSlot(i).isEmpty()) {
                result = false;
            }
        }
        return result;
    }


    @Override
    public void load(CompoundTag pTag) {
        this.container.deserializeNBT(pTag.getCompound("inventory"));
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", container.serializeNBT());
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(Main.MODID.concat(".").concat("exchange_block_entity"));
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        DevLog.print(this, "createMenu>>");
        if (!(pPlayer instanceof ServerPlayer)) {
            DevLog.print(this, "createMenu>>failed");
            return null;
        }

        return new ExchangeBlockMenu(pContainerId, pPlayerInventory, this.container);
    }

}
