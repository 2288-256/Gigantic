package click.seichi.gigantic.player

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.transform
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.util.*

enum class Setting(
    val id: Int,
    private val localizedName: LocalizedText,
    val default: Int,
    val range: Int,
    val category: ToggleSetting.Category,
    private val descriptions: Map<Int, LocalizedText> = emptyMap()
) {
    fun getName(locale: Locale) = localizedName.asSafety(locale)

    fun getDescription(player: Player): List<String> {
        val value = getValue(player)
        return (0..range).map { i ->
            val text = descriptions[i]?.asSafety(player.wrappedLocale) ?: ""
            if (i == value) "${ChatColor.GREEN}▶ $text" else "${ChatColor.GRAY}   $text"
        }
    }

    fun getValue(player: Player) = player.getOrPut(Keys.SETTING_MAP.getValue(this))

    fun rotateValue(player: Player) = player.transform(Keys.SETTING_MAP.getValue(this)) { (it + 1) % range }
}