package net.digitalpear.snifferiety;

import net.digitalpear.snifferiety.registry.SeedProperties;
import net.digitalpear.snifferiety.registry.SnifferSeedRegistry;
import net.digitalpear.snifferiety.util.SnifferJsonReader;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Snifferiety {

    public static final String MOD_ID = "snifferiety";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static ResourceLocation id(String text) {
        return new ResourceLocation(MOD_ID, text);
    }



    @Override
    public void onInitialize() {

        /*
        Examples of usage


        //Cactus can be dug up from any blocks in the minecraft:sand tag.

        SnifferSeedRegistry.register(Blocks.CACTUS, new SeedProperties(70, SeedProperties.NOTHING, BlockTags.SAND));


        //Iron nuggets can be dug up from any block that can be mined with a shovel, but not in the minecraft:sand tag.

        SnifferSeedRegistry.register(Items.IRON_NUGGET, new SeedProperties(70, BlockTags.SAND, BlockTags.SHOVEL_MINEABLE));


        //Apples can be dug up from the default minecraft:sniffer_diggable_block tag, but only in jungle biomes.

        SnifferSeedRegistry.register(Blocks.APPLE, 70);

        SnifferSeedRegistry.registerBiomeFilter(Items.APPLE, BiomeTags.IS_JUNGLE);
         */

        SnifferSeedRegistry.register(Items.TORCHFLOWER_SEEDS, new SeedProperties(70));
        SnifferSeedRegistry.register(Items.PITCHER_POD, new SeedProperties(70));

        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new SnifferJsonReader());
    }
}
