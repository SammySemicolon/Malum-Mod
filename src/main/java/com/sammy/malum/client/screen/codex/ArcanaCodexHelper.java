package com.sammy.malum.client.screen.codex;

import com.mojang.blaze3d.systems.*;
import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.core.systems.rites.*;
import com.sammy.malum.core.systems.ritual.*;
import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.renderer.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import org.joml.*;
import org.lwjgl.opengl.*;

import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.recipe.*;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.shader.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static com.sammy.malum.config.ClientConfig.*;
import static net.minecraft.util.FastColor.ARGB32.*;

public class ArcanaCodexHelper {

    public static final VFXBuilders.ScreenVFXBuilder VFX_BUILDER = VFXBuilders.createScreen().setPosTexDefaultFormat();

    public enum BookTheme {
        DEFAULT, EASY_READING
    }

    public static void renderRitualIcon(MalumRitualType rite, PoseStack stack, boolean corrupted, float glowAlpha, int x, int y) {
        renderRiteIcon(rite.getIcon(), rite.spirit, stack, corrupted, glowAlpha, x, y, 0);
    }

    public static void renderRiteIcon(MalumRiteType rite, PoseStack stack, boolean corrupted, float glowAlpha, int x, int y) {
        renderRiteIcon(rite.getIcon(), rite.getEffectSpirit(), stack, corrupted, glowAlpha, x, y, 0);
    }

    public static void renderRiteIcon(ResourceLocation texture, MalumSpiritType spiritType, PoseStack stack, boolean corrupted, float glowAlpha, int x, int y) {
        renderRiteIcon(texture, spiritType, stack, corrupted, glowAlpha, x, y, 0);
    }

    public static void renderRiteIcon(ResourceLocation texture, MalumSpiritType spiritType, PoseStack stack, boolean corrupted, float glowAlpha, int x, int y, int z) {
        ExtendedShaderInstance shaderInstance = (ExtendedShaderInstance) LodestoneShaderRegistry.DISTORTED_TEXTURE.getInstance().get();
        shaderInstance.safeGetUniform("YFrequency").set(corrupted ? 5f : 11f);
        shaderInstance.safeGetUniform("XFrequency").set(corrupted ? 12f : 17f);
        shaderInstance.safeGetUniform("Speed").set(2000f * (corrupted ? -0.75f : 1));
        shaderInstance.safeGetUniform("Intensity").set(corrupted ? 14f : 50f);
        Supplier<ShaderInstance> shaderInstanceSupplier = () -> shaderInstance;

        VFXBuilders.ScreenVFXBuilder builder = VFXBuilders.createScreen()
                .setPosColorTexLightmapDefaultFormat()
                .setShader(shaderInstanceSupplier)
                .setColor(spiritType.getPrimaryColor())
                .setAlpha(0.9f)
                .setZLevel(z)
                .setShader(() -> shaderInstance);

        RenderSystem.enableBlend();
        RenderSystem.disableDepthTest();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        renderTexture(texture, stack, builder, x, y, 0, 0, 16, 16, 16, 16);
        builder.setAlpha(glowAlpha);
        renderTexture(texture, stack, builder, x - 1, y, 0, 0, 16, 16, 16, 16);
        renderTexture(texture, stack, builder, x + 1, y, 0, 0, 16, 16, 16, 16);
        renderTexture(texture, stack, builder, x, y - 1, 0, 0, 16, 16, 16, 16);
        if (corrupted) {
            builder.setColor(spiritType.getSecondaryColor());
        }
        renderTexture(texture, stack, builder, x, y + 1, 0, 0, 16, 16, 16, 16);
        shaderInstance.setUniformDefaults();
        RenderSystem.enableDepthTest();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
    }

    public static void renderWavyIcon(ResourceLocation location, PoseStack stack, int x, int y) {
        renderWavyIcon(location, stack, x, y, 0);
    }

