package com.sammy.malum.common.blocks.spiritstorage.jar;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.items.SpiritSplinterItem;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

import java.awt.*;
import java.util.stream.Stream;

public class SpiritJarBlock extends Block
{
    public SpiritJarBlock(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        ItemStack heldItem = player.getHeldItem(handIn);
        if (worldIn.getTileEntity(pos) instanceof SpiritJarTileEntity)
        {
            SpiritJarTileEntity tileEntity = (SpiritJarTileEntity) worldIn.getTileEntity(pos);
            if (heldItem.getItem() instanceof SpiritSplinterItem)
            {
                SpiritSplinterItem spiritSplinterItem = (SpiritSplinterItem) heldItem.getItem();
                if (tileEntity.type == null)
                {
                    tileEntity.type = spiritSplinterItem.type;
                    tileEntity.count = heldItem.getCount();
                    player.setHeldItem(handIn, ItemStack.EMPTY);
                    particles(worldIn,hit, tileEntity.type);
                    return ActionResultType.SUCCESS;
                }
                else if (tileEntity.type.equals(spiritSplinterItem.type))
                {
                    tileEntity.count += heldItem.getCount();
                    player.setHeldItem(handIn, ItemStack.EMPTY);
                    particles(worldIn,hit, tileEntity.type);
                    return ActionResultType.SUCCESS;
                }
            }
            else if (tileEntity.type != null)
            {
                int count = Math.min(tileEntity.count, 64);
                ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(tileEntity.type.splinterItem, count));
                tileEntity.count -= count;
                particles(worldIn,hit, tileEntity.type);
                if (tileEntity.count == 0)
                {
                    tileEntity.type = null;
                }
                return ActionResultType.SUCCESS;
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
    public void particles(World world, BlockRayTraceResult hit, MalumSpiritType type)
    {
        if (MalumHelper.areWeOnClient(world))
        {
            Color color = type.color;
            ParticleManager.create(MalumParticles.WISP_PARTICLE)
                    .setAlpha(0.4f, 0f)
                    .setLifetime(20)
                    .setScale(0.3f, 0)
                    .setSpin(0.2f)
                    .randomOffset(0.1f, 0.1f)
                    .setColor(color, color.darker())
                    .enableNoClip()
                    .repeat(world, hit.getHitVec().getX(), hit.getHitVec().y, hit.getHitVec().z, 10);
        }
    }
    public static final VoxelShape base = Stream.of(Block.makeCuboidShape(2.5, 0.5, 2.5, 13.5, 13.5, 13.5), Block.makeCuboidShape(3.5, 14.5, 3.5, 12.5, 16.5, 12.5), Block.makeCuboidShape(4.5, 13.5, 4.5, 11.5, 14.5, 11.5), Block.makeCuboidShape(5.5, 0, 5.5, 10.5, 1, 10.5)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return base;
    }
    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }
    
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new SpiritJarTileEntity();
    }
}
