package com.stellermods.mess.entity.render.manul;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stellermods.mess.Mess;
import com.stellermods.mess.entity.entity.ManulEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
public class ManulRender extends GeoEntityRenderer<ManulEntity> {
    public ManulRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ManulModel());
        this.shadowRadius = 0.4f;
    }

    @Override
    public ResourceLocation getTextureLocation(ManulEntity animatable) {
        return new ResourceLocation(Mess.MODID, "textures/entity/manul.png");
    }

    @Override
    public void render(ManulEntity animatable, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        if (animatable.isBaby()){
            poseStack.scale(0.7f, 0.7f, 0.7f);
        }
        super.render(animatable, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