    public static void renderWavyIcon(ResourceLocation location, PoseStack stack, int x, int y, int z) {
        ExtendedShaderInstance shaderInstance = (ExtendedShaderInstance) LodestoneShaderRegistry.DISTORTED_TEXTURE.getInstance().get();
        shaderInstance.safeGetUniform("YFrequency").set(10f);
        shaderInstance.safeGetUniform("XFrequency").set(12f);
        shaderInstance.safeGetUniform("Speed").set(1000f);
        shaderInstance.safeGetUniform("Intensity").set(50f);
        shaderInstance.safeGetUniform("UVCoordinates").set(new Vector4f(0f, 1f, 0f, 1f));
        Supplier<ShaderInstance> shaderInstanceSupplier = () -> shaderInstance;

        VFXBuilders.ScreenVFXBuilder builder = VFXBuilders.createScreen()
                .setPosColorTexLightmapDefaultFormat()
                .setShader(shaderInstanceSupplier)
                .setAlpha(0.7f)
                .setZLevel(z)
                .setShader(() -> shaderInstance);

        RenderSystem.enableBlend();
        RenderSystem.disableDepthTest();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        renderTexture(location, stack, builder, x, y, 0, 0, 16, 16, 16, 16);
        builder.setAlpha(0.1f);
        renderTexture(location, stack, builder, x - 1, y, 0, 0, 16, 16, 16, 16);
        renderTexture(location, stack, builder, x + 1, y, 0, 0, 16, 16, 16, 16);
        renderTexture(location, stack, builder, x, y - 1, 0, 0, 16, 16, 16, 16);
        renderTexture(location, stack, builder, x, y + 1, 0, 0, 16, 16, 16, 16);
        shaderInstance.setUniformDefaults();
        RenderSystem.enableDepthTest();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
    }

    public static void renderTexture(ResourceLocation texture, PoseStack poseStack, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        renderTexture(texture, poseStack, VFX_BUILDER, x, y, u, v, width, height, textureWidth, textureHeight);
    }

    public static void renderTexture(ResourceLocation texture, PoseStack poseStack, VFXBuilders.ScreenVFXBuilder builder, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        builder.setPositionWithWidth(x, y, width, height)
                .setShaderTexture(texture)
                .setUVWithWidth(u, v, width, height, textureWidth, textureHeight)
                .draw(poseStack);
    }

    public static void renderTransparentTexture(ResourceLocation texture, PoseStack poseStack, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        renderTransparentTexture(texture, poseStack, VFX_BUILDER, x, y, u, v, width, height, textureWidth, textureHeight);
    }

    public static void renderTransparentTexture(ResourceLocation texture, PoseStack poseStack, VFXBuilders.ScreenVFXBuilder builder, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        renderTexture(texture, poseStack, builder, x, y, u, v, width, height, textureWidth, textureHeight);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();
    }

    public static void renderComponents(AbstractMalumScreen screen, GuiGraphics guiGraphics, java.util.List<? extends IRecipeComponent> components, int left, int top, int mouseX, int mouseY, boolean vertical) {
        java.util.List<ItemStack> items = components.stream().map(IRecipeComponent::getStack).collect(Collectors.toList());
        renderItemList(screen, guiGraphics, items, left, top, mouseX, mouseY, vertical).run();
    }

    public static Runnable renderBufferedComponents(AbstractMalumScreen screen, GuiGraphics guiGraphics, java.util.List<? extends IRecipeComponent> components, int left, int top, int mouseX, int mouseY, boolean vertical) {
        java.util.List<ItemStack> items = components.stream().map(IRecipeComponent::getStack).collect(Collectors.toList());
        return renderItemList(screen, guiGraphics, items, left, top, mouseX, mouseY, vertical);
    }

    public static void renderComponent(AbstractMalumScreen screen, GuiGraphics guiGraphics, IRecipeComponent component, int posX, int posY, int mouseX, int mouseY) {
        if (component.getStacks().size() == 1) {
            renderItem(screen, guiGraphics, component.getStack(), posX, posY, mouseX, mouseY);
            return;
        }
        int index = (int) (Minecraft.getInstance().level.getGameTime() % (20L * component.getStacks().size()) / 20);
        ItemStack stack = component.getStacks().get(index);
        guiGraphics.renderItem(stack, posX, posY);
        guiGraphics.renderItemDecorations(Minecraft.getInstance().font, stack, posX, posY, null);
        if (screen.isHovering(mouseX, mouseY, posX, posY, 16, 16)) {
            guiGraphics.renderComponentTooltip(Minecraft.getInstance().font, Screen.getTooltipFromItem(Minecraft.getInstance(), stack), mouseX, mouseY);
        }
    }

    public static void renderItem(AbstractMalumScreen screen, GuiGraphics guiGraphics, Ingredient ingredient, int posX, int posY, int mouseX, int mouseY) {
        renderItem(screen, guiGraphics, List.of(ingredient.getItems()), posX, posY, mouseX, mouseY);
    }

