package org.heart.gui;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import org.heart.api.init.MenuInit;
import org.heart.gui.component.BodyCurioSlot;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.ICuriosMenu;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.concurrent.atomic.AtomicReference;

public class BodyContainer extends AbstractContainerMenu implements ICuriosMenu {


    private final LazyOptional<ICuriosItemHandler> curiosHandler;
    private Player player;

    public static final String[] BODY_SLOT = new String[]{
        "body_head",
        "body_heart",
        "body_hand",
        "body_foot",
        "body_body"
    };

    //server - 服务端通过 MenuProvider 调用
    public BodyContainer(int containerId, Inventory playerInventory) {
        super(MenuInit.BODY_MENU.get(), containerId);
        this.player = playerInventory.player;
        this.curiosHandler = CuriosApi.getCuriosInventory(player);
        init(playerInventory);
    }

    //client - 客户端通过网络包数据构造
    public BodyContainer(int windowId, Inventory playerInventory, FriendlyByteBuf packetBuffer) {
        this(windowId, playerInventory);
    }



    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    public void init(Inventory playerInventory) {

        //获取指定槽位
        //1. 添加身体部位槽位（每个identify的index从0开始）
        var head = initSlot(BODY_SLOT[0], 49, -6, 0);          // body_head 的第 0 个槽
        var heart = initSlot(BODY_SLOT[1], 107, 2, 0);         // body_heart 的第 0 个槽
        var handLeft = initSlot(BODY_SLOT[2], 20, 42, 0);      // body_hand 的第 0 个槽（左手）
        var handRight = initSlot(BODY_SLOT[2], 133, 74, 1);    // body_hand 的第 1 个槽（右手）
        var footLeft = initSlot(BODY_SLOT[3], 46, 83, 0);     // body_foot 的第 0 个槽（左脚）
        var footRight = initSlot(BODY_SLOT[3], 108, 93, 1);    // body_foot 的第 1 个槽（右脚）
        var body = initSlot(BODY_SLOT[4], 79, 38, 0);         // body_body 的第 0 个槽

        if (head != null) this.addSlot(head);
        if (heart != null) this.addSlot(heart);
        if (handLeft != null) this.addSlot(handLeft);
        if (handRight != null) this.addSlot(handRight);
        if (footLeft != null) this.addSlot(footLeft);
        if (footRight != null) this.addSlot(footRight);
        if (body != null) this.addSlot(body);

        // 2. 添加玩家背包主区域 (3行9列，索引 6-32)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory,
                        col + row * 9 + 9,  // 9是因为快捷栏占用了0-8
                        8 + col * 18,
                        84+35 + row * 18));
            }
        }

        // 3. 添加玩家快捷栏 (索引 33-41)
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142+37));
        }
    }

    @Override
    public void resetSlots() {

    }


    public Slot initSlot(String identify, int x, int y, int index) {
        AtomicReference<Slot> result = new AtomicReference<>(null);
        this.curiosHandler.ifPresent(handler -> {
            var slotMap = handler.getCurios();
            ICurioStacksHandler slotInventory = slotMap.get(identify);
            if (slotInventory != null) {
                IDynamicStackHandler stackHandler = slotInventory.getStacks();
                if (stackHandler != null && index >= 0 && index < stackHandler.getSlots()) {
                    try {
                        result.set(new BodyCurioSlot(
                            this.player,
                            stackHandler,
                            index,
                            identify,
                            x,
                            y,
                            slotInventory.getRenders(),
                            slotInventory.canToggleRendering()
                        ));
                    } catch (Exception e) {
                        // 如果创建BodyCurioSlot失败，使用默认槽位
                        result.set(new Slot(new SimpleContainer(1), 0, x, y) {
                            @Override
                            public boolean mayPlace(ItemStack stack) {
                                return false;
                            }
                        });
                    }
                }
            }
        });
        return result.get();
    }

}
