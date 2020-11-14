package com.sammy.malum.items.equipment.curios;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sammy.malum.MalumMod;
import com.sammy.malum.models.vanity.*;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.UUID;

public class CurioBandOfFriendship extends Item implements ICurio
{
    public CurioBandOfFriendship(Properties builder)
    {
        super(builder);
    }
    
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT unused)
    {
        return CurioProvider.createProvider(new ICurio()
        {
            //cute furry, kitty
            public final ResourceLocation kittys_tail_texture = new ResourceLocation(MalumMod.MODID, "textures/other/kittys_tail.png");
            //good lesbian girl, cherry
            public final ResourceLocation cherrys_things_texture = new ResourceLocation(MalumMod.MODID, "textures/other/cherrys_things.png");
            //boss, angela
            public final ResourceLocation angelasThingsTexture = new ResourceLocation(MalumMod.MODID, "textures/other/angelas_things.png");
            //dumbass
            public final ResourceLocation flowerFriendTexture = new ResourceLocation("minecraft", "textures/block/orange_tulip.png");
            //greedy capitalist, jt
            public final ResourceLocation jtsThingsTexture = new ResourceLocation(MalumMod.MODID, "textures/other/jts_things.png");
            //adorable in many ways, enny
            public final ResourceLocation ennysThingsTexture = new ResourceLocation(MalumMod.MODID, "textures/other/ennys_things.png");
            //proud owner of splash mountain, moist
            public final ResourceLocation bucketTexture = new ResourceLocation(MalumMod.MODID, "textures/other/bucket.png");
            //the third greatest furry, gwen
            public final ResourceLocation furryBabyFurryTexture = new ResourceLocation(MalumMod.MODID, "textures/other/furry_baby_furry_things.png");
            public ModelKittysTail<LivingEntity> kittys_tail;
            public ModelCherrysBracelets<LivingEntity> cherrysBracelets;
            public ModelCherrysGlasses<LivingEntity> cherrysGlasses;
            public ModelAngelasGlasses<LivingEntity> angelasGlasses;
            public ModelAngelasScarf<LivingEntity> angelasScarf;
            public ModelAngelasScarfHandBits<LivingEntity> angelasScarfHandBits;
            public ModelFlowerFriend<LivingEntity> flowerFriend;
            public ModelJTSThings<LivingEntity> jtsThings;
            public ModelEnnysThings<LivingEntity> ennysThings;
            public ModelMoistyBucket<LivingEntity> bucket;
            public ModelFurryBabyFurryTail<LivingEntity> furryBabyFurryTail;
            public ModelFurryBabyFurryEars<LivingEntity> furryBabyFurryEars;

            @Override
            public void playRightClickEquipSound(LivingEntity livingEntity)
            {
                livingEntity.world.playSound(null, livingEntity.getPosition(), SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, SoundCategory.NEUTRAL, 1.0f, 1.0f);
            }

            @Override
            public boolean canRightClickEquip()
            {
                return true;
            }

            @Override
            public boolean canRender(String identifier, int index, LivingEntity livingEntity)
            {
                return true;
            }
            
            @Override
            public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
            {
                if (livingEntity instanceof PlayerEntity)
                {
                    PlayerEntity playerEntity = (PlayerEntity) livingEntity;
                    UUID uuid = PlayerEntity.getUUID(playerEntity.getGameProfile());
                    if (uuid.equals(UUID.fromString("0ca54301-6170-4c44-b3e0-b8afa6b81ed2")))
                    {
                        matrixStack.push();
                        if (kittys_tail == null)
                        {
                            kittys_tail = new ModelKittysTail<>();
                        }
                        double curSpeed = livingEntity.getMotion().length();
                        if (curSpeed != 0.0)
                        {
                            Vector3d A = new Vector3d(livingEntity.getMotion().x, 0.0, livingEntity.getMotion().z);
                            Vector3d yawLook = Vector3d.fromPitchYaw(0.0f, livingEntity.rotationYaw);
                            Vector3d look = new Vector3d(yawLook.x, 0.0, yawLook.z);
                            Vector3d desiredDirection = look.rotateYaw((float) Math.toRadians(90)).normalize();
                            Vector3d sidewaysVelocity = desiredDirection.scale(A.dotProduct(desiredDirection));
                            double speedAndDirection = (sidewaysVelocity.length() * -Math.signum(desiredDirection.dotProduct(sidewaysVelocity))) / curSpeed;
                            double rotation = speedAndDirection * 55;
                            matrixStack.rotate(Vector3f.YP.rotationDegrees((float) rotation));
                        }
                        if (!playerEntity.isSneaking())
                        {
                            double xRotation = Math.sin(livingEntity.world.getGameTime() / 18f) * 6;
                            matrixStack.rotate(Vector3f.XP.rotationDegrees((float) xRotation));
                        }
                        kittys_tail.setRotationAngles(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                        kittys_tail.setLivingAnimations(livingEntity, limbSwing, limbSwingAmount, partialTicks);
                        
                        RenderHelper.followBodyRotations(livingEntity, kittys_tail);
                        RenderHelper.translateIfSneaking(matrixStack, livingEntity);
                        RenderHelper.rotateIfSneaking(matrixStack, livingEntity);
                        IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, kittys_tail.getRenderType(kittys_tail_texture), false, false);
                        kittys_tail.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
                        matrixStack.pop();
                    }
                    if (uuid.equals(UUID.fromString("5ab35c1f-fe8e-4c5d-b7ff-47d89f654ad7")) || uuid.equals(UUID.fromString("0c0ea06a-b3d3-4529-9178-1995d68550a4")))
                    {
                        matrixStack.push();
                        if (angelasScarf == null)
                        {
                            angelasScarf = new ModelAngelasScarf<>();
                        }
                        RenderHelper.translateIfSneaking(matrixStack, livingEntity);
                        RenderHelper.rotateIfSneaking(matrixStack, livingEntity);
                        IVertexBuilder scarfBuilder = ItemRenderer.getBuffer(renderTypeBuffer, angelasScarf.getRenderType(angelasThingsTexture), false, false);
                        angelasScarf.render(matrixStack, scarfBuilder, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
                        matrixStack.pop();
                        
                        matrixStack.push();
                        if (angelasScarfHandBits == null)
                        {
                            angelasScarfHandBits = new ModelAngelasScarfHandBits<>();
                        }
                        angelasScarfHandBits.setLivingAnimations(livingEntity, limbSwing, limbSwingAmount, partialTicks);
                        angelasScarfHandBits.setRotationAngles(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                        ICurio.RenderHelper.followBodyRotations(livingEntity, angelasScarfHandBits);
                        IVertexBuilder scarfHandBitsBuilder = ItemRenderer.getBuffer(renderTypeBuffer, angelasScarfHandBits.getRenderType(angelasThingsTexture), false, false);
                        angelasScarfHandBits.render(matrixStack, scarfHandBitsBuilder, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
                        matrixStack.pop();
                        
                        matrixStack.push();
                        if (angelasGlasses == null)
                        {
                            angelasGlasses = new ModelAngelasGlasses<>();
                        }
                        ICurio.RenderHelper.followHeadRotations(livingEntity, angelasGlasses.glasses);
                        IVertexBuilder glassesBuilder = ItemRenderer.getBuffer(renderTypeBuffer, angelasGlasses.getRenderType(angelasThingsTexture), false, stack.hasEffect());
                        angelasGlasses.render(matrixStack, glassesBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                        matrixStack.pop();
                    }
                    
                    if (uuid.equals(UUID.fromString("85175c8f-4f71-4ebd-9f3a-96dfd8e8e390")))
                    {
                        matrixStack.push();
                        if (cherrysBracelets == null)
                        {
                            cherrysBracelets = new ModelCherrysBracelets<>();
                        }
                        cherrysBracelets.setRotationAngles(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                        cherrysBracelets.setLivingAnimations(livingEntity, limbSwing, limbSwingAmount, partialTicks);
                        
                        RenderHelper.followBodyRotations(livingEntity, cherrysBracelets);
                        IVertexBuilder braceletBuilder = ItemRenderer.getBuffer(renderTypeBuffer, cherrysBracelets.getRenderType(cherrys_things_texture), false, false);
                        cherrysBracelets.render(matrixStack, braceletBuilder, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
                        matrixStack.pop();
                        
                        matrixStack.push();
                        if (cherrysGlasses == null)
                        {
                            cherrysGlasses = new ModelCherrysGlasses<>();
                        }
                        ICurio.RenderHelper.followHeadRotations(livingEntity, cherrysGlasses.glasses);
                        IVertexBuilder glassesBuilder = ItemRenderer.getBuffer(renderTypeBuffer, cherrysGlasses.getRenderType(cherrys_things_texture), false, stack.hasEffect());
                        cherrysGlasses.render(matrixStack, glassesBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                        matrixStack.pop();
                    }
                    
                    if (uuid.equals(UUID.fromString("42676e70-bb7d-4e85-8841-939ff2730cdb")))
                    {
                        matrixStack.push();
                        if (flowerFriend == null)
                        {
                            flowerFriend = new ModelFlowerFriend<>();
                        }
                        ICurio.RenderHelper.followHeadRotations(livingEntity, flowerFriend.flower);
                        IVertexBuilder flowerBuilder = ItemRenderer.getBuffer(renderTypeBuffer, flowerFriend.getRenderType(flowerFriendTexture), false, stack.hasEffect());
                        flowerFriend.render(matrixStack, flowerBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                        matrixStack.pop();
                    }
                    
                    if (uuid.equals(UUID.fromString("86fadb8a-eabb-4914-a570-107f069a5de7")) || uuid.equals(UUID.fromString("09f34641-a0cd-4d13-827a-8dcad75e0daf")))
                    {
                        matrixStack.push();
                        if (jtsThings == null)
                        {
                            jtsThings = new ModelJTSThings<>();
                        }
                        ICurio.RenderHelper.followHeadRotations(livingEntity, jtsThings.theTHINGS);
                        IVertexBuilder jtBuilder = ItemRenderer.getBuffer(renderTypeBuffer, jtsThings.getRenderType(jtsThingsTexture), false, stack.hasEffect());
                        jtsThings.render(matrixStack, jtBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                        matrixStack.pop();
                    }
                    if (uuid.equals(UUID.fromString("4a4dc655-be1c-40cb-8ce0-a2618251d513")))
                    {
                        matrixStack.push();
                        if (ennysThings == null)
                        {
                            ennysThings = new ModelEnnysThings<>();
                        }
                        ICurio.RenderHelper.followHeadRotations(livingEntity, ennysThings.theTHINGS);
                        IVertexBuilder ennyBuilder = ItemRenderer.getBuffer(renderTypeBuffer, ennysThings.getRenderType(ennysThingsTexture), false, stack.hasEffect());
                        ennysThings.render(matrixStack, ennyBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                        matrixStack.pop();
                    }
                    if (uuid.equals(UUID.fromString("17dcd99c-aa61-408d-9057-7df3cf4f5295")))
                    {
                        matrixStack.push();
                        if (bucket == null)
                        {
                            bucket = new ModelMoistyBucket<>();
                        }
                        ICurio.RenderHelper.followHeadRotations(livingEntity, bucket.bucket);
                        IVertexBuilder bucketBuilder = ItemRenderer.getBuffer(renderTypeBuffer, bucket.getRenderType(bucketTexture), false, stack.hasEffect());
                        bucket.render(matrixStack, bucketBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                        matrixStack.pop();
                    }
                    if (uuid.equals(UUID.fromString("9a611e6c-44ab-473b-bd8b-f488d96fe654")))
                    {
                        matrixStack.push();
                        if (furryBabyFurryEars == null)
                        {
                            furryBabyFurryEars = new ModelFurryBabyFurryEars<>();
                        }
                        ICurio.RenderHelper.followHeadRotations(livingEntity, furryBabyFurryEars.ears);
                        IVertexBuilder furryBuilder = ItemRenderer.getBuffer(renderTypeBuffer, furryBabyFurryEars.getRenderType(furryBabyFurryTexture), false, stack.hasEffect());
                        furryBabyFurryEars.render(matrixStack, furryBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                        matrixStack.pop();
                        
                        matrixStack.push();
                        if (furryBabyFurryTail == null)
                        {
                            furryBabyFurryTail = new ModelFurryBabyFurryTail<>();
                        }
                        double xRotation = Math.sin(livingEntity.world.getGameTime() / 18f) * 4;
                        double yRotation = Math.sin(livingEntity.world.getGameTime() / 36f) * 12;
                        matrixStack.rotate(Vector3f.XP.rotationDegrees((float) xRotation));
                        matrixStack.rotate(Vector3f.YP.rotationDegrees((float) yRotation));
                        
                        furryBabyFurryTail.setRotationAngles(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                        furryBabyFurryTail.setLivingAnimations(livingEntity, limbSwing, limbSwingAmount, partialTicks);
                        
                        RenderHelper.followBodyRotations(livingEntity, furryBabyFurryTail);
                        RenderHelper.translateIfSneaking(matrixStack, livingEntity);
                        RenderHelper.rotateIfSneaking(matrixStack, livingEntity);
                        IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, furryBabyFurryTail.getRenderType(furryBabyFurryTexture), false, false);
                        furryBabyFurryTail.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
                        matrixStack.pop();
                    }
                }
            }
        });
    }
}