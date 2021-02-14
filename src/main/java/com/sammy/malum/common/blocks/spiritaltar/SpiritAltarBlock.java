package com.sammy.malum.common.blocks.spiritaltar;

import com.sammy.malum.common.items.SpiritSplinterItem;
import com.sammy.malum.core.modcontent.MalumSpiritAltarRecipes;
import com.sammy.malum.core.modcontent.MalumSpiritAltarRecipes.MalumSpiritAltarRecipe;
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
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class SpiritAltarBlock extends Block
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
                if (player.isSneaking() || player.getHeldItem(handIn).isEmpty())
                {
                    tileEntity.spiritInventory.playerExtractItem(worldIn, player);
                }
                else if (heldStack.getItem() instanceof SpiritSplinterItem)
                {
                    tileEntity.spiritInventory.playerInsertItem(worldIn, player.getHeldItem(handIn));
                }
                if (!(heldStack.getItem() instanceof SpiritSplinterItem))
                {
                    MalumSpiritAltarRecipe recipe = MalumSpiritAltarRecipes.getRecipe(heldStack);
                    if (recipe != null && recipe.matches(tileEntity.spiritInventory.nonEmptyStacks()))
                    {
                        for (MalumSpiritIngredient ingredient : recipe.spiritIngredients)
                        {
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
                        ItemEntity entity = new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() + 1.25f,pos.getZ() + 0.5f, recipe.outputIngredient.outputItem());
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