package com.thehairydog.tmtrutil.registry

import com.thehairydog.tmtrutil.item.TmItem
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item

object ModItems {

    // Helper function
    fun register(name: String, item: Item): Item {
        return Registry.register(
            BuiltInRegistries.ITEM,
            ResourceLocation.fromNamespaceAndPath("simpletms", name),
            item
        )
    }

    fun registerAll() {
        println("Registering TMs for simpletms")

        // Register all TMs from TMItems
        TMItems.items.values.forEach { /* already registered via register() */ }
    }
}
