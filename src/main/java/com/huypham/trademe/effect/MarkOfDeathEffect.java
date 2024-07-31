package com.huypham.trademe.effect;

import com.huypham.trademe.helper.DevLog;
import com.huypham.trademe.model.MarkOfDeathData;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;

public class MarkOfDeathEffect extends MobEffect {

    final Map<LivingEntity, MarkOfDeathData> store = new HashMap<>();
    int lastTick;

    public MarkOfDeathEffect() {
        super(MobEffectCategory.HARMFUL, 0xB45151);
    }

    //here
    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!store.containsKey(pLivingEntity)){
            store.put(pLivingEntity, new MarkOfDeathData());
        }


        if (store.get(pLivingEntity).getSnapShotHealth() < pLivingEntity.getHealth()){
            store.get(pLivingEntity).setSnapShotHealth(pLivingEntity.getHealth());
        }


        if (pLivingEntity.getHealth() < store.get(pLivingEntity).getSnapShotHealth()){
            float bonus = store.get(pLivingEntity).getSnapShotHealth() - pLivingEntity.getHealth();
            int level = pAmplifier + 1;
            store.get(pLivingEntity).addBonusDamage(bonus * (0.1f * level)); // +10% with per level
            store.get(pLivingEntity).setSnapShotHealth(pLivingEntity.getHealth());
        }

        if (this.lastTick <= 1){
            float storeBonusDamage = 0;
            if (pLivingEntity.getHealth() <= store.get(pLivingEntity).getBonusDamage()){
                storeBonusDamage = store.get(pLivingEntity).getBonusDamage();
                store.remove(pLivingEntity);
                pLivingEntity.hurt(pLivingEntity.damageSources().magic(), storeBonusDamage);
            }else{
                pLivingEntity.hurt(pLivingEntity.damageSources().magic(), store.get(pLivingEntity).getBonusDamage());
                store.remove(pLivingEntity);
            }
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }


    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        this.lastTick = pDuration;
        return pDuration > 0;
    }


    @Override
    public String getDescriptionId() {
        return "effect.trademe.message.mark_of_death";
    }
}
