package com.sammy.malum.common.cooler_book;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sammy.malum.ClientHelper;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.cooler_book.objects.CoolerBookObject;
import com.sammy.malum.common.cooler_book.objects.EntryBookObject;
import com.sammy.malum.core.init.items.MalumItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

import static com.sammy.malum.common.cooler_book.CoolerBookEntry.EntryLine.LineEnum.*;
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

    public final int texture_width = 512;
    public final int texture_height = 256;

    public final int parallax_width = 512;
    public final int parallax_height = 2560;
    public static CoolerBookScreen screen;
    float xOffset;
    float yOffset;
    float xMovement;
    float yMovement;

    public static ArrayList<CoolerBookEntry> entries;
    public static ArrayList<CoolerBookObject> objects;

    protected CoolerBookScreen()
    {
        super(ClientHelper.simpleTranslatableComponent("malum.gui.book.title"));
        minecraft = Minecraft.getInstance();
        setupEntries();
        setupObjects();
    }
    public void setupEntries()
    {
        entries = new ArrayList<>();
        entries.add(new CoolerBookEntry(
                "introduction",
                MalumItems.ENCYCLOPEDIA_ARCANA.get(),0,1)
                .down(1));

        entries.add(new CoolerBookEntry(
                "spirit_magics",
                Items.SOUL_SAND,0,0)
                .leftUp(2,2)
                .rightUp(2,2));

        entries.add(new CoolerBookEntry(
                "runewood",
                MalumItems.RUNEWOOD_SAPLING.get(),1,1)
                .upLeft(2,2));

        entries.add(new CoolerBookEntry(
                "soulstone",
                MalumItems.SOULSTONE.get(),-1,1)
                .leftUp(2,2)
                .upRight(2,2));

        entries.add(new CoolerBookEntry(
                "spirit_infusion",
                MalumItems.SPIRIT_ALTAR.get(),0,2));

        entries.add(new CoolerBookEntry(
                "simple_spirit_harvesting",
                MalumItems.CRUDE_SCYTHE.get(),-2,2)
                .left(1));

        entries.add(new CoolerBookEntry(
                "spirit_types",
                MalumItems.ARCANE_SPIRIT.get(),-3,2));

//        entries.add(new CoolerBookEntry("welcome", MalumItems.ENCYCLOPEDIA_ARCANA.get(),0,1));
//        entries.add(new CoolerBookEntry("basics_of_magic", MalumItems.ARCANE_SPIRIT.get(),0,0));
//        entries.add(new CoolerBookEntry("runewood", MalumItems.RUNEWOOD_SAPLING.get(),1,1));
//        entries.add(new CoolerBookEntry("soulstone", MalumItems.SOULSTONE.get(),-1,1));
//        entries.add(new CoolerBookEntry("simple_spirit_harvesting", MalumItems.CRUDE_SCYTHE.get(),-2, 2));
//        entries.add(new CoolerBookEntry("sacrificial_dagger", MalumItems.SACRIFICIAL_DAGGER.get(),-3, 3));
//        entries.add(new CoolerBookEntry("types_of_spirit", MalumItems.ARCANE_SPIRIT.get(),-3, 2));
//
//        entries.add(new CoolerBookEntry("spirit_infusion", MalumItems.SPIRIT_ALTAR.get(),0, 2));
//        entries.add(new CoolerBookEntry("ether", MalumItems.ETHER.get(),1, 2));
//        entries.add(new CoolerBookEntry("item_holders", MalumItems.SPIRIT_ALTAR.get(),0, 2));
//        entries.add(new CoolerBookEntry("arcane_rock", MalumItems.TAINTED_ROCK.get(),-1, 3));
//        entries.add(new CoolerBookEntry("soul_stained_steel", MalumItems.SOUL_STAINED_STEEL_INGOT.get(),-1, 4));
//        entries.add(new CoolerBookEntry("soul_stained_steel_gear", MalumItems.CREATIVE_SCYTHE.get(),-2, 4));
//        entries.add(new CoolerBookEntry("hallowed_gold", MalumItems.HALLOWED_GOLD_INGOT.get(),1, 4));
//        entries.add(new CoolerBookEntry("spirit_resonators", MalumItems.HALLOWED_SPIRIT_RESONATOR.get(),0, 5));
//        entries.add(new CoolerBookEntry("spirit_jar", MalumItems.SPIRIT_JAR.get(),2, 6));
//
//        entries.add(new CoolerBookEntry("totem_magic", MalumItems.TOTEM_BASE.get(),1, 3));
//        entries.add(new CoolerBookEntry("crafting_with_totems", MalumItems.RUNE_TABLE.get(),2, 4));


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
        xMovement = 0;
        yMovement = 0;
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }

    public void renderEntries(MatrixStack stack, int mouseX, int mouseY, float partialTicks)
    {
        for (int i = objects.size()-1; i >= 0; i--)
        {
            CoolerBookObject object = objects.get(i);
            object.render(minecraft, stack, xOffset, yOffset, mouseX, mouseY, partialTicks);
        }
    }

    public void renderParallax(ResourceLocation texture, MatrixStack matrixStack, float xModifier, float yModifier, float extraXOffset, float extraYOffset)
    {
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        int insideLeft = guiLeft + 17;
        int insideTop = guiTop + 18;
        float uOffset = -(xOffset) * xModifier - extraXOffset;
        float vOffset = Math.min(parallax_height-bookInsideHeight,(parallax_height-bookInsideHeight-yOffset*yModifier) + extraYOffset);

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

    public void playSound()
    {
        PlayerEntity playerEntity = Minecraft.getInstance().player;
        playerEntity.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.PLAYERS, 1.0f, 1.0f);
    }

    public static void openScreen()
    {
        Minecraft.getInstance().displayGuiScreen(getInstance());
        screen.playSound();
    }

    public static CoolerBookScreen getInstance()
    {
//        if (screen == null)
        {
            screen = new CoolerBookScreen();
        }
        screen.faceObject(objects.get(0));
        return screen;
    }
}