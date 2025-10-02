import com.cobblemon.mod.common.api.moves.BenchedMove
import com.cobblemon.mod.common.api.moves.Move
import com.cobblemon.mod.common.api.moves.MoveTemplate
import com.cobblemon.mod.common.api.moves.Moves
import com.cobblemon.mod.common.api.pokemon.moves.Learnset
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import com.cobblemon.mod.common.pokemon.Pokemon
import com.thehairydog.tmtrutil.item.TmItem
import net.fabricmc.fabric.api.event.player.UseItemCallback
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.item.ItemStack
import net.minecraft.network.chat.Component
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3

object ModEvents {

    fun register() {
        UseItemCallback.EVENT.register(UseItemCallback { player, world, hand ->
            val stack: ItemStack = player.getItemInHand(hand)
            val item = stack.item

            if (item is TmItem && !world.isClientSide) {
                val targetPokemon: Pokemon? = getPokemonPlayerIsTargeting(player as ServerPlayer)
                if (targetPokemon != null) {
                    applyTM(targetPokemon, player, item)
                    return@UseItemCallback InteractionResultHolder.success(stack)
                } else {
                    player.sendSystemMessage(Component.literal("No Pokémon targeted!"))
                    return@UseItemCallback InteractionResultHolder.fail(stack)
                }
            }

            InteractionResultHolder.pass(stack)
        })
    }

    private fun getPokemonPlayerIsTargeting(player: ServerPlayer, maxDistance: Double = 3.0): Pokemon? {
        val eyePos: Vec3 = player.eyePosition
        val lookVec: Vec3 = player.lookAngle
        val endVec: Vec3 = eyePos.add(lookVec.scale(maxDistance))
        val aabb: AABB = player.boundingBox.expandTowards(lookVec.scale(maxDistance)).inflate(1.0)

        val entities = player.serverLevel().getEntitiesOfClass(PokemonEntity::class.java, aabb) {
            it.isAlive && it.isPickable
        }

        var closestEntity: PokemonEntity? = null
        var closestDistance = Double.MAX_VALUE

        for (entity in entities) {
            val entityBox = entity.boundingBox.inflate(0.3)
            val hitOptional = entityBox.clip(eyePos, endVec)
            if (hitOptional.isPresent) {
                val hit = hitOptional.get()
                val distance = eyePos.distanceTo(hit)
                if (distance < closestDistance) {
                    closestDistance = distance
                    closestEntity = entity
                }
            }
        }
        return closestEntity?.pokemon
    }

    fun applyTM(pokemon: Pokemon, player: ServerPlayer, tmItem: TmItem) {
        val moveName = tmItem.tmName.removePrefix("tm_")
        val moveTemplate = Moves.getByName(moveName)

        if (moveTemplate == null) {
            player.sendSystemMessage(Component.translatable("Move $moveName not found. Report this to an admin."))
            return
        }

        // Check if Pokémon can learn the move via TM
        val learnSet: Learnset = pokemon.species.moves
        if (!learnSet.tmMoves.contains(moveTemplate)) {
            player.sendSystemMessage(Component.translatable("${pokemon.species.name} can't learn ${moveTemplate.name} via TM!"))
            return
        }

        // Teach the move
        pokemon.learnMoveSafely(moveTemplate)

        player.sendSystemMessage(Component.translatable("${pokemon.species.name} learned ${moveTemplate.name}!"))
    }

    fun Pokemon.learnMoveSafely(template: MoveTemplate) {
        // Already knows this move?
        if (this.allAccessibleMoves.contains(template)) return

        // Create a new Move instance with full PP
        val newMove = Move(template, template.maxPp)

        // Add to active moves if space exists, otherwise add to benched moves
        if (this.moveSet.hasSpace()) {
            this.moveSet.add(newMove)
        } else {
            val benchedMove = BenchedMove(template, 0)
            this.benchedMoves.add(benchedMove)
        }

        // Update MoveSet observables for syncing
        this.moveSet.update()
    }
}
