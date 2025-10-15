package lu.kolja.appdyenamics.mixin;

import appeng.api.util.AEColor;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import net.minecraft.world.item.DyeColor;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Mixin(value = AEColor.class, remap = false, priority = 1)
public class MixinAEColor {

    @Unique
    private static List<AEColor> appdyenamics$dyenamicColors;

    @Invoker("<init>")
    public static AEColor invokeInit(String name, int ordinal, String englishName, String translationKey, String registryPrefix, DyeColor dye,
                                     int blackVariant, int mediumVariant, int whiteVariant, int contrastTextColor) {
        throw new AssertionError();
    }

    @Unique
    private static AEColor appdyenamics$addVariant(String name, String englishName, String translationKey, String registryPrefix, DyeColor dye,
                                                   int blackVariant, int mediumVariant, int whiteVariant, int contrastTextColor) {
        if (appdyenamics$dyenamicColors == null) appdyenamics$dyenamicColors = new ArrayList<>();
        var ordinal = appdyenamics$dyenamicColors.isEmpty() ? 17 : appdyenamics$dyenamicColors.getLast().ordinal() + 1;
        return invokeInit(name, ordinal,
                englishName, translationKey, registryPrefix, dye, blackVariant, mediumVariant, whiteVariant, contrastTextColor);
    }

    @Unique
    private static void appdyenamics$init() {
        for (var color : DyenamicDyeColor.dyenamicValues()) {
            var name = color.getSerializedName();
            var colorcode = color.getColorValue();
            var variant = appdyenamics$addVariant(name.toUpperCase(Locale.ROOT),
                    name.substring(0, 1).toUpperCase(Locale.ROOT) + name.substring(1),
                    "gui.appdyenamics." + name,
                    name,
                    color.getVanillaColor(),
                    colorcode,
                    appdyenamics$lighten(colorcode, .2f),
                    appdyenamics$lighten(colorcode, .4f),
                    0x000000
            );
            appdyenamics$dyenamicColors.add(variant);
        }
    }

    @ModifyReturnValue(
            method = "values()[Lappeng/api/util/AEColor;",
            at = @At("RETURN")
    )
    private static AEColor[] modifyValues(AEColor[] original) {
        if (appdyenamics$dyenamicColors == null) appdyenamics$init();
        return ArrayUtils.addAll(original, appdyenamics$dyenamicColors.toArray(new AEColor[0]));
    }
    @Unique
    private static int appdyenamics$lighten(int color, float factor) {
        factor = Math.max(0, Math.min(factor, 1));

        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        r += (int) ((255 - r) * factor);
        g += (int) ((255 - g) * factor);
        b += (int) ((255 - b) * factor);

        r = Math.min(255, r);
        g = Math.min(255, g);
        b = Math.min(255, b);

        return (r << 16) | (g << 8) | b;
    }
}
