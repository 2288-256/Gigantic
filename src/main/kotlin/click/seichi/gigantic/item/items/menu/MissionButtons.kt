package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.config.Config
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.message.messages.menu.MissionMessages
import click.seichi.gigantic.mission.Mission
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.math.RoundingMode
import kotlin.math.ceil

object MissionButtons {
    val DAILY = object : Button {
        override fun toShownItemStack(player: Player): ItemStack {
            return itemStackOf(Material.BOOK) {
                setDisplayName(MissionMessages.DAILYTITLE.asSafety(player.wrappedLocale))
                val nowClearMission = player.getOrPut(Keys.MISSION_MAP).values.count { it.missionType == 1 && it.complete }
                setLore("${ChatColor.GREEN}ミッション達成率:$nowClearMission/${Config.MISSION_DAILY_AMOUNT}")
            }
        }
    }

    val MISSION: (Mission) -> Button = { mission: Mission ->
        object : Button {
            override fun toShownItemStack(player: Player): ItemStack {
                val missionMap = player.getOrPut(Keys.MISSION_MAP)
                val missionData = missionMap.values.first { it.missionId == mission.id }
                if (missionData.complete) {
                    return itemStackOf(Material.CHEST) {
                        setDisplayName("${ChatColor.WHITE}${ChatColor.BOLD}${ChatColor.UNDERLINE}${mission.getName(player.wrappedLocale) ?: "null"}")
                        clearLore()
                        val lore = mission.getLore(
                            player.wrappedLocale,
                            missionData.missionDifficulty,
                            missionData.missionReqSize,
                            missionData.missionReqBlock
                        )
                        addLore("${ChatColor.AQUA}目標:$lore")
                        addLore("${ChatColor.GRAY}進捗:${ChatColor.GOLD}達成済み")
                        addLore("${ChatColor.GRAY}報酬:${mission.getRewardAmount(missionData.missionDifficulty)}個の${mission.getRewardType().displayName}")
                        addLore("${ChatColor.UNDERLINE}クリックして報酬を受け取る")
                    }
                }else {
                    return itemStackOf(Material.PAPER) {
                        val progressData = missionData.progress.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
                        val progressDisplay = if (progressData.stripTrailingZeros().scale() <= 0) {
                            progressData.toInt()
                        } else {
                            progressData.toDouble()
                        }
                        val progress = ((progressDisplay.toDouble() / mission.getRequiredAmount(missionData.missionDifficulty)) * 100).toBigDecimal().setScale(1, RoundingMode.HALF_UP).toDouble()
                        setDisplayName(
                            "${ChatColor.WHITE}${ChatColor.BOLD}${ChatColor.UNDERLINE}${
                                mission.getName(
                                    player.wrappedLocale
                                ) ?: "null"
                            }${ChatColor.RESET}  ${ChatColor.GRAY}($progress%)"
                        )
                        clearLore()
                        val lore = mission.getLore(
                            player.wrappedLocale,
                            missionData.missionDifficulty,
                            missionData.missionReqSize,
                            missionData.missionReqBlock
                        )
                        addLore("${ChatColor.AQUA}目標:$lore")
                        addLore("${ChatColor.GRAY}進捗:$progressDisplay/${mission.getRequiredAmount(missionData.missionDifficulty)}")
                        addLore("${ChatColor.GRAY}報酬:${mission.getRewardAmount(missionData.missionDifficulty)}個の${mission.getRewardType().displayName}")
                    }
                }
            }
        }
    }
}