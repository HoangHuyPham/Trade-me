package com.huypham.trademe.enchantment;

import com.huypham.trademe.effect.Effects;
import com.huypham.trademe.item.bow.MysticBowItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

public class InfinityFireworkEnchantment extends Enchantment {

    public static final EnchantmentCategory CUSTOM_BOW_OR_BOW = EnchantmentCategory.create("custom_bow_or_bow", item -> EnchantmentCategory.BOW.canEnchant(item) || EnchantmentCategory.CROSSBOW.canEnchant(item) || item instanceof MysticBowItem);

    public InfinityFireworkEnchantment() {
        super(Rarity.VERY_RARE, CUSTOM_BOW_OR_BOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public int getMaxCost(int pLevel) {
        return 550;
    }

    @Override
    public int getMinCost(int pLevel) {
        return 160;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
