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

    private val playerMap: Map<String, String> = mapOf(
        // TODO:SpcialThanksをここに記述
        // "{MCID}" to "{UUID}",
    )

    private val officialSpecialThanksIndex: Int
        get() = size - 1
    
    init {
        registerButton(officialSpecialThanksIndex, SpecialThanksButtons.OFFICIAL_SPECIAL_THANKS)
        var index = 0
        playerMap.forEach { name, uuidString ->
            val uuid = UUID.fromString(uuidString) ?: return@forEach
            val button = OfflinePlayerHeadButton(uuid, name)
            registerButton(index, button)
            index++
        }
    }
}