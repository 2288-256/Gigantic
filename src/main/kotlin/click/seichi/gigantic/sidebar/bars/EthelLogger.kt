package click.seichi.gigantic.sidebar.bars

import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.ethel
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.sidebar.Log
import click.seichi.gigantic.sidebar.Logger
import click.seichi.gigantic.will.Will
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.math.RoundingMode
import java.util.*

/**
 * @author tar0ss
 */
object EthelLogger : Logger("ethel") {

    override fun canShow(player: Player): Boolean {
        return Achievement.FIRST_WILL.isGranted(player)
    }

    override fun getDeque(player: Player): Deque<Log> {
        return player.getOrPut(Keys.ETHEL_DEQUE)
    }

    override fun getTitle(player: Player): String {
        return "${ChatColor.GREEN}${ChatColor.BOLD}" +
                "エーテルログ"
    }

    fun add(player: Player, will: Will, amount: Long) {
        val all = player.ethel(will)
        val formattedAll = if (all > 99999) {
            "99.99k"
        } else if (all > 999) {
            "${(all / 1000.0).toBigDecimal().setScale(2, RoundingMode.HALF_UP)}k"
        } else {
            all.toString()
        }
        log(
            player, "${ChatColor.GREEN}" +
                    "${will.chatColor}${ChatColor.BOLD}" +
                    will.getName(player.wrappedLocale) +
                    "${ChatColor.GREEN}" +
                    "+$amount" +
                    "${ChatColor.DARK_GREEN}" +
                    "($formattedAll)"
        )
    }


    fun use(player: Player, will: Will, amount: Long) {
        val all = player.ethel(will)
        val formattedAll = if (all > 99999) {
            "99.99k"
        } else if (all > 999) {
            "${(all / 1000.0).toBigDecimal().setScale(2, RoundingMode.HALF_UP)}k"
        } else {
            all.toString()
        }
        log(
            player, "${ChatColor.RED}" +
                    "" +
                    "${will.chatColor}${ChatColor.BOLD}" +
                    will.getName(player.wrappedLocale) +
                    "${ChatColor.RED}" +
                    "-$amount" +
                    "${ChatColor.DARK_RED}" +
                    "($formattedAll)"
        )
    }
}