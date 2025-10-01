package com.thehairydog.tmtrutil.item

import eu.pb4.polymer.core.api.item.PolymerItem
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

class TmItem(
    settings: Properties,
    private val fallback: Item // e.g. PAPER, BOOK, MUSIC_DISC
) : Item(settings), PolymerItem {
    override fun getPolymerItem(stack: ItemStack, player: ServerPlayer?): Item {
        // If client doesn’t load your resource pack, it will look like this item
        return fallback
    }
}
