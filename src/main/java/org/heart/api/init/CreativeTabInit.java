package org.heart.api.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.heart.Heart;

public class CreativeTabInit {

    private static final DeferredRegister<CreativeModeTab> CREATIVE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Heart.MODID);

    public static void register(IEventBus eventBus){
        CREATIVE_TAB.register(eventBus);
    }

//    BlockBehaviour.BlockStateBase


    public static final RegistryObject<CreativeModeTab> HEART_TAB = CREATIVE_TAB.register("heart", () -> CreativeModeTab.builder()
            // Set name of tab to display
            .title(Component.translatable("item_group." + Heart.MODID + ".heart"))
            // Set icon of creative tab
            .icon(() -> new ItemStack(ItemInit.HEART.get()))
            // Add default items to tab
            .displayItems((params, output) -> {
                output.accept(ItemInit.HEART.get());
                output.accept(ItemInit.BODY.get());
                output.accept(ItemInit.HAND.get());
                output.accept(ItemInit.FOOT.get());
                output.accept(Items.PLAYER_HEAD);
            })
            .build()
    );

}
