package org.heart.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.PlayerHeadItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PlayerHeadBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.PushReaction;

public class BodyHeadItem extends PlayerHeadItem {
    public BodyHeadItem() {
            super(Blocks.PLAYER_HEAD, Blocks.PLAYER_WALL_HEAD, new Item.Properties().stacksTo(1));
        }


}
