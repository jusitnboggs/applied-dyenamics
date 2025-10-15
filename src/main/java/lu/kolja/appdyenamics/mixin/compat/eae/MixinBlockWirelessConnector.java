package lu.kolja.appdyenamics.mixin.compat.eae;

import com.glodblock.github.extendedae.common.blocks.BlockWirelessConnector;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = BlockWirelessConnector.class, remap = false)
public class MixinBlockWirelessConnector {
    @Mutable
    @Shadow
    @Final
    private static IntegerProperty COLOR;

    static {
        COLOR = IntegerProperty.create("color", 0, 34);
    }

    @ModifyExpressionValue(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;setValue(Lnet/minecraft/world/level/block/state/properties/Property;Ljava/lang/Comparable;)Ljava/lang/Object;",
                    ordinal = 1
            )
    )
    private static Object setColor(Object original) {
        return ((BlockState) original).setValue(COLOR, 34);
    }
}
