package click.seichi.gigantic.message.messages.menu

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.cache.RankingPlayerCacheMemory
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.config.PlayerLevelConfig
import click.seichi.gigantic.extension.hasAptitude
import click.seichi.gigantic.message.LinedChatMessage
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.ranking.Score
import click.seichi.gigantic.relic.Relic
import click.seichi.gigantic.will.Will
import click.seichi.gigantic.will.WillGrade
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

/**
 * @author tar0ss
 */
object ProfileMessages {
    val score = Score.values().first { it == Score.EXP }
    val ranking = Gigantic.RANKING_MAP[score]

    val TITLE = LocalizedText(
            Locale.JAPANESE to "プロフィール"
    )

    val PROFILE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.AQUA}プロフィール"
    )

    val UPDATE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}${ChatColor.UNDERLINE}クリックして更新"
    )

    val UPDATING = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}更新中....."
    )

    val PROFILE_LEVEL = { level: Int ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.GREEN}整地レベル: ${ChatColor.WHITE}$level"
        )
    }

    val PROFILE_EXP = { level: Int, exp: BigDecimal ->
        val isMax = level == PlayerLevelConfig.MAX
        if (isMax) {
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.GREEN}経験値: ${ChatColor.WHITE}${exp.setScale(0, RoundingMode.FLOOR)} / ${exp.setScale(0, RoundingMode.FLOOR)}"
            )
        } else {
            val expToNextLevel = PlayerLevelConfig.LEVEL_MAP[level + 1]
                    ?: PlayerLevelConfig.LEVEL_MAP[PlayerLevelConfig.MAX]!!
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.GREEN}経験値: ${ChatColor.WHITE}${exp.setScale(0, RoundingMode.FLOOR)} / ${expToNextLevel.setScale(0, RoundingMode.FLOOR)}"
            )
        }
    }
    val PROFILE_ABOVE_RANKING = { player: Player, value: BigDecimal ->
        val rank = ranking?.findRank(player.uniqueId)
        var abovePlayerName: String? = null
        var aboveDiff: Long? = null
        if (rank != null) {
            if (rank > 1) {
                val aboveRank = rank - 1
                val aboveUniqueId = ranking?.findUUID(aboveRank)
                if (aboveUniqueId != null) {
                    val abovePlayerCache = RankingPlayerCacheMemory.find(aboveUniqueId)
                    if (abovePlayerCache != null) {
                        abovePlayerName = abovePlayerCache.getOrPut(Keys.RANK_PLAYER_NAME)
                        val aboveValue = ranking?.findValue(aboveUniqueId)
                        if (aboveValue != null) {
                            aboveDiff = aboveValue - value.toLong()!!
                        }
                    }
                }
            }
        }
        when {
            rank == 1 -> LocalizedText(Locale.JAPANESE to "${ChatColor.GOLD}あなたは現在1位です！")
            rank == null || value == null || abovePlayerName == null || aboveDiff == null -> LocalizedText(Locale.JAPANESE to "${ChatColor.RED}ランキングエラー")
            aboveDiff < 0 -> LocalizedText(Locale.JAPANESE to "${ChatColor.RED}ランキングが変動しました。更新までお待ち下さい")
            else -> LocalizedText(Locale.JAPANESE to "${ChatColor.GREEN}${rank.minus(1)}位(${abovePlayerName})までとの差: ${ChatColor.WHITE}$aboveDiff")
        }
    }
    val PROFILE_BELOW_RANKING = { player: Player, value: BigDecimal ->
        val rank = ranking?.findRank(player.uniqueId)

        val isLastRank = rank != null && rank == ranking?.size
        var belowPlayerName: String? = null
        var belowDiff: Long? = null

        if (rank != null){
            val belowRank = rank + 1
            val belowUniqueId = ranking?.findUUID(belowRank)
            if (belowUniqueId != null) {
                val belowPlayerCache = RankingPlayerCacheMemory.find(belowUniqueId)
                if (belowPlayerCache != null) {
                    belowPlayerName = belowPlayerCache.getOrPut(Keys.RANK_PLAYER_NAME)
                    val belowValue = ranking?.findValue(belowUniqueId)
                    if (belowValue != null) {
                        belowDiff = value.toLong()!! - belowValue
                    }
                }
            }
        }

        when {
            isLastRank == null -> LocalizedText(Locale.JAPANESE to "${ChatColor.RED}ランキングエラー")
            isLastRank -> LocalizedText(Locale.JAPANESE to "${ChatColor.GRAY}あなたは現在最下位です...")
            rank == null || value == null || belowPlayerName == null || belowDiff == null -> LocalizedText(Locale.JAPANESE to "${ChatColor.RED}ランキングエラー")
            belowDiff < 0 -> LocalizedText(Locale.JAPANESE to "${ChatColor.RED}ランキングが変動しました。更新までお待ち下さい")
            else -> LocalizedText(Locale.JAPANESE to "${ChatColor.GREEN}${rank.plus(1)}位(${belowPlayerName})までとの差: ${ChatColor.WHITE}$belowDiff")
        }
    }

    val PROFILE_MANA = { mana: BigDecimal, maxMana: BigDecimal ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.GREEN}マナ: ${ChatColor.WHITE}${mana.setScale(1, RoundingMode.HALF_UP)} / ${maxMana.setScale(1, RoundingMode.HALF_UP)}"
        )
    }

    val PROFILE_MAX_COMBO = { maxCombo: Long ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.GREEN}最大コンボ数: ${ChatColor.WHITE}$maxCombo combo"
        )
    }

    val PROFILE_VOTE_POINT = { votePoint: Int ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.GREEN}累計投票数: ${ChatColor.WHITE}$votePoint"
        )
    }

    val PROFILE_POMME = { pomme: Int ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.GREEN}累計ポム: ${ChatColor.WHITE}$pomme"
        )
    }

    val PROFILE_DONATION = { donation: Int ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.GREEN}累計寄付額: ${ChatColor.WHITE}$donation"
        )
    }

    val PROFILE_RELIC_NUM = { num: Long ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.GREEN}累計レリック数: ${ChatColor.WHITE}${num}個"
        )
    }

    val PROFILE_RELIC_COMPLETE_RATIO = { type: Int ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.GREEN}コンプリート達成率: ${ChatColor.WHITE}$type/${Relic.values().size}"
        )
    }

    val PROFILE_WILL_APTITUDE_BASIC = { player: Player ->
        mutableListOf(
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.GREEN}適正意志"
                ),
                LocalizedText(
                        Locale.JAPANESE.let { locale ->
                            locale to Will.values()
                                    .filter { it.grade == WillGrade.BASIC }
                                    .filter { player.hasAptitude(it) }
                                    .sortedBy { it.displayPriority }
                                    .joinToString(" ") {
                                            "${it.chatColor}${ChatColor.BOLD}" + it.getName(locale)
                                    }
                        }
                )
        )
    }

    val PROFILE_WILL_APTITUDE_ADVANCED = { player: Player ->
        mutableListOf(
                LocalizedText(
                        Locale.JAPANESE.let { locale ->
                            locale to Will.values()
                                    .filter { it.grade == WillGrade.ADVANCED }
                                    .filter { player.hasAptitude(it) }
                                    .sortedBy { it.displayPriority }
                                    .joinToString(" ") {
                                        "${it.chatColor}${ChatColor.BOLD}" + it.getName(locale)
                                    }
                        }
                )
        )
    }

    val PROFILE_WILL_APTITUDE_SPECIAL = { player: Player ->
        mutableListOf(
                LocalizedText(
                        Locale.JAPANESE.let { locale ->
                            locale to Will.values()
                                    .filter { it.grade == WillGrade.SPECIAL }
                                    .filter { player.hasAptitude(it) }
                                    .sortedBy { it.displayPriority }
                                    .joinToString(" ") {
                                        "${it.chatColor}${ChatColor.BOLD}" + it.getName(locale)
                                    }
                        }
                )
        )
    }

}