package net.lomeli.genemod.core.capability.items;

import net.lomeli.genemod.core.genetics.traits.ITrait;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Map;

public interface IStackGene extends INBTSerializable<CompoundNBT> {
    void onFoodEaten(PlayerEntity player, ItemStack stack);

    void setTrait(ITrait trait, float efficacy);

    void setTrait(ResourceLocation trait, float efficacy);

    Map<ITrait, Float> getStackTraits();
}
