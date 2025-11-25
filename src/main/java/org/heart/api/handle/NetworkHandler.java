package org.heart.api.handle;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.heart.Heart;
import org.heart.network.OpenBodyPacket;

public class NetworkHandler {
    private static final String PTC_VERSION = "10";

    public static SimpleChannel INSTANCE;



    public static void register(){
        INSTANCE = NetworkRegistry.ChannelBuilder.named(ResourceLocation.fromNamespaceAndPath(Heart.MODID,"main"))
                .networkProtocolVersion(() -> PTC_VERSION).clientAcceptedVersions(PTC_VERSION::equals)
                .serverAcceptedVersions(PTC_VERSION::equals).simpleChannel();

        //Client
        INSTANCE.registerMessage(1,
                OpenBodyPacket.class,
                OpenBodyPacket::encode,
                OpenBodyPacket::decode,
                OpenBodyPacket::handle);
        //Server
    }
}
