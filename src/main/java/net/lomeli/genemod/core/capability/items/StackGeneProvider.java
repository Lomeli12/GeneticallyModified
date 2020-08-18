package net.lomeli.genemod.core.capability.items;

import net.lomeli.genemod.GeneticallyModified;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class StackGeneProvider implements ICapabilitySerializable<CompoundNBT> {
    public static final ResourceLocation ID = new ResourceLocation(GeneticallyModified.MOD_ID, "stack_genes");

    @CapabilityInject(IStackGene.class)
    public static final Capability<IStackGene> GENES = null;
    @SuppressWarnings({"ConstantConditions", "NullableProblems"})
    private final LazyOptional<IStackGene> INSTANCE = LazyOptional.of(GENES::getDefaultInstance);

    @Nonnull
    @Override
    @SuppressWarnings("ConstantConditions")
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == GENES ? INSTANCE.cast() : LazyOptional.empty();
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public CompoundNBT serializeNBT() {
        return (CompoundNBT) GENES.getStorage().writeNBT(GENES, INSTANCE.orElseThrow(() ->
                new IllegalArgumentException("LazyOptional must not be empty")), null);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void deserializeNBT(CompoundNBT nbt) {
        GENES.getStorage().readNBT(GENES, INSTANCE.orElseThrow(() ->
                new IllegalArgumentException("LazyOptional must not be empty")), null, nbt);
    }
}
