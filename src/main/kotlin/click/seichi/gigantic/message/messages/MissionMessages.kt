package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.ChatMessage
import click.seichi.gigantic.message.ChatMessageProtocol
import click.seichi.gigantic.message.LinedChatMessage
import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor
import java.util.*

object MissionMessages {

    val PREFIX = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}[ミッション]"
    )

    val MISSION_COMPLETE = { missionName: String ->
        LocalizedText(
            Locale.JAPANESE to "${PREFIX.asSafety(Locale.JAPANESE)} ${ChatColor.WHITE}ミッション「$missionName」を達成しました！"
        )
    }

}