    public static void renderItem(AbstractMalumScreen screen, GuiGraphics guiGraphics, java.util.List<ItemStack> stacks, int posX, int posY, int mouseX, int mouseY) {
        if (stacks.size() == 1) {
            renderItem(screen, guiGraphics, stacks.get(0), posX, posY, mouseX, mouseY);
            return;
        }
        int index = (int) (Minecraft.getInstance().level.getGameTime() % (20L * stacks.size()) / 20);
        ItemStack stack = stacks.get(index);
        guiGraphics.renderItem(stack, posX, posY);
        guiGraphics.renderItemDecorations(Minecraft.getInstance().font, stack, posX, posY, null);
        if (screen.isHovering(mouseX, mouseY, posX, posY, 16, 16)) {
            guiGraphics.renderComponentTooltip(Minecraft.getInstance().font, Screen.getTooltipFromItem(Minecraft.getInstance(), stack), mouseX, mouseY);
        }
    }

    public static void renderItem(AbstractMalumScreen screen, GuiGraphics guiGraphics, ItemStack stack, int posX, int posY, int mouseX, int mouseY) {
        guiGraphics.renderItem(stack, posX, posY);
        guiGraphics.renderItemDecorations(Minecraft.getInstance().font, stack, posX, posY, null);
        if (screen.isHovering(mouseX, mouseY, posX, posY, 16, 16)) {
            guiGraphics.renderComponentTooltip(Minecraft.getInstance().font, Screen.getTooltipFromItem(Minecraft.getInstance(), stack), mouseX, mouseY);
        }
    }

    public static Runnable renderItemList(AbstractMalumScreen screen, GuiGraphics guiGraphics, java.util.List<ItemStack> items, int left, int top, int mouseX, int mouseY, boolean vertical) {
        int slots = items.size();
        renderItemFrames(guiGraphics.pose(), slots, left, top, vertical);
        return () -> {
            int finalLeft = left;
            int finalTop = top;
            if (vertical) {
                finalTop -= 10 * (slots - 1);
            } else {
                finalLeft -= 10 * (slots - 1);
            }
            for (int i = 0; i < slots; i++) {
                ItemStack stack = items.get(i);
                int offset = i * 20;
                int oLeft = finalLeft + 2 + (vertical ? 0 : offset);
                int oTop = finalTop + 2 + (vertical ? offset : 0);
                renderItem(screen, guiGraphics, stack, oLeft, oTop, mouseX, mouseY);
            }
        };
    }

