package org.heart.api.util;


import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.heart.Heart;
import org.heart.api.init.CapabilityInit;
import org.heart.gui.BodyContainer;

@Mod.EventBusSubscriber
public class Text{

    @SubscribeEvent
    public static void rightClick(PlayerInteractEvent.RightClickItem event){
        if (false) return;

        if (event.getEntity() instanceof ServerPlayer player){
            var msg = event.getItemStack().getTags().toList().toString();
            Heart.LOGGER.info(msg);

            var cap = event.getEntity().getCapability(CapabilityInit.BODY_DATA);
            cap.ifPresent(capability->{
                var a = capability.getHead().getItem();
                var b = capability.getBody().getItem();
                var c = capability.getHeart().getItem();
                var d = capability.getLeftHand().getItem();
                var e = capability.getRightHand().getItem();
                var f = capability.getLeftFoot().getItem();
                var g = capability.getRightFoot().getItem();

                if (a != null) Heart.LOGGER.info(a.toString());
                if (b != null) Heart.LOGGER.info(b.toString());
                if (c != null) Heart.LOGGER.info(c.toString());
                if (d != null) Heart.LOGGER.info(d.toString());
                if (e != null) Heart.LOGGER.info(e.toString());
                if (f != null) Heart.LOGGER.info(f.toString());
                if (g != null) Heart.LOGGER.info(g.toString());

                Heart.LOGGER.info(String.valueOf(capability.getHead().isEmpty()));



            });
        }

    }
}

