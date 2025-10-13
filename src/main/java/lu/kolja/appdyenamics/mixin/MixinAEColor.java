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
    private static List<AEColor> expandedae$dyenamicColors;

    @Invoker("<init>")
    public static AEColor invokeInit(String name, int ordinal, String englishName, String translationKey, String registryPrefix, DyeColor dye,
                                     int blackVariant, int mediumVariant, int whiteVariant, int contrastTextColor) {
        throw new AssertionError();
    }

    @Unique
    private static AEColor expandedae$addVariant(String name, String englishName, String translationKey, String registryPrefix, DyeColor dye,
                                                 int blackVariant, int mediumVariant, int whiteVariant, int contrastTextColor) {
        if (expandedae$dyenamicColors == null) expandedae$dyenamicColors = new ArrayList<>();
        var ordinal = expandedae$dyenamicColors.isEmpty() ? 17 : expandedae$dyenamicColors.getLast().ordinal() + 1;
        return invokeInit(name, ordinal,
                englishName, translationKey, registryPrefix, dye, blackVariant, mediumVariant, whiteVariant, contrastTextColor);
    }

    @Unique
    private static void expandedae$init() {
        int[] colors = new int[DyenamicDyeColor.dyenamicValues().length];
        for (var color : DyenamicDyeColor.dyenamicValues()) {
            var name = color.getSerializedName();
            var colorcode = color.getColorValue();
            var variant = expandedae$addVariant(name.toUpperCase(Locale.ROOT),
                    name.substring(0, 1).toUpperCase(Locale.ROOT) + name.substring(1),
                    "gui.expandedae." + name,
                    name,
                    color.getVanillaColor(),
                    colorcode,
                    expandedae$lighten(colorcode, .2f),
                    expandedae$lighten(colorcode, .4f),
                    0x000000
            );
            colors[expandedae$dyenamicColors.size()] = colorcode;
            expandedae$dyenamicColors.add(variant);
        }
    }

    @ModifyReturnValue(
            method = "values()[Lappeng/api/util/AEColor;",
            at = @At("RETURN")
    )
    private static AEColor[] modifyValues(AEColor[] original) {
        if (expandedae$dyenamicColors == null) expandedae$init();
        return ArrayUtils.addAll(original, expandedae$dyenamicColors.toArray(new AEColor[0]));
    }
    @Unique
    private static int expandedae$lighten(int color, float factor) {
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

    /*
    @Unique
    private static final AEColor PEACH = addVariant("PEACH", "Peach", "gui.expandedae.peach", "peach", DyeColor.CYAN, 0x00ffff, 0x00e0e0, 0x008080, 0x000000);
    @Unique
    private static final AEColor AQUAMARINE = addVariant("AQUAMArINE", "Aquamarine", "gui.expandedae.aquamarine", "aquamarine", DyeColor.CYAN, 0x00ffff, 0x00e0e0, 0x008080, 0x000000);
    @Unique
    private static final AEColor FLUORESCENT = addVariant("FLUORESCENT", "Fluorescent", "gui.expandedae.fluorescent", "fluorescent", DyeColor.YELLOW, 0x00ffff, 0x00e0e0, 0x008080, 0x000000);
    @Unique
    private static final AEColor MINT = addVariant("MINT", "Mint", "gui.expandedae.mint", "mint", DyeColor.LIME, 0x00ffff, 0x00e0e0, 0x008080, 0x000000);
    @Unique
    private static final AEColor MAROON = addVariant("MAROON", "Maroon", "gui.expandedae.maroon", "maroon", DyeColor.RED, 0x00ffff, 0x00e0e0, 0x008080, 0x000000);
    @Unique
    private static final AEColor BUBBLEGUM = addVariant("BUBBLEGUM", "Bubblegum", "gui.expandedae.bubblegum", "bubblegum", DyeColor.PINK, 0x00ffff, 0x00e0e0, 0x008080, 0x000000);
    @Unique
    private static final AEColor LAVENDER = addVariant("LAVENDER", "Lavender", "gui.expandedae.lavender", "lavender", DyeColor.MAGENTA, 0x00ffff, 0x00e0e0, 0x008080, 0x000000);
    @Unique
    private static final AEColor PERSIMMON = addVariant("persimmon", "Persimmon", "gui.expandedae.persimmon", "persimmon", DyeColor.ORANGE, 0x00ffff, 0x00e0e0, 0x008080, 0x000000);
    @Unique
    private static final AEColor CHERENKOV = addVariant("CHERENKOV", "Cherenkov", "gui.expandedae.cherenkov", "cherenkov", DyeColor.LIGHT_BLUE, 0x00ffff, 0x00e0e0, 0x008080, 0x000000);
    @Unique
    private static final AEColor AMBER = addVariant("AMBER", "Amber", "gui.expandedae.amber", "amber", DyeColor.ORANGE, 0x00ffff, 0x00e0e0, 0x008080, 0x000000);
    @Unique
    private static final AEColor HONEY = addVariant("HONEY", "Honey", "gui.expandedae.honey", "honey", DyeColor.YELLOW, 0x00ffff, 0x00e0e0, 0x008080, 0x000000);
    @Unique
    private static final AEColor ULTRAMARINE = addVariant("ULTRAMARINE", "Ultramarine", "gui.expandedae.ultramarine", "ultramarine", DyeColor.BLUE, 0x00ffff, 0x00e0e0, 0x008080, 0x000000);
    @Unique
    private static final AEColor SPRING_GREEN = addVariant("SPRING_GREEN", "Spring Green", "gui.expandedae.spring_green", "spring_green", DyeColor.LIME, 0x00ffff, 0x00e0e0, 0x008080, 0x000000);
    @Unique
    private static final AEColor ROSE = addVariant("ROSE", "Rose", "gui.expandedae.rose", "rose", DyeColor.RED, 0x00ffff, 0x00e0e0, 0x008080, 0x000000);
    @Unique
    private static final AEColor NAVY = addVariant("NAVY", "Navy", "gui.expandedae.navy", "navy", DyeColor.BLUE, 0x00ffff, 0x00e0e0, 0x008080, 0x000000);
    @Unique
    private static final AEColor ICY_BLUE = addVariant("ICY_BLUE", "Icy Blue", "gui.expandedae.icy_blue", "icy_blue", DyeColor.BLUE, 0x00ffff, 0x00e0e0, 0x008080, 0x000000);
    @Unique
    private static final AEColor WINE = addVariant("WINE", "Wine", "gui.expandedae.wine", "wine", DyeColor.PURPLE, 0x00ffff, 0x00e0e0, 0x008080, 0x000000);
    @Unique
    private static final AEColor CONIFER = addVariant("CONIFER", "Conifer", "gui.expandedae.conifer", "conifer", DyeColor.LIME, 0x00ffff, 0x00e0e0, 0x008080, 0x000000);
    */
}
