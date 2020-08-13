package net.lomeli.genemod.core.handlers;

import net.lomeli.genemod.GeneticallyModified;
import net.lomeli.genemod.core.genetics.items.ItemTraitManager;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;

import java.util.function.Predicate;

@Mod.EventBusSubscriber(modid = GeneticallyModified.MOD_ID)
public class ResourceHandler implements ISelectiveResourceReloadListener {
    ResourceHandler() {
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
        GeneticallyModified.log.info("Reloading item gene info");
        ItemTraitManager.INSTANCE.loadInfo(resourceManager);
    }

    @SubscribeEvent
    public static void serverStart(FMLServerStartingEvent event) {
        IReloadableResourceManager manager = event.getServer().getResourceManager();
        manager.addReloadListener(new ResourceHandler());
        ItemTraitManager.INSTANCE.loadInfo(manager);
    }
}
