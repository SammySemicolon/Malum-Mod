package com.sammy.malum.client.screen.codex.display.texture;

import com.mojang.blaze3d.pipeline.RenderCall;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexSorting;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.display.texture.request.DynamicTextureRequest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.Tickable;
import org.joml.Matrix4f;

import static net.minecraft.client.Minecraft.ON_OSX;

public class RenderedDynamicTexture extends DynamicTexture implements Tickable {

    protected final DynamicTextureRequest request;
    private final int width, height;
    private final float hScale, vScale;

    private RenderTarget renderTarget;
    private boolean needsUpdate = true;
    private boolean closed;

    public RenderedDynamicTexture(DynamicTextureRequest request, int width, int height, float hScale, float vScale) {
        super(width, height, false);
        RenderSystem.assertOnRenderThread();
        this.request = request;
        this.width = width;
        this.height = height;
        this.hScale = hScale;
        this.vScale = vScale;

        Minecraft.getInstance().getTextureManager().register(request.getWriteLocation(), this);
    }

    public void redraw() {
        needsUpdate = true;
    }

    public boolean isClosed() {
        return closed;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void bind(int id) {
        if (closed) {
            return;
        }
        RenderSystem.setShaderTexture(id, renderTarget.getColorTextureId());
    }

    @Override
    public void bind() {
        if (closed) {
            return;
        }
        super.bind();
    }

    @Override
    public int getId() {
        if (closed) {
            return -1;
        }
        RenderSystem.assertOnRenderThreadOrInit();
        if (renderTarget == null) {
            var pixels = getPixels();
            int w = pixels.getWidth();
            int h = pixels.getHeight();
            renderTarget = new TextureTarget(w, h, true, ON_OSX);
        }
        return renderTarget.getColorTextureId();
    }

    @Override
    public void releaseId() {
        if (!closed) {
            var writeLocation = request.getWriteLocation();
            Minecraft.getInstance().getTextureManager().release(writeLocation);
            executeOnRenderThread(() -> {
                if (renderTarget != null) {
                    renderTarget.destroyBuffers();
                    renderTarget = null;
                }
            });
            closed = true;
        }
    }

    @Override
    public void tick() {
        if (closed) {
            return;
        }
        if (needsUpdate) {
            bakeRequest();
            needsUpdate = false;
        }
    }

    public void bakeRequest() {
        executeOnRenderThread(() -> {
            bind();
            bakeContents();
        });
    }

    private void bakeContents() {
        var oldProjMat = RenderSystem.getProjectionMatrix();
        var newProjMat = getProjectionMatrix();
        var minecraft = Minecraft.getInstance();
        var guiGraphics = new GuiGraphics(minecraft, minecraft.renderBuffers().bufferSource());

        var modelViewStack = RenderSystem.getModelViewStack();
        modelViewStack.pushMatrix();
        modelViewStack.set(getModelViewMatrix());
        RenderSystem.applyModelViewMatrix();

        RenderSystem.setProjectionMatrix(newProjMat, VertexSorting.ORTHOGRAPHIC_Z);

        renderTarget.setClearColor(0, 0, 0, 0);
        renderTarget.clear(Minecraft.ON_OSX);
        renderTarget.bindWrite(true);

        request.drawTexture(this, guiGraphics);


        renderTarget.unbindWrite();
        minecraft.getMainRenderTarget().bindWrite(true);

        modelViewStack.popMatrix();
        RenderSystem.applyModelViewMatrix();
        RenderSystem.setProjectionMatrix(oldProjMat, VertexSorting.ORTHOGRAPHIC_Z);
    }

    private Matrix4f getProjectionMatrix() {
        return new Matrix4f().ortho(
                0, width/hScale,
                height/vScale, 0,
                0.05f, 2000.0f
        );
    }

    private Matrix4f getModelViewMatrix() {
        Matrix4f matrix4f = new Matrix4f();
        matrix4f.identity();
        matrix4f.translate(0, 0, -2000);
        return matrix4f;
    }

    private static void executeOnRenderThread(RenderCall call) {
        if (RenderSystem.isOnRenderThreadOrInit()) {
            call.execute();
            return;
        }
        RenderSystem.recordRenderCall(call);
    }
}