package com.kittykitcatcat.malum.blocks.utility.soulstorage;

import com.kittykitcatcat.malum.SpiritStorage;
import com.kittykitcatcat.malum.SpiritDataHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import static com.kittykitcatcat.malum.SpiritDataHelper.extractSpiritFromStorage;
import static com.kittykitcatcat.malum.SpiritDataHelper.insertSpiritIntoStorage;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;
import static net.minecraft.state.properties.BlockStateProperties.LIT;

public abstract class SpiritStoringBlock extends Block implements SpiritStorage
{
    public static final IntegerProperty TYPE = IntegerProperty.create("type", 0, 2);
    public SpiritStoringBlock(Properties properties)
    {
        super(properties);
    }
    
    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(TYPE);
        super.fillStateContainer(builder);
    }
    
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        ItemStack stack = player.getHeldItem(handIn);
        if (stack.getItem() instanceof SpiritStorage)
        {
            if (worldIn.getTileEntity(pos) instanceof SpiritStoringTileEntity)
            {
                SpiritStoringBlock block = (SpiritStoringBlock) worldIn.getBlockState(pos).getBlock();
                SpiritStoringTileEntity tileEntity = (SpiritStoringTileEntity) worldIn.getTileEntity(pos);
                if (player.isSneaking())
                {
                    for (int i = 0; i < tileEntity.count; i++)
                    {
                        extractSpiritFromStorage(player.getHeldItem(handIn), tileEntity, ((SpiritStorage) stack.getItem()).capacity(), tileEntity.type);
                    }
                }
                else
                {
                    if (SpiritDataHelper.doesItemHaveSpirit(stack))
                    {
                        for (int i = 0; i < tileEntity.count; i++)
                        {
                            insertSpiritIntoStorage(player.getHeldItem(handIn), tileEntity, block.capacity(), stack.getTag().getString(SpiritDataHelper.typeNBT));
                        }
                    }
                }
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if (!isMoving)
        {
            if (worldIn.getTileEntity(pos) instanceof SpiritStoringTileEntity)
            {
                SpiritStoringTileEntity tileEntity = (SpiritStoringTileEntity) worldIn.getTileEntity(pos);
                ItemStack stack = new ItemStack(state.getBlock().asItem());
                stack.setTag(new CompoundNBT());
                for (int i = 0; i < tileEntity.count; i++)
                {
                    SpiritDataHelper.increaseSpiritOfItem(stack, tileEntity.type);
                }
                Entity entity = new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() + 0.9f, pos.getZ() + 0.5f, stack);
                worldIn.addEntity(entity);
            }
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }
}