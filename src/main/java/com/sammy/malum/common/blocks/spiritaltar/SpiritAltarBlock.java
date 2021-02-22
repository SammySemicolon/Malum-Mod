package com.sammy.malum.common.blocks.spiritaltar;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.blocks.spiritstorage.pipe.IPipeConnected;
import com.sammy.malum.common.items.SpiritSplinterItem;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.modcontent.MalumSpiritAltarRecipes;
import com.sammy.malum.core.modcontent.MalumSpiritAltarRecipes.MalumSpiritAltarRecipe;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.recipes.MalumSpiritIngredient;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.awt.*;

public class SpiritAltarBlock extends Block implements IPipeConnected
{
    public SpiritAltarBlock(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (handIn.equals(Hand.MAIN_HAND))
        {
            if (worldIn.getTileEntity(pos) instanceof SpiritAltarTileEntity)
            {
                SpiritAltarTileEntity tileEntity = (SpiritAltarTileEntity) worldIn.getTileEntity(pos);
                ItemStack heldStack = player.getHeldItemMainhand();
                tileEntity.spiritInventory.playerHandleItem(worldIn,player,handIn);
                if (!(heldStack.getItem() instanceof SpiritSplinterItem))
                {
                    MalumSpiritAltarRecipe recipe = MalumSpiritAltarRecipes.getRecipe(heldStack);
                    if (recipe != null && recipe.matches(tileEntity.spiritInventory.nonEmptyStacks()))
                    {
                        Vector3d itemPos = MalumHelper.pos(pos).add(0.5f,1.05f,0.5f);
                        for (MalumSpiritIngredient ingredient : recipe.spiritIngredients)
                        {
                            if (MalumHelper.areWeOnClient(player.world))
                            {
                                Color color = ingredient.type.color;
                                ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(0.5f, 0f).setLifetime(10).setScale(0.075f, 0).setColor(color, color.darker()).randomOffset(0.1f).randomVelocity(0.04f, 0.04f).enableNoClip().repeat(worldIn,itemPos.x,itemPos.y,itemPos.z, 10);
                                ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(0.1f, 0f).setLifetime(40).setScale(0.4f, 0).setColor(color, color.darker()).randomOffset(0.1f).randomVelocity(0.016f, 0.0160f).enableNoClip().repeat(worldIn, itemPos.x, itemPos.y, itemPos.z, 20);
                            }
                            for (int i = 0; i < tileEntity.spiritInventory.slotCount; i++)
                            {
                                ItemStack stack = tileEntity.spiritInventory.getStackInSlot(i);
                                if (ingredient.matches(stack))
                                {
                                    stack.shrink(ingredient.count);
                                    break;
                                }
                            }
                        }
                        heldStack.shrink(recipe.outputIngredient.count);
                        ItemEntity entity = new ItemEntity(worldIn, itemPos.x,itemPos.y,itemPos.z, recipe.outputIngredient.outputItem());
                        worldIn.addEntity(entity);
                    }
                }
                return ActionResultType.SUCCESS;
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }
    
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new SpiritAltarTileEntity();
    }
    
}