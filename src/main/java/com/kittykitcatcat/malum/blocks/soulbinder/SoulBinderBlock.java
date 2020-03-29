package com.kittykitcatcat.malum.blocks.soulbinder;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.blocks.ritualanchor.RitualAnchorTileEntity;
import com.kittykitcatcat.malum.init.ModBlocks;
import com.kittykitcatcat.malum.init.ModRecipes;
import com.kittykitcatcat.malum.init.ModTileEntities;
import com.kittykitcatcat.malum.recipes.RitualAnchorInput;
import com.kittykitcatcat.malum.recipes.SpiritInfusionRecipe;
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
import java.util.ArrayList;
import java.util.List;

import static com.kittykitcatcat.malum.MalumHelper.updateState;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SoulBinderBlock extends Block
{
    @SubscribeEvent
    public static void setRenderLayer(FMLClientSetupEvent event)
    {
        RenderTypeLookup.setRenderLayer(ModBlocks.soul_binder, RenderType.getCutout());
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.soul_binder_tile_entity, SoulBinderRenderer::new);
    }
    public static final IntegerProperty RENDER = IntegerProperty.create("render",0,9);

    public SoulBinderBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(RENDER, 0));
    }
    public enum anchorOffset
    {
        anchor1(3, 3),
        anchor2(3, -3),
        anchor3(-3, -3),
        anchor4(-3, 3);

        private final int offsetY;
        private final int offsetX;
        private anchorOffset(int offsetX, int offsetY) { this.offsetX = offsetX; this.offsetY = offsetY;}
    }
    public static RitualAnchorTileEntity getAnchorAt(BlockPos blockPos, World world)
    {
        if (world.getTileEntity(blockPos) instanceof RitualAnchorTileEntity);
        {
            return (RitualAnchorTileEntity) world.getTileEntity(blockPos);
        }
    }
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (!worldIn.isRemote())
        {
            if (handIn != Hand.OFF_HAND)
            {
                if (worldIn.getTileEntity(pos) instanceof SoulBinderTileEntity)
                {
                    ItemStack heldItem = player.getHeldItem(handIn);
                    List<RitualAnchorInput> anchorInputs = new ArrayList<>();
                    for (anchorOffset offset : anchorOffset.values())
                    {
                        BlockPos anchorPos = pos.add(offset.offsetX, 0, offset.offsetY);
                        if (getAnchorAt(anchorPos, worldIn) != null)
                        {
                            List<Item> anchorItems = new ArrayList<>();
                            RitualAnchorTileEntity anchorTileEntity = getAnchorAt(anchorPos, worldIn);
                            for (int i = 0; i < anchorTileEntity.inventory.getSlots(); i++)
                            {
                                if (!anchorTileEntity.inventory.getStackInSlot(i).isEmpty())
                                {
                                    anchorItems.add(anchorTileEntity.inventory.getStackInSlot(i).getItem());
                                }
                            }
                            if (anchorItems.size() == 4)
                            {
                                anchorInputs.add(new RitualAnchorInput(anchorItems.get(0), anchorItems.get(1), anchorItems.get(2), anchorItems.get(3)));
                            }
                        }
                    }

                    SoulBinderTileEntity soulBinderTileEntity = (SoulBinderTileEntity) worldIn.getTileEntity(pos);
                    ItemStack inputItem = soulBinderTileEntity.getInputStack(soulBinderTileEntity.inventory);

                    for (SpiritInfusionRecipe recipe : ModRecipes.spiritInfusionRecipes)
                    {
                        if (inputItem.isEmpty())
                        {
                            if (RitualAnchorInput.isEqualList(anchorInputs, recipe.getInputs()))
                            {
                                if (heldItem.getItem().equals(recipe.getCatalyst()))
                                {
                                    ItemStack newItem = heldItem.copy();
                                    newItem.setCount(1);
                                    MalumHelper.setStackInTEInventory(soulBinderTileEntity.inventory, newItem, 0);
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
        return new SoulBinderTileEntity();
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> blockStateBuilder)
    {
        blockStateBuilder.add(HORIZONTAL_FACING);
        blockStateBuilder.add(RENDER);
    }
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing()).with(RENDER, 0);
    }
}