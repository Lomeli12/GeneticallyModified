package net.lomeli.genemod.core.capability.items;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class StackGeneStorage implements Capability.IStorage<IStackGene>{
    @Nullable
    @Override
    public INBT writeNBT(Capability<IStackGene> capability, IStackGene instance, Direction side) {
        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<IStackGene> capability, IStackGene instance, Direction side, INBT nbt) {
        if (nbt instanceof CompoundNBT)
            instance.deserializeNBT((CompoundNBT) nbt);
    }
}
