package org.heart;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.heart.api.handle.NetworkHandler;
import org.heart.api.init.MenuInit;
import org.heart.gui.BodyContainerScreen;
import org.slf4j.Logger;
import top.theillusivec4.curios.api.CuriosApi;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Heart.MODID)
public class Heart {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "heart";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public Heart() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        NetworkHandler.register();
        MenuInit.register(modEventBus);

        // 注册客户端设置事件
        modEventBus.addListener(this::setupClient);
    }

    private void setupClient(FMLClientSetupEvent evt) {
        evt.enqueueWork(() -> {
            MenuScreens.register(MenuInit.BODY_MENU.get(), BodyContainerScreen::new);
        });
    }
}
