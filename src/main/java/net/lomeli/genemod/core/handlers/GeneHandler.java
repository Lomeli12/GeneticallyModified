package net.lomeli.genemod.core.handlers;

import com.google.common.collect.Maps;
import net.lomeli.genemod.core.genetics.GeneManager;
import net.lomeli.genemod.core.genetics.traits.ITrait;
import net.lomeli.genemod.util.NBTUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import java.util.*;

public class GeneHandler {
    private static Random rand = new Random();
    private HashMap<String, Float> foodTraits = Maps.newHashMap();

    private GeneHandler() {
    }

    public void onFoodEaten(PlayerEntity player, ItemStack stack) {
        for (Map.Entry<String, Float> entry : foodTraits.entrySet()) {
            ITrait trait = GeneManager.INSTANCE.getTrait(entry.getKey());
            trait.onEaten(player, stack, entry.getValue());
        }
    }

    public Map<String, Float> getCropTraits() {
        return Collections.unmodifiableMap(foodTraits);
    }

    public void setTrait(String trait, float efficacy) {
        foodTraits.put(trait, efficacy);
    }

    public CompoundNBT toNBT() {
        CompoundNBT nbt = new CompoundNBT();
        for (Map.Entry<String, Float> entry : foodTraits.entrySet()) {
            nbt.putFloat(entry.getKey(), entry.getValue());
        }

        return nbt;
    }

    public static GeneHandler getGeneInfo(ItemStack stack) {
        return getGeneInfo(NBTUtil.getItemGeneTag(stack));
    }

    public static GeneHandler getGeneInfo(CompoundNBT nbt) {
        GeneHandler geneHandler = new GeneHandler();

        if (nbt.keySet().isEmpty()) {
            List<ITrait> traits = GeneManager.INSTANCE.generateRandomTraits();
            for (ITrait gene : traits) {
                float efficacy = rand.nextFloat() * gene.maxNaturalEfficacy();
                if (gene.defaultTrait() && efficacy == 0f) efficacy += 0.01f;

                if (efficacy == 0)
                    continue;

                geneHandler.setTrait(gene.getTraitID(), efficacy);
            }
        } else {
            for (String key : nbt.keySet()) {
                ITrait gene = GeneManager.INSTANCE.getTrait(key);
                if (gene == null)
                    continue;

                float efficacy = nbt.getFloat(key);
                if (GeneManager.INSTANCE.getTrait(key) != null)
                    geneHandler.setTrait(gene.getTraitID(), efficacy);
            }
        }

        return geneHandler;
    }
}
