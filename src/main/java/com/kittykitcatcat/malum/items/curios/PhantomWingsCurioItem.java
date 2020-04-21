package com.kittykitcatcat.malum.items.curios;

import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.capabilities.CapabilityValueGetter;
import com.kittykitcatcat.malum.models.ModelLeftWing;
import com.kittykitcatcat.malum.models.ModelRightWing;
import com.kittykitcatcat.malum.network.NetworkManager;
import com.kittykitcatcat.malum.network.packets.CanFlyPacket;
import com.kittykitcatcat.malum.network.packets.FlightTimePacket;
import com.kittykitcatcat.malum.network.packets.TotalFlightTimePacket;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.minecraftforge.fml.network.PacketDistributor;
import top.theillusivec4.curios.api.capability.ICurio;

import java.util.Random;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;

public class PhantomWingsCurioItem extends Item implements ICurio
{
    public PhantomWingsCurioItem(Properties builder)
    {
        super(builder);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT unused)
    {
        return CurioThingIDK.createProvider(new ICurio()
        {
            private final ResourceLocation WING_TEXTURE =
                new ResourceLocation(MalumMod.MODID, "textures/armor/phantom_wings.png");
            private Object left_wing_model;
            private Object right_wing_model;

            @Override
            public void playEquipSound(LivingEntity entityLivingBase)
            {
                entityLivingBase.world.playSound(null, entityLivingBase.getPosition(),
                    SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundCategory.NEUTRAL,
                    1.0f, 1.0f);
            }

            @Override
            public boolean hasRender(String identifier, LivingEntity livingEntity)
            {
                return true;
            }

            @Override
            public void render(String identifier, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
            {
                if (!(this.right_wing_model instanceof ModelRightWing))
                {
                    this.right_wing_model = new ModelRightWing();
                }
                if (!(this.left_wing_model instanceof ModelLeftWing))
                {
                    this.left_wing_model = new ModelLeftWing();
                }
                ModelLeftWing wing_model = (ModelLeftWing) this.left_wing_model;
                IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, wing_model.getRenderType(WING_TEXTURE), false, false);


                Minecraft.getInstance().getTextureManager().bindTexture(WING_TEXTURE);

                RenderHelper.translateIfSneaking(matrixStack, livingEntity);
                RenderHelper.rotateIfSneaking(matrixStack, livingEntity);

                float totalFlightTime = (float) CapabilityValueGetter.getTotalFlightTime((PlayerEntity) livingEntity);
                float rotationX = 60 - (totalFlightTime > 30 ? 60 : totalFlightTime * 2);
                float rotationY = 20f + (totalFlightTime > 25 ? 25 : totalFlightTime);
                float rotationXAgain = 10f - (totalFlightTime > 20 ? 20 : totalFlightTime) - (float) Math.sin(livingEntity.world.getGameTime() * 0.4f) * (totalFlightTime > 36 ? 36 : totalFlightTime);

                matrixStack.rotate(Vector3f.ZP.rotationDegrees(-rotationX));
                matrixStack.rotate(Vector3f.ZP.rotationDegrees(-rotationXAgain));
                matrixStack.rotate(Vector3f.YP.rotationDegrees(rotationY));

                ((ModelRightWing) right_wing_model).render(matrixStack, vertexBuilder, light, NO_OVERLAY, 1.0F,
                    1.0F, 1.0F, 1.0F);

                matrixStack.rotate(Vector3f.YP.rotationDegrees(-rotationY));
                matrixStack.rotate(Vector3f.ZP.rotationDegrees(rotationXAgain));
                matrixStack.rotate(Vector3f.ZP.rotationDegrees(rotationX));

                matrixStack.rotate(Vector3f.ZP.rotationDegrees(rotationX));
                matrixStack.rotate(Vector3f.ZP.rotationDegrees(rotationXAgain));
                matrixStack.rotate(Vector3f.YP.rotationDegrees(-rotationY));
                ((ModelLeftWing) left_wing_model).render(matrixStack, vertexBuilder, light, NO_OVERLAY, 1.0F,
                    1.0F, 1.0F, 1.0F);

                matrixStack.rotate(Vector3f.YP.rotationDegrees(rotationY));
                matrixStack.rotate(Vector3f.ZP.rotationDegrees(-rotationXAgain));
                matrixStack.rotate(Vector3f.ZP.rotationDegrees(-rotationX));
            }
            //THIS CODE IS SHIT, CLEAN IT UP SOMEDAY
            @Override
            public void onCurioTick(String identifier, int index, LivingEntity entityLivingBase)
            {
                if (entityLivingBase.world.isRemote)
                {
                    if (entityLivingBase instanceof PlayerEntity)
                    {
                        double flightTime = CapabilityValueGetter.getAvaiableFlightTime((PlayerEntity) entityLivingBase);
                        boolean canFly = CapabilityValueGetter.getCanFly((PlayerEntity) entityLivingBase);
                        boolean isJumpHeld = Minecraft.getInstance().gameSettings.keyBindJump.isKeyDown();
                        if (!entityLivingBase.onGround)
                        {
                            if (!canFly)
                            {
                                if (!isJumpHeld)
                                {
                                    NetworkManager.INSTANCE.send(
                                        PacketDistributor.SERVER.noArg(),
                                        new CanFlyPacket(true));
                                    CapabilityValueGetter.setCanFly((PlayerEntity) entityLivingBase, true);
                                }
                            }
                        }
                        if (entityLivingBase.onGround)
                        {
                            if (CapabilityValueGetter.getTotalFlightTime((PlayerEntity) entityLivingBase) > 0f)
                            {
                                        NetworkManager.INSTANCE.send(
                                    PacketDistributor.SERVER.noArg(),
                                    new TotalFlightTimePacket(CapabilityValueGetter.getTotalFlightTime((PlayerEntity) entityLivingBase) - 2d));
                                CapabilityValueGetter.setTotalFlightTime((PlayerEntity) entityLivingBase, CapabilityValueGetter.getTotalFlightTime((PlayerEntity) entityLivingBase) - 2d);
                            }
                            if (canFly)
                            {
                                NetworkManager.INSTANCE.send(
                                    PacketDistributor.SERVER.noArg(),
                                    new CanFlyPacket(false));
                                CapabilityValueGetter.setCanFly((PlayerEntity) entityLivingBase, false);
                            }
                            if (flightTime != 20)
                            {
                                NetworkManager.INSTANCE.send(
                                    PacketDistributor.SERVER.noArg(),
                                    new FlightTimePacket(20));
                                CapabilityValueGetter.setAvaiableFlightTime((PlayerEntity) entityLivingBase, 20);
                            }
                        }
                        else
                        {
                            if (canFly)
                            {
                                if (CapabilityValueGetter.getTotalFlightTime((PlayerEntity) entityLivingBase) < 60f)
                                {
                                    NetworkManager.INSTANCE.send(
                                        PacketDistributor.SERVER.noArg(),
                                        new TotalFlightTimePacket(CapabilityValueGetter.getTotalFlightTime((PlayerEntity) entityLivingBase) + 1d));
                                    CapabilityValueGetter.setTotalFlightTime((PlayerEntity) entityLivingBase, CapabilityValueGetter.getTotalFlightTime((PlayerEntity) entityLivingBase) + 1);
                                }
                                if (isJumpHeld)
                                {
                                    float yaw = entityLivingBase.rotationYaw;
                                    float pitch = entityLivingBase.rotationPitch;
                                    float f = -MathHelper.sin(yaw * ((float) Math.PI / 180F)) * MathHelper.cos(pitch * ((float) Math.PI / 180F));
                                    float f1 = -MathHelper.sin(pitch * ((float) Math.PI / 180F));
                                    float f2 = MathHelper.cos(yaw * ((float) Math.PI / 180F)) * MathHelper.cos(pitch * ((float) Math.PI / 180F));
                                    Vec3d direction = new Vec3d(f, f1, f2);
                                    Vec3d particlePosition = entityLivingBase.getPositionVec().add(MathHelper.nextDouble(entityLivingBase.world.rand, -0.5, 0.5), MathHelper.nextDouble(new Random(), -0.15, 0.15), MathHelper.nextDouble(new Random(), -0.5, 0.5)).subtract(direction);
                                    entityLivingBase.world.addParticle(ParticleTypes.MYCELIUM, particlePosition.x, particlePosition.y, particlePosition.z, 0, 0, 0);
                                    if (flightTime > 0d)
                                    {
                                        if (entityLivingBase.getMotion().y < 0.2)
                                        {
                                            entityLivingBase.setMotion(entityLivingBase.getMotion().add(0,0.1,0));
                                        }
                                        entityLivingBase.setMotion(entityLivingBase.getMotion().add(0,0.05,0));

                                        NetworkManager.INSTANCE.send(
                                            PacketDistributor.SERVER.noArg(),
                                            new FlightTimePacket(CapabilityValueGetter.getAvaiableFlightTime((PlayerEntity) entityLivingBase) - 1d));
                                        CapabilityValueGetter.setAvaiableFlightTime((PlayerEntity) entityLivingBase, CapabilityValueGetter.getAvaiableFlightTime((PlayerEntity) entityLivingBase) - 1d);
                                    }
                                    else
                                    {
                                        if (entityLivingBase.getMotion().y < -0.2f)
                                        {
                                            entityLivingBase.addVelocity(0, 0.1f, 0);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public boolean canRightClickEquip()
            {

                return true;
            }
        });
    }
}