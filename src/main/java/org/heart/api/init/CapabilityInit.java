package org.heart.api.init;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.heart.Heart;
import org.heart.api.body.BodyDataProvider;
import org.heart.api.body.IBodyCapability;

@Mod.EventBusSubscriber
public class CapabilityInit {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(IBodyCapability.class);
    }

    @SubscribeEvent
    public static void attachCap(AttachCapabilitiesEvent<Entity> event){

        if(event.getObject() instanceof Player player){
            event.addCapability(
                    ResourceLocation.fromNamespaceAndPath(Heart.MODID,"body_data"),
                    new BodyDataProvider(player));
        }
    }

    public static final Capability<IBodyCapability> BODY_DATA;


    static {
        BODY_DATA = CapabilityManager.get(new CapabilityToken<>() {});
    }
}
