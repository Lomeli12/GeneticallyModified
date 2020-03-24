package net.lomeli.genemod.core.handlers;

import net.lomeli.genemod.GeneticallyModified;
import net.lomeli.genemod.util.NBTUtil;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber(modid = GeneticallyModified.MOD_ID)
public class ItemEventHandler {
    @SubscribeEvent
    public static void itemEntityCreated(EntityJoinWorldEvent event) {
        if (!event.getEntity().world.isRemote && event.getEntity() instanceof ItemEntity) {
            ItemEntity entity = (ItemEntity) event.getEntity();
            ItemStack stack = entity.getItem();

            if (stack.isEmpty() || !stack.isFood())
                return;

            GeneHandler geneHandler = GeneHandler.getGeneInfo(stack);
            NBTUtil.saveItemGeneTag(stack, geneHandler.toNBT());
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

        Food food = stack.getItem().getFood();
        GeneHandler geneHandler = GeneHandler.getGeneInfo(stack);

        geneHandler.onFoodEaten(player, stack);

    }

    @SubscribeEvent
    public static void foodToolTip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (!stack.isFood())
            return;
        Food food = stack.getItem().getFood();
        GeneHandler geneHandler = GeneHandler.getGeneInfo(stack);
        event.getToolTip().add(new StringTextComponent("Traits:"));
        for (Map.Entry<String, Float> entry : geneHandler.getCropTraits().entrySet()) {
            float efficacy = entry.getValue() * 100;
            event.getToolTip().add(new StringTextComponent(" - " + entry.getKey() + ": " + efficacy + "%"));
        }
    }
}
