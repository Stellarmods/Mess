package net.digitalpear.snifferiety.mixin;


import net.digitalpear.snifferiety.registry.SnifferSeedRegistry;
import net.digitalpear.snifferiety.util.RandomCollection;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Sniffer.class)
public abstract class SnifferSeedDropMixin extends Animal {

    protected abstract BlockPos getDigPos();

    protected SnifferSeedDropMixin(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
    }

    /*
        Add items to drop based on chosen block
     */
    @ModifyVariable(method = "dropSeed", at = @At("STORE"), ordinal = 0)
    private ItemStack getSeed(ItemStack itemStack){
        RandomCollection<Item> itemRandomCollection = new RandomCollection<>();
        BlockPos pos = getOnPos().below();

        /*
            Filter based on block that is being dug.
         */
        SnifferSeedRegistry.getSnifferDropMap().forEach((item, seedProperties) -> {
            if (SnifferSeedRegistry.willItemDropFromBlock(item, this.level().getBlockState(pos)) && SnifferSeedRegistry.isBiomeValid(item,level(), pos)) {
                itemRandomCollection.add(seedProperties.getWeight(), item);
            }
        });

        return new ItemStack(itemRandomCollection.next());
    }


    /*
        Check if block is diggable
     */
    @Inject(method = "canDig(Lnet/minecraft/core/BlockPos;)Z", at = @At("RETURN"), cancellable = true)
    private void injectMethod(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(SnifferSeedRegistry.checkDiggability(this.level(), pos));
    }
}