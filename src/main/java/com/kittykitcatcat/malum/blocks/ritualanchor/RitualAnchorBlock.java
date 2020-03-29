package com.kittykitcatcat.malum.blocks.ritualanchor;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.init.ModBlocks;
import com.kittykitcatcat.malum.init.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.annotation.Nullable;

import static com.kittykitcatcat.malum.MalumHelper.updateState;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;
import static net.minecraft.state.properties.BlockStateProperties.LIT;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RitualAnchorBlock extends Block
{
    @SubscribeEvent
    public static void setRenderLayer(FMLClientSetupEvent event)
    {
        RenderTypeLookup.setRenderLayer(ModBlocks.soul_binder, RenderType.getCutout());
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.ritual_anchor_tile_entity, RitualAnchorRenderer::new);
    }
    public RitualAnchorBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(LIT, false));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (!worldIn.isRemote())
        {
            if (handIn != Hand.OFF_HAND)
            {
                if (worldIn.getTileEntity(pos) instanceof RitualAnchorTileEntity)
                {
                    RitualAnchorTileEntity ritualAnchorTileEntity = (RitualAnchorTileEntity) worldIn.getTileEntity(pos);
                    ItemStack heldItem = player.getHeldItem(handIn);
                    if (player.isCrouching())
                    {
                        if (heldItem.isEmpty())
                        {
                            for (int i = ritualAnchorTileEntity.inventory.getSlots() - 1; i >= 0; i--)
                            {
                                if (!ritualAnchorTileEntity.inventory.getStackInSlot(i).isEmpty())
                                {
                                    MalumHelper.giveItemStackToPlayer(player, ritualAnchorTileEntity.inventory.getStackInSlot(i));
                                    MalumHelper.setStackInTEInventory(ritualAnchorTileEntity.inventory, ItemStack.EMPTY, i);
                                    return ActionResultType.SUCCESS;
                                }
                            }
                        }
                    }
                    else
                    {
                        for (int i = 0; i < ritualAnchorTileEntity.inventory.getSlots(); i++)
                        {
                            if (ritualAnchorTileEntity.inventory.isItemValid(i, heldItem))
                            {
                                ItemStack inputItem = ritualAnchorTileEntity.getInputStack(ritualAnchorTileEntity.inventory, i);
                                if (inputItem.isEmpty())
                                {
                                    ItemStack newItem = heldItem.copy();
                                    newItem.setCount(1);
                                    MalumHelper.setStackInTEInventory(ritualAnchorTileEntity.inventory, newItem, i);
                                    updateState(worldIn, state, pos);
                                    updateState(worldIn, state, pos.down());
                                    heldItem.setCount(heldItem.getCount() - 1);
                                    player.swingArm(handIn);
                                    return ActionResultType.SUCCESS;
                                }
                            }
                        }
                    }
                }
            }
        }
        return ActionResultType.SUCCESS;
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
        return new RitualAnchorTileEntity();
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> blockStateBuilder)
    {
        blockStateBuilder.add(LIT);
    }
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return this.getDefaultState().with(LIT, false);
    }
}