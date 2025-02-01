package click.seichi.gigantic.mission

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.extension.wrappedLocale
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask
import java.math.RoundingMode

class ProgressBossBar(private val targetPlayer: Player? = null, val mission: Mission, private val missionData: MissionClient) : MissionProgress(){
    private var bossBar: BossBar? = null
    private var bossBarRemoveTask: BukkitTask? = null

    override val lifespan = 60 * 3L

    fun bossBarCreate() {
        val progressData = missionData.progress.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
        val progressDisplay = if (progressData.stripTrailingZeros().scale() <= 0) {
            progressData.toInt()
        } else {
            progressData.toDouble()
        }
        val willName = targetPlayer?.let {
            mission.getLore(
                it.wrappedLocale,
                missionData.missionDifficulty,
                missionData.missionReqSize,
                missionData.missionReqBlock
            )
        }
        if (bossBar == null) {
            bossBar = Bukkit.createBossBar(
                "${ChatColor.WHITE}目標:$willName",
                BarColor.YELLOW,
                BarStyle.SOLID
            )
            bossBar?.progress = progressDisplay.toDouble() / mission.getRequiredAmount(missionData.missionDifficulty)
            targetPlayer?.let { bossBar?.addPlayer(it) }
            // 3秒後にボスバーを消す
            bossBarRemoveTask = Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, Runnable {
                bossBar?.removeAll()
                bossBar = null
                remove()
            }, 60L)
        }
    }

    fun bossBarUpdate() {
        if (bossBarRemoveTask != null) {
            bossBarRemoveTask?.cancel()
            bossBarRemoveTask = null
        }
        val progressData = missionData.progress.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
        val progressDisplay = if (progressData.stripTrailingZeros().scale() <= 0) {
            progressData.toInt()
        } else {
            progressData.toDouble()
        }
        bossBar?.progress = progressDisplay.toDouble() / mission.getRequiredAmount(missionData.missionDifficulty)
        bossBarRemoveTask = Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, Runnable {
            bossBar?.removeAll()
            bossBar = null
            remove()
        }, 60L)
    }

    override fun onSpawn() {
        bossBarCreate()
    }

    override fun onUpdate() {
        bossBarUpdate()
    }

}