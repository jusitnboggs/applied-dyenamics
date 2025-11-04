// src/main/java/lu/kolja/appdyenamics/AppDyenamics.java
package lu.kolja.appdyenamics;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.Contract;

/**
 * Main mod class for AppDyenamics.
 */
@Mod(AppDyenamics.ID)
public class AppDyenamics {
    public static final String ID = "appdyenamics";

    public AppDyenamics() {
        // Register our item registry on the mod event bus
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.ITEMS.register(modBus);
    }

    /**
     * Utility to create ResourceLocations within this mod's namespace.
     */
    @Contract("_ -> new")
    public static ResourceLocation makeId(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }
}