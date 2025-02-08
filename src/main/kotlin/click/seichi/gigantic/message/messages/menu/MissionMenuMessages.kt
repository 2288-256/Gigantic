package click.seichi.gigantic.message.messages.menu

import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor
import java.util.*

/**
 * @author 2288-256
 */
object MissionMenuMessages {
    val DAILY_TITLE = LocalizedText(
            Locale.JAPANESE to "デイリーミッション"
    )
    val WEEKLY_TITLE = LocalizedText(
            Locale.JAPANESE to "ウィークリーミッション"
    )
    val MONTHLY_TITLE = LocalizedText(
            Locale.JAPANESE to "マンスリーミッション"
    )
    val SEASON_TITLE = LocalizedText(
            Locale.JAPANESE to "シーズンミッション"
    )
    val NO_RELEASE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.RED}近日実装"
    )
    val NO_UNLOCK = { unlockLevel: Int ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.RED}レベル${unlockLevel}で解放"
        )
    }
    val CATEGORY_TITLE_LORE = { num: Long, clearAmount: Int, allAmount: Int ->
        listOf(
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.GREEN}" +
                                "クリアしたミッション:" +
                                "${ChatColor.WHITE}" +
                                "${num}個"
                ),
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.GREEN}" +
                                "クリア達成率:" +
                                "${ChatColor.WHITE}" +
                                "$clearAmount/$allAmount"
                )
        )
    }

}