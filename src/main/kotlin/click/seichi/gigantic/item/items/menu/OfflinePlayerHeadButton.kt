package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.extension.setDisplayName
import click.seichi.gigantic.extension.setLore
import click.seichi.gigantic.head.Head
import click.seichi.gigantic.item.Button
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*

/**
 * @author tar0ss
 * @author kuroma6666
 */
class OfflinePlayerHeadButton(
        private val uniqueId: UUID,
        private val name: String,
        private val lore: String? = null
) : Button {

    override fun toShownItemStack(player: Player): ItemStack? {
        return Head.getOfflinePlayerHead(uniqueId).apply {
            setDisplayName(name)
            if(!lore.isNullOrEmpty()){
                setLore("${ChatColor.GRAY}"+lore)
            }
        }
    }

}
