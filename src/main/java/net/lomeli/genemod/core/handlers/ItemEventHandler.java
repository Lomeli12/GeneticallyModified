package net.lomeli.genemod.core.handlers;

import net.lomeli.genemod.GeneticallyModified;
import net.lomeli.genemod.util.NBTUtil;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber(modid = GeneticallyModified.MOD_ID)
public class ItemEventHandler {

    private static GeneHandler giveFoodOrSeedsTraits(ItemStack stack) {
        if (stack.isEmpty() || !stack.isFood())
            return null;

        GeneHandler geneHandler = GeneHandler.getGeneInfo(stack);
        NBTUtil.saveItemGeneTag(stack, geneHandler.toNBT());
        return geneHandler;
    }

    @SubscribeEvent
    public static void itemEntityCreated(EntityJoinWorldEvent event) {
        if (!event.getWorld().isRemote() && event.getEntity() instanceof ItemEntity) {
            ItemEntity entity = (ItemEntity) event.getEntity();
            ItemStack stack = entity.getItem();

            GeneHandler handler = giveFoodOrSeedsTraits(stack);
            //TODO: stuff??
        }
    }

    @SubscribeEvent
    public static void playerPicksUpItem(PlayerEvent.ItemPickupEvent event) {
        if (!event.getPlayer().world.isRemote()) {
            GeneHandler handler = giveFoodOrSeedsTraits(event.getStack());
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

        GeneHandler geneHandler = GeneHandler.getGeneInfo(stack);
        geneHandler.onFoodEaten(player, stack);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void foodToolTip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (!stack.isFood())
            return;
        GeneHandler geneHandler = GeneHandler.getGeneInfo(stack);
        event.getToolTip().add(new StringTextComponent("Traits:"));
        for (Map.Entry<ResourceLocation, Float> entry : geneHandler.getCropTraits().entrySet()) {
            float efficacy = entry.getValue() * 100;
            event.getToolTip().add(new StringTextComponent(" - " + entry.getKey() + ": " + efficacy + "%"));
        }
    }
}
