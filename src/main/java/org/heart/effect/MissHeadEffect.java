package org.heart.effect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class MissHeadEffect extends MobEffect {
    public MissHeadEffect() {
        super(MobEffectCategory.HARMFUL, 0x932423);
    }

//    MobEffects
    /**
     * 窒息，失明，扣血
     * @param pLivingEntity
     * @param pAmplifier
     */
    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        pLivingEntity.setAirSupply(pLivingEntity.getAirSupply() - 8);
        if (pLivingEntity.tickCount % 23 != 7) return;


        pLivingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0));
        pLivingEntity.hurt(pLivingEntity.damageSources().drown(), 1.0F);

        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
