package net.digitalpear.snifferiety.util;

import com.google.gson.*;
import net.digitalpear.snifferiety.Snifferiety;
import net.digitalpear.snifferiety.registry.SeedProperties;
import net.digitalpear.snifferiety.registry.SnifferSeedRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import org.antlr.runtime.debug.Profiler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/*
    As taken from https://github.com/0rc1nus/Galosphere-Main/blob/c89199e0847021582618b33e478d7357cb5a827f/src/main/java/net/orcinus/galosphere/crafting/LumiereReformingManager.java#L31

    Made by 0rc1nus.
 */

public class SnifferJsonReader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON_INSTANCE = (new GsonBuilder()).create();

    public SnifferJsonReader() {
        super(GSON_INSTANCE, "loot_tables/gameplay");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> p_10793_, ResourceManager manager, ProfilerFiller profiler) {
        ResourceLocation resourceLocation = Snifferiety.id("loot_tables/gameplay/sniffer_seeds.json");
        try {
            for (Resource iResource : manager.getResourceStack(resourceLocation)) {
                try (Reader reader = new BufferedReader(new InputStreamReader(iResource.open(), StandardCharsets.UTF_8))) {
                    JsonObject jsonObject = GsonHelper.fromJson(GSON_INSTANCE, reader, JsonObject.class);
                    if (jsonObject != null) {
                        JsonArray entryList = jsonObject.get("entries").getAsJsonArray();
                        for (JsonElement entry : entryList) {

                            SeedProperties seedProperties = new SeedProperties(70);

                            Item seed = ForgeRegistries.ITEMS.getValue(new ResourceLocation(entry.getAsJsonObject().get("seed").getAsString()));

                                /*
                                    Override default values if they are present in the entry.
                                 */
                            if (entry.getAsJsonObject().has("weight")){
                                seedProperties.setWeight(entry.getAsJsonObject().get("weight").getAsInt());
                            }
                            if (entry.getAsJsonObject().has("block_whitelist")){
                                List<String> whitelist = List.of(entry.getAsJsonObject().get("block_whitelist").getAsString().split(":"));
                                seedProperties.setWhitelist(TagKey.create(ForgeRegistries.BLOCKS.getRegistryKey(), new ResourceLocation(whitelist.get(0),whitelist.get(1))));
                            }
                            if (entry.getAsJsonObject().has("block_blacklist")){
                                List<String> blacklist = List.of(entry.getAsJsonObject().get("block_blacklist").getAsString().split(":"));
                                seedProperties.setBlacklist(TagKey.create(ForgeRegistries.BLOCKS.getRegistryKey(), new ResourceLocation(blacklist.get(0),blacklist.get(1))));
                            }

                            SnifferSeedRegistry.register(seed, seedProperties);

                                /*
                                    Add to biome blacklist and whitelist if values are present in the entry.
                                 */
                            if (entry.getAsJsonObject().has("biome_blacklist")){
                                List<String> blacklist = List.of(entry.getAsJsonObject().get("biome_blacklist").getAsString().split(":"));
                                SnifferSeedRegistry.registerBiomeBlacklist(seed, TagKey.create(ForgeRegistries.BIOMES.getRegistryKey(), new ResourceLocation(blacklist.get(0),blacklist.get(1))));
                            }
                            if (entry.getAsJsonObject().has("biome_whitelist")){
                                List<String> whitelist = List.of(entry.getAsJsonObject().get("biome_whitelist").getAsString().split(":"));
                                SnifferSeedRegistry.registerBiomeWhitelist(seed, TagKey.create(ForgeRegistries.BIOMES.getRegistryKey(), new ResourceLocation(whitelist.get(0),whitelist.get(1))));
                            }
                        }
                    }
                } catch (RuntimeException | IOException exception) {
                    Snifferiety.LOGGER.info("Couldn't read table list {} in data pack {}", resourceLocation, iResource.sourcePackId(), exception);
                }
            }
        } catch (NoSuchElementException exception) {
            Snifferiety.LOGGER.error("Couldn't read table from {}");
        }
    }

}
