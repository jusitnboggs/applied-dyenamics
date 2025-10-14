package lu.kolja.appdyenamics.mixin;

import appeng.block.networking.CableBusBlock;
import appeng.parts.ICableBusContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = CableBusBlock.class, remap = false)
public interface AccessorCableBusBlock {
    @Invoker("cb")
    ICableBusContainer cableBusContainer(BlockGetter level, BlockPos pos);
}
