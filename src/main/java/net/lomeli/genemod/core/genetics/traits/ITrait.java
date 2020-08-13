package net.lomeli.genemod.core.genetics.traits;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public interface ITrait {
    ResourceLocation getTraitID();

    void onHarvest(NonNullList<ItemStack> drops, float efficacy);

    void onEaten(PlayerEntity player, ItemStack stack, float efficacy);

    float maxEfficacy();

    float maxNaturalEfficacy();

    //TODO: Biome chance boost?

    ResourceLocation getUnlocalizedName();
}
