package net.lomeli.genemod.core.handlers;

import com.google.common.collect.Maps;
import net.lomeli.genemod.core.genetics.GeneManager;
import net.lomeli.genemod.core.genetics.traits.ITrait;
import net.lomeli.genemod.util.NBTUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class GeneHandler {
    private static Random rand = new Random();
    private HashMap<ResourceLocation, Float> foodTraits = Maps.newHashMap();

    private GeneHandler() {
    }

    public void onFoodEaten(PlayerEntity player, ItemStack stack) {
        for (Map.Entry<ResourceLocation, Float> entry : foodTraits.entrySet()) {
            ITrait trait = GeneManager.INSTANCE.getTrait(entry.getKey());
            trait.onEaten(player, stack, entry.getValue());
        }
    }

    public Map<ResourceLocation, Float> getCropTraits() {
        return Collections.unmodifiableMap(foodTraits);
    }

    public void setTrait(ResourceLocation trait, float efficacy) {
        foodTraits.put(trait, efficacy);
    }

    public CompoundNBT toNBT() {
        CompoundNBT nbt = new CompoundNBT();
        for (Map.Entry<ResourceLocation, Float> entry : foodTraits.entrySet()) {
            nbt.putFloat(entry.getKey().toString(), entry.getValue());
        }

        return nbt;
    }

    public static GeneHandler getGeneInfo(ItemStack stack) {
        return getGeneInfo(stack, NBTUtil.getItemGeneTag(stack));
    }

    public static GeneHandler getGeneInfo(ItemStack stack, CompoundNBT nbt) {
        GeneHandler geneHandler = new GeneHandler();

        if (nbt.keySet().isEmpty()) {
            List<ITrait> traits = GeneManager.INSTANCE.generateRandomTraits(stack);
            for (ITrait gene : traits) {
                float efficacy = rand.nextFloat() * gene.maxNaturalEfficacy() + 0.01f;

                geneHandler.setTrait(gene.getTraitID(), efficacy);
            }
        } else {
            for (String key : nbt.keySet()) {
                ITrait gene = GeneManager.INSTANCE.getTrait(new ResourceLocation(key));
                if (gene == null)
                    continue;

                float efficacy = nbt.getFloat(key);
                if (GeneManager.INSTANCE.getTrait(new ResourceLocation(key)) != null)
                    geneHandler.setTrait(gene.getTraitID(), efficacy);
            }
        }

        return geneHandler;
    }
}
