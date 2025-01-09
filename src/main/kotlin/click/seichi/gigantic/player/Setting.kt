package click.seichi.gigantic.player

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.transform
import click.seichi.gigantic.message.LocalizedText
import org.bukkit.entity.Player
import java.util.*

enum class Setting(
    val id: Int,
    private val localizedName: LocalizedText,
    val default: Int,
    val range: Int,
    val category: ToggleSetting.Category
) {
    fun getName(locale: Locale) = localizedName.asSafety(locale)

    fun getToggle(player: Player) = player.getOrPut(Keys.SETTING_MAP.getValue(this))

    fun setValue(player: Player) = player.transform(Keys.SETTING_MAP.getValue(this)) { it }
}