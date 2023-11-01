package com.stellermods.mess;

import com.mojang.logging.LogUtils;
import com.stellermods.mess.entity.EntityTypes;
import com.stellermods.mess.item.Items;
import com.stellermods.mess.loot_tables.LootTables;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Mess.MODID)
public class Mess
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "mess";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace

    public Mess()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        Items.ITEMS.register(modEventBus);
        EntityTypes.ENTITY.register(modEventBus);
        LootTables.LOOT_MODIFIER_SERIALIZERS.register(modEventBus);

        // Register the commonSetup method for modloading

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }
}
