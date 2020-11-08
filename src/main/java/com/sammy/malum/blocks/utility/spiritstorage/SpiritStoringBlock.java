package com.sammy.malum.blocks.utility.spiritstorage;

import com.sammy.malum.SpiritDataHelper;
import com.sammy.malum.SpiritStorage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import static com.sammy.malum.SpiritDataHelper.extractSpiritFromStorage;
import static com.sammy.malum.SpiritDataHelper.insertSpiritIntoStorage;

public abstract class SpiritStoringBlock extends Block implements SpiritStorage
{
    
    public SpiritStoringBlock(Properties properties)
    {
        super(properties);
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
                    int cachedCount = tileEntity.count;
                    for (int i = 0; i < cachedCount; i++)
                    {
                        boolean success = extractSpiritFromStorage(player.getHeldItem(handIn), tileEntity, ((SpiritStorage) stack.getItem()).capacity(), tileEntity.type);
                        if (!success)
                        {
                            break;
                        }
                    }
                }
                else
                {
                    if (SpiritDataHelper.doesItemHaveSpirit(stack))
                    {
                        int cachedCount = stack.getTag().getInt(SpiritDataHelper.countNBT);
                        for (int i = 0; i < cachedCount; i++)
                        {
                            boolean success = insertSpiritIntoStorage(player.getHeldItem(handIn), tileEntity, block.capacity(), stack.getTag().getString(SpiritDataHelper.typeNBT));
                            if (!success)
                            {
                                break;
                            }
                        }
                    }
                }
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
    
    @Override
    public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player)
    {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof SpiritStoringTileEntity)
        {
            SpiritStoringTileEntity spiritStoringTileEntity = (SpiritStoringTileEntity) te;
            if (!world.isRemote && spiritStoringTileEntity.count > 0)
            {
                ItemStack stack = new ItemStack(state.getBlock().asItem());
                stack.setTag(new CompoundNBT());
                for (int i = 0; i < spiritStoringTileEntity.count; i++)
                {
                    SpiritDataHelper.increaseSpiritOfItem(stack, spiritStoringTileEntity.type);
                }
                ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack);
                itemEntity.setDefaultPickupDelay();
                world.addEntity(itemEntity);
            }
        }
        super.onBlockHarvested(world, pos, state, player);
    }
}