package lu.kolja.appdyenamics;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Contract;

@Mod(AppDyenamics.ID)
public class AppDyenamics {
    public static final String ID = "appdyenamics";

    @Contract("_ -> new")
    public static ResourceLocation makeId(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }
}
