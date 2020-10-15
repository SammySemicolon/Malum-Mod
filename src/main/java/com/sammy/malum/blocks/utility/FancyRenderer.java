package com.sammy.malum.blocks.utility;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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