package lu.kolja.appdyenamics

import net.minecraft.resources.ResourceLocation
import net.neoforged.fml.common.Mod

@Mod(AppDyenamics.ID)
object AppDyenamics {
    const val ID = "appdyenamics"

    fun makeId(path: String) = ResourceLocation.fromNamespaceAndPath(ID, path)

    init {
    }
}
