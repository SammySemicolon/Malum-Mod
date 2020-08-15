package com.kittykitcatcat.malum.blocks.utility;

import com.kittykitcatcat.malum.MalumHelper;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;

@OnlyIn(value = Dist.CLIENT)
public interface FancyRenderer
{
    
    
    Direction lookingAtFace();
    BlockPos lookingAtPos();
    float time();
    void setLookingAtFace(Direction direction);
    void setLookingAtPos(BlockPos pos);
    void setTime(float time);
}