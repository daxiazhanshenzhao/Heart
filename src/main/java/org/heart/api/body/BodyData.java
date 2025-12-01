package org.heart.api.body;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import org.heart.api.handle.ModEventHandle;
import org.heart.api.init.EffectInit;
import org.heart.gui.BodyContainer;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.HashMap;

public class BodyData implements IBodyCapability{

    private Player player;
    private HashMap<String, ICurioStacksHandler> bodySlots = new HashMap<>();
    private boolean open;


    public BodyData(Player player) {
        this.player = player;
        this.bodySlots = ModEventHandle.updateBodySlots(player);

    }

    public void updateBodyData(HashMap<String, ICurioStacksHandler> bodySlots) {
        this.bodySlots = bodySlots;
    }

    /**
     * 没 1 tick触发一次
     * 心脏为空则以下皆触发

     * 头部为空则无法呼吸，在陆地上也会室息并永久失明

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
    public void handleTick(TickEvent.PlayerTickEvent event){
        //heart
        if (getHeart().isEmpty()){
//            player.addEffect(new MobEffectInstance(EffectInit.MISS_HEAD.get(),20,0,false,false,false));
            // 只在玩家没有效果或效果剩余时间少于10 tick时才添加，避免每tick都刷新
            MobEffectInstance existingEffect = player.getEffect(EffectInit.MISS_HAND.get());
            if (existingEffect == null || existingEffect.getDuration() < 10) {
                player.addEffect(new MobEffectInstance(EffectInit.MISS_HAND.get(), 40, 1, false, false, false));
            }
            return;
        }
        //head
        if(getHead().isEmpty()){

        }
        //body
        if (getBody().isEmpty()){

        }
        //hand
        if (getLeftHand().isEmpty() || getRightHand().isEmpty()){

            if (getLeftHand().isEmpty() && getRightHand().isEmpty()){

            }
        }
        //foot
        if(getLeftFoot().isEmpty() || getRightFoot().isEmpty()){

            if (getLeftFoot().isEmpty() && getRightFoot().isEmpty()){

            }
        }






    }

    public void handleInventory(){

    }




    @Override
    public ICurioStacksHandler getBodySlot(String identify) {
        if (bodySlots.containsKey(identify)) return bodySlots.get(identify);
        return null;
    }



    public Player getPlayer() {
        return player;
    }

    public HashMap<String, ICurioStacksHandler> getBodySlots() {
        return bodySlots;
    }

    public ItemStack getHead() {
        return getStackInSlot(0,0);
    }
    public ItemStack getHeart(){
        return getStackInSlot(1,0);
    }
    public ItemStack getBody(){
        return getStackInSlot(4,0);
    }
    public ItemStack getLeftHand(){
        return getStackInSlot(2,0);
    }
    public ItemStack getRightHand(){
        return getStackInSlot(2,1);
    }
    public ItemStack getLeftFoot(){
        return getStackInSlot(3,0);
    }
    public ItemStack getRightFoot(){
        return getStackInSlot(3,1);
    }
    public ItemStack getStackInSlot(int stringIndex,int slotIndex) {
        var a = bodySlots.get(BodyContainer.BODY_SLOT[stringIndex]);
        if (a != null) {
            return a.getStacks().getStackInSlot(slotIndex);
        }
        return ItemStack.EMPTY;

    }



    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
