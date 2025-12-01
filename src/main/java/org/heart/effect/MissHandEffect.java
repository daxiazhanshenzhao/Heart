package org.heart.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.heart.api.init.EffectInit;


@Mod.EventBusSubscriber
public class MissHandEffect extends MobEffect {

    public MissHandEffect() {
        super(MobEffectCategory.HARMFUL, 0x932423);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        // 一级：造成伤害，挖掘速度，攻速减半
        // 二级：造成伤害，挖掘速度，攻速归零
        // 注意：二级效果的"攻速归零"完全通过 attackEvent 取消实现
        // 属性修改器保持适中的值（-50%），避免冷却条显示异常

        if (pAmplifier == 0) {
            // 一级：攻速减半（-50%）
            addAttributeModifier(Attributes.ATTACK_SPEED, "f395d734-8784-4979-812d-251def592f4f", -0.5D, AttributeModifier.Operation.MULTIPLY_BASE);
        } else if (pAmplifier >= 1) {
            // 二级：属性也设置为减半，但攻击会被事件完全阻止
            // 这样可以避免冷却条显示问题，同时通过事件取消实现完全禁止攻击
            addAttributeModifier(Attributes.ATTACK_SPEED, "f395d734-8784-4979-812d-251def592f4f", -0.5D, AttributeModifier.Operation.MULTIPLY_BASE);
        }


        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

    @SubscribeEvent
    public static void digSpeedEvent(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        if (player.hasEffect(EffectInit.MISS_HAND.get())) {
            MobEffectInstance effectInstance = player.getEffect(EffectInit.MISS_HAND.get());
            if (effectInstance != null) {
                int amplifier = effectInstance.getAmplifier();
                if (amplifier == 0) {
                    // 一级：挖掘速度减半
                    event.setNewSpeed(event.getOriginalSpeed() * 0.5f);
                } else if (amplifier >= 1) {
                    // 二级：挖掘速度归零
                    event.setNewSpeed(0.0f);
                }
            }

        }
    }


    @SubscribeEvent
    public static void damageEvent(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            if (player.hasEffect(EffectInit.MISS_HAND.get())) {
                MobEffectInstance effectInstance = player.getEffect(EffectInit.MISS_HAND.get());
                if (effectInstance != null) {
                    int amplifier = effectInstance.getAmplifier();
                    if (amplifier == 0) {
                        // 一级：造成伤害减半
                        event.setAmount(event.getAmount() * 0.5F);
                    } else if (amplifier >= 1) {
                        // 二级：造成伤害归零
                        event.setAmount(0.0F);
                    }
                }
            }

        }
    }
    @SubscribeEvent
    public static void attackEvent(AttackEntityEvent event) {
        Player player = event.getEntity();
        if (player.hasEffect(EffectInit.MISS_HAND.get())) {
            MobEffectInstance effectInstance = player.getEffect(EffectInit.MISS_HAND.get());
            if (effectInstance != null && effectInstance.getAmplifier() >= 1) {
                // 二级：完全禁止攻击
                // 只取消事件，不重置冷却计时器（resetAttackStrengthTicker 会触发冷却条显示）
                event.setCanceled(true);
            }
        }
    }

}

