package net.digitalpear.snifferiety.registry;


import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SnifferSeedRegistry {
    private static final Logger LOGGER = LoggerFactory.getLogger(SnifferSeedRegistry.class);

    private static final Map<Item, SeedProperties> SNIFFER_DROP_MAP = new HashMap<>();
    private static final Map<Item, TagKey<Biome>> BIOME_WHITELIST_MAP = new HashMap<>();
    private static final Map<Item, TagKey<Biome>> BIOME_BLACKLIST_MAP = new HashMap<>();

    public static Map<Item, SeedProperties> getSnifferDropMap(){
        return SNIFFER_DROP_MAP;
    }

    public static Map<Item, TagKey<Biome>> getBiomeWhitelistMap() {
        return BIOME_WHITELIST_MAP;
    }

    public static Map<Item, TagKey<Biome>> getBiomeBlacklistMap() {
        return BIOME_BLACKLIST_MAP;
    }

    public static void registerBiomeWhitelist(Item seed, TagKey<Biome> biomes){
        if (BIOME_WHITELIST_MAP.containsKey(seed)){
            LOGGER.debug("Changed old biome whitelist value from {} to {} with {}", biomes, seed, biomes);
        }
        BIOME_WHITELIST_MAP.put(seed, biomes);
    }
    public static void registerBiomeBlacklist(Item seed, TagKey<Biome> biomes){
        if (BIOME_BLACKLIST_MAP.containsKey(seed)){
            LOGGER.debug("Changed old biome blacklist value from {} to {} with {}", biomes, seed, biomes);
        }
        BIOME_BLACKLIST_MAP.put(seed, biomes);
    }

    public static void register(Item seed, int weight) {
        register(seed, new SeedProperties(weight));
    }

    public static void register(Item seed, SeedProperties seedProperties) {
        requireNonNullAndAxisProperty(seed, "seed item");
        requireNonNullAndAxisProperty(seedProperties, "seed properties");


        if (SNIFFER_DROP_MAP.containsKey(seed)){
            Item old = getKey(SNIFFER_DROP_MAP, seed.asItem());
            SNIFFER_DROP_MAP.put(seed.asItem(), seedProperties);
            LOGGER.debug("Replaced old sniffing mapping from {} to {} with seed properties {}", old, seed, seedProperties.getWeight());
        }
        else{
            SNIFFER_DROP_MAP.put(seed.asItem(), seedProperties);
            LOGGER.debug("Set new sniffing mapping {} with seed properties {}.", seed, seedProperties.getWeight());
        }
    }

    private static void requireNonNullAndAxisProperty(SeedProperties seedProperties, String name) {
        Objects.requireNonNull(seedProperties, name + " cannot be null");
    }
    private static void requireNonNullAndAxisProperty(Item item, String name) {
        Objects.requireNonNull(item, name + " cannot be null");
    }
    private static Item getKey(Map<Item, SeedProperties> map, Item key)
    {
        if(map.containsKey(key)) {
            return key;
        }
        return null;
    }



    private static boolean checkWhitelist(Item seed, Level world, BlockPos pos){
        if (!BIOME_WHITELIST_MAP.containsKey(seed)){
            return true;
        }
        return world.getBiome(pos).is(BIOME_WHITELIST_MAP.get(seed));
    }
    private static boolean checkBlacklist(Item seed, Level world, BlockPos pos){
        if (!BIOME_BLACKLIST_MAP.containsKey(seed)){
            return true;
        }
        return !world.getBiome(pos).is(BIOME_BLACKLIST_MAP.get(seed));
    }
    public static boolean isBiomeValid(Item seed, Level world, BlockPos pos){
        return checkBlacklist(seed, world, pos) && checkWhitelist(seed, world, pos);
    }



    private static boolean checkBlockWhitelist(Item seed, BlockState state){
        if (getSnifferDropMap().get(seed).getWhitelist().location() == SeedProperties.NOTHING.location()){
            return true;
        }
        return state.is(getSnifferDropMap().get(seed).getWhitelist());
    }
    private static boolean checkBlockBlacklist(Item seed, BlockState state){
        if (getSnifferDropMap().get(seed).getBlacklist().location() == SeedProperties.NOTHING.location()){
            return true;
        }
        return !state.is(getSnifferDropMap().get(seed).getBlacklist());
    }
    public static boolean willItemDropFromBlock(Item seed, BlockState blockState) {
        return checkBlockWhitelist(seed, blockState) && checkBlockBlacklist(seed, blockState);
    }




    private static boolean checkBlockWhitelist(Item seed, Level world, BlockPos pos){
        if (getSnifferDropMap().get(seed).getWhitelist().location() == SeedProperties.NOTHING.location()){
            return true;
        }
        return world.getBlockState(pos).is(getSnifferDropMap().get(seed).getWhitelist());
    }
    private static boolean checkBlockBlacklist(Item seed, Level world, BlockPos pos){
        if (getSnifferDropMap().get(seed).getBlacklist().location() == SeedProperties.NOTHING.location()){
            return true;
        }
        return !world.getBlockState(pos).is(getSnifferDropMap().get(seed).getBlacklist());
    }
    public static boolean checkDiggability(Level world, BlockPos pos){
        if (!world.getBlockState(pos.above()).isAir()) {
            return false;
        }
        return SnifferSeedRegistry.getSnifferDropMap().keySet().stream().anyMatch(item -> checkBlockWhitelist(item, world, pos) && checkBlockBlacklist(item, world, pos));
    }
}
