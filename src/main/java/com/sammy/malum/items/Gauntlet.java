package com.sammy.malum.items;

import com.sammy.malum.MalumMod;
import com.sammy.malum.items.tools.ModSwordItem;
import com.sammy.malum.items.utility.IConfigurableItem;
import com.sammy.malum.items.utility.Option;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.TicketType;

import java.util.ArrayList;
import java.util.UUID;

import static com.sammy.malum.capabilities.MalumDataProvider.getGauntletTarget;

public class Gauntlet extends ModSwordItem implements IConfigurableItem
{
    public ArrayList<Option> options;
    
    public Gauntlet(IItemTier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties);
        options = new ArrayList<>();
        addOption(0, "malum.tooltip.gauntlet.option.a"); //scrape space and time infront of yourself
        addOption(1, "malum.tooltip.gauntlet.option.b"); //scrape space and time between you and your enemy
        addOption(2, "malum.tooltip.gauntlet.option.c"); //none
    }
    
    private static void teleport(Entity entityIn, ServerWorld worldIn, double x, double y, double z)
    {
        if (entityIn instanceof ServerPlayerEntity)
        {
            ChunkPos chunkpos = new ChunkPos(new BlockPos(x, y, z));
            worldIn.getChunkProvider().registerTicket(TicketType.POST_TELEPORT, chunkpos, 1, entityIn.getEntityId());
            entityIn.stopRiding();
            if (((ServerPlayerEntity) entityIn).isSleeping())
            {
                ((ServerPlayerEntity) entityIn).stopSleepInBed(true, true);
            }
            
            if (worldIn == entityIn.world)
            {
                ((ServerPlayerEntity) entityIn).connection.setPlayerLocation(x, y, z, entityIn.rotationYaw, entityIn.rotationPitch);
            }
            else
            {
                ((ServerPlayerEntity) entityIn).teleport(worldIn, x, y, z, entityIn.rotationYaw, entityIn.rotationPitch);
            }
            
            entityIn.setRotationYawHead(((ServerPlayerEntity) entityIn).rotationYawHead);
        }
        else
        {
            float f1 = MathHelper.wrapDegrees(entityIn.rotationYaw);
            float f = MathHelper.wrapDegrees(entityIn.rotationPitch);
            f = MathHelper.clamp(f, -90.0F, 90.0F);
            if (worldIn == entityIn.world)
            {
                entityIn.setLocationAndAngles(x, y, z, f1, f);
                entityIn.setRotationYawHead(f1);
            }
            else
            {
                entityIn.detach();
                Entity entity = entityIn;
                entityIn = entityIn.getType().create(worldIn);
                if (entityIn == null)
                {
                    return;
                }
                
                entityIn.copyDataFromOld(entity);
                entityIn.setLocationAndAngles(x, y, z, f1, f);
                entityIn.setRotationYawHead(f1);
                worldIn.addFromAnotherDimension(entityIn);
            }
        }
        if (!(entityIn instanceof LivingEntity) || !((LivingEntity) entityIn).isElytraFlying())
        {
            entityIn.setMotion(entityIn.getMotion().mul(1.0D, 0.0D, 1.0D));
            entityIn.setOnGround(true);
        }
        
        if (entityIn instanceof CreatureEntity)
        {
            ((CreatureEntity) entityIn).getNavigator().clearPath();
        }
    }
    
    @Override
    public ArrayList<Option> getOptions()
    {
        return options;
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerEntity, Hand handIn)
    {
        ItemStack stack = playerEntity.getHeldItem(handIn);
        if (playerEntity.world instanceof ServerWorld)
        {
            UUID uuid = getGauntletTarget(playerEntity);
            if (uuid != null)
            {
                Entity targetEntity = ((ServerWorld) playerEntity.world).getEntityByUuid(uuid);
                Vector3d pos = playerEntity.getPositionVec().add(playerEntity.getLookVec().mul(1, 1, 1));
                teleport(targetEntity, (ServerWorld) playerEntity.world, pos.x, targetEntity.getPositionVec().y, pos.z);
                MalumMod.LOGGER.info("teleport entity");
            }
            else
            {
                Vector3d pos = playerEntity.getPositionVec().add(playerEntity.getLookVec().mul(4, 4, 4));
                teleport(playerEntity, (ServerWorld) playerEntity.world, pos.x, pos.y, pos.z);
                MalumMod.LOGGER.info("teleport player");
            }
        }
        return ActionResult.resultSuccess(stack);
    }
}