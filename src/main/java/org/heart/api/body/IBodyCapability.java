package org.heart.api.body;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.HashMap;

//@AutoRegisterCapability
public interface IBodyCapability {

    ICurioStacksHandler getBodySlot(String identify);
    void updateBodyData(HashMap<String, ICurioStacksHandler> bodySlots);

    /**
     * 没 1 tick触发一次
     * 心脏为空则以下皆触发

     * 头部为空则无法呼吸，在陆地上也会室息并永久失明

     * 若躯体为空则心脏无法安装

     * 若双臂栏为空则无法攻击以及破坏/放置方块
     * 若空一栏则造成伤害，挖掘速度，攻速减半

     * 若空一栏则减少50%移动速度和跳跃高度
     * 若双腿栏为空则无法移动

     * 打开gui制作需要独立指令（kjs兼容性更好）
     * 玩家打开ui时若受到伤害则一击必杀
     * 玩家若身上有排斥效果（需要自己写）则无法更换肢体
     * 可以通过 嵌合剂（需要写）删除排斥
     * 更改不同肢体时会获得10分钟到排斥效果
     * 玩家进入默认拥有基础肢体
     * 提供15HP以及其他基础功能
     * @param event
     */
    void handleTick(TickEvent.PlayerTickEvent event);
    HashMap<String, ICurioStacksHandler> getBodySlots();

    ItemStack getHead();
    ItemStack getBody();
    ItemStack getHeart();
    ItemStack getLeftHand();
    ItemStack getRightHand();
    ItemStack getLeftFoot();
    ItemStack getRightFoot();

    boolean isOpen();
    void setOpen(boolean open);
}
