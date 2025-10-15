package lu.kolja.appdyenamics.mixin;

import appeng.api.config.Actionable;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import appeng.api.util.AEColor;
import appeng.block.networking.CableBusBlock;
import appeng.items.tools.powered.ColorApplicatorItem;
import appeng.me.cells.BasicCellInventory;
import appeng.me.helpers.BaseActionSource;
import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import cy.jdkdigital.dyenamics.common.item.DyenamicDyeItem;
import cy.jdkdigital.dyenamics.core.init.ItemInit;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;

import java.util.Locale;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ColorApplicatorItem.class, remap = false)
public abstract class MixinColorApplicatorItem {
    @Unique
    private static final BiMap<DyenamicDyeColor, DeferredHolder<Item, Item>> DYENAMIC_DYES = EnumHashBiMap.create(DyenamicDyeColor.class);

    @Inject(
            method = "getColorFrom",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void getColorFrom(AEKey key, CallbackInfoReturnable<AEColor> cir) {
        if (key instanceof AEItemKey itemKey) {
            Item item = itemKey.getItem();
            if (item instanceof DyenamicDyeItem dyeItem) {
                cir.setReturnValue(AEColor.valueOf(dyeItem.getDyeColor().getSerializedName().toUpperCase(Locale.ROOT)));
            }
        }
    }

    @Inject(
            method = "createFullColorApplicator",
            at = @At("RETURN")
    )
    private static void createFullColorApplicator(CallbackInfoReturnable<ItemStack> cir, @Local BasicCellInventory dyeStorage) {
        for (var dye : DYENAMIC_DYES.values()) {
            dyeStorage.insert(AEItemKey.of(dye.get()), 128L, Actionable.MODULATE, new BaseActionSource());
        }
    }

    @ModifyReturnValue(
            method = "getTotalTypes",
            at = @At("RETURN")
    )
    private static int getTotalTypes(int original) {
        return 45;
    }

    @Redirect(
            method = "recolourBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lappeng/block/networking/CableBusBlock;recolorBlock(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;Lnet/minecraft/world/item/DyeColor;Lnet/minecraft/world/entity/player/Player;)Z"
            )
    )
    private static boolean recolourBlock(CableBusBlock instance, BlockGetter level, BlockPos pos, Direction side, DyeColor color, Player who, @Local(argsOnly = true) AEColor newColor) {
        return ((AccessorCableBusBlock) instance).cableBusContainer(level, pos).recolourBlock(side, newColor, who);
    }

    @Inject(
            method = "<clinit>",
            at = @At("TAIL")
    )
    private static void init(CallbackInfo ci) {
        for (DyenamicDyeColor dye : DyenamicDyeColor.dyenamicValues()) {
            DYENAMIC_DYES.put(dye, ItemInit.DYE_ITEMS.get(dye.getSerializedName() + "_dye"));
        }
    }
}