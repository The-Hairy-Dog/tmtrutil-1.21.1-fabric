package com.thehairydog.tmtrutil.registry

import eu.pb4.polymer.core.api.item.PolymerItemGroupUtils
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items

object ModCreativeTabs {

    lateinit var TM_TAB: CreativeModeTab
    lateinit var TR_TAB: CreativeModeTab

    fun register() {
        TM_TAB = PolymerItemGroupUtils.builder()
            .icon { ItemStack(TMItems.items["tm_falseswipe"] ?: Items.PAPER) }
            .title(Component.translatable("itemGroup.simpletms.tms"))
            .displayItems { _, output ->
                TMItems.items.values.forEach { output.accept(it) }
            }
            .build()

        PolymerItemGroupUtils.registerPolymerItemGroup(
            ResourceLocation.fromNamespaceAndPath("simpletms", "tms"),
            TM_TAB
        )

        TR_TAB = PolymerItemGroupUtils.builder()
            .icon { ItemStack(TMItems.items.values.firstOrNull() ?: Items.PAPER) }
            .title(Component.translatable("itemGroup.simpletms.trs"))
            .displayItems { _, output ->
                // TR items later
            }
            .build()

        PolymerItemGroupUtils.registerPolymerItemGroup(
            ResourceLocation.fromNamespaceAndPath("simpletms", "trs"),
            TR_TAB
        )
    }

}
