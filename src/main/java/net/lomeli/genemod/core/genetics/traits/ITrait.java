package net.lomeli.genemod.core.genetics.traits;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public interface ITrait {
    String getTraitID();

    void onHarvest(float efficacy);

    void onEaten(PlayerEntity player, ItemStack stack, float efficacy);

    float maxEfficacy();

    float maxNaturalEfficacy();

    boolean defaultTrait();

    //TODO: Biome chance boost?

    ResourceLocation getUnlocalizedName();
}
