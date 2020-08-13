package net.lomeli.genemod.core.genetics.items;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import net.lomeli.genemod.GeneticallyModified;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public enum ItemTraitManager {
    INSTANCE;

    private static final ResourceLocation DATA_FOLDER = new ResourceLocation(GeneticallyModified.MOD_ID, "geneinfo");
    private static final Gson GSON = new Gson();

    private final Map<ResourceLocation, ItemGeneInfo> itemTraitMap;

    ItemTraitManager() {
        itemTraitMap = Maps.newHashMap();
    }

    public boolean addItemTraitMap(ItemGeneInfo info) {
        if (info != null && info.getItem() != null && !itemTraitMap.containsKey(info.getItem())) {
            itemTraitMap.put(info.getItem(), info);
            return true;
        }
        return false;
    }

    public ItemGeneInfo getGeneInfo(ResourceLocation itemID) {
        return itemTraitMap.get(itemID);
    }

    public ItemGeneInfo getGeneInfo(ItemStack stack) {
        return getGeneInfo(stack.getStack());
    }

    public ItemGeneInfo getGeneInfo(Item item) {
        return getGeneInfo(item.getRegistryName());
    }

    public void loadInfo(IResourceManager manager) {
        itemTraitMap.clear();
        GeneticallyModified.log.debug("Attempting to read data files in {}", DATA_FOLDER.toString());
        manager.getAllResourceLocations(DATA_FOLDER.getPath(), (s -> s.endsWith(".json"))).forEach(res -> {
            GeneticallyModified.log.debug("Is {} a datapack?", res.toString());
            try {
                IResource source = manager.getResource(res);
                ItemGeneInfo geneInfo = GSON.fromJson(new InputStreamReader(source.getInputStream()),
                        ItemGeneInfo.class);
                if (addItemTraitMap(geneInfo))
                    GeneticallyModified.log.info("Added {} trait file", source.getLocation().toString());
                else
                    GeneticallyModified.log.info("Couldn't add {}. The item was probably already registered or " +
                            "malformed.", source.getLocation().toString());
            } catch (IOException e) {
                //noinspection PlaceholderCountMatchesArgumentCount
                GeneticallyModified.log.error("Failed to read {} trait file! Is it corrupted?", e, res.toString());
            }
        });
    }
}
