package com.kittykitcatcat.malum;

import com.kittykitcatcat.malum.blocks.souljar.SoulJarTileEntity;
import com.kittykitcatcat.malum.init.ModBlocks;
import com.kittykitcatcat.malum.recipes.SpiritInfusionRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.kittykitcatcat.malum.MalumHelper.makeTooltip;

public class SpiritData
{
    public String entityRegistryName;
    public float purity;
    public SpiritData(LivingEntity entity, float purity)
    {
        if (entity.getType().getRegistryName() != null)
        {
            entityRegistryName = entity.getType().getRegistryName().toString();
            this.purity = purity;
        }
    }
    public SpiritData(String entityRegistryName, float purity)
    {
        this.entityRegistryName = entityRegistryName;
        this.purity = purity;
    }
    public Optional<EntityType<?>> getType()
    {
        return EntityType.byKey(entityRegistryName);
    }
    public void writeSpiritDataIntoNBT(CompoundNBT nbt)
    {
        nbt.putString("entityRegistryName", entityRegistryName);
        nbt.putFloat("purity", purity);
    }
    public static BlockPos findBlockByData(int radius, SpiritData data, BlockPos pos, World worldIn)
    {
        for (int x = -radius; x <= radius; x++)
        {
            for (int z = -radius; z <= radius; z++)
            {
                int posX = pos.getX() + x;
                int posZ = pos.getZ() + z;
                BlockPos blockPos = new BlockPos(posX,pos.getY(),posZ);
                BlockState state1 = worldIn.getBlockState(blockPos);
                if (state1.getBlock().equals(ModBlocks.soul_jar))
                {
                    if (worldIn.getTileEntity(blockPos) instanceof SoulJarTileEntity)
                    {
                        SoulJarTileEntity tileEntity = (SoulJarTileEntity) worldIn.getTileEntity(blockPos);
                        if (tileEntity.purity == data.purity && tileEntity.entityRegistryName.equals(data.entityRegistryName))
                        {
                            return blockPos;
                        }
                    }
                }
            }
        }
        return BlockPos.ZERO;
    }
    public static SpiritData findSpiritData(int radius, SpiritInfusionRecipe recipe, BlockPos pos, World worldIn)
    {
        for (int x = -radius; x <= radius; x++)
        {
            for (int z = -radius; z <= radius; z++)
            {
                int posX = pos.getX() + x;
                int posZ = pos.getZ() + z;
                BlockPos blockPos = new BlockPos(posX,pos.getY(),posZ);
                BlockState state1 = worldIn.getBlockState(blockPos);
                if (state1.getBlock().equals(ModBlocks.soul_jar))
                {
                    if (worldIn.getTileEntity(blockPos) instanceof SoulJarTileEntity)
                    {
                        SpiritData data = recipe.getData();
                        SoulJarTileEntity tileEntity = (SoulJarTileEntity) worldIn.getTileEntity(blockPos);
                        if (data.entityRegistryName.equals(tileEntity.entityRegistryName))
                        {
                            if (tileEntity.purity >= data.purity)
                            {
                                return new SpiritData(tileEntity.entityRegistryName, tileEntity.purity);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
    @OnlyIn(Dist.CLIENT)
    public static void makeDefaultTooltip(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        if (stack.getTag() != null)
        {
            if (stack.getTag().contains("entityRegistryName"))
            {
                CompoundNBT nbt = stack.getTag();
                SpiritData data = new SpiritData(nbt.getString("entityRegistryName"),nbt.getFloat("purity"));
                String name = "malum.tooltip.name.desc";
                String purity = "malum.tooltip.purity.desc";
                ArrayList<ITextComponent> arrayList = new ArrayList<>();
                arrayList.add(new TranslationTextComponent(name).appendSibling(new StringTextComponent(" " + data.getType().get().getName().getString())));
                arrayList.add(new TranslationTextComponent(purity).appendSibling(new StringTextComponent(" " + data.purity)));
                makeTooltip(stack,worldIn,tooltip,flagIn,arrayList);
            }
        }
    }
}
