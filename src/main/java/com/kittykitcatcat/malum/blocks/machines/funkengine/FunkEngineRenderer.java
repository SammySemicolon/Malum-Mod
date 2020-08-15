package com.kittykitcatcat.malum.blocks.machines.funkengine;

import com.kittykitcatcat.malum.SpiritStorage;
import com.kittykitcatcat.malum.blocks.utility.FancyRenderer;
import com.kittykitcatcat.malum.blocks.utility.soulstorage.SpiritStoringTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;

import static com.kittykitcatcat.malum.MalumHelper.makeImportantComponent;
import static com.kittykitcatcat.malum.MalumHelper.renderTEdataInTheCoolFancyWay;
import static com.kittykitcatcat.malum.SpiritDataHelper.getName;
import static net.minecraft.util.math.RayTraceResult.Type.BLOCK;

@OnlyIn(value = Dist.CLIENT)
public class FunkEngineRenderer extends TileEntityRenderer<FunkEngineTileEntity> implements FancyRenderer
{

    public FunkEngineRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super(rendererDispatcherIn);
    }
    
    public Direction lookingAtFace;
    public BlockPos lookingAtPos;
    public float time;
    
    @Override
    public Direction lookingAtFace()
    {
        return lookingAtFace;
    }
    
    @Override
    public void setLookingAtFace(Direction direction)
    {
        lookingAtFace = direction;
    }
    
    @Override
    public BlockPos lookingAtPos()
    {
        return lookingAtPos;
    }
    
    @Override
    public void setLookingAtPos(BlockPos pos)
    {
        lookingAtPos = pos;
    }
    
    
    @Override
    public float time()
    {
        return time;
    }
    
    @Override
    public void setTime(float time)
    {
        this.time = time;
    }
    @Override
    public void render(FunkEngineTileEntity blockEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int light, int overlay)
    {
        if (blockEntity.inventory.getStackInSlot(0).getItem() instanceof MusicDiscItem)
        {
            MusicDiscItem discItem = (MusicDiscItem) blockEntity.inventory.getStackInSlot(0).getItem();
            ArrayList<ITextComponent> components = new ArrayList<>();
            components.add(new StringTextComponent("uwu"));
            components.add(new StringTextComponent("UwU"));
            components.add(new StringTextComponent("owo"));
            components.add(new StringTextComponent("OwO"));
            components.add(new StringTextComponent(">w<"));
            components.add(new StringTextComponent(">W<"));
            components.add(new TranslationTextComponent("malum.tooltip.playing.desc").appendSibling(makeImportantComponent(discItem.getRecordDescription().getFormattedText(), true)));
            renderTEdataInTheCoolFancyWay(blockEntity, this, matrixStack, iRenderTypeBuffer, renderDispatcher, components);
        }
    }
}