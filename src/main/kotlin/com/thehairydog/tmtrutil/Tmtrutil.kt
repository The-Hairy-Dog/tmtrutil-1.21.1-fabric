package com.thehairydog.tmtrutil

import com.thehairydog.tmtrutil.registry.ModCreativeTabs
import com.thehairydog.tmtrutil.registry.ModItems
import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object Tmtrutil : ModInitializer {
    private val logger = LoggerFactory.getLogger("tmtrutil")

    override fun onInitialize() {
        logger.info("Initialised TMs & TRs")


        ModItems.registerAll()
        ModEvents.register()
        ModCreativeTabs.register()

    }
}
