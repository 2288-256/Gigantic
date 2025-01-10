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
    SEE_WILL_BOSSBAR(
        0, LocalizedText(
            Locale.JAPANESE to "意志の交感中表示"
        ), 0, 3, ToggleSetting.Category.FUNCTION,
        mapOf(
            0 to LocalizedText(
                Locale.JAPANESE to "表示しない"
            ),
            1 to LocalizedText(
                Locale.JAPANESE to "神友以外表示"
            ),
            2 to LocalizedText(
                Locale.JAPANESE to "すべて表示"
            )
        )
    ),
    MANA_HP_DISPLAY(
        1, LocalizedText(
            Locale.JAPANESE to "マナ回復・HP回復の表示"
        ), 0,4, ToggleSetting.Category.DISPLAY,
        mapOf(
            0 to LocalizedText(
                Locale.JAPANESE to "表示"
            ),
            1 to LocalizedText(
                Locale.JAPANESE to "マナ回復のみ非表示"
            ),
            2 to LocalizedText(
                Locale.JAPANESE to "HP回復のみ非表示"
            ),
            3 to LocalizedText(
                Locale.JAPANESE to "両方非表示"
            )
        )
    ),
    RELIC_GENERATION_VALUE(
        2, LocalizedText(
            Locale.JAPANESE to "レリックを一度に生成する量"
        ),0,4,ToggleSetting.Category.FUNCTION,
        mapOf(
            0 to LocalizedText(
                Locale.JAPANESE to "1個"
            ),
            1 to LocalizedText(
                Locale.JAPANESE to "10個"
            ),
            2 to LocalizedText(
                Locale.JAPANESE to "50個"
            ),
            3 to LocalizedText(
                Locale.JAPANESE to "100個"
            )
        )
    );
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