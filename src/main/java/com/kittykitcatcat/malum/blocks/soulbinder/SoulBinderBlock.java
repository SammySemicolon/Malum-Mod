package com.kittykitcatcat.malum.blocks.soulbinder;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.SpiritData;
import com.kittykitcatcat.malum.blocks.ritualanchor.RitualAnchorTileEntity;
import com.kittykitcatcat.malum.init.ModBlocks;
import com.kittykitcatcat.malum.init.ModRecipes;
import com.kittykitcatcat.malum.init.ModTileEntities;
import com.kittykitcatcat.malum.recipes.SpiritInfusionRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.ArrayList;
import java.util.List;

import static com.kittykitcatcat.malum.MalumHelper.updateState;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SoulBinderBlock extends Block
{
    @SubscribeEvent
    public static void setRenderLayer(FMLClientSetupEvent event)
    {
        RenderTypeLookup.setRenderLayer(ModBlocks.soul_binder, RenderType.getCutout());
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.soul_binder_tile_entity, SoulBinderRenderer::new);
    }

    @Override
    public boolean canEntityDestroy(BlockState state, IBlockReader world, BlockPos pos, Entity entity)
    {
        if (world.getTileEntity(pos) instanceof SoulBinderTileEntity)
        {
            SoulBinderTileEntity tileEntity = (SoulBinderTileEntity) world.getTileEntity(pos);
            return !tileEntity.active;
        }
        return true;
    }

    public SoulBinderBlock(Properties properties)
    {
        super(properties);
    }
    public enum anchorOffset
    {
        anchor1(3, 0),
        anchor2(2, 2),
        anchor3(0, 3),
        anchor4(-2, 2),
        anchor5(-3, 0),
        anchor6(-2, -2),
        anchor7(0, -3),
        anchor8(2, -2);

        public final int offsetY;
        public final int offsetX;
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

                    SoulBinderTileEntity soulBinderTileEntity = (SoulBinderTileEntity) worldIn.getTileEntity(pos);
                    ItemStack inputItem = soulBinderTileEntity.getInputStack(soulBinderTileEntity.inventory);
                    List<Item> items = findList(pos, worldIn);
                    if (inputItem.isEmpty())
                    {
                        for (SpiritInfusionRecipe recipe : ModRecipes.spiritInfusionRecipes)
                        {
                            if (items.equals(recipe.getItems()))
                            {
                                if (heldItem.getItem().equals(recipe.getCatalyst()))
                                {
                                    if (SpiritData.findSpiritData(2, recipe, pos, worldIn) != null)
                                    {
                                        soulBinderTileEntity.jarPos = SpiritData.findDataBlock(2, recipe, pos,worldIn);
                                        ItemStack newItem = heldItem.copy();
                                        newItem.setCount(1);
                                        MalumHelper.setStackInTEInventory(soulBinderTileEntity.inventory, newItem, 0);

                                        updateState(worldIn, state, pos);
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
        }
        return ActionResultType.SUCCESS;
    }
    public static List<Item> findList(BlockPos pos,World worldIn)
    {
        List<Item> anchorItems = new ArrayList<>();
        for (anchorOffset offset : anchorOffset.values())
        {
            BlockPos anchorPos = pos.add(offset.offsetX, 0, offset.offsetY);
            if (getAnchorAt(anchorPos, worldIn) != null)
            {
                RitualAnchorTileEntity anchorTileEntity = getAnchorAt(anchorPos, worldIn);
                if (!anchorTileEntity.inventory.getStackInSlot(0).isEmpty())
                {
                    anchorItems.add(anchorTileEntity.inventory.getStackInSlot(0).getItem());
                }
                else
                {
                    anchorItems.add(Items.AIR);
                }
            }
        }
        return anchorItems;
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
}