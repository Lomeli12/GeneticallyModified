package net.lomeli.genemod.core.genetics.items;

import com.google.common.collect.Lists;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.lomeli.genemod.core.genetics.GeneManager;
import net.lomeli.genemod.core.genetics.traits.ITrait;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.List;

@JsonAdapter(ItemGeneInfo.ItemGeneInfoAdapter.class)
public class ItemGeneInfo {
    private ResourceLocation item;
    private ITrait[] defaultTraits;
    private ITrait[] possibleTraits;

    public ItemGeneInfo(ResourceLocation item, ITrait[] defaultTraits, ITrait[] possibleTraits) {
        this.item = item;
        this.defaultTraits = defaultTraits;
        this.possibleTraits = possibleTraits;
    }

    public ResourceLocation getItem() {
        return item;
    }

    public ITrait[] getDefaultTraits() {
        return defaultTraits;
    }

    public ITrait[] getPossibleTraits() {
        return possibleTraits;
    }

    public static class ItemGeneInfoAdapter extends TypeAdapter<ItemGeneInfo> {
        @Override
        public void write(JsonWriter out, ItemGeneInfo value) throws IOException {
            out.beginObject();
            out.name("item").value(value.item.toString());

            out.name("defaultTraits");
            out.beginArray();
            for (ITrait trait : value.getDefaultTraits())
                out.value(trait.getTraitID().toString());
            out.endArray();

            out.name("possibleTraits");
            out.beginArray();
            for (ITrait trait : value.getDefaultTraits())
                out.value(trait.getTraitID().toString());
            out.endArray();

            out.endObject();
            out.close();
        }

        @Override
        public ItemGeneInfo read(JsonReader in) throws IOException {
            ResourceLocation itemID = null;
            List<ITrait> traits = Lists.newArrayList();
            List<ITrait> possible = Lists.newArrayList();

            in.beginObject();
            while (in.hasNext()) {
                switch (in.nextName()) {
                    case "item":
                        itemID = new ResourceLocation(in.nextString());
                        break;
                    case "defaultTraits":
                        in.beginArray();
                        while (in.hasNext()) {
                            ITrait trait = GeneManager.INSTANCE.getTrait(new ResourceLocation(in.nextString()));
                            if (trait != null)
                                traits.add(trait);
                        }
                        in.endArray();
                        break;
                    case "possibleTraits":
                        in.beginArray();
                        while (in.hasNext()) {
                            ITrait trait = GeneManager.INSTANCE.getTrait(new ResourceLocation(in.nextString()));
                            if (trait != null)
                                possible.add(trait);
                        }
                        in.endArray();
                        break;
                }
            }
            in.endObject();

            return new ItemGeneInfo(itemID, traits.toArray(new ITrait[0]), possible.toArray(new ITrait[0]));
        }
    }
}
