package com.thehairydog.tmtrutil.item

import eu.pb4.polymer.core.api.item.SimplePolymerItem
import eu.pb4.polymer.resourcepack.api.PolymerModelData
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items

class TmItem(
    val tmName: String,
    settings: Properties = Properties().stacksTo(1) // default
) : SimplePolymerItem(settings, Items.PAPER) {

    private val modelData: PolymerModelData = PolymerResourcePackUtils.requestModel(
        Items.PAPER,
        ResourceLocation.fromNamespaceAndPath("simpletms", "item/$tmName")
    )

    override fun getPolymerCustomModelData(stack: ItemStack, player: ServerPlayer?): Int {
        return modelData.value()
    }
}


