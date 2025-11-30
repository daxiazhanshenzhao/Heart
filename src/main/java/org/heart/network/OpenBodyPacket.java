package org.heart.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;
import org.heart.Heart;
import org.heart.player.inventory.BodyContainerProvider;

import java.util.function.Supplier;

public class OpenBodyPacket {

    private final ItemStack carried;

    public OpenBodyPacket(ItemStack stack) {
        this.carried = stack;
    }

    public static void encode(OpenBodyPacket msg, FriendlyByteBuf buf) {
        buf.writeItem(msg.carried);
    }
    public static OpenBodyPacket decode(FriendlyByteBuf buf) {
        return new OpenBodyPacket(buf.readItem());
    }

    public static void handle(OpenBodyPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer sender = ctx.get().getSender();
            if (sender != null) {
                ItemStack stack = sender.isCreative() ? msg.carried : sender.containerMenu.getCarried();
                sender.containerMenu.setCarried(ItemStack.EMPTY);
//                Heart.LOGGER.info("发包成功");
                NetworkHooks.openScreen(sender, new BodyContainerProvider());

            }
        });
        ctx.get().setPacketHandled(true);

    }

}
