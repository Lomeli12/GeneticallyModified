package net.lomeli.genemod.core.genetics;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.lomeli.genemod.core.genetics.traits.Hunger;
import net.lomeli.genemod.core.genetics.traits.ITrait;
import net.lomeli.genemod.core.genetics.traits.Saturation;

import java.util.HashMap;
import java.util.List;

public enum GeneManager {
    INSTANCE;

    private final HashMap<String, ITrait> registeredTraits;


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

    public ITrait getTrait(String id) {
        return registeredTraits.get(id);
    }

    public List<ITrait> generateRandomTraits() {
        List<ITrait> traits = Lists.newArrayList();

        traits.add(registeredTraits.get(Saturation.traitID));
        traits.add(registeredTraits.get(Hunger.traitID));
        //TODO: Add defaults

        //TODO: Randomly choose genes

        return traits;
    }
}
