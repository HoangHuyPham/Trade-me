package com.huypham.trademe.block.custom;

import com.huypham.trademe.block.entity.ExchangeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class ExchangeBlock extends BaseEntityBlock {

    public ExchangeBlock() {
        super(BlockBehaviour.Properties.of().forceSolidOn());
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.box(0.125, 0, 0.125, 0.875, 0.25, 0.875);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.box(0.125, 0, 0.125, 0.875, 0.25, 0.875);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ExchangeBlockEntity(blockPos, blockState);
    }


    @Override
    public SoundType getSoundType(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        return SoundType.METAL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pPlayer instanceof ServerPlayer && !pLevel.isClientSide) {
            NetworkHooks.openScreen((ServerPlayer) pPlayer, this.getMenuProvider(pState, pLevel, pPos));
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }


    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }



    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof ExchangeBlockEntity) {
                if (pLevel instanceof ServerLevel) {
                    ItemStackHandler itemStackHandler = ((ExchangeBlockEntity) blockentity).container;
                    Container c = new SimpleContainer(itemStackHandler.getSlots());
                    // -1 for ignore result slot
                    for (int i=0; i<c.getContainerSize()-1; i++){
                        c.setItem(i, itemStackHandler.getStackInSlot(i));
                    }
                    Containers.dropContents(pLevel, pPos, c);
                }

                pLevel.updateNeighbourForOutputSignal(pPos, this);
            }

            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

}
