package com.huypham.trademe.effect;

import com.huypham.trademe.model.MarkOfDeathData;
import com.huypham.trademe.particle.Particles;
import com.huypham.trademe.sound.Sounds;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

        if (this.lastTick % 30 == 0 && !(this.lastTick <= 1)){
            if (pLivingEntity.getHealth() <= store.get(pLivingEntity).getBonusDamage()){
                if ((pLivingEntity.level() instanceof ServerLevel))
                    ((ServerLevel)pLivingEntity.level()).sendParticles(Particles.MARK_OF_DEATH_PARTICLE.get(), pLivingEntity.getX(), pLivingEntity.getY() + 0.5, pLivingEntity.getZ(), 1, 0, 0.1, 0.1, 0.1);

            }
        }

        if (this.lastTick <= 1){
            float storeBonusDamage = store.get(pLivingEntity).getBonusDamage();
            LivingEntity source = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayer(UUID.fromString(pLivingEntity.getPersistentData().getString("entity_source")));

            if (pLivingEntity.getHealth() <= store.get(pLivingEntity).getBonusDamage()){
                store.remove(pLivingEntity);
                if (source != null){
                    System.out.println("PLAYER_KILLED_ENTITY");
                    pLivingEntity.hurt(pLivingEntity.damageSources().indirectMagic(source, pLivingEntity), storeBonusDamage);
                    CriteriaTriggers.PLAYER_KILLED_ENTITY.trigger((ServerPlayer) source, pLivingEntity,pLivingEntity.damageSources().indirectMagic(source, pLivingEntity));
                }else{
                    pLivingEntity.hurt(pLivingEntity.damageSources().magic(), storeBonusDamage);
                }

            }else{
                if (source != null){
                    System.out.println("PLAYER_HURT_ENTITY");
                    pLivingEntity.hurt(pLivingEntity.damageSources().indirectMagic(source, pLivingEntity), storeBonusDamage);
                    pLivingEntity.getPersistentData().remove("entity_source");
                    CriteriaTriggers.PLAYER_HURT_ENTITY.trigger((ServerPlayer) source, pLivingEntity, pLivingEntity.damageSources().indirectMagic(source, pLivingEntity), storeBonusDamage, 0, false);

                }else{
                    pLivingEntity.hurt(pLivingEntity.damageSources().magic(), storeBonusDamage);
                }
                store.remove(pLivingEntity);
            }
            if ((pLivingEntity.level() instanceof ServerLevel)){
                ((ServerLevel)pLivingEntity.level()).sendParticles(Particles.MARK_OF_DEATH_LAST_PARTICLE.get(), pLivingEntity.getX(), pLivingEntity.getY() + 0.5, pLivingEntity.getZ(), 1, 0, 0, 0, 1);
                pLivingEntity.level().playSound(null, pLivingEntity.getOnPos(), Sounds.MARK_OF_DEATH_LAST.get(), SoundSource.MASTER, 0.5f, 1);
            }
        }
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
