package net.lomeli.genemod.core.genetics.traits;

import net.lomeli.genemod.GeneticallyModified;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class Saturation implements ITrait {
    public static final ResourceLocation traitID = new ResourceLocation(GeneticallyModified.MOD_ID, "saturation");

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
        player.getFoodStats().addStats(0, -food.getSaturation());
        float trueSaturation = food.getSaturation() * efficacy;
        player.getFoodStats().addStats(0, trueSaturation);
    }

    @Override
    public float maxEfficacy() {
        return 2f;
    }

    @Override
    public float maxNaturalEfficacy() {
        return 0.1f;
    }

    @Override
    public ResourceLocation getUnlocalizedName() {
        return traitID;
    }
}
