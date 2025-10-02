package com.thehairydog.tmtrutil.registry

import com.thehairydog.tmtrutil.item.TmItem
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item

object ModItems {
    // Example: register one TM
    val TM_ABSORB = register("tm_absorb", TmItem("tm_absorb"))

    // Helper function
    private fun register(name: String, item: Item): Item {
        return Registry.register(
            BuiltInRegistries.ITEM,
            ResourceLocation.fromNamespaceAndPath("simpletms", name),
            item
        )
    }

    fun registerAll() {
        println("Registering TMs for simpletms")
        TM_ABSORB
    }
}
