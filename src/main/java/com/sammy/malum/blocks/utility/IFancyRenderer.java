package com.sammy.malum.blocks.utility;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IFancyRenderer
{
    Direction lookingAtFace();
    void setLookingAtFace(Direction direction);
    BlockPos lookingAtPos();
    void setLookingAtPos(BlockPos pos);
    int maxOptions();
    int getSelectedOption(int option);
    boolean isConfigurable();
    float getTime();
    void setTime(float time);
}