package com.sammy.malum.client.screen.codex;

import com.mojang.blaze3d.systems.*;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import com.sammy.malum.core.systems.rites.*;
import net.minecraft.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.*;
import net.minecraft.network.chat.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import org.lwjgl.opengl.*;
import team.lodestar.lodestone.setup.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.recipe.*;
import team.lodestar.lodestone.systems.rendering.*;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.*;
import java.util.stream.*;

import static com.sammy.malum.config.ClientConfig.BOOK_THEME;
import static net.minecraft.util.FastColor.ARGB32.color;

public class ArcanaCodexHelper {

    public static final VFXBuilders.ScreenVFXBuilder VFX_BUILDER = VFXBuilders.createScreen().setPosTexDefaultFormat();

    public enum BookTheme {
        DEFAULT, EASY_READING
    }

    public static void renderRiteIcon(MalumRiteType rite, PoseStack stack, boolean corrupted, int x, int y) {
        renderRiteIcon(rite, stack, corrupted, x, y, 0);
    }

    public static void renderRiteIcon(MalumRiteType rite, PoseStack stack, boolean corrupted, int x, int y, int z) {
        ExtendedShaderInstance shaderInstance = (ExtendedShaderInstance) LodestoneShaderRegistry.DISTORTED_TEXTURE.getInstance().get();
        shaderInstance.safeGetUniform("YFrequency").set(corrupted ? 5f : 11f);
        shaderInstance.safeGetUniform("XFrequency").set(corrupted ? 12f : 17f);
        shaderInstance.safeGetUniform("Speed").set(2000f * (corrupted ? -0.75f : 1));
        shaderInstance.safeGetUniform("Intensity").set(corrupted ? 14f : 50f);
        Supplier<ShaderInstance> shaderInstanceSupplier = () -> shaderInstance;
        Color color = rite.getEffectSpirit().getPrimaryColor();

        VFXBuilders.ScreenVFXBuilder builder = VFXBuilders.createScreen()
                .setPosColorTexLightmapDefaultFormat()
                .setShader(shaderInstanceSupplier)
                .setColor(color)
                .setAlpha(0.9f)
                .setZLevel(z)
                .setShader(() -> shaderInstance);

        RenderSystem.enableBlend();
        RenderSystem.disableDepthTest();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        renderTexture(rite.getIcon(), stack, builder, x, y, 0, 0, 16, 16, 16, 16);
        builder.setAlpha(0.4f);
        renderTexture(rite.getIcon(), stack, builder, x - 1, y, 0, 0, 16, 16, 16, 16);
        renderTexture(rite.getIcon(), stack, builder, x + 1, y, 0, 0, 16, 16, 16, 16);
        renderTexture(rite.getIcon(), stack, builder, x, y - 1, 0, 0, 16, 16, 16, 16);
        if (corrupted) {
            builder.setColor(rite.getEffectSpirit().getSecondaryColor());
        }
        renderTexture(rite.getIcon(), stack, builder, x, y + 1, 0, 0, 16, 16, 16, 16);
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

    public static void renderComponents(AbstractMalumScreen screen, PoseStack poseStack, java.util.List<? extends IRecipeComponent> components, int left, int top, int mouseX, int mouseY, boolean vertical) {
        java.util.List<ItemStack> items = components.stream().map(IRecipeComponent::getStack).collect(Collectors.toList());
        renderItemList(screen, poseStack, items, left, top, mouseX, mouseY, vertical).run();
    }

    public static Runnable renderBufferedComponents(AbstractMalumScreen screen, PoseStack poseStack, java.util.List<? extends IRecipeComponent> components, int left, int top, int mouseX, int mouseY, boolean vertical) {
        java.util.List<ItemStack> items = components.stream().map(IRecipeComponent::getStack).collect(Collectors.toList());
        return renderItemList(screen, poseStack, items, left, top, mouseX, mouseY, vertical);
    }

    public static void renderComponent(AbstractMalumScreen screen, PoseStack poseStack, IRecipeComponent component, int posX, int posY, int mouseX, int mouseY) {
        if (component.getStacks().size() == 1) {
            renderItem(screen, poseStack, component.getStack(), posX, posY, mouseX, mouseY);
            return;
        }
        int index = (int) (Minecraft.getInstance().level.getGameTime() % (20L * component.getStacks().size()) / 20);
        ItemStack stack = component.getStacks().get(index);
        Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(stack, posX, posY);
        Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(Minecraft.getInstance().font, stack, posX, posY, null);
        if (screen.isHovering(mouseX, mouseY, posX, posY, 16, 16)) {
            screen.renderComponentTooltip(poseStack, screen.getTooltipFromItem(stack), mouseX, mouseY);
        }
    }

    public static void renderItem(AbstractMalumScreen screen, PoseStack poseStack, Ingredient ingredient, int posX, int posY, int mouseX, int mouseY) {
        renderItem(screen, poseStack, List.of(ingredient.getItems()), posX, posY, mouseX, mouseY);
    }

    public static void renderItem(AbstractMalumScreen screen, PoseStack poseStack, java.util.List<ItemStack> stacks, int posX, int posY, int mouseX, int mouseY) {
        if (stacks.size() == 1) {
            renderItem(screen, poseStack, stacks.get(0), posX, posY, mouseX, mouseY);
            return;
        }
        int index = (int) (Minecraft.getInstance().level.getGameTime() % (20L * stacks.size()) / 20);
        ItemStack stack = stacks.get(index);
        Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(stack, posX, posY);
        Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(Minecraft.getInstance().font, stack, posX, posY, null);
        if (screen.isHovering(mouseX, mouseY, posX, posY, 16, 16)) {
            screen.renderComponentTooltip(poseStack, screen.getTooltipFromItem(stack), mouseX, mouseY);
        }
    }

    public static void renderItem(AbstractMalumScreen screen, PoseStack poseStack, ItemStack stack, int posX, int posY, int mouseX, int mouseY) {
        Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(stack, posX, posY);
        Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(Minecraft.getInstance().font, stack, posX, posY, null);
        if (screen.isHovering(mouseX, mouseY, posX, posY, 16, 16)) {
            screen.renderComponentTooltip(poseStack, screen.getTooltipFromItem(stack), mouseX, mouseY);
        }
    }

    public static Runnable renderItemList(AbstractMalumScreen screen, PoseStack poseStack, java.util.List<ItemStack> items, int left, int top, int mouseX, int mouseY, boolean vertical) {
        int slots = items.size();
        renderItemFrames(poseStack, slots, left, top, vertical);
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
                renderItem(screen, poseStack, stack, oLeft, oTop, mouseX, mouseY);
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

    public static void renderWrappingText(PoseStack mStack, String text, int x, int y, int w) {
        net.minecraft.client.gui.Font font = Minecraft.getInstance().font;
        text = new TranslatableComponent(text).getString() + "\n";
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
            renderRawText(mStack, currentLine, x, y + i * (font.lineHeight + 1), getTextGlow(i / 4f));
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

    public static void renderText(PoseStack stack, String text, int x, int y) {
        renderText(stack, new TranslatableComponent(text), x, y, getTextGlow(0));
    }

    public static void renderText(PoseStack stack, net.minecraft.network.chat.Component component, int x, int y) {
        String text = component.getString();
        renderRawText(stack, text, x, y, getTextGlow(0));
    }

    public static void renderText(PoseStack stack, String text, int x, int y, float glow) {
        renderText(stack, new TranslatableComponent(text), x, y, glow);
    }

    public static void renderText(PoseStack stack, Component component, int x, int y, float glow) {
        String text = component.getString();
        renderRawText(stack, text, x, y, glow);
    }

    private static void renderRawText(PoseStack stack, String text, int x, int y, float glow) {
        Font font = Minecraft.getInstance().font;
        if (BOOK_THEME.getConfigValue().equals(BookTheme.EASY_READING)) {
            font.draw(stack, text, x, y, 0);
            return;
        }

        glow = Easing.CUBIC_IN.ease(glow, 0, 1, 1);
        int r = (int) Mth.lerp(glow, 163, 227);
        int g = (int) Mth.lerp(glow, 44, 39);
        int b = (int) Mth.lerp(glow, 191, 228);

        font.draw(stack, text, x - 1, y, color(96, 255, 210, 243));
        font.draw(stack, text, x + 1, y, color(128, 240, 131, 232));
        font.draw(stack, text, x, y - 1, color(128, 255, 183, 236));
        font.draw(stack, text, x, y + 1, color(96, 236, 110, 226));

        font.draw(stack, text, x, y, color(255, r, g, b));
    }

    public static float getTextGlow(float offset) {
        return Mth.sin(offset + Minecraft.getInstance().player.level.getGameTime() / 40f) / 2f + 0.5f;
    }

    public static boolean isHovering(double mouseX, double mouseY, int posX, int posY, int width, int height) {
        return mouseX > posX && mouseX < posX + width && mouseY > posY && mouseY < posY + height;
    }
}
