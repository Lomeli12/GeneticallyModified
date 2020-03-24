package net.lomeli.genemod.util;

import net.lomeli.genemod.GeneticallyModified;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class NBTUtil {
    private static final String GENE_TAG = GeneticallyModified.MOD_ID + ":genetic_information";

    public static CompoundNBT getItemCompoundTag(ItemStack stack, String tagName) {
        return stack.getTag() != null && stack.getTag().contains(tagName, 10) ?
                stack.getTag().getCompound(tagName) : new CompoundNBT();
    }

    public static void setItemCompoundTag(ItemStack stack, CompoundNBT tag) {
        stack.setTag(tag);
    }

    public static CompoundNBT getItemGeneTag(ItemStack stack) {
        return getItemCompoundTag(stack, GENE_TAG);
    }

    public static void saveItemGeneTag(ItemStack stack, CompoundNBT geneInfo) {
        if (stack.getTag() == null)
            stack.setTag(new CompoundNBT());
        stack.getTag().put(GENE_TAG, geneInfo);
    }
}
