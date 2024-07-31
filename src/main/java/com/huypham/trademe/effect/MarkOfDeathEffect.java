package com.huypham.trademe.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class MarkOfDeathEffect extends MobEffect {
    int lastDuration;

    protected MarkOfDeathEffect() {
        super(MobEffectCategory.HARMFUL, 0xB45151);

    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        int displayLevel = pAmplifier +1;
        float baseDamage = pLivingEntity.getMaxHealth() * 0.1f; // 10% max health
        float damageScale = (pLivingEntity.getMaxHealth() * (0.05f * displayLevel)); // add scale 5% with per level
        float totalDamage = baseDamage + damageScale;

        if (pLivingEntity.isAlive()){
            if (displayLevel >= 2){ // displayLevel effect > 2 will add slow scale
                float factor = 1 - 0.1f * displayLevel;
                pLivingEntity.setDeltaMovement(pLivingEntity.getDeltaMovement().multiply(factor, 1F, factor));
            }
            if (lastDuration <= 1){
                pLivingEntity.hurt(pLivingEntity.damageSources().magic(), totalDamage);
            }
        }

        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        this.lastDuration = pDuration;
        return pDuration > 0;
    }

    @Override
    public String getDescriptionId() {
        return "effect.trademe.message.mark_of_death";
    }
}
