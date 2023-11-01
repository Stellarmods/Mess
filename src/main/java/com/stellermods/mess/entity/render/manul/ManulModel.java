package com.stellermods.mess.entity.render.manul;

import com.stellermods.mess.Mess;
import com.stellermods.mess.entity.entity.ManulEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ManulModel extends GeoModel<ManulEntity> {
    @Override
    public ResourceLocation getModelResource(ManulEntity object) {
        return new ResourceLocation(Mess.MODID, "geo/manul.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ManulEntity object) {
        return new ResourceLocation(Mess.MODID, "textures/entity/manul.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ManulEntity animatable) {
        return new ResourceLocation(Mess.MODID, "animations/manul.animation.json");
    }
}
