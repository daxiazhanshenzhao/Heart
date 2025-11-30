package org.heart.api.init;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.heart.Heart;
import org.heart.item.BodyHeadItem;
import org.heart.item.BodyItem;

public class ItemInit {

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Heart.MODID);

    public static void register(){
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

//    public static final RegistryObject<Item> HEAD;
    public static final RegistryObject<Item> HEART;
    public static final RegistryObject<Item> BODY;
    public static final RegistryObject<Item> HAND;
    public static final RegistryObject<Item> FOOT;
    public static final RegistryObject<Item> PLAYER_HEAD;

    static {
//        HEAD = ITEMS.register("head", BodyItem::new);
        HEART = ITEMS.register("heart", BodyItem::new);
        BODY = ITEMS.register("body", BodyItem::new);
        HAND = ITEMS.register("hand", BodyItem::new);
        FOOT = ITEMS.register("foot", BodyItem::new);
        PLAYER_HEAD = ITEMS.register("player_head", BodyHeadItem::new);
    }


}
