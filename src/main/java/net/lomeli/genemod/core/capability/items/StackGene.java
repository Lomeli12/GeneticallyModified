package net.lomeli.genemod.core.capability.items;

import com.google.common.collect.Maps;
import net.lomeli.genemod.core.genetics.GeneManager;
import net.lomeli.genemod.core.genetics.traits.ITrait;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Map;

public class StackGene implements IStackGene {
    private Map<ITrait, Float> traits;

    public StackGene() {
        traits = Maps.newHashMap();
    }

    @Override
    public void onFoodEaten(PlayerEntity player, ItemStack stack) {
        for (Map.Entry<ITrait, Float> entry : traits.entrySet())
            entry.getKey().onEaten(player, stack, entry.getValue());
    }

    @Override
    public void setTrait(ITrait trait, float efficacy) {
        if (trait != null)
            traits.put(trait, efficacy);
    }

    @Override
    public void setTrait(ResourceLocation trait, float efficacy) {
        setTrait(GeneManager.INSTANCE.getTrait(trait), efficacy);
    }

    @Override
    public Map<ITrait, Float> getStackTraits() {
        return traits;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        for (Map.Entry<ITrait, Float> entry : traits.entrySet())
            nbt.putFloat(entry.getKey().getTraitID().toString(), entry.getValue());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        for (String key : nbt.keySet()) {
            ITrait trait = GeneManager.INSTANCE.getTrait(new ResourceLocation(key));
            if (trait != null)
                traits.put(trait, nbt.getFloat(key));
        }
    }

    @SuppressWarnings("all")
    public static IStackGene getGenes(ItemStack stack) {
        if (stack == null || stack.isEmpty())
            return null;
        return stack.getCapability(StackGeneProvider.GENES)
                .orElseThrow(() -> new IllegalArgumentException("LazyOptional should not be empty!"));
    }
}
