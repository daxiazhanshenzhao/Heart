package org.heart.effect;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.heart.Heart;
import org.heart.api.init.EffectInit;

import java.util.Objects;

@Mod.EventBusSubscriber
public class MissHandEffect extends MobEffect {
    public MissHandEffect() {
        super(MobEffectCategory.HARMFUL, 0x932423);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        //一级：造成伤害，挖掘速度，攻速减半
        if (pAmplifier == 0){
            addAttributeModifier(Attributes.ATTACK_SPEED, "f395d734-8784-4979-812d-251def592f4f", -0.5D, AttributeModifier.Operation.MULTIPLY_BASE);

        }
        //二级：造成伤害，挖掘速度，攻速归零
        if (pAmplifier == 1){
            addAttributeModifier(Attributes.ATTACK_SPEED, "f395d734-8784-4979-812d-251def592f4f", -1D, AttributeModifier.Operation.MULTIPLY_BASE);

        }

        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
//    BlockBehaviour.BlockStateBase

    @SubscribeEvent
    public static void digSpeedEvent(PlayerEvent.BreakSpeed event) {
        if (true) {
            var player = event.getEntity();
            if (player.hasEffect(EffectInit.MISS_HAND.get())){
                if (Objects.requireNonNull(player.getEffect(EffectInit.MISS_HAND.get())).getAmplifier()== 0){
                    event.setNewSpeed(event.getOriginalSpeed() * 0.5f);
                }
                if (Objects.requireNonNull(player.getEffect(EffectInit.MISS_HAND.get())).getAmplifier() == 1) {
                    event.setCanceled(true);

                }
            }

        }
    }
    @SubscribeEvent
    public static void damageEvent(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player) {

            if (player.hasEffect(EffectInit.MISS_HAND.get())){
                if (Objects.requireNonNull(player.getEffect(EffectInit.MISS_HAND.get())).getAmplifier()== 0){
                    event.setAmount(event.getAmount() * 0.5F);
                    return;
                }

                if (Objects.requireNonNull(player.getEffect(EffectInit.MISS_HAND.get())).getAmplifier() == 1) {

                    event.setAmount(event.getAmount() * 0);
                }
            }


        }
    }
}
