package com.kittykitcatcat.malum.blocks.souljar;

import com.kittykitcatcat.malum.init.ModBlocks;
import com.kittykitcatcat.malum.init.ModTileEntities;
import com.kittykitcatcat.malum.items.ItemSpiritwoodStave;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
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

import static com.kittykitcatcat.malum.MalumHelper.updateState;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SoulJarBlock extends Block
{
    @SubscribeEvent
    public static void setRenderLayer(FMLClientSetupEvent event)
    {
        RenderTypeLookup.setRenderLayer(ModBlocks.soul_jar, RenderType.getTranslucent());
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.soul_jar_tile_entity, SoulJarRenderer::new);
    }
    public static final IntegerProperty RENDER = IntegerProperty.create("render", 0, 2);
    public SoulJarBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(RENDER, 0));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(RENDER);
        super.fillStateContainer(builder);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        player.world.notifyBlockUpdate(pos, state, state, 3);
        if (!worldIn.isRemote())
        {
            if (handIn != Hand.OFF_HAND)
            {
                if (worldIn.getTileEntity(pos) instanceof SoulJarTileEntity)
                {
                    SoulJarTileEntity tileEntity = (SoulJarTileEntity) worldIn.getTileEntity(pos);
                    ItemStack heldItem = player.getHeldItem(handIn);
                    if (heldItem.getItem() instanceof ItemSpiritwoodStave)
                    {
                        if (heldItem.getTag() != null)
                        {
                            String heldRegistryName = heldItem.getTag().getString("entityRegistryName");
                            if (tileEntity.purity == 0)
                            {
                                tileEntity.purity = heldItem.getTag().getFloat("purity");
                                tileEntity.entityRegistryName = heldRegistryName;
                                heldItem.getTag().remove("purity");
                                heldItem.getTag().remove("entityRegistryName");
                                heldItem.getTag().remove("entityDisplayName");
                                player.swingArm(handIn);
                                return ActionResultType.SUCCESS;
                            }
                            if (heldRegistryName.equals(tileEntity.entityRegistryName))
                            {
                                float toAdd = heldItem.getTag().getFloat("purity");
                                float alreadyIn = tileEntity.purity;
                                if (toAdd + alreadyIn <= 5f)
                                {
                                    tileEntity.purity += toAdd;
                                    heldItem.getTag().remove("purity");
                                    heldItem.getTag().remove("entityRegistryName");
                                    heldItem.getTag().remove("entityDisplayName");
                                    player.swingArm(handIn);
                                }
                                else
                                {
                                    float empty = 5f - alreadyIn;
                                    for (float f = 0; f < empty; f += 0.01f)
                                    {
                                        tileEntity.purity += 0.01f;
                                        if (tileEntity.purity >= 5f)
                                        {
                                            heldItem.getTag().remove("purity");
                                            heldItem.getTag().remove("entityRegistryName");
                                            heldItem.getTag().remove("entityDisplayName");
                                            break;
                                        }
                                    }
                                    player.swingArm(handIn);
                                    return ActionResultType.SUCCESS;
                                }
                                return ActionResultType.SUCCESS;
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
        return new SoulJarTileEntity();
    }
}