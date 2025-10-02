package com.thehairydog.tmtrutil

import com.cobblemon.mod.common.api.moves.Moves
import com.thehairydog.tmtrutil.registry.ModItems
import net.fabricmc.api.ModInitializer
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import org.slf4j.LoggerFactory

object Tmtrutil : ModInitializer {
    private val logger = LoggerFactory.getLogger("tmtrutil")

    override fun onInitialize() {
        logger.info("Initialised TMs & TRs")

        ModItems.registerAll()
        ModEvents.register()

    }
}
