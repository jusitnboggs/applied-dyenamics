// src/main/java/lu/kolja/appdyenamics/CableColor.java
package lu.kolja.appdyenamics;

import java.util.Locale;

public enum CableColor {
    AMBER,
    AQUAMARINE,
    BUBBLEGUM,
    CHERENKOV,
    CONIFER,
    FLUORESCENT,
    HONEY,
    ICY_BLUE,
    LAVENDER,
    MAROON,
    MINT,
    NAVY,
    PEACH,
    PERSIMMON,
    ROSE,
    SPRING_GREEN,
    ULTRAMARINE,
    WINE;

    /** registry‐safe name (lowercase, underscores) */
    public String getPath() {
        return name().toLowerCase(Locale.ROOT);
    }
}