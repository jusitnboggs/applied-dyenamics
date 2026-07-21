package lu.kolja.appdyenamics;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * Main mod class for AppDyenamics.
 */
@Mod(AppDyenamics.ID)
public class AppDyenamics {
    public static final String ID = "appdyenamics";

    public AppDyenamics() {
        // Register our item registry on the mod event bus
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    /**
     * Utility to create ResourceLocations within this mod's namespace.
     */
    public static ResourceLocation makeId(String path) {
        return new ResourceLocation(ID, path);
    }
}