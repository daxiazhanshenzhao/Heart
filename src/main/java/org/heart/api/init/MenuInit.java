package org.heart.api.init;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.heart.Heart;
import org.heart.gui.BodyContainer;

public class MenuInit {
public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Heart.MODID);



    public static final RegistryObject<MenuType<BodyContainer>> BODY_MENU = MENUS.register("body_menu",
            () -> IForgeMenuType.create(BodyContainer::new));

    public static void register(IEventBus eventBus) {


        MENUS.register(eventBus);
    }
}
