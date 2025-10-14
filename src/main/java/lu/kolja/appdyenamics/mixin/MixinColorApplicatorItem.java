package lu.kolja.appdyenamics.mixin;

import appeng.api.config.Actionable;
import appeng.api.stacks.AEItemKey;
import appeng.api.util.AEColor;
import appeng.block.networking.CableBusBlock;
import appeng.items.tools.powered.ColorApplicatorItem;
import appeng.me.cells.BasicCellInventory;
import appeng.me.helpers.BaseActionSource;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import cy.jdkdigital.dyenamics.common.items.DyenamicDyeItem;
import cy.jdkdigital.dyenamics.core.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Locale;

@Mixin(value = ColorApplicatorItem.class, remap = false)
public abstract class MixinColorApplicatorItem {

    @Inject(
            method = "getColorFromItem(Lnet/minecraft/world/item/Item;)Lappeng/api/util/AEColor;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void getColorFrom(Item item, CallbackInfoReturnable<AEColor> cir) {
        if (item instanceof DyenamicDyeItem dyeItem) {
            cir.setReturnValue(AEColor.valueOf(dyeItem.getDyeColor().getSerializedName().toUpperCase(Locale.ROOT)));
        }
    }

    @Inject(
            method = "createFullColorApplicator",
            at = @At("RETURN")
    )
    private static void createFullColorApplicator(CallbackInfoReturnable<ItemStack> cir, @Local BasicCellInventory dyeStorage) {
        for (var dye : ItemInit.DYE_ITEMS.values()) {
            dyeStorage.insert(AEItemKey.of(dye.get()), 128, Actionable.MODULATE, new BaseActionSource());
        }
    }

    @ModifyReturnValue(
            method = "getTotalTypes",
            at = @At("RETURN")
    )
    private int getTotalTypes(int original) {
        return 45;
    }

    @Redirect(
            method = "recolourBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lappeng/block/networking/CableBusBlock;recolorBlock(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;Lnet/minecraft/world/item/DyeColor;Lnet/minecraft/world/entity/player/Player;)Z"
            )
    )
    private boolean recolourBlock(CableBusBlock instance, BlockGetter level, BlockPos pos, Direction side, DyeColor color, Player who, @Local(argsOnly = true) AEColor newColor) {
        return ((AccessorCableBusBlock) instance).cableBusContainer(level, pos).recolourBlock(side, newColor, who);
    }
}
