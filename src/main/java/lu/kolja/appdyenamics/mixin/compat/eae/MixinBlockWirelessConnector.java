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
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BlockWirelessConnector.class, remap = false)
public class MixinBlockWirelessConnector {
    @Mutable
    @Final
    @Shadow(remap = false)
    private static IntegerProperty COLOR;

    @Inject(
            method = "<clinit>",
            at = @At("TAIL")
    )
    private static void init(CallbackInfo ci) {
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
