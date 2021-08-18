package com.sammy.malum.client.screen.cooler_book;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sammy.malum.ClientHelper;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.client.screen.cooler_book.objects.CoolerBookObject;
import com.sammy.malum.client.screen.cooler_book.objects.EntryBookObject;
import com.sammy.malum.client.screen.cooler_book.pages.*;
import com.sammy.malum.core.init.enchantment.MalumEnchantments;
import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.mod_content.MalumSpiritAltarRecipes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

import static com.sammy.malum.core.init.items.MalumItems.*;
import static net.minecraft.item.ItemStack.EMPTY;
import static net.minecraft.item.Items.*;
import static org.lwjgl.opengl.GL11C.GL_SCISSOR_TEST;

public class CoolerBookScreen extends Screen
{
    public static final ResourceLocation FRAME_TEXTURE = MalumHelper.prefix("textures/gui/book/frame.png");
    public static final ResourceLocation FADE_TEXTURE = MalumHelper.prefix("textures/gui/book/fade.png");

    public static final ResourceLocation SKY_TEXTURE = MalumHelper.prefix("textures/gui/book/sky.png");
    public static final ResourceLocation FARAWAY_CLOUDS_TEXTURE = MalumHelper.prefix("textures/gui/book/faraway_clouds.png");
    public static final ResourceLocation CLOUDS_TEXTURE = MalumHelper.prefix("textures/gui/book/clouds.png");

    public int bookWidth = 458;
    public int bookHeight = 250;
    public int bookInsideWidth = 424;
    public int bookInsideHeight = 215;

    public final int parallax_width = 512;
    public final int parallax_height = 2560;
    public static CoolerBookScreen screen;
    public float xOffset;
    public float yOffset;
    public float cachedXOffset;
    public float cachedYOffset;
    public float xMovement;
    public float yMovement;
    public boolean ignoreNextMouseClick;

    public static ArrayList<CoolerBookEntry> entries;
    public static ArrayList<CoolerBookObject> objects;

