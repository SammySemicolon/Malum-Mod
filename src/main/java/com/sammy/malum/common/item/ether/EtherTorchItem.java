package com.sammy.malum.common.item.ether;

import com.sammy.malum.core.helper.ColorHelper;
import com.sammy.malum.core.setup.client.ScreenParticleRegistry;
import com.sammy.malum.core.systems.easing.Easing;
import com.sammy.malum.core.systems.rendering.particle.ParticleBuilders;
import com.sammy.malum.core.systems.rendering.particle.screen.base.ScreenParticle;
import com.sammy.malum.core.systems.rendering.particle.screen.emitter.ParticleEmitter;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.Map;
import java.util.Random;

import static net.minecraft.util.Mth.nextFloat;

public class EtherTorchItem extends AbstractEtherItem {
    protected final Block wallBlock;

    public EtherTorchItem(Block floorBlock, Block wallBlockIn, Properties builder, boolean iridescent) {
        super(floorBlock, builder, iridescent);
        this.wallBlock = wallBlockIn;
    }

    @Nullable
    protected BlockState getPlacementState(BlockPlaceContext context) {
        BlockState blockstate = this.wallBlock.getStateForPlacement(context);
        BlockState blockstate1 = null;
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();

        for (Direction direction : context.getNearestLookingDirections()) {
            if (direction != Direction.UP) {
                BlockState blockstate2 = direction == Direction.DOWN ? this.getBlock().getStateForPlacement(context) : blockstate;
                if (blockstate2 != null && blockstate2.canSurvive(level, blockpos)) {
                    blockstate1 = blockstate2;
                    break;
                }
            }
        }

        return blockstate1 != null && level.isUnobstructed(blockstate1, blockpos, CollisionContext.empty()) ? blockstate1 : null;
    }

    public void registerBlocks(Map<Block, Item> blockToItemMap, Item itemIn) {
        super.registerBlocks(blockToItemMap, itemIn);
        blockToItemMap.put(this.wallBlock, itemIn);
    }

    public void removeFromBlockToItemMap(Map<Block, Item> blockToItemMap, Item itemIn) {
        super.removeFromBlockToItemMap(blockToItemMap, itemIn);
        blockToItemMap.remove(this.wallBlock);
    }

    @Override
    public void particleTick(ItemStack stack, float x, float y, ScreenParticle.RenderOrder renderOrder) {
        Level level = Minecraft.getInstance().level;
        float gameTime = level.getGameTime() + Minecraft.getInstance().timer.partialTick;
        AbstractEtherItem etherItem = (AbstractEtherItem) stack.getItem();
        Color firstColor = ColorHelper.getColor(etherItem.getFirstColor(stack));
        Color secondColor = ColorHelper.getColor(etherItem.getSecondColor(stack));
        float alphaMultiplier = etherItem.iridescent ? 1.5f : 1;
        ParticleBuilders.create(ScreenParticleRegistry.STAR)
                .setAlpha(0.06f*alphaMultiplier, 0f)
                .setLifetime(7)
                .setScale((float) (0.75f + Math.sin(gameTime * 0.05f) * 0.125f), 0)
                .setColor(firstColor, secondColor)
                .setColorCurveMultiplier(1.25f)
                .randomOffset(0.05f)
                .setSpinOffset(0.025f * gameTime % 6.28f)
                .setSpin(0, 1)
                .setSpinEasing(Easing.EXPO_IN_OUT)
                .setAlphaEasing(Easing.QUINTIC_IN)
                .overwriteRenderOrder(renderOrder)
                .centerOnStack(stack, 0, -1)
                .repeat(x, y, 1)
                .setScale((float) (0.75f - Math.sin(gameTime * 0.075f) * 0.125f), 0)
                .setColor(secondColor, firstColor)
                .setSpinOffset(0.785f-0.01f * gameTime % 6.28f)
                .repeat(x, y, 1);
    }
}