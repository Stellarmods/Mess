package com.stellermods.mess.item;

import com.stellermods.mess.Mess;
import com.stellermods.mess.entity.EntityTypes;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Items {
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Mess.MODID);

    public static RegistryObject<Item> EGG = ITEMS.register("manul_spawn_egg", ()-> new ForgeSpawnEggItem(EntityTypes.MANUL,  0xD57E36, 0x1D0D00,
            new Item.Properties()));
}
