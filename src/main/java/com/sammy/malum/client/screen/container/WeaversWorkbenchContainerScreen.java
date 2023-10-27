package com.sammy.malum.client.screen.container;

import com.mojang.blaze3d.platform.*;
import com.mojang.blaze3d.systems.*;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import com.sammy.malum.*;
import com.sammy.malum.common.container.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.decoration.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionf;

import javax.annotation.*;

public class WeaversWorkbenchContainerScreen extends AbstractContainerScreen<WeaversWorkbenchContainer> {

    public static final Quaternionf ROTATION = new Quaternionf(0.43633232F, 0.0F, 3.1415927F, 0);
    private static final ResourceLocation TEXTURE = MalumMod.malumPath("textures/gui/weavers_workbench.png");

    private final WeaversWorkbenchContainer weaversWorkbenchContainer;

    private ItemStack cachedOutput;
    private ArmorStand armorStand;

    public WeaversWorkbenchContainerScreen(WeaversWorkbenchContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.weaversWorkbenchContainer = pMenu;
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 176;
        this.imageHeight = 166;
        setupArmorStand();
    }
    @Override
    public void render(@Nonnull GuiGraphics poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(poseStack, mouseX, mouseY);
    }
    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        //RenderSystem.setShaderTexture(0, TEXTURE);
        pGuiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        ItemStack output = weaversWorkbenchContainer.itemHandler.getStackInSlot(2);

        if (!output.equals(cachedOutput)) {
            setupArmorStand();
        }
        cachedOutput = output;

        drawEntity(pGuiGraphics.pose(), this.leftPos + 141, this.topPos + 65, 25, ROTATION, null, this.armorStand);
    }

    protected void setupArmorStand() {
        this.armorStand = new ArmorStand(Minecraft.getInstance().level, 0.0, 0.0, 0.0);
        this.armorStand.setNoBasePlate(true);
        this.armorStand.setShowArms(true);
        this.armorStand.yBodyRot = 210.0F;
        this.armorStand.setXRot(25.0F);
        this.armorStand.yHeadRot = this.armorStand.yBodyRot;
        this.armorStand.yHeadRotO = this.armorStand.yBodyRot;
        this.dressUpStand(weaversWorkbenchContainer.itemHandler.getStackInSlot(2));
    }

    private void dressUpStand(ItemStack stack) {
        if (this.armorStand != null) {
            EquipmentSlot[] var2 = EquipmentSlot.values();

            for (EquipmentSlot equipmentSlot : var2) {
                this.armorStand.setItemSlot(equipmentSlot, ItemStack.EMPTY);
            }

            if (!stack.isEmpty()) {
                ItemStack itemStack = stack.copy();
                Item var8 = stack.getItem();
                if (var8 instanceof ArmorItem armorItem) {
                    this.armorStand.setItemSlot(armorItem.getEquipmentSlot(), itemStack);
                }
            }
        }
    }

    public static void drawEntity(PoseStack matrices, int x, int y, int size, Quaternionf matrixMultiplier, @Nullable Quaternionf rotation, LivingEntity entity) {
        PoseStack matrixStack = RenderSystem.getModelViewStack();
        matrixStack.pushPose();
        matrixStack.translate(0.0, 0.0, 1000.0);
        RenderSystem.applyModelViewMatrix();
        matrices.pushPose();
        matrices.translate(x, y, -950.0);
        matrices.mulPoseMatrix((new Matrix4f()).scaling((float)size, (float)size, (float)(-size)));
        matrices.mulPose(matrixMultiplier);
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        if (rotation != null) {
            rotation.conjugate();
            entityRenderDispatcher.overrideCameraOrientation(rotation);
        }

        entityRenderDispatcher.setRenderShadow(false);
        MultiBufferSource.BufferSource immediate = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> {
            entityRenderDispatcher.render(entity, 0.0, 0.0, 0.0, 0.0F, 1.0F, matrices, immediate, 15728880);
        });
        immediate.endBatch();
        entityRenderDispatcher.setRenderShadow(true);
        matrices.popPose();
        Lighting.setupForFlatItems();
        matrixStack.popPose();
        RenderSystem.applyModelViewMatrix();
    }
}

