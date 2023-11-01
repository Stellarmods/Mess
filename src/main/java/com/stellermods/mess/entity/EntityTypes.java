package com.stellermods.mess.entity;

import com.stellermods.mess.Mess;
import com.stellermods.mess.entity.entity.ManulEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Marker;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityTypes {
    public static DeferredRegister<EntityType<?>> ENTITY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Mess.MODID);

    public static RegistryObject<EntityType<ManulEntity>> MANUL = ENTITY.register("manul", () ->
            EntityType.Builder.of(ManulEntity::new, MobCategory.CREATURE).
            sized(0.8f, 0.8f).
                    build(new ResourceLocation(Mess.MODID, "manul").toString()));
}
