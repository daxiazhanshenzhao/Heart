package org.heart.player;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import org.heart.Heart;
import org.heart.api.handle.NetworkHandler;
import org.heart.network.OpenBodyPacket;


public class KeyMappings {

    public static final KeyMapping OPEN_BODY_SCREEN = new KeyMapping("key.heart.open_body_screen", 67, "key.categories.heart");

    @Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Heart.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEvents {

        @SubscribeEvent
        public static void registerBindings(RegisterKeyMappingsEvent event) {
            event.register(OPEN_BODY_SCREEN);
        }
    }

    @Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Heart.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                while (OPEN_BODY_SCREEN.consumeClick()) {
                    Minecraft minecraft = Minecraft.getInstance();
                    var player = minecraft.player;
                    if (player != null && minecraft.isWindowActive()) {
                        NetworkHandler.INSTANCE.send(PacketDistributor.SERVER.noArg(), new OpenBodyPacket(ItemStack.EMPTY));
                    }
                }
            }
        }
    }



}
