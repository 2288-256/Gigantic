package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.items.menu.SpecialThanksButtons
import click.seichi.gigantic.item.items.menu.OfflinePlayerHeadButton
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.BagMessages
import org.bukkit.entity.Player
import java.util.*

/**
 * @author tar0ss
 */
object SpecialThanksMenu : Menu() {

    override val size: Int
        get() = playerMap.size.minus(1).div(9).plus(1).coerceIn(1..6).times(9)

    override fun getTitle(player: Player): String {
        return BagMessages.SPECIAL_THANKS_TITLE.asSafety(player.wrappedLocale)
    }

    private val playerMap: Map<String, Pair<String, String>> = mapOf(
        "kuroma6666" to ("4e3fbdca-1158-4744-a9b9-c08caf97d254" to "開発協力"),
        "kora429" to ("58fcfaf8-41b1-4a7d-b506-1e322abbe1a7" to "不具合報告・機能提案"),
        "mend3141" to ("deda238b-64a3-4931-8a85-f55109a57bb1" to "不具合報告・機能提案"),
        "LevelingMS" to ("a1965397-bf44-4368-8330-d2f1dbc6de17" to "機能提案"),
        "kaerusan82433413" to ("9cec894e-9ae3-4a25-97c5-b7a6c55c1376" to "不具合報告"),
        "Ichinose_4HML" to ("d643195d-c5c1-4ddc-87ad-afa59b843e19" to "機能提案")
    )

    private val officialSpecialThanksIndex: Int
        get() = size - 1
    
    init {
        registerButton(officialSpecialThanksIndex, SpecialThanksButtons.OFFICIAL_SPECIAL_THANKS)
        var index = 0
        playerMap.forEach { (name, playerDisplayData) ->
            val (uuidString, lore) = playerDisplayData
            val uuid = UUID.fromString(uuidString) ?: return@forEach
            val button = OfflinePlayerHeadButton(uuid, name, lore)
            registerButton(index, button)
            index++
        }
    }
}