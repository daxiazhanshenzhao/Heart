package org.heart.api.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.heart.Heart;
import org.heart.effect.MissHandEffect;
import org.heart.effect.MissHeadEffect;

public class EffectInit {

    private static final DeferredRegister<MobEffect> MOB_EFFECT = DeferredRegister.create(Registries.MOB_EFFECT, Heart.MODID);

    public static void register(IEventBus eventBus){
        MOB_EFFECT.register(eventBus);
    }

    public static final RegistryObject<MobEffect> MISS_HEAD;
    public static final RegistryObject<MobEffect> MISS_HAND;

    static {
        MISS_HEAD = MOB_EFFECT.register("miss_head", MissHeadEffect::new);
        MISS_HAND = MOB_EFFECT.register("miss_hand", MissHandEffect::new);
    }

}
