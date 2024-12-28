package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.item.items.menu.SettingButtons
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.menu.SettingMenuMessages
import click.seichi.gigantic.message.messages.menu.ToolSwitchMessages
import click.seichi.gigantic.player.ToggleSetting
import click.seichi.gigantic.sound.sounds.PlayerSounds
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.util.*

/**
 * @author tar0ss
 */
object SettingMenu : Menu() {

    override val size: Int
        get() = 27

    override fun getTitle(player: Player): String {
        return SettingMenuMessages.TITLE.asSafety(player.wrappedLocale)
    }

    init {
        // 設定のカテゴリー
        registerButton(11, SettingButtons.DISPLAY_SETTING)
        registerButton(13, SettingButtons.FUNCTION_SETTING)
        registerButton(15, SettingButtons.NOTIFICATION_SETTING)
        // ツール切り替え設定
        registerButton(26, SettingButtons.TOOL_SWITCH_SETTING)
        // テクスチャ切り替え設定
        // 権利関連が不明のため無効化
        // registerButton(15, SettingButtons.TEXTURE)
    }
}