package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.config.Config
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.menus.MissionMenu.close
import click.seichi.gigantic.message.messages.MissionMessages
import click.seichi.gigantic.message.messages.menu.MissionMenuMessages
import click.seichi.gigantic.mission.Mission
import click.seichi.gigantic.relic.Relic
import click.seichi.gigantic.util.Random
import click.seichi.gigantic.will.Will
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.math.RoundingMode
import kotlin.random.asKotlinRandom

object MissionButtons {
    val DAILY = object : Button {
        override fun toShownItemStack(player: Player): ItemStack {
            return itemStackOf(Material.BOOK) {
                setDisplayName(MissionMenuMessages.DAILYTITLE.asSafety(player.wrappedLocale))
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
                if (missionData.rewardReceived) {
                    return itemStackOf(Material.BEDROCK) {
                        setDisplayName("${ChatColor.RED}[済] ${ChatColor.WHITE}${ChatColor.BOLD}${ChatColor.UNDERLINE}${mission.getName(player.wrappedLocale) ?: "null"}")
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
                        addLore("${ChatColor.RED}${ChatColor.UNDERLINE}報酬を受け取りました")
                    }
                }
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

            override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
                val missionMap = player.getOrPut(Keys.MISSION_MAP)
                val missionData = missionMap.values.firstOrNull { it.missionId == mission.id }
                if (missionData == null) {
                    player.sendMessage(MissionMessages.NO_MISSION_ERROR.asSafety(player.wrappedLocale))
                    close(player)
                    return false
                }
                if (missionData.rewardReceived){
                    player.sendMessage(MissionMessages.REWARD_ALREADY_RECEIVED_ERROR.asSafety(player.wrappedLocale))
                    close(player)
                    return false
                }
                if (missionData.complete) {
                    when (mission.getRewardType()) {
                        Mission.QuestRewardType.Ethel -> {
                            val rewardAmount = mission.getRewardAmount(missionData.missionDifficulty)
                            val getEthel = Will.values()
                                .filter { player.hasAptitude(it) }
                                .shuffled(Random.generator)
                                .take(1)
                                .toSet()
                            getEthel.forEach {
                                it.addEthel(player, rewardAmount.toLong())
                                info("hello")
                                player.sendMessage(MissionMessages.MISSION_REWARD_GET_ETHEL_ONE(it, rewardAmount).asSafety(player.wrappedLocale))
                            }
                        }
                        Mission.QuestRewardType.Relic -> {
                            val relicCountMap = mutableMapOf<Relic, Int>()
                            val rewardAmount = mission.getRewardAmount(missionData.missionDifficulty)
                            repeat(rewardAmount) {
                                val createRelic = Relic.values().random(Random.generator.asKotlinRandom())
                                info(createRelic.getName(player.wrappedLocale))
                                // レリックを付与
                                createRelic.dropTo(player)
                                // レリックを保存
                                player.offer(Keys.GENERATED_RELIC, createRelic)
                                createRelic.let { relic ->
                                    relicCountMap[relic] = relicCountMap.getOrDefault(relic, 0) + 1
                                }
                            }

                            val playerLocale = player.wrappedLocale
                            if (rewardAmount != 1) {
                                player.sendMessage(
                                    MissionMessages.MISSION_REWARD_GET_RELIC_MULTIPLE(rewardAmount)
                                        .asSafety(playerLocale)
                                )
                                player.sendMessage(MissionMessages.MISSION_REWARD_GET_RELIC_MORE_START.asSafety(playerLocale))
                                for ((relic, count) in relicCountMap) {
                                    player.sendMessage(
                                        MissionMessages.MISSION_REWARD_GET_RELIC_DETAIL(relic, count).asSafety(playerLocale)
                                    )
                                }
                                player.sendMessage(MissionMessages.MISSION_REWARD_GET_RELIC_MORE_END.asSafety(playerLocale))
                            }else{
                                player.sendMessage(
                                    MissionMessages.MISSION_REWARD_GET_RELIC_ONE(relicCountMap.keys.first())
                                        .asSafety(playerLocale)
                                )
                            }
                        }
                    }
                    missionData.rewardReceived = true
                    player.transform(Keys.MISSION_MAP) {
                        it.toMutableMap().apply {
                            put(missionData.missionId, missionData)
                        }
                    }
                    close(player)
                    return true
                }else{
                    return false
                }
            }
        }
    }
}