package com.huypham.trademe.block.custom;

import com.huypham.trademe.helper.DevLog;
import com.huypham.trademe.helper.Utils;
import com.huypham.trademe.sound.Sounds;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class LuckyBlock extends Block {

    ItemStack itemStack = ItemStack.EMPTY;

    public LuckyBlock() {
        super(BlockBehaviour.Properties.of().forceSolidOn());
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.box(0.0625, 0, 0.0625, 0.875, 0.875, 0.875);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.box(0.0625, 0, 0.0625, 0.875, 0.875, 0.875);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void destroy(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        DevLog.print(this, "destroy>>");
        if (!pLevel.isClientSide()){
            itemStack = new ItemStack(Utils.getRandomItem(pLevel.getServer()), new Random().nextInt(1, 3));
        }
        super.destroy(pLevel, pPos, pState);
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
        DevLog.print(this, "getDrops>>");
        List<ItemStack> list = new ArrayList<>();
        list.add(this.itemStack);

        //
        List<Enchantment> nonProtectEnchantments = new LinkedList<>();
        nonProtectEnchantments.add(Enchantments.FIRE_ASPECT);
        nonProtectEnchantments.add(Enchantments.RIPTIDE);
        nonProtectEnchantments.add(Enchantments.SMITE);
        nonProtectEnchantments.add(Enchantments.BINDING_CURSE);
        nonProtectEnchantments.add(Enchantments.QUICK_CHARGE);
        nonProtectEnchantments.add(Enchantments.SWEEPING_EDGE);
        nonProtectEnchantments.add(Enchantments.VANISHING_CURSE);
        nonProtectEnchantments.add(Enchantments.AQUA_AFFINITY);
        nonProtectEnchantments.add(Enchantments.BANE_OF_ARTHROPODS);
        nonProtectEnchantments.add(Enchantments.BLOCK_EFFICIENCY);
        nonProtectEnchantments.add(Enchantments.FROST_WALKER);
        nonProtectEnchantments.add(Enchantments.MENDING);
        nonProtectEnchantments.add(Enchantments.LOYALTY);
        nonProtectEnchantments.add(Enchantments.PIERCING);
        nonProtectEnchantments.add(Enchantments.SHARPNESS);
        nonProtectEnchantments.add(Enchantments.SILK_TOUCH);
        nonProtectEnchantments.add(Enchantments.SOUL_SPEED);
        nonProtectEnchantments.add(Enchantments.THORNS);
        nonProtectEnchantments.add(Enchantments.POWER_ARROWS);
        nonProtectEnchantments.add(Enchantments.UNBREAKING);
        nonProtectEnchantments.add(Enchantments.IMPALING);
        nonProtectEnchantments.add(Enchantments.FISHING_LUCK);
        nonProtectEnchantments.add(com.huypham.trademe.enchantment.Enchantments.MARK_OF_DEATH_ENCHANTMENT.get());
        Random rd = new Random();
        if (rd.nextInt(1, 11) == 4){
            list.add(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(nonProtectEnchantments.get(rd.nextInt(nonProtectEnchantments.size())), rd.nextInt(6, 11))));
        }

        return list;
    }

    @Override
    public int getExpDrop(BlockState state, LevelReader level, RandomSource randomSource, BlockPos pos, int fortuneLevel, int silkTouchLevel) {
        return randomSource.nextInt(0, 20);
    }

    @Override
    public SoundType getSoundType(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        return Sounds.LUCKY_BLOCK_SOUND;
    }
}
