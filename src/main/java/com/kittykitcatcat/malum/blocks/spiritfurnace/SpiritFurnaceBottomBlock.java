package com.kittykitcatcat.malum.blocks.spiritfurnace;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.init.ModBlocks;
import com.kittykitcatcat.malum.init.ModItems;
import com.kittykitcatcat.malum.init.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.annotation.Nullable;

import static com.kittykitcatcat.malum.MalumHelper.updateState;
import static com.kittykitcatcat.malum.blocks.spiritfurnace.SpiritFurnaceBottomTileEntity.spiritFuranceSlotEnum.fuel;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;
import static net.minecraft.state.properties.BlockStateProperties.LIT;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SpiritFurnaceBottomBlock extends Block
{
    @SubscribeEvent
    public static void setRenderLayer(FMLClientSetupEvent event)
    {
        RenderTypeLookup.setRenderLayer(ModBlocks.spirit_furnace_bottom, RenderType.getCutout());
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.spirit_furnace_bottom_tile_entity, SpiritFurnaceBottomRenderer::new);

    }
    public SpiritFurnaceBottomBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(LIT, false));
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return false;
    }

    @Override
    public boolean hasTileEntity(final BlockState state)
    {
        return true;
    }
    @Override
    public TileEntity createTileEntity(final BlockState state, final IBlockReader world)
    {
        return new SpiritFurnaceBottomTileEntity();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (!worldIn.isRemote())
        {
            if (handIn != Hand.OFF_HAND)
            {
                if (worldIn.getTileEntity(pos) instanceof SpiritFurnaceBottomTileEntity)
                {
                    SpiritFurnaceBottomTileEntity furnaceTileEntity = (SpiritFurnaceBottomTileEntity) worldIn.getTileEntity(pos);
                    ItemStack heldItem = player.getHeldItem(handIn);
                    ItemStack fuelItem = furnaceTileEntity.getItemStack(fuel);
                    //when input is empty
                    //right clicking adds held item to input
                    if (fuelItem.isEmpty())
                    {
                        if (heldItem.getItem().equals(ModItems.spirit_charcoal))
                        {
                            MalumHelper.setStackInTEInventory(furnaceTileEntity.inventory, heldItem, 0);
                            updateState(worldIn, state,pos);
                            updateState(worldIn, state,pos.up());
                            player.setHeldItem(handIn, ItemStack.EMPTY);
                            player.swingArm(handIn);
                            return ActionResultType.SUCCESS;
                        }
                    }
                    //otherwise
                    //right clicking adds input to hand if its empty
                    else if (heldItem.isEmpty())
                    {
                        player.setHeldItem(handIn, fuelItem);
                        MalumHelper.setStackInTEInventory(furnaceTileEntity.inventory, ItemStack.EMPTY, 0);
                        updateState(worldIn, state,pos);
                        updateState(worldIn, state,pos.up());
                        player.swingArm(handIn);
                        return ActionResultType.SUCCESS;
                    }

                    //right clicking with an item matching input adds its count to input
                    if (heldItem.getItem().equals(ModItems.spirit_charcoal))
                    {
                        int cachedCount = heldItem.getCount();
                        for (int i = 0; i < cachedCount; i++)
                        {
                            if (fuelItem.getCount() < 64)
                            {
                                MalumHelper.increaseStackSizeInTEInventory(furnaceTileEntity.inventory, 1, 0);
                                updateState(worldIn, state, pos);
                                updateState(worldIn, state, pos.down());
                                heldItem.setCount(heldItem.getCount() - 1);
                            }
                        }
                        player.swingArm(handIn);
                        return ActionResultType.SUCCESS;
                    }
                }
            }
        }
        return ActionResultType.SUCCESS;
    }

    /*@Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        if (!worldIn.getBlockState(currentPos.up()).getBlock().equals(ModBlocks.spirit_furnace_top))
        {
            worldIn.setBlockState(currentPos, Blocks.AIR.getDefaultState(), 3);
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }*/
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        worldIn.setBlockState(pos.up(), ModBlocks.spirit_furnace_top.getDefaultState().with(HORIZONTAL_FACING, state.get(HORIZONTAL_FACING)));
    }
    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player)
    {
        worldIn.setBlockState(pos.up(), Blocks.AIR.getDefaultState());
        super.onBlockHarvested(worldIn, pos, state, player);
    }
    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
    {
        if (worldIn.getBlockState(pos.up()).getBlock().equals(Blocks.AIR))
        {
            return true;
        }
        return false;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> blockStateBuilder)
    {
        blockStateBuilder.add(HORIZONTAL_FACING);
        blockStateBuilder.add(LIT);
    }
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite()).with(LIT, false);
    }
}