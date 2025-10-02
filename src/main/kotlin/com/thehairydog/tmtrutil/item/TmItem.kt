package com.thehairydog.tmtrutil.item

import com.cobblemon.mod.common.api.moves.Moves
import com.thehairydog.tmtrutil.util.ColourUtil
import eu.pb4.polymer.core.api.item.SimplePolymerItem
import eu.pb4.polymer.resourcepack.api.PolymerModelData
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils
import net.minecraft.core.HolderLookup
import net.minecraft.core.component.DataComponents
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.TextColor
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.component.ItemLore

class TmItem(
    val tmName: String,
    settings: Properties = Properties().stacksTo(1)
) : SimplePolymerItem(settings, Items.PAPER) {

    private val modelData: PolymerModelData = PolymerResourcePackUtils.requestModel(
        Items.PAPER,
        ResourceLocation.fromNamespaceAndPath("simpletms", "item/$tmName")
    )

    override fun getPolymerCustomModelData(stack: ItemStack, player: ServerPlayer?): Int {
        return modelData.value()
    }

    /**
     * Generates a bullet-point lore for this TM
     */
    fun generateLore(): List<Component> {
        val moveId = tmName.removePrefix("tm_")
        val move = Moves.getByNameOrDummy(moveId)
        if (move == null) {
            return listOf(Component.literal("• Unknown Move").withStyle { it.withColor(TextColor.fromRgb(0xFF5555)) })
        }

        val lore = mutableListOf<Component>()
        val grey = TextColor.fromRgb(0xAAAAAA)
        val white = TextColor.fromRgb(0xFFFFFF)

        // Helper for bullet point
        fun bullet(label: String, value: String, valueColor: TextColor): Component {
            val comp: MutableComponent = Component.literal("• ").withStyle { it.withColor(grey) }
                .append(Component.literal("$label: ").withStyle { it.withColor(white) })
                .append(Component.literal(value).withStyle { it.withColor(valueColor) })
            return comp
        }

        // Type
        val typeColor = ColourUtil.typeColors[move.elementalType.name.lowercase()] ?: white
        lore.add(bullet("Type", move.elementalType.name, typeColor))

        // Category
        val catColor = ColourUtil.categoryColors[move.damageCategory.name.lowercase()] ?: white
        lore.add(bullet("Category", move.damageCategory.name, catColor))

        // Power
        val powerValue = if (move.power.toInt() <= 0) "-" else move.power.toInt().toString()
        lore.add(bullet("Power", powerValue, ColourUtil.getPowerColor(move.power.toInt())))

        // Accuracy
        val accValue = if (move.accuracy < 0) "-" else move.accuracy.toInt().toString()
        lore.add(bullet("Accuracy", accValue, ColourUtil.getAccuracyColor(move.accuracy.toInt())))

        return lore
    }

    override fun getPolymerItemStack(
        itemStack: ItemStack?,
        tooltipType: TooltipFlag?,
        lookup: HolderLookup.Provider?,
        player: ServerPlayer?
    ): ItemStack? {
        val out = super.getPolymerItemStack(itemStack, tooltipType, lookup, player) ?: return null

        // Preserve custom name
        if (itemStack != null && itemStack.has(DataComponents.CUSTOM_NAME)) {
            out.set(DataComponents.CUSTOM_NAME, itemStack.get(DataComponents.CUSTOM_NAME))
        }

        // Add lore
        val loreList = generateLore()
        if (loreList.isNotEmpty()) {
            out.set(DataComponents.LORE, ItemLore(loreList))
        }

        return out
    }
}
