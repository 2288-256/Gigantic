package click.seichi.gigantic.message.messages

import click.seichi.gigantic.config.Config
import click.seichi.gigantic.extension.toRainbow
import click.seichi.gigantic.message.ChatMessageProtocol
import click.seichi.gigantic.message.LinedChatMessage
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.TitleMessage
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object LoginMessages {

    val LOGIN_CHAT = LinedChatMessage(ChatMessageProtocol.CHAT,
        LocalizedText(
            Locale.JAPANESE to "${ChatColor.WHITE}" +
                    (1..53).joinToString("") { "-" } +
                    LinedChatMessage.NEW_LINE_SYMBOL +
                    "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}" +
                    "非公式整地鯖(春)" +
                    "${ChatColor.WHITE}" +
                    "へようこそ!" +
                    LinedChatMessage.NEW_LINE_SYMBOL +
                    "${ChatColor.AQUA}${ChatColor.BOLD}" +
                            "このサーバーは整地鯖(春)をGPL-3.0 licenseの条件でForkした"+
                    LinedChatMessage.NEW_LINE_SYMBOL +
                            "${ChatColor.RED}${ChatColor.BOLD}"+
                    "非公式サーバーです。" +
                    LinedChatMessage.NEW_LINE_SYMBOL +
                    "${ChatColor.WHITE}" +
                    "ソースコード: " +
                    "${ChatColor.YELLOW}" +
                    "https://s.seichi-haru.pgw.jp/github" +
                    LinedChatMessage.NEW_LINE_SYMBOL +
                    "${ChatColor.WHITE}" +
                    "実装予定の機能・発生中の不具合: " +
                    "${ChatColor.YELLOW}" +
                    "https://s.seichi-haru.pgw.jp/issues" +
                    LinedChatMessage.NEW_LINE_SYMBOL +
                    "${ChatColor.WHITE}" +
                    "リリースノート: " +
                    "${ChatColor.YELLOW}" +
                    "https://s.seichi-haru.pgw.jp/releases" +
                    LinedChatMessage.NEW_LINE_SYMBOL +
                    "${ChatColor.WHITE}" +
                    "お問い合わせフォーム(機能追加の要望・通報): " +
                    "${ChatColor.YELLOW}" +
                    "https://forms.gle/PMMiXbGrRATTVReP9" +
                    LinedChatMessage.NEW_LINE_SYMBOL +
                    "${ChatColor.WHITE}" +
                    "Discord: " +
                    "${ChatColor.YELLOW}" +
                    "https://s.seichi-haru.pgw.jp/discord" +
                    LinedChatMessage.NEW_LINE_SYMBOL +
                    "${ChatColor.WHITE}" +
                    "WebMap: " +
                    "${ChatColor.YELLOW}" +
                    "https://map.seichi-haru.pgw.jp" +
                    LinedChatMessage.NEW_LINE_SYMBOL +
                    LinedChatMessage.NEW_LINE_SYMBOL +
                    "${ChatColor.WHITE}" +
                    "※" +
                    "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}" +
                    "本サーバー 非公式整地鯖(春)" +
                    "${ChatColor.WHITE}" +
                    "に関するお問い合わせを" +
                    LinedChatMessage.NEW_LINE_SYMBOL +
                    "${ChatColor.GREEN}${ChatColor.BOLD}" +
                    "ギガンティック整地鯖・整地鯖(春)" +
                    "${ChatColor.WHITE}" +
                    "運営チームに" +
                    "${ChatColor.RED}${ChatColor.BOLD}"+
                    "行わないでください" +
                    LinedChatMessage.NEW_LINE_SYMBOL +
                    "${ChatColor.WHITE}" +
                    (1..53).joinToString("") { "-" }

        ))

    val LOGIN_TITLE = TitleMessage(
        title = LocalizedText(
            Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                    "非公式整地鯖(春)"
            ), subTitle = null)

    val EVENT_SAKURA = LinedChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
        Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                (1..53).joinToString("") { "-" } +
                LinedChatMessage.NEW_LINE_SYMBOL +
                "${ChatColor.WHITE}" +
                "初イベント " +
                "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}" +
                "春の整地祭り" +
                "${ChatColor.WHITE}" +
                " を開催中!!" +
                LinedChatMessage.NEW_LINE_SYMBOL +
                "${ChatColor.WHITE}" +
                "期間限定意志" +
                "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}" +
                " 桜の意志 " +
                "${ChatColor.WHITE}" +
                "を獲得して、特別なレリックを手に入れよう!!" +
                LinedChatMessage.NEW_LINE_SYMBOL +
                "${ChatColor.LIGHT_PURPLE}" +
                (1..53).joinToString("") { "-" }
    ), 0L)

    val EVENT_MIO = LinedChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
        Locale.JAPANESE to "${ChatColor.AQUA}" +
                (1..53).joinToString("") { "-" } +
                LinedChatMessage.NEW_LINE_SYMBOL +
                "${ChatColor.WHITE}" +
                "${ChatColor.AQUA}${ChatColor.BOLD}" +
                "夏の整地祭り" +
                "${ChatColor.WHITE}" +
                " を開催中!!" +
                LinedChatMessage.NEW_LINE_SYMBOL +
                "${ChatColor.WHITE}" +
                "期間限定意志" +
                "${ChatColor.AQUA}${ChatColor.BOLD}" +
                " 澪の意志 " +
                "${ChatColor.WHITE}" +
                "を獲得して、特別なレリックを手に入れよう!!" +
                LinedChatMessage.NEW_LINE_SYMBOL +
                "${ChatColor.AQUA}" +
                (1..53).joinToString("") { "-" }
    ), 0L)

    val EVENT_KAEDE = LinedChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
        Locale.JAPANESE to "${ChatColor.GOLD}" +
                (1..53).joinToString("") { "-" } +
                LinedChatMessage.NEW_LINE_SYMBOL +
                "${ChatColor.WHITE}" +
                "${ChatColor.GOLD}${ChatColor.BOLD}" +
                "秋の整地祭り" +
                "${ChatColor.WHITE}" +
                " を開催中!!" +
                LinedChatMessage.NEW_LINE_SYMBOL +
                "${ChatColor.WHITE}" +
                "期間限定意志" +
                "${ChatColor.GOLD}${ChatColor.BOLD}" +
                " 楓の意志 " +
                "${ChatColor.WHITE}" +
                "を獲得して、特別なレリックを手に入れよう!!" +
                LinedChatMessage.NEW_LINE_SYMBOL +
                "${ChatColor.GOLD}" +
                (1..53).joinToString("") { "-" }
    ), 0L)

    val EVENT_REI = LinedChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
        Locale.JAPANESE to "${ChatColor.AQUA}" +
                (1..53).joinToString("") { "-" } +
                LinedChatMessage.NEW_LINE_SYMBOL +
                "${ChatColor.WHITE}" +
                "${ChatColor.GOLD}${ChatColor.BOLD}" +
                "冬の整地祭り" +
                "${ChatColor.WHITE}" +
                " を開催中!!" +
                LinedChatMessage.NEW_LINE_SYMBOL +
                "${ChatColor.WHITE}" +
                "期間限定意志" +
                "${ChatColor.AQUA}${ChatColor.BOLD}" +
                " 玲の意志 " +
                "${ChatColor.WHITE}" +
                "を獲得して、特別なレリックを手に入れよう!!" +
                LinedChatMessage.NEW_LINE_SYMBOL +
                "${ChatColor.AQUA}" +
                (1..53).joinToString("") { "-" }
    ), 0L)

    val EVENT_JMS_KING = LinedChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE to (1..53).joinToString("") { "-" }.toRainbow() +
                    LinedChatMessage.NEW_LINE_SYMBOL +
                    "${ChatColor.WHITE}" +
                    "JMS おすすめサーバーリスト 1位達成記念イベント開催中!!" +
                    LinedChatMessage.NEW_LINE_SYMBOL +
                    "${ChatColor.WHITE}" +
                    "期間中にログインしたレベル20以上の全てのプレイヤーに" +
                    LinedChatMessage.NEW_LINE_SYMBOL +
                    "${ChatColor.WHITE}" +
                    "特別レリック" +
                    "${ChatColor.DARK_PURPLE}${ChatColor.BOLD}" +
                    " 魔王の盃 " +
                    "${ChatColor.WHITE}" +
                    "をプレゼント!!" +
                    LinedChatMessage.NEW_LINE_SYMBOL +
                    (1..53).joinToString("") { "-" }.toRainbow()
    ))

}