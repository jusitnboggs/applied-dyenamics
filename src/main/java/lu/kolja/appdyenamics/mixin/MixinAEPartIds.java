package lu.kolja.appdyenamics.mixin;

import appeng.api.ids.AEPartIds;
import appeng.api.util.AEColor;
import com.google.common.collect.ImmutableMap;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import lu.kolja.appdyenamics.AppDyenamics;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Mixin(value = AEPartIds.class, remap = false)
public abstract class MixinAEPartIds {

    @Mutable
    @Shadow @Final public static Map<AEColor, ResourceLocation> CABLE_SMART;

    @Mutable
    @Shadow @Final public static Map<AEColor, ResourceLocation> CABLE_GLASS;

    @Mutable
    @Shadow @Final public static Map<AEColor, ResourceLocation> CABLE_COVERED;

    @Mutable
    @Shadow @Final public static Map<AEColor, ResourceLocation> CABLE_DENSE_COVERED;

    @Mutable
    @Shadow @Final public static Map<AEColor, ResourceLocation> CABLE_DENSE_SMART;

    @Inject(
            method = "<clinit>",
            at = @At("TAIL")
    )
    private static void init(CallbackInfo ci) {
        var cable_glass = new HashMap<>(CABLE_GLASS);
        for (var color : DyenamicDyeColor.dyenamicValues()) {
            var name = color.getSerializedName().toUpperCase(Locale.ROOT);
            cable_glass.put(AEColor.valueOf(name), AppDyenamics.INSTANCE.makeId(color.getSerializedName() + "_glass_cable"));
        }
        CABLE_GLASS = ImmutableMap.copyOf(cable_glass);

        var cable_covered = new HashMap<>(CABLE_COVERED);
        for (var color : DyenamicDyeColor.dyenamicValues()) {
            var name = color.getSerializedName().toUpperCase(Locale.ROOT);
            cable_covered.put(AEColor.valueOf(name), AppDyenamics.INSTANCE.makeId(color.getSerializedName() + "_covered_cable"));
        }
        CABLE_COVERED = ImmutableMap.copyOf(cable_covered);

        var cable_smart = new HashMap<>(CABLE_SMART);
        for (var color : DyenamicDyeColor.dyenamicValues()) {
            var name = color.getSerializedName().toUpperCase(Locale.ROOT);
            cable_smart.put(AEColor.valueOf(name), AppDyenamics.INSTANCE.makeId(color.getSerializedName() + "_smart_cable"));
        }
        CABLE_SMART = ImmutableMap.copyOf(cable_smart);

        var cable_dense_covered = new HashMap<>(CABLE_DENSE_COVERED);
        for (var color : DyenamicDyeColor.dyenamicValues()) {
            var name = color.getSerializedName().toUpperCase(Locale.ROOT);
            cable_dense_covered.put(AEColor.valueOf(name), AppDyenamics.INSTANCE.makeId(color.getSerializedName() + "_covered_dense_cable"));
        }
        CABLE_DENSE_COVERED = ImmutableMap.copyOf(cable_dense_covered);

        var cable_dense_smart = new HashMap<>(CABLE_DENSE_SMART);
        for (var color : DyenamicDyeColor.dyenamicValues()) {
            var name = color.getSerializedName().toUpperCase(Locale.ROOT);
            cable_dense_smart.put(AEColor.valueOf(name), AppDyenamics.INSTANCE.makeId(color.getSerializedName() + "_smart_dense_cable"));
        }
        CABLE_DENSE_SMART = ImmutableMap.copyOf(cable_dense_smart);
    }
}
