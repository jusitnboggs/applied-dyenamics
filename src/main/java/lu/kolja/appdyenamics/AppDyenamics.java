package lu.kolja.appdyenamics;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.common.Mod;
import org.jetbrains.annotations.Contract;

@Mod(AppDyenamics.MODID)
public class AppDyenamics {
    public static final String MODID = "appdyenamics";

    @Contract("_ -> new")
    public static ResourceLocation makeId(String path) {
        return  ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
