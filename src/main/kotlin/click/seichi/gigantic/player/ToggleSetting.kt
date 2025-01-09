package click.seichi.gigantic.player

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.transform
import click.seichi.gigantic.message.LocalizedText
import org.bukkit.entity.Player
import java.util.*

/**
 * @author tar0ss
 */
enum class ToggleSetting(
        val id: Int,
        private val localizedName: LocalizedText,
        val default: Boolean,
        val category: Category
) {
    GAIN_EXP(
        0, LocalizedText(
            Locale.JAPANESE to "獲得経験値表示"
        ), false, Category.DISPLAY
    ),
    UNDER_PLAYER(
        1, LocalizedText(
            Locale.JAPANESE to "低い位置のブロック破壊警告"
        ), true, Category.FUNCTION
    ),
    COMBO(
        2, LocalizedText(
            Locale.JAPANESE to "コンボ表示"
        ), true, Category.DISPLAY
    ),
    AUTORCH(
        3, LocalizedText(
            Locale.JAPANESE to "自動松明設置"
        ), true, Category.FUNCTION
    ),
    NIGHT_VISION(
        4, LocalizedText(
            Locale.JAPANESE to "暗視"
        ), true, Category.FUNCTION
    ),
    SEE_OTHER_WILL_SPIRIT(
        5, LocalizedText(
            Locale.JAPANESE to "他の人の意志の表示"
        ), true, Category.DISPLAY
    ),
    COMBO_POSITION_FIX(
        6, LocalizedText(
            Locale.JAPANESE to "コンボ表示位置の修正"
        ), true, Category.FUNCTION
    ),
    UPDATE_RANKING(
        7, LocalizedText(
            Locale.JAPANESE to "ランキングの更新通知"
        ), false, Category.NOTIFICATION
    ),
    MANA_HP_DISPLAY(
        8, LocalizedText(
            Locale.JAPANESE to "マナ回復・HP回復の表示"
        ), true, Category.DISPLAY
    ),
    SCOREBOARD_MANA(
        9, LocalizedText(
            Locale.JAPANESE to "スコアボードにマナの情報を表示"
        ), false, Category.DISPLAY
    ),
    SCOREBOARD_TOTAL_EXP(
        10, LocalizedText(
            Locale.JAPANESE to "スコアボードに総経験値量の情報を表示"
        ), false, Category.DISPLAY
    ),
    TIPS_NOTIFICATION(
        11, LocalizedText(
            Locale.JAPANESE to "TIPSの通知"
        ), true, Category.NOTIFICATION
    ),
    COMBO_RANKING_NOTIFICATION(
        12, LocalizedText(
            Locale.JAPANESE to "コンボランキングの通知"
        ), true, Category.NOTIFICATION
    ),
    ;

    fun getName(locale: Locale) = localizedName.asSafety(locale)

    fun getToggle(player: Player) = player.getOrPut(Keys.TOGGLE_SETTING_MAP.getValue(this))

    fun toggle(player: Player) = player.transform(Keys.TOGGLE_SETTING_MAP.getValue(this)) { !it }

    enum class Category {
        DISPLAY, FUNCTION, NOTIFICATION
    }
}