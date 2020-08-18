package net.lomeli.genemod.core.genetics.traits;

import net.lomeli.genemod.GeneticallyModified;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class Hunger implements ITrait {
    public static final ResourceLocation traitID = new ResourceLocation(GeneticallyModified.MOD_ID, "hunger");

    @Override
    public ResourceLocation getTraitID() {
        return traitID;
    }

    @Override
    public void onHarvest(NonNullList<ItemStack> drops, float efficacy) {

    }

    @Override
    public void onEaten(PlayerEntity player, ItemStack stack, float efficacy) {
        Food food = stack.getItem().getFood();
        player.getFoodStats().addStats(-food.getHealing(), 0);
        int trueHunger = Math.round(food.getHealing() * efficacy);
        player.getFoodStats().addStats(trueHunger, 0);
    }

    @Override
    public float maxEfficacy() {
        return 2f;
    }

    @Override
    public float maxNaturalEfficacy() {
        return 0.1f;
    }
}
