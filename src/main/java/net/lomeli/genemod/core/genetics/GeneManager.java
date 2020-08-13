package net.lomeli.genemod.core.genetics;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.lomeli.genemod.core.genetics.items.ItemGeneInfo;
import net.lomeli.genemod.core.genetics.items.ItemTraitManager;
import net.lomeli.genemod.core.genetics.traits.Hunger;
import net.lomeli.genemod.core.genetics.traits.ITrait;
import net.lomeli.genemod.core.genetics.traits.Saturation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public enum GeneManager {
    INSTANCE;

    private final HashMap<ResourceLocation, ITrait> registeredTraits;

    GeneManager() {
        registeredTraits = Maps.newHashMap();
    }

    public void initTraits() {
        registerTrait(new Saturation());
        registerTrait(new Hunger());
    }

    //Possibly do API and expose this function?
    public void registerTrait(ITrait trait) {
        if (trait != null)
            registeredTraits.put(trait.getTraitID(), trait);
    }

    public ITrait getTrait(ResourceLocation id) {
        return registeredTraits.get(id);
    }

    public List<ITrait> generateRandomTraits(ItemStack stack) {
        List<ITrait> traits = Lists.newArrayList();

        ItemGeneInfo info = ItemTraitManager.INSTANCE.getGeneInfo(stack);
        if (info.getDefaultTraits().length > 0)
            Collections.addAll(traits, info.getDefaultTraits());

        if (info.getPossibleTraits().length > 0) {
            Random rand = new Random();
            if (rand.nextBoolean())
                traits.add(info.getPossibleTraits()[rand.nextInt(info.getPossibleTraits().length)]);
        }

        return traits;
    }
}
