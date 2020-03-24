package net.lomeli.genemod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(GeneticallyModified.MOD_ID)
public class GeneticallyModified {
    public static final String MOD_ID = "genetically_modified";
    public static final String MOD_NAME = "Genetically Modified";

    public static boolean DEV_ENV = false;

    public static Logger log = LogManager.getLogger(MOD_NAME);


    public GeneticallyModified() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientInit);

        //TODO: configs
    }

    public void init(final FMLCommonSetupEvent event) {
        DEV_ENV = FMLLoader.getNameFunction("srg").isPresent();
        //TODO: Load the things
        //TODO: Proxys?
        //TODO: Packets?
    }

    public void clientInit(final FMLClientSetupEvent event) {
        //TODO: Load client things
        //TODO: Proxys?
    }

    public void processIMC(final InterModProcessEvent event) {
        //TODO: Process other mod messages
    }
}
