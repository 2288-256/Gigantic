package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.menus.*
import click.seichi.gigantic.message.messages.SpecialThanksMenuMessages
import click.seichi.gigantic.message.messages.MenuMessages
import click.seichi.gigantic.message.messages.BagMessages
import click.seichi.gigantic.sound.sounds.MenuSounds
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

object SpecialThanksButtons {
    val OFFICIAL_SPECIAL_THANKS = object : Button {

        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.MUSIC_DISC_13) {
                setDisplayName("${ChatColor.AQUA}${ChatColor.UNDERLINE}"
                        + SpecialThanksMenuMessages.UNOFFICAL_SPECIAL_THANKS_LORE.asSafety(player.wrappedLocale))
                clearLore()
                itemMeta = itemMeta?.apply {
                    addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    addItemFlags(ItemFlag.HIDE_ENCHANTS)
                    addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
                    addItemFlags(ItemFlag.HIDE_PLACED_ON)
                }
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            if (event.inventory.holder === OfficalSpecialThanksMenu) return false
            OfficalSpecialThanksMenu.open(player)
            return true
        }

    }
}