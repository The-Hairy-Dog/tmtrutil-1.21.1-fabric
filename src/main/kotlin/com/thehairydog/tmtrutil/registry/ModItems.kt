package com.thehairydog.tmtrutil.registry

import com.thehairydog.tmtrutil.item.TmItem
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.item.CreativeModeTabs

object ModItems {
    val TM_ABSORB: Item = TmItem(
        Item.Properties().stacksTo(1),
        Items.PAPER // fallback appearance
    )

    fun registerAll() {
        register("tm_absorb", TM_ABSORB)

        // Show in creative tab
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register { entries ->
            entries.accept(TM_ABSORB)
        }
    }

    private fun register(name: String, item: Item) {
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath("simpletms", name), item)
    }
}
