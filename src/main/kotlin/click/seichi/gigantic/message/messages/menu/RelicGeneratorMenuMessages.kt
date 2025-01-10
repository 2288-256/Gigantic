package click.seichi.gigantic.message.messages.menu

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.relic.Relic
import click.seichi.gigantic.will.Will
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object RelicGeneratorMenuMessages {
    val TITLE = LocalizedText(
            Locale.JAPANESE to "レリック生成器"
    )

    val SELECT_ETHEL = LocalizedText(
            Locale.JAPANESE to "のエーテル"
    )

    val SELECTED = LocalizedText(
            Locale.JAPANESE to "選択中: "
    )

    val NULL_OF_ETHEL = LocalizedText(
            Locale.JAPANESE to "${ChatColor.RESET}" +
                    "${ChatColor.RED}" +
                    "エーテルを選択してください"
    )

    val LOST_OF_ETHEL = LocalizedText(
            Locale.JAPANESE to "${ChatColor.RESET}" +
                    "${ChatColor.RED}" +
                    "必要なエーテルが足りません"
    )

    val USE_ETHEL = { multiValue: Int ->
        LocalizedText(
            Locale.JAPANESE to "のエーテルを${Defaults.RELIC_GENERATOR_REQUIRE_ETHEL * multiValue}消費します"
        )
    }

    val GENERATE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.RESET}" +
                    "${ChatColor.GREEN}${ChatColor.BOLD}${ChatColor.UNDERLINE}" +
                    "生成する"
    )

    val GENERATED = LocalizedText(
            Locale.JAPANESE to "が生成されました"
    )

    val GENERATE_NOTIFICATION_SETTING_LORE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.RED}チャットに結果が送信されるのは複数個一度に作成した場合のみです。"
    )
    val GENERATE_MESSAGE_SIMPLE = { selectedWill: Will, amount: Int ->
        LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}[レリック] ${selectedWill.chatColor}${ChatColor.BOLD}${selectedWill.getName(it)}${ChatColor.RESET}${ChatColor.GREEN}のレリックを${amount}個生成しました。"
            }
        )
    }
    val GENERATE_MESSAGE_MORE_START = LocalizedText(
        Locale.JAPANESE to "${ChatColor.YELLOW}[レリック] ${ChatColor.WHITE}======== 生成したレリック ========"
    )
    val GENERATE_MESSAGE_MORE_END = LocalizedText(
        Locale.JAPANESE to "${ChatColor.YELLOW}[レリック] ${ChatColor.WHITE}============================="
    )
    val GENERATE_MESSAGE_DETAIL = { relic: Relic, amount: Int ->
        LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}[レリック] ${Will.findByRelic(relic)?.chatColor}${relic.getName(it)} ${ChatColor.GRAY}x$amount"
            }
        )
    }
}