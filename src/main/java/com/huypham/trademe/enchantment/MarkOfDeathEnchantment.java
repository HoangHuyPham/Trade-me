package com.huypham.trademe.enchantment;

import com.huypham.trademe.effect.Effects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class MarkOfDeathEnchantment extends Enchantment {

    public MarkOfDeathEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {
        int second = 20;
        if (!pAttacker.level().isClientSide()) {
            if (pTarget instanceof LivingEntity){
                LivingEntity target = (LivingEntity) pTarget;
                if (!target.hasEffect(Effects.MARK_OF_DEATH.get())){
                    target.addEffect(new MobEffectInstance(Effects.MARK_OF_DEATH.get(), 20 * second, pLevel-1));
                }
            }
        }
        super.doPostAttack(pAttacker, pTarget, pLevel);
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
        return 10;
    }
}
