package lu.kolja.appdyenamics.mixin;

import appeng.api.util.AEColor;
import appeng.items.tools.powered.BlockRecolorer;
import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Locale;

import static com.google.common.collect.ImmutableBiMap.copyOf;

@Mixin(value = BlockRecolorer.class, remap = false)
public abstract class MixinBlockRecolorer {
    @Mutable
    @Shadow @Final private static BiMap<AEColor, Block> STAINED_GLASS_BY_COLOR;

    @Shadow @Final private static BiMap<AEColor, Block> WOOL_BY_COLOR;

    @Mutable
    @Shadow @Final private static BiMap<AEColor, Block> BANNER_BY_COLOR;

    @Mutable
    @Shadow @Final private static BiMap<AEColor, Block> WALL_BANNER_BY_COLOR;

    @Mutable
    @Shadow @Final private static BiMap<AEColor, Block> CARPET_BY_COLOR;

    @Mutable
    @Shadow @Final private static BiMap<AEColor, Block> TERRACOTTA_BY_COLOR;

    @Mutable
    @Shadow @Final private static BiMap<AEColor, Block> GLAZED_TERRACOTTA_BY_COLOR;

    @Mutable
    @Shadow @Final private static BiMap<AEColor, Block> CONCRETE_BY_COLOR;

    @Inject(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/google/common/collect/ImmutableList;of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Lcom/google/common/collect/ImmutableList;",
                    shift = At.Shift.BEFORE
            )
    )
    private static void init(CallbackInfo ci) {
        var stainedGlass = EnumHashBiMap.create(STAINED_GLASS_BY_COLOR);
        var stainedGlassPane = EnumHashBiMap.create(STAINED_GLASS_BY_COLOR);
        var wool = EnumHashBiMap.create(WOOL_BY_COLOR);
        var banner = EnumHashBiMap.create(BANNER_BY_COLOR);
        var wallBanner = EnumHashBiMap.create(WALL_BANNER_BY_COLOR);
        var carpet = EnumHashBiMap.create(CARPET_BY_COLOR);
        var terracotta = EnumHashBiMap.create(TERRACOTTA_BY_COLOR);
        var glazedTerracotta = EnumHashBiMap.create(GLAZED_TERRACOTTA_BY_COLOR);
        var concrete = EnumHashBiMap.create(CONCRETE_BY_COLOR);
        for (var color : DyenamicDyeColor.dyenamicValues()) {
            var name = color.getSerializedName().toUpperCase(Locale.ROOT);
            stainedGlass.put(AEColor.valueOf(name), BuiltInRegistries.BLOCK.get(appliedDyenamics$id(color.getSerializedName() + "_stained_glass")));
            stainedGlassPane.put(AEColor.valueOf(name), BuiltInRegistries.BLOCK.get(appliedDyenamics$id(color.getSerializedName() + "_stained_glass_pane")));
            wool.put(AEColor.valueOf(name), BuiltInRegistries.BLOCK.get(appliedDyenamics$id(color.getSerializedName() + "_wool")));
            banner.put(AEColor.valueOf(name), BuiltInRegistries.BLOCK.get(appliedDyenamics$id(color.getSerializedName() + "_banner")));
            wallBanner.put(AEColor.valueOf(name), BuiltInRegistries.BLOCK.get(appliedDyenamics$id(color.getSerializedName() + "_wall_banner")));
            carpet.put(AEColor.valueOf(name), BuiltInRegistries.BLOCK.get(appliedDyenamics$id(color.getSerializedName() + "_carpet")));
            terracotta.put(AEColor.valueOf(name), BuiltInRegistries.BLOCK.get(appliedDyenamics$id(color.getSerializedName() + "_terracotta")));
            glazedTerracotta.put(AEColor.valueOf(name), BuiltInRegistries.BLOCK.get(appliedDyenamics$id(color.getSerializedName() + "_glazed_terracotta")));
            concrete.put(AEColor.valueOf(name), BuiltInRegistries.BLOCK.get(appliedDyenamics$id(color.getSerializedName() + "_concrete")));
        }
        STAINED_GLASS_BY_COLOR = copyOf(stainedGlass);
        BANNER_BY_COLOR = copyOf(banner);
        WALL_BANNER_BY_COLOR = copyOf(wallBanner);
        CARPET_BY_COLOR = copyOf(carpet);
        TERRACOTTA_BY_COLOR = copyOf(terracotta);
        GLAZED_TERRACOTTA_BY_COLOR = copyOf(glazedTerracotta);
        CONCRETE_BY_COLOR = copyOf(concrete);
    }

    @Unique
    private static ResourceLocation appliedDyenamics$id(String path) {
        return ResourceLocation.fromNamespaceAndPath("dyenamics", path);
    }
}
