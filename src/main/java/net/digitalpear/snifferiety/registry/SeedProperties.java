package net.digitalpear.snifferiety.registry;

import net.digitalpear.snifferiety.Snifferiety;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class SeedProperties {
    private int weight;
    private TagKey<Block> whitelist;
    private TagKey<Block> blacklist;
    public static final TagKey<Block> NOTHING = TagKey.create(ForgeRegistries.BLOCKS.getRegistryKey(), new ResourceLocation(Snifferiety.MOD_ID, "nothing"));

    public SeedProperties(int weight, TagKey<Block> blacklist, TagKey<Block> whitelist){
        requireNonNullAndAxisProperty(weight, "weight");
        requireNonNullAndAxisProperty(whitelist, "whitelist");
        requireNonNullAndAxisProperty(blacklist, "blacklist");

        this.weight = weight;
        this.whitelist = whitelist;
        this.blacklist = blacklist;
    }
    public SeedProperties(int weight, TagKey<Block> blacklist){
        requireNonNullAndAxisProperty(weight, "weight");
        requireNonNullAndAxisProperty(blacklist, "blacklist");

        this.weight = weight;
        this.whitelist = BlockTags.SNIFFER_DIGGABLE_BLOCK;
        this.blacklist = blacklist;
    }


    public SeedProperties(int weight){
        requireNonNullAndAxisProperty(weight, "weight");
        this.weight = weight;
        this.whitelist = BlockTags.SNIFFER_DIGGABLE_BLOCK;
        this.blacklist = NOTHING;
    }

    public int getWeight() {
        return weight;
    }

    public TagKey<Block> getBlacklist() {
        return blacklist;
    }

    public TagKey<Block> getWhitelist() {
        return whitelist;
    }

    private static void requireNonNullAndAxisProperty(TagKey<Block> tag, String name) {
        Objects.requireNonNull(tag, name + " cannot be null");
    }
    private static void requireNonNullAndAxisProperty(Integer weight, String name) {
        Objects.requireNonNull(weight, name + " cannot be null");
    }

    public void setBlacklist(TagKey<Block> blacklist) {
        this.blacklist = blacklist;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setWhitelist(TagKey<Block> whitelist) {
        this.whitelist = whitelist;
    }
}