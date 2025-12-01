package org.heart.gui.component;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.common.inventory.CurioSlot;

public class BodyCurioSlot extends CurioSlot {

    public BodyCurioSlot(Player player, IDynamicStackHandler handler, int index, String identifier, int xPosition, int yPosition, NonNullList<Boolean> renders, boolean canToggleRender) {
        super(player, handler, index, identifier, xPosition, yPosition, renders, canToggleRender);
    }


}
