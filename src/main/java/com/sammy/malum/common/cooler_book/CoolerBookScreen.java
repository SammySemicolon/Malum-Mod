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

        //beginning

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
                "simple_spirit_harvesting",
                MalumItems.CRUDE_SCYTHE.get(),-2,2)
                .left(1));

        entries.add(new CoolerBookEntry(
                "spirit_types",
                MalumItems.ARCANE_SPIRIT.get(),-3,2));

        entries.add(new CoolerBookEntry(
                "spirit_infusion",
                MalumItems.SPIRIT_ALTAR.get(),0,2)
                .upLeft(2,2)
                .upLeft(2,4)
                .upRight(2,4));

        //spirit tinkering

        entries.add(new CoolerBookEntry(
                "arcane_rock",
                MalumItems.TAINTED_ROCK.get(),-1,3)
                .left(1));

        entries.add(new CoolerBookEntry(
                "ether",
                MalumItems.ETHER.get(),-2,3));

        entries.add(new CoolerBookEntry(
                "blazing_quartz",
                MalumItems.BLAZING_QUARTZ.get(),-3,3));

        entries.add(new CoolerBookEntry(
                "spirit_metallurgy",
                MalumItems.HALLOWED_GOLD_INGOT.get(),-1,4)
                .left(1)
                .upLeft(2,2));

        entries.add(new CoolerBookEntry(
                "spirit_trinkets",
                MalumItems.GILDED_RING.get(),-2,4)
                .leftUp(2,2));

        entries.add(new CoolerBookEntry(
                "spirit_resonators",
                MalumItems.HALLOWED_SPIRIT_RESONATOR.get(),-2,5)
                .left(1));

        entries.add(new CoolerBookEntry(
                "spirit_spoils",
                MalumItems.RING_OF_ARCANE_SPOIL.get(),-3,5));

        entries.add(new CoolerBookEntry(
                "precise_soul_shattering",
                MalumItems.PITHING_NEEDLE.get(),-4,4));

        entries.add(new CoolerBookEntry(
                "brilliance",
                MalumItems.CONFINED_BRILLIANCE.get(),-2,6));

        //dark arts

        entries.add(new CoolerBookEntry(
                "totem_rites",
                MalumItems.TOTEM_BASE.get(),1,4)
                .right(1)
                .upRight(2,2));

        entries.add(new CoolerBookEntry(
                "aura_rites",
                MalumItems.SACRED_SPIRIT.get(),2,4));

        entries.add(new CoolerBookEntry(
                "assembler_rites",
                MalumItems.ARCANE_ASSEMBLY_TABLE.get(),2,5)
                .up(1)
                .right(1));

        entries.add(new CoolerBookEntry(
                "voodoo_magic",
                MalumItems.CURSED_STRAND.get(),3,5));

        entries.add(new CoolerBookEntry(
                "ectoplasm",
                MalumItems.ECTOPLASM.get(),2,6)
                .left(3));

        entries.add(new CoolerBookEntry(
                "eldritch_magic",
                MalumItems.ELDRITCH_SPIRIT.get(),0,6)
                .up(1));

        entries.add(new CoolerBookEntry(
                "radiant_soulstone",
                MalumItems.RADIANT_SOULSTONE.get(),0,7)
                .right(1)
                .upRight(2,2));

        entries.add(new CoolerBookEntry(
                "tyrving",
                MalumItems.TYRVING.get(),1,7)
                .right(1));

        entries.add(new CoolerBookEntry(
                "stronghold_armor",
                MalumItems.SOUL_STAINED_STRONGHOLD_CHESTPLATE.get(),2,7));
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
            boolean isHovering = object.isHovering(minecraft, stack, xOffset, yOffset, mouseX, mouseY, partialTicks);
            object.isHovering = isHovering;
            object.hover = isHovering ? Math.min(object.hover++, object.hoverCap()) : Math.max(object.hover--, 0);
            object.render(minecraft, stack, xOffset, yOffset, mouseX, mouseY, partialTicks);
        }
    }

    public static boolean isHovering(int mouseX, int mouseY, int posX, int posY, int width, int height)
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
     //   if (screen == null)
        {
            screen = new CoolerBookScreen();
            screen.faceObject(objects.get(0));
        }
        return screen;
    }
}