package com.kittykitcatcat.malum.blocks.spiritbellows;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.blocks.spiritfurnace.SpiritFurnaceBottomRenderer;
import com.kittykitcatcat.malum.blocks.spiritfurnace.SpiritFurnaceBottomTileEntity;
import com.kittykitcatcat.malum.init.ModBlocks;
import com.kittykitcatcat.malum.init.ModItems;
import com.kittykitcatcat.malum.init.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static com.kittykitcatcat.malum.MalumHelper.updateState;
import static com.kittykitcatcat.malum.blocks.spiritfurnace.SpiritFurnaceBottomTileEntity.spiritFuranceSlotEnum.fuel;
import static net.minecraft.state.properties.BlockStateProperties.*;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SpiritBellowsBlock extends Block
{
    @SubscribeEvent
    public static void setRenderLayer(FMLClientSetupEvent event)
    {
        RenderTypeLookup.setRenderLayer(ModBlocks.spirit_bellows, RenderType.getCutout());
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.spirit_bellows_tile_entity, SpiritBellowsRenderer::new);
    }
    public static final BooleanProperty RENDER = BooleanProperty.create("render");

    public SpiritBellowsBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(RENDER, false));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (!worldIn.isRemote())
        {
            if (handIn != Hand.OFF_HAND)
            {
                if (worldIn.getTileEntity(pos) instanceof SpiritBellowsTileEntity)
                {
                    SpiritBellowsTileEntity bellowsTileEntity = (SpiritBellowsTileEntity) worldIn.getTileEntity(pos);
                    ItemStack heldItem = player.getHeldItem(handIn);
                    ItemStack fuelItem = bellowsTileEntity.inventory.getStackInSlot(0);
                    //when input is empty
                    //right clicking adds held item to input
                    if (fuelItem.isEmpty())
                    {
                        if (heldItem.getItem().equals(ModItems.spirit_charcoal))
                        {
                            MalumHelper.setStackInTEInventory(bellowsTileEntity.inventory, heldItem, 0);
                            updateState(worldIn, state, pos);
                            updateState(worldIn, state, pos.up());
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
                        MalumHelper.setStackInTEInventory(bellowsTileEntity.inventory, ItemStack.EMPTY, 0);
                        updateState(worldIn, state, pos);
                        updateState(worldIn, state, pos.up());
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
                                MalumHelper.increaseStackSizeInTEInventory(bellowsTileEntity.inventory, 1, 0);
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
        return new SpiritBellowsTileEntity();
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> blockStateBuilder)
    {
        blockStateBuilder.add(HORIZONTAL_FACING);
        blockStateBuilder.add(RENDER);
    }
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {

        return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing()).with(RENDER, false);
    }
}