    public static void renderItemFrames(PoseStack poseStack, int slots, int left, int top, boolean vertical) {
        if (vertical) {
            top -= 10 * (slots - 1);
        } else {
            left -= 10 * (slots - 1);
        }
        //item slot
        for (int i = 0; i < slots; i++) {
            int offset = i * 20;
            int oLeft = left + (vertical ? 0 : offset);
            int oTop = top + (vertical ? offset : 0);
            renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, oLeft, oTop, 75, 192, 20, 20, 512, 512);

            if (vertical) {
                //bottom fade
                if (slots > 1 && i != slots - 1) {
                    renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, left + 1, oTop + 19, 75, 213, 18, 2, 512, 512);
                }
                //bottommost fade
                if (i == slots - 1) {
                    renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, oLeft + 1, oTop + 19, 75, 216, 18, 2, 512, 512);
                }
            } else {
                //bottom fade
                renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, oLeft + 1, top + 19, 75, 216, 18, 2, 512, 512);
                if (slots > 1 && i != slots - 1) {
                    //side fade
                    renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, oLeft + 19, top, 96, 192, 2, 20, 512, 512);
                }
            }
        }

        //crown
        int crownLeft = left + 5 + (vertical ? 0 : 10 * (slots - 1));
        renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, crownLeft, top - 5, 128, 192, 10, 6, 512, 512);

        //side-bars
        if (vertical) {
            renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, left - 4, top - 4, 99, 200, 28, 7, 512, 512);
            renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, left - 4, top + 17 + 20 * (slots - 1), 99, 192, 28, 7, 512, 512);
        }
        // top bars
        else {
            renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, left - 4, top - 4, 59, 192, 7, 28, 512, 512);
            renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, left + 17 + 20 * (slots - 1), top - 4, 67, 192, 7, 28, 512, 512);
        }
    }

    public static void renderWrappingText(GuiGraphics guiGraphics, String text, int x, int y, int w) {
        net.minecraft.client.gui.Font font = Minecraft.getInstance().font;
        text = Component.translatable(text).getString() + "\n";
        java.util.List<String> lines = new ArrayList<>();

        boolean italic = false;
        boolean bold = false;
        boolean strikethrough = false;
        boolean underline = false;
        boolean obfuscated = false;

        StringBuilder line = new StringBuilder();
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char chr = text.charAt(i);
            if (chr == ' ' || chr == '\n') {
                if (word.length() > 0) {
                    if (font.width(line.toString()) + font.width(word.toString()) > w) {
                        line = newLine(lines, italic, bold, strikethrough, underline, obfuscated, line);
                    }
                    line.append(word).append(' ');
                    word = new StringBuilder();
                }

                String noFormatting = ChatFormatting.stripFormatting(line.toString());

                if (chr == '\n' && !(noFormatting == null || noFormatting.isEmpty())) {
                    line = newLine(lines, italic, bold, strikethrough, underline, obfuscated, line);
                }
            } else if (chr == '$') {
                if (i != text.length() - 1) {
                    char peek = text.charAt(i + 1);
                    switch (peek) {
                        case 'i' -> {
                            word.append(ChatFormatting.ITALIC);
                            italic = true;
                            i++;
                        }
                        case 'b' -> {
                            word.append(ChatFormatting.BOLD);
                            bold = true;
                            i++;
                        }
                        case 's' -> {
                            word.append(ChatFormatting.STRIKETHROUGH);
                            strikethrough = true;
                            i++;
                        }
                        case 'u' -> {
                            word.append(ChatFormatting.UNDERLINE);
                            underline = true;
                            i++;
                        }
                        case 'k' -> {
                            word.append(ChatFormatting.OBFUSCATED);
                            obfuscated = true;
                            i++;
                        }
                        default -> word.append(chr);
                    }
                } else {
                    word.append(chr);
                }
            } else if (chr == '/') {
                if (i != text.length() - 1) {
                    char peek = text.charAt(i + 1);
                    if (peek == '$') {
                        italic = bold = strikethrough = underline = obfuscated = false;
                        word.append(ChatFormatting.RESET);
                        i++;
                    } else
                        word.append(chr);
                } else
                    word.append(chr);
            } else {
                word.append(chr);
            }
        }

        for (int i = 0; i < lines.size(); i++) {
            String currentLine = lines.get(i);
            renderRawText(guiGraphics, currentLine, x, y + i * (font.lineHeight + 1), getTextGlow(i / 4f));
        }
    }

    private static StringBuilder newLine(List<String> lines, boolean italic, boolean bold, boolean strikethrough, boolean underline, boolean obfuscated, StringBuilder line) {
        lines.add(line.toString());
        line = new StringBuilder();
        if (italic) line.append(ChatFormatting.ITALIC);
        if (bold) line.append(ChatFormatting.BOLD);
        if (strikethrough) line.append(ChatFormatting.STRIKETHROUGH);
        if (underline) line.append(ChatFormatting.UNDERLINE);
        if (obfuscated) line.append(ChatFormatting.OBFUSCATED);
        return line;
    }

    public static void renderText(GuiGraphics guiGraphics, String text, int x, int y) {
        renderText(guiGraphics, Component.translatable(text), x, y, getTextGlow(0));
    }

    public static void renderText(GuiGraphics guiGraphics, Component component, int x, int y) {
        String text = component.getString();
        renderRawText(guiGraphics, text, x, y, getTextGlow(0));
    }

    public static void renderText(GuiGraphics guiGraphics, String text, int x, int y, float glow) {
        renderText(guiGraphics, Component.translatable(text), x, y, glow);
    }

    public static void renderText(GuiGraphics guiGraphics, Component component, int x, int y, float glow) {
        String text = component.getString();
        renderRawText(guiGraphics, text, x, y, glow);
    }

    private static void renderRawText(GuiGraphics guiGraphics, String text, int x, int y, float glow) {
        Font font = Minecraft.getInstance().font;
        if (BOOK_THEME.getConfigValue().equals(BookTheme.EASY_READING)) {
            guiGraphics.drawString(font, text, x, y, 0);
            return;
        }

        glow = Easing.CUBIC_IN.ease(glow, 0, 1, 1);
        int r = (int) Mth.lerp(glow, 163, 227);
        int g = (int) Mth.lerp(glow, 44, 39);
        int b = (int) Mth.lerp(glow, 191, 228);

        guiGraphics.drawString(font, text, x - 1f, y, color(96, 255, 210, 243), false);
        guiGraphics.drawString(font, text, x + 1f, y, color(128, 240, 131, 232), false);
        guiGraphics.drawString(font, text, x, y - 1f, color(128, 255, 183, 236), false);
        guiGraphics.drawString(font, text, x, y + 1f, color(96, 236, 110, 226), false);

        guiGraphics.drawString(font, text, x, y, color(255, r, g, b), false);
    }

    public static float getTextGlow(float offset) {
        return Mth.sin(offset + Minecraft.getInstance().player.level().getGameTime() / 40f) / 2f + 0.5f;
    }

    public static boolean isHovering(double mouseX, double mouseY, int posX, int posY, int width, int height) {
        return mouseX > posX && mouseX < posX + width && mouseY > posY && mouseY < posY + height;
    }
}
