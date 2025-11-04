// src/main/java/lu/kolja/appdyenamics/ModItems.java
package lu.kolja.appdyenamics;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.EnumMap;
import java.util.Map;

public class ModItems {
    // Use the AE2 namespace so tags "ae2:*" resolve
    public static final String AE2_MODID = "ae2";

    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, AE2_MODID);

    public static final Map<CableColor, RegistryObject<Item>> COVERED_CABLES      = new EnumMap<>(CableColor.class);
    public static final Map<CableColor, RegistryObject<Item>> COVERED_DENSE_CABLES = new EnumMap<>(CableColor.class);
    public static final Map<CableColor, RegistryObject<Item>> GLASS_CABLES        = new EnumMap<>(CableColor.class);
    public static final Map<CableColor, RegistryObject<Item>> SMART_CABLES        = new EnumMap<>(CableColor.class);
    public static final Map<CableColor, RegistryObject<Item>> SMART_DENSE_CABLES  = new EnumMap<>(CableColor.class);

    static {
        // Register the DeferredRegister to the mod event bus
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

        for (CableColor color : CableColor.values()) {
            String c = color.getPath();

            // covered_cable
            COVERED_CABLES.put(color, ITEMS.register(
                c + "_covered_cable",
                () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE))
            ));

            // covered_dense_cable
            COVERED_DENSE_CABLES.put(color, ITEMS.register(
                c + "_covered_dense_cable",
                () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE))
            ));

            // glass_cable
            GLASS_CABLES.put(color, ITEMS.register(
                c + "_glass_cable",
                () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE))
            ));

            // smart_cable
            SMART_CABLES.put(color, ITEMS.register(
                c + "_smart_cable",
                () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE))
            ));

            // smart_dense_cable
            SMART_DENSE_CABLES.put(color, ITEMS.register(
                c + "_smart_dense_cable",
                () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE))
            ));
        }
    }
}