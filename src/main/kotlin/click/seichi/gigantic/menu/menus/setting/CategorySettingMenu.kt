package click.seichi.gigantic.menu.menus.setting

import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.item.items.menu.BackButton
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.menu.menus.SettingMenu
import click.seichi.gigantic.message.messages.menu.SettingMenuMessages
import click.seichi.gigantic.message.messages.menu.ToolSwitchMessages
import click.seichi.gigantic.player.Setting
import click.seichi.gigantic.player.ToggleSetting
import click.seichi.gigantic.sound.sounds.PlayerSounds
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.util.*

class CategorySettingMenu(private val category: ToggleSetting.Category) : Menu() {
    override val size: Int
        get() = 27

        override fun getTitle(player: Player): String {
        return when (category) {
            ToggleSetting.Category.DISPLAY -> SettingMenuMessages.DISPLAY_SETTINGS.asSafety(player.wrappedLocale)
            ToggleSetting.Category.FUNCTION -> SettingMenuMessages.FUNCTION_SETTINGS.asSafety(player.wrappedLocale)
            ToggleSetting.Category.NOTIFICATION -> SettingMenuMessages.NOTIFICATION_SETTINGS.asSafety(player.wrappedLocale)
        }
    }

    init {
        var slotNum = -1
        ToggleSetting.values()
            .filter { it.category == category }
            .forEachIndexed { index, setting ->
                slotNum++
                // 見栄えが悪いので18スロット以降にはボタンを置かない
                if (index >= 18) return@forEachIndexed

                registerButton(slotNum, object : Button {

                    override fun toShownItemStack(player: Player): ItemStack? {
                        val itemStack = if (setting.getToggle(player)) {
                            ItemStack(Material.ENDER_EYE)
                        } else {
                            ItemStack(Material.ENDER_PEARL)
                        }
                        return itemStack.apply {
                            setDisplayName(
                                "${ChatColor.WHITE}${ChatColor.BOLD}" +
                                    setting.getName(player.wrappedLocale) + ": " +
                                    if (setting.getToggle(player)) {
                                        "${ChatColor.GREEN}${ChatColor.BOLD}ON"
                                    } else {
                                        "${ChatColor.RED}${ChatColor.BOLD}OFF"
                                    }
                            )
                            setLore(ToolSwitchMessages.CLICK_TO_TOGGLE.asSafety(player.wrappedLocale))
                        }
                    }

                    val coolTimeSet = mutableSetOf<UUID>()

                    override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
                        val uniqueId = player.uniqueId
                        if (coolTimeSet.contains(uniqueId)) return false
                        coolTimeSet.add(uniqueId)
                        runTaskLater(5L) {
                            coolTimeSet.remove(uniqueId)
                        }
                        setting.toggle(player)
                        PlayerSounds.TOGGLE.playOnly(player)
                        reopen(player)
                        player.updateDisplay(applyMainHand = true, applyOffHand = true)
                        return true
                    }
                })
            }
        Setting.values()
            .filter { it.category == category }
            .forEachIndexed { index, setting ->
                slotNum++
                if (index >= 18) return@forEachIndexed
                registerButton(slotNum, object : Button {

                    override fun toShownItemStack(player: Player): ItemStack? {
                        val itemStack = ItemStack(Material.OAK_SIGN)
                        return itemStack.apply {
                            setDisplayName(
                                "${ChatColor.WHITE}${ChatColor.BOLD}" +
                                        setting.getName(player.wrappedLocale)
                            )
                            val lore = mutableListOf<String>()
                            lore.addAll(setting.getDescription(player))
                            setLore(*lore.toTypedArray())
                            addLore(ToolSwitchMessages.CLICK_TO_TOGGLE.asSafety(player.wrappedLocale))
                        }
                    }

                    val coolTimeSet = mutableSetOf<UUID>()

                    override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
                        val uniqueId = player.uniqueId
                        if (coolTimeSet.contains(uniqueId)) return false
                        coolTimeSet.add(uniqueId)
                        runTaskLater(5L) {
                            coolTimeSet.remove(uniqueId)
                        }
                        setting.rotateValue(player)
                        PlayerSounds.TOGGLE.playOnly(player)
                        reopen(player)
                        player.updateDisplay(applyMainHand = true, applyOffHand = true)
                        return true
                    }
                })
            }
        registerButton(18, BackButton(this, SettingMenu))
    }
}