    protected CoolerBookScreen()
    {
        super(ClientHelper.simpleTranslatableComponent("malum.gui.book.title"));
        minecraft = Minecraft.getInstance();
        setupEntries();
        setupObjects();
    }
    public static void setupEntries()
    {
        entries = new ArrayList<>();
        Item EMPTY = ItemStack.EMPTY.getItem();
        entries.add(new CoolerBookEntry(
                "introduction", ENCYCLOPEDIA_ARCANA.get(),0,1)
                .down(1)
                .addPage(new HeadlineTextPage("introduction","introduction_a", MalumItems.ENCYCLOPEDIA_ARCANA.get()))
                .addPage(new TextPage("introduction_b"))
                .addPage(new TextPage("introduction_c")));

        entries.add(new CoolerBookEntry(
                "spirit_magics", SOUL_SAND,0,0)
                .leftUp(2,2)
                .rightUp(2,2)
                .addPage(new HeadlineTextPage("spirit_magics", "spirit_magics_a", ARCANE_SPIRIT.get()))
                .addPage(new TextPage("spirit_magics_b"))
                .addPage(new TextPage("spirit_magics_c")));

        entries.add(new CoolerBookEntry(
                "runewood", RUNEWOOD_SAPLING.get(),1,1)
                .upLeft(2,2)
                .addPage(new HeadlineTextPage("runewood", "runewood_a", RUNEWOOD_SAPLING.get()))
                .addPage(new TextPage("runewood_b"))
                .addPage(new HeadlineTextPage("solar_sap", "solar_sap_a", SOLAR_SAP_BOTTLE.get()))
                .addPage(new TextPage("solar_sap_b"))
                .addPage(new SmeltingBookPage(SOLAR_SAP_BOTTLE.get(), SOLAR_SYRUP_BOTTLE.get()))
                .addPage(new CraftingBookPage(SOLAR_SAPBALL.get(), Items.SLIME_BALL, SOLAR_SAP_BOTTLE.get()))
                .addModCompatPage(new TextPage("solar_sap_c"), "thermal_expansion"));

        entries.add(new CoolerBookEntry(
                "soulstone", SOULSTONE.get(),-1,1)
                .upRight(2,2)
                .addPage(new HeadlineTextPage("soulstone", "soulstone_a", SOULSTONE.get()))
                .addPage(new TextPage("soulstone_b")));

        entries.add(new CoolerBookEntry(
                "scythes", CRUDE_SCYTHE.get(),0,2)
                .upLeft(2,2)
                .addPage(new HeadlineTextPage("scythes", "scythes_a", MalumItems.CRUDE_SCYTHE.get()))
                .addPage(new TextPage("scythes_b"))
                .addPage(new TextPage("scythes_c"))
                .addPage(CraftingBookPage.scythePage(MalumItems.CRUDE_SCYTHE.get(), Items.IRON_INGOT, SOULSTONE.get()))
                .addPage(new HeadlineTextPage("haunted", "haunted", EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(MalumEnchantments.HAUNTED.get(), 0))))
                .addPage(new HeadlineTextPage("spirit_plunder", "spirit_plunder", EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(MalumEnchantments.SPIRIT_PLUNDER.get(), 0))))
                .addPage(new HeadlineTextPage("rebound", "rebound", EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(MalumEnchantments.REBOUND.get(), 0)))));

        entries.add(new CoolerBookEntry(
                "simple_spirit_types", ARCANE_SPIRIT.get(),-1,3)
                .upRight(4,2)
                .addPage(new SpiritTextPage("sacred_spirit", "sacred_spirit_a", SACRED_SPIRIT.get()))
                .addPage(new TextPage("sacred_spirit_b"))
                .addPage(new SpiritTextPage("wicked_spirit", "wicked_spirit_a", WICKED_SPIRIT.get()))
                .addPage(new TextPage("wicked_spirit_b"))
                .addPage(new SpiritTextPage("arcane_spirit", "arcane_spirit_a", ARCANE_SPIRIT.get()))
                .addPage(new TextPage("arcane_spirit_b"))
                .addPage(new TextPage("arcane_spirit_c")));

        entries.add(new CoolerBookEntry(
                "spirit_infusion", SPIRIT_ALTAR.get(),1,4)
                .upLeft(2,4)
                .addPage(new HeadlineTextPage("spirit_infusion", "spirit_infusion_a", SPIRIT_ALTAR.get()))
                .addPage(new TextPage("spirit_infusion_b"))
                .addPage(new TextPage("spirit_infusion_c"))
                .addPage(new CraftingBookPage(SPIRIT_ALTAR.get(), AIR, SOULSTONE.get(), AIR, GOLD_INGOT, RUNEWOOD.get(), GOLD_INGOT, RUNEWOOD.get(), RUNEWOOD.get(), RUNEWOOD.get()))
                .addPage(new HeadlineTextPage("hex_ash", "hex_ash", HEX_ASH.get()))
                .addPage(new SpiritInfusionPage(HEX_ASH.get())));

        entries.add(new CoolerBookEntry(
                "spirit_metallurgy", HALLOWED_GOLD_INGOT.get(),0,5)
                .left(3)
                .addPage(new HeadlineTextPage("hallowed_gold", "hallowed_gold_a", HALLOWED_GOLD_INGOT.get()))
                .addPage(new TextPage("hallowed_gold_b"))
                .addPage(new HeadlineTextPage("soul_stained_steel", "soul_stained_steel_a", SOUL_STAINED_STEEL_INGOT.get()))
                .addPage(new TextPage("soul_stained_steel_b"))
                .addPage(CraftingBookPage.resonatorPage(HALLOWED_SPIRIT_RESONATOR.get(), QUARTZ, HALLOWED_GOLD_INGOT.get(), RUNEWOOD_PLANKS.get()))
                .addPage(CraftingBookPage.resonatorPage(STAINED_SPIRIT_RESONATOR.get(), QUARTZ, SOUL_STAINED_STEEL_INGOT.get(), RUNEWOOD_PLANKS.get())));

        entries.add(new CoolerBookEntry(
                "spirit_trinkets", GILDED_RING.get(), -2, 5)
                .addPage(new HeadlineTextPage("spirit_trinkets", "spirit_trinkets_a", GILDED_RING.get()))
                .addPage(new TextPage("spirit_trinkets_b"))
                .addPage(new CraftingBookPage(GILDED_BELT.get(), LEATHER, LEATHER, LEATHER, HALLOWED_GOLD_INGOT.get(), SOULSTONE.get(), HALLOWED_GOLD_INGOT.get(), EMPTY, HALLOWED_GOLD_INGOT.get(), EMPTY))
                .addPage(CraftingBookPage.ringPage(GILDED_RING.get(), LEATHER,HALLOWED_GOLD_INGOT.get()))
                .addPage(CraftingBookPage.ringPage(ORNATE_RING.get(), LEATHER,SOUL_STAINED_STEEL_INGOT.get()))
                .addPage(new CraftingBookPage(ORNATE_NECKLACE.get(), EMPTY, STRING, EMPTY, STRING, EMPTY, STRING, EMPTY, SOUL_STAINED_STEEL_INGOT.get(), EMPTY))
        );

        entries.add(new CoolerBookEntry(
                "spirit_rites", TOTEM_BASE.get(),0,6)
                .addPage(new HeadlineTextPage("spirit_rites", "spirit_rites_a", TOTEM_BASE.get()))
                .addPage(new TextPage("spirit_rites_b"))
                .addPage(new TextPage("spirit_rites_c"))
                .addPage(new SpiritInfusionPage(TOTEM_BASE.get())));
    }
    public void setupObjects()
    {
        objects = new ArrayList<>();
        this.width = minecraft.getMainWindow().getScaledWidth();
        this.height = minecraft.getMainWindow().getScaledHeight();
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        int coreX = guiLeft + bookInsideWidth;
        int coreY = guiTop + bookInsideHeight;
        int one = 64;
        for (CoolerBookEntry entry : entries)
        {
            objects.add(new EntryBookObject(entry, coreX+entry.xOffset*one, coreY-entry.yOffset*one));
        }
        faceObject(objects.get(0));
    }
    public void faceObject(CoolerBookObject object)
    {
        this.width = minecraft.getMainWindow().getScaledWidth();
        this.height = minecraft.getMainWindow().getScaledHeight();
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        xOffset = -object.posX+guiLeft + bookInsideWidth;
        yOffset = -object.posY+guiTop + bookInsideHeight;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        xOffset += xMovement;
        yOffset += yMovement;
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;

        GL11.glEnable(GL_SCISSOR_TEST);
        cut();
        renderParallax(SKY_TEXTURE, matrixStack, 0.6f, 0.4f, 0, 0);
        renderParallax(FARAWAY_CLOUDS_TEXTURE, matrixStack, 0.45f, 0.55f, 0, 0);
        renderParallax(CLOUDS_TEXTURE, matrixStack, 0.3f, 0.7f, 64, 0);

        renderEntries(matrixStack, mouseX, mouseY, partialTicks);
        GL11.glDisable(GL_SCISSOR_TEST);

        renderTransparentTexture(FADE_TEXTURE, matrixStack, guiLeft, guiTop, 1, 1, bookWidth, bookHeight, 512, 512);
        renderTexture(FRAME_TEXTURE, matrixStack, guiLeft, guiTop, 1, 1, bookWidth, bookHeight, 512, 512);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta)
    {
        yMovement += delta > 0 ? 1f : -1f;
        yOffset += delta > 0 ? 0.01f : - 0.01f;
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY)
    {
        xOffset += dragX;
        yOffset += dragY;
        xMovement = 0;
        yMovement = 0;
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        cachedXOffset = xOffset;
        cachedYOffset = yOffset;
        xMovement = 0;
        yMovement = 0;
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button)
    {
        if (ignoreNextMouseClick)
        {
            ignoreNextMouseClick = false;
            return super.mouseReleased(mouseX, mouseY, button);
        }
        if (xOffset != cachedXOffset || yOffset != cachedYOffset)
        {
            return super.mouseReleased(mouseX, mouseY, button);
        }
        for (CoolerBookObject object : objects)
        {
            if (object.isHovering(xOffset, yOffset, mouseX, mouseY))
            {
                object.click(xOffset, yOffset, mouseX, mouseY);
            }
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        if (keyCode == GLFW.GLFW_KEY_E)
        {
            closeScreen();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public void renderEntries(MatrixStack stack, int mouseX, int mouseY, float partialTicks)
    {
        for (int i = objects.size()-1; i >= 0; i--)
        {
            CoolerBookObject object = objects.get(i);
            boolean isHovering = object.isHovering(xOffset, yOffset, mouseX, mouseY);
            object.isHovering = isHovering;
            object.hover = isHovering ? Math.min(object.hover++, object.hoverCap()) : Math.max(object.hover--, 0);
            object.render(minecraft, stack, xOffset, yOffset, mouseX, mouseY, partialTicks);
        }
    }

    public static boolean isHovering(double mouseX, double mouseY, int posX, int posY, int width, int height)
    {
        return mouseX > posX && mouseX < posX + width && mouseY > posY && mouseY < posY + height;
    }
    public void renderParallax(ResourceLocation texture, MatrixStack matrixStack, float xModifier, float yModifier, float extraXOffset, float extraYOffset)
    {
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        int insideLeft = guiLeft + 17;
        int insideTop = guiTop + 18;
        float uOffset = -(xOffset) * xModifier - extraXOffset;
        float vOffset = Math.min(parallax_height-bookInsideHeight,(parallax_height-bookInsideHeight-yOffset*yModifier) + extraYOffset);
        if (vOffset <= parallax_height/2f+1)
        {
            vOffset = parallax_height/2f+1;
        }
        renderTexture(texture, matrixStack, insideLeft, (int) (insideTop-vOffset), uOffset, 0, parallax_width, parallax_height, parallax_width/2, parallax_height/2);
    }

    public void cut()
    {
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        int insideLeft = guiLeft + 17;
        int insideTop = guiTop + 18;
        int scale = (int) getMinecraft().getMainWindow().getGuiScaleFactor();
        GL11.glScissor(insideLeft * scale, insideTop * scale, bookInsideWidth * scale, bookInsideHeight * scale);
    }

    public static void renderTexture(ResourceLocation texture, MatrixStack matrixStack, int x, int y, float uOffset, float vOffset, int width, int height, int textureWidth, int textureHeight)
    {
        Minecraft mc = Minecraft.getInstance();
        mc.getTextureManager().bindTexture(texture);
        blit(matrixStack, x, y, uOffset, vOffset, width, height, textureWidth, textureHeight);
    }

    public static void renderTransparentTexture(ResourceLocation texture, MatrixStack matrixStack, int x, int y, float uOffset, float vOffset, int width, int height, int textureWidth, int textureHeight)
    {
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        renderTexture(texture, matrixStack, x, y, uOffset, vOffset, width, height, textureWidth, textureHeight);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();
    }
    public static void renderItem(MatrixStack matrixStack, ItemStack stack, int posX, int posY, int mouseX, int mouseY)
    {
        Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(stack, posX, posY);
        Minecraft.getInstance().getItemRenderer().renderItemOverlayIntoGUI(Minecraft.getInstance().fontRenderer, stack, posX, posY, null);
        if (isHovering(mouseX, mouseY, posX, posY, 16,16))
        {
            screen.renderTooltip(matrixStack, ClientHelper.simpleTranslatableComponent(stack.getTranslationKey()), mouseX, mouseY);
        }
    }
    public static int packColor(int alpha, int red, int green, int blue)
    {
        return alpha << 24 | red << 16 | green << 8 | blue;
    }

    public static void renderWrappingText(MatrixStack mStack, String text, int x, int y, int w)
    {
        FontRenderer font = Minecraft.getInstance().fontRenderer;
        text = ClientHelper.simpleTranslatableComponent(text).getString();
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        String line = "";
        for (String s : words)
        {
            if (font.getStringWidth(line) + font.getStringWidth(s) > w)
            {
                lines.add(line);
                line = s + " ";
            }
            else line += s + " ";
        }
        if (!line.isEmpty()) lines.add(line);
        for (int i = 0; i < lines.size(); i++)
        {
            String currentLine = lines.get(i);
            renderRawText(mStack, currentLine, x,y + i * (font.FONT_HEIGHT + 1), glow(i/4f));
        }
    }

    public static void renderText(MatrixStack stack, String text, int x, int y)
    {
        renderText(stack, ClientHelper.simpleTranslatableComponent(text), x,y, glow(0));
    }

    public static void renderText(MatrixStack stack, ITextComponent component, int x, int y)
    {
        String text = component.getString();
        renderRawText(stack, text, x,y, glow(0));
    }
    public static void renderText(MatrixStack stack, String text, int x, int y, float glow)
    {
        renderText(stack, ClientHelper.simpleTranslatableComponent(text), x,y, glow);
    }

    public static void renderText(MatrixStack stack, ITextComponent component, int x, int y, float glow)
    {
        String text = component.getString();
        renderRawText(stack, text, x,y, glow);
    }
    private static void renderRawText(MatrixStack stack, String text, int x, int y, float glow)
    {
        FontRenderer font = Minecraft.getInstance().fontRenderer;
        //182, 61, 183   227, 39, 228
        int r = (int) MathHelper.lerp(glow, 182, 227);
        int g = (int) MathHelper.lerp(glow, 61, 39);
        int b = (int) MathHelper.lerp(glow, 183, 228);

        font.drawString(stack, text, x - 1, y, packColor(96, 255, 210, 243));
        font.drawString(stack, text, x + 1, y, packColor(128, 240, 131, 232));
        font.drawString(stack, text, x, y - 1, packColor(128, 255, 183, 236));
        font.drawString(stack, text, x, y + 1, packColor(96, 236, 110, 226));

        font.drawString(stack, text, x, y, packColor(255, r, g, b));
    }
    public static float glow(float offset)
    {
        return MathHelper.sin(offset+Minecraft.getInstance().player.world.getGameTime() / 40f)/2f + 0.5f;
    }
    public void playSound()
    {
        PlayerEntity playerEntity = Minecraft.getInstance().player;
        playerEntity.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.PLAYERS, 1.0f, 1.0f);
    }

    public static void openScreen()
    {
        Minecraft.getInstance().displayGuiScreen(getInstance());
        screen.playSound();
        screen.ignoreNextMouseClick = true;
    }

    public static CoolerBookScreen getInstance()
    {
        if (screen == null)
        {
            screen = new CoolerBookScreen();
            screen.faceObject(objects.get(0));
        }
        return screen;
    }
}