package com.kittykitcatcat.malum.blocks.souljar;

import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.blocks.ritualanchor.RitualAnchorTileEntity;
import com.kittykitcatcat.malum.items.tools.ItemSpiritwoodStave;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

import static com.kittykitcatcat.malum.MalumHelper.updateState;

public class SoulJarBlock extends Block
{
    public SoulJarBlock(Properties properties)
    {
        super(properties);
    }
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        MalumMod.LOGGER.info("homo");
        if (!worldIn.isRemote())
        {
            MalumMod.LOGGER.info("homo2");
            if (handIn != Hand.OFF_HAND)
            {
                MalumMod.LOGGER.info("homo3");
                if (worldIn.getTileEntity(pos) instanceof SoulJarTileEntity)
                {
                    MalumMod.LOGGER.info("homo4");
                    SoulJarTileEntity tileEntity = (SoulJarTileEntity) worldIn.getTileEntity(pos);
                    ItemStack heldItem = player.getHeldItem(handIn);
                    if (heldItem.getItem() instanceof ItemSpiritwoodStave)
                    {
                        MalumMod.LOGGER.info("homo5");
                        if (heldItem.getTag() != null)
                        {
                            MalumMod.LOGGER.info("homo6");
                            String heldRegistryName = heldItem.getTag().getString("entityRegistryName");
                            tileEntity.purity += heldItem.getTag().getFloat("purity");
                            tileEntity.entityRegistryName = heldRegistryName;
                            heldItem.getTag().remove("purity");
                            heldItem.getTag().remove("entityRegistryName");
                            heldItem.getTag().remove("entityDisplayName");
                            MalumMod.LOGGER.info(tileEntity.entityRegistryName);
                            MalumMod.LOGGER.info(tileEntity.purity);
                            return ActionResultType.SUCCESS;
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