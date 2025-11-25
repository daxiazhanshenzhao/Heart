package org.heart.player.inventory;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.heart.gui.BodyContainer;
import org.jetbrains.annotations.Nullable;

public class BodyContainerProvider implements MenuProvider {

    @Override
    public Component getDisplayName() {
        return Component.empty();
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new BodyContainer(pContainerId,pPlayerInventory);
    }
}
