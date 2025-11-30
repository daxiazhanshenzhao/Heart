package org.heart.api.handle;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.heart.api.init.CapabilityInit;
import org.heart.gui.BodyContainer;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.event.CurioChangeEvent;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.HashMap;

@Mod.EventBusSubscriber
public class ModEventHandle {

    @SubscribeEvent
    public static void PlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player instanceof ServerPlayer player) {

            var bodyData = player.getCapability(CapabilityInit.BODY_DATA);

            bodyData.ifPresent(capability->{
                capability.handleTick(event);
            });
        }
    }

    //玩家打开GUI被秒杀
    @SubscribeEvent
    public static void handleContainer(PlayerContainerEvent.Open event){
        if (event.getEntity() instanceof ServerPlayer player &&
            event.getContainer() instanceof BodyContainer bodyContainer) {
            player.getCapability(CapabilityInit.BODY_DATA).ifPresent(bodyData -> {
                bodyData.setOpen(true);
            });

        }
    }
    @SubscribeEvent
    public static void handleCloseContainer(PlayerContainerEvent.Close event){
        if (event.getEntity() instanceof ServerPlayer player &&
            event.getContainer() instanceof BodyContainer bodyContainer) {
            player.getCapability(CapabilityInit.BODY_DATA).ifPresent(bodyData -> {
                bodyData.setOpen(false);
            });
        }
    }
    @SubscribeEvent
    public static void handlePlayerHurt(LivingHurtEvent event){
        if (event.getEntity() instanceof ServerPlayer player){
            player.getCapability(CapabilityInit.BODY_DATA).ifPresent(bodyData -> {
                if (bodyData.isOpen()){
                    event.setAmount(event.getAmount()*(Integer.MAX_VALUE));
                }

            });
        }
    }

    /**
     * 更新身体数据
     * @param event
     */
    @SubscribeEvent
    public static void updateBodyData(CurioChangeEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            var map = updateBodySlots(player);
            var bodyData = player.getCapability(CapabilityInit.BODY_DATA);

            bodyData.ifPresent(capability->{
                capability.updateBodyData(map);
            });
        }

    }

    /**
     * 提取身体数据成为一个map
     * @param player
     * @return
     */
    public static HashMap<String, ICurioStacksHandler> updateBodySlots(Player player) {
        HashMap<String, ICurioStacksHandler> map = new HashMap<>();

        //Cap BodyData
        var bodyData = player.getCapability(CapabilityInit.BODY_DATA);
        bodyData.ifPresent(bodyData1 -> {
            for(String identify : BodyContainer.BODY_SLOT){
                CuriosApi.getCuriosInventory(player).ifPresent(curios ->{
                    ICurioStacksHandler stack = curios.getCurios().get(identify);
                    map.put(identify, stack);
                });
            }
        });

        return map;
    }



}
