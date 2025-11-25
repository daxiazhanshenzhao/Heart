package org.heart.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.heart.Heart;

import javax.annotation.Nonnull;

public class BodyContainerScreen extends AbstractContainerScreen<BodyContainer> {
    public BodyContainerScreen(BodyContainer pMenu, Inventory pInventory,Component pTitleIn) {
        super(pMenu, pInventory,pTitleIn);
    }

    @Override
    protected void init() {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        super.init();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBg(guiGraphics, 0, 0, 0);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }


    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        int guiLeft = (this.width - 256) / 2;
        int guiTop = (this.height - 256) / 2;

        guiGraphics.blit(
                ResourceLocation.fromNamespaceAndPath(Heart.MODID, "textures/gui/body_screen.png"),
                guiLeft,      // 屏幕上的X位置
                guiTop,       // 屏幕上的Y位置
                0,            // 纹理上的起始X坐标
                0,            // 纹理上的起始Y坐标
                256,          // 要渲染的宽度
                256,          // 要渲染的高度
                256,          // 纹理文件的总宽度（2的幂次方）
                256           // 纹理文件的总高度（2的幂次方）
        );
    }
}
