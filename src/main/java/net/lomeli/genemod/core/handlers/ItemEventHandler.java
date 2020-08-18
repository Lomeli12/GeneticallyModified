package net.lomeli.genemod.core.handlers;

import net.lomeli.genemod.GeneticallyModified;
import net.lomeli.genemod.core.capability.items.IStackGene;
import net.lomeli.genemod.core.capability.items.StackGene;
import net.lomeli.genemod.core.capability.items.StackGeneProvider;
import net.lomeli.genemod.core.genetics.GeneManager;
import net.lomeli.genemod.core.genetics.items.ItemGeneInfo;
import net.lomeli.genemod.core.genetics.items.ItemTraitManager;
import net.lomeli.genemod.core.genetics.traits.ITrait;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ForgeI18n;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Mod.EventBusSubscriber(modid = GeneticallyModified.MOD_ID)
public class ItemEventHandler {
    private static final Random rand = new Random();

    @SubscribeEvent
    @SuppressWarnings("ConstantConditions")
    public static void attachItemCapability(AttachCapabilitiesEvent<ItemStack> event) {
        ItemStack stack = event.getObject();
        if (stack.isEmpty())
            return;
        ItemGeneInfo traits = ItemTraitManager.INSTANCE.getGeneInfo(stack);
        if (traits != null && (traits.getDefaultTraits().length > 0 || traits.getPossibleTraits().length > 0))
            event.addCapability(StackGeneProvider.ID, new StackGeneProvider());
    }

    private static IStackGene getTraits(ItemStack stack) {
        IStackGene gene = StackGene.getGenes(stack);
        if (gene.getStackTraits().isEmpty()) {
            List<ITrait> traits = GeneManager.INSTANCE.generateRandomTraits(stack);
            for (ITrait trait : traits) {
                float efficacy = rand.nextFloat() * trait.maxNaturalEfficacy() + 0.01f;
                gene.setTrait(trait, efficacy);
            }
        }
        return gene;
    }

    @SubscribeEvent
    public static void itemEntityCreated(EntityJoinWorldEvent event) {
        if (!event.getWorld().isRemote() && event.getEntity() instanceof ItemEntity) {
            ItemEntity entity = (ItemEntity) event.getEntity();
            ItemStack stack = entity.getItem();

            IStackGene genes = getTraits(stack);
            //TODO: stuff??
        }
    }

    @SubscribeEvent
    public static void playerPicksUpItem(PlayerEvent.ItemPickupEvent event) {
        if (!event.getPlayer().world.isRemote()) {
            IStackGene genes = getTraits(event.getStack());
            //TODO: stuff??
        }
    }

    @SubscribeEvent
    public static void onFoodEaten(LivingEntityUseItemEvent.Finish event) {
        if (!(event.getEntityLiving() instanceof PlayerEntity))
            return;
        PlayerEntity player = (PlayerEntity) event.getEntityLiving();
        ItemStack stack = event.getItem();

        if (!stack.isFood())
            return;

        IStackGene genes = getTraits(stack);
        genes.onFoodEaten(player, stack);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void foodToolTip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (!stack.isFood())
            return;
        IStackGene genes = getTraits(stack);
        event.getToolTip().add(new StringTextComponent("Traits:"));
        for (Map.Entry<ITrait, Float> entry : genes.getStackTraits().entrySet()) {
            float efficacy = entry.getValue() * 100;
            event.getToolTip().add(new StringTextComponent(" - " +
                    ForgeI18n.parseMessage(entry.getKey().getUnlocalizedName()) + ": " + efficacy + "%"));
        }
    }
}
