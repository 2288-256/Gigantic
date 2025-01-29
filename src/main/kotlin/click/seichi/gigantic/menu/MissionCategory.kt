package click.seichi.gigantic.menu

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.mission.Mission
import org.bukkit.entity.Player
import java.util.*

enum class MissionCategory(val menuTitle: LocalizedText) {
    DAILY(
        LocalizedText(
        Locale.JAPANESE to "デイリーミッション一覧"
    )
    ) {
        override fun isContain(player: Player, mission: Mission): Boolean {
            val missionMap = player.getOrPut(Keys.MISSION_MAP)
            return missionMap.values.any { it.missionId == mission.id }
        }
    },
    ;
    abstract fun isContain(player: Player, mission: Mission): Boolean
}