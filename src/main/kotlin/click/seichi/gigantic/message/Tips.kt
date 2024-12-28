package click.seichi.gigantic.message

import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.config.Config
import click.seichi.gigantic.player.Defaults
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.util.*

/**
 * @author tar0ss
 */
enum class Tips(
    private val linedMessage: LinedChatMessage,
    private val sendingCondition: (Player) -> Boolean = { true }
) {
    COMBO(LinedChatMessage(ChatMessageProtocol.CHAT,
        LocalizedText(
            Locale.JAPANESE to Defaults.TIPS_PREFIX +
                    "${ChatColor.WHITE}" +
                    "コンボは" +
                    "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}" +
                    "${Config.SKILL_MINE_COMBO_CONTINUATION_SECONDS}秒" +
                    "${ChatColor.WHITE}" +
                    "経つと減少するぞ！"
            ), 2L), { Achievement.SKILL_MINE_COMBO.isGranted(it) }),
    ETHEL(LinedChatMessage(ChatMessageProtocol.CHAT,
        LocalizedText(
            Locale.JAPANESE to Defaults.TIPS_PREFIX +
                    "${ChatColor.WHITE}" +
                    "エーテルは" +
                    "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}" +
                    "100個" +
                    "${ChatColor.WHITE}" +
                    "集めるとレリックに変換できるぞ！"
            ), 2L), { Achievement.FIRST_WILL.isGranted(it) }),
    OPTIFINE(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                Locale.JAPANESE to Defaults.TIPS_PREFIX +
                        "${ChatColor.WHITE}" +
                        "OptiFineを導入すると大体の環境で動作が軽くなるぞ！" +
                        LinedChatMessage.NEW_LINE_SYMBOL +
                        "ダウンロード→${ChatColor.AQUA}" +
                        "https://optifine.net/downloads"
            ), 2L)),
    WIKI(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                Locale.JAPANESE to Defaults.TIPS_PREFIX +
                        "${ChatColor.WHITE}" +
                        "元祖整地鯖(春)非公式Wiki→" +
                        "${ChatColor.AQUA}" +
                        "https://springseichi.sokuhou.wiki/"
            ), 2L)),
    HOME(LinedChatMessage(ChatMessageProtocol.CHAT,
        LocalizedText(
            Locale.JAPANESE to Defaults.TIPS_PREFIX +
                    "${ChatColor.WHITE}" +
                    "ホーム機能でお気に入りの場所を登録(テレポートメニュー)！"
            ), 2L), { Achievement.TELEPORT_HOME.isGranted(it) }),
    ADMIN_CAUTION(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                Locale.JAPANESE to Defaults.TIPS_PREFIX +
                        "${ChatColor.WHITE}" +
                        "運営チームのなりすましに注意！"
            ), 2L)),
    CONTACT_US_2(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                Locale.JAPANESE to Defaults.TIPS_PREFIX +
                        "${ChatColor.WHITE}" +
                        "お問い合わせはこちらから->" +
                        "${ChatColor.AQUA}" +
                        "https://forms.gle/PMMiXbGrRATTVReP9"
                    ), 2L)),
    FRIEND(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                Locale.JAPANESE to Defaults.TIPS_PREFIX +
                        "${ChatColor.WHITE}" +
                        "相互フォローになれば，近くのブロックも掘れるようになるぞ!"
            ), 2L)),
    MUTE(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                Locale.JAPANESE to Defaults.TIPS_PREFIX +
                        "${ChatColor.WHITE}" +
                        "ミュート機能で快適整地ライフフォロー設定から利用できるぞ"
            ), 2L)),
    SETTINGS(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                Locale.JAPANESE to Defaults.TIPS_PREFIX +
                        "${ChatColor.WHITE}" +
                        "メニューの詳細設定→表示設定から不要な機能をオフにできるぞ！"
            ), 2L)),
    STUCK(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                Locale.JAPANESE to Defaults.TIPS_PREFIX +
                        "${ChatColor.WHITE}" +
                        "地形にハマったときは，テレポートメニューから初期スポーンへGO"
            ), 2L)),
    RELIC_INFO(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                Locale.JAPANESE to Defaults.TIPS_PREFIX +
                        "${ChatColor.WHITE}" +
                        "レリック一覧から最後にボーナスが適用されたレリックが見れる!!"
            ), 2L)),
    SERVER_MAP(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                Locale.JAPANESE to Defaults.TIPS_PREFIX +
                        "${ChatColor.WHITE}" +
                        "みんな大好きサーバマップ->" +
                        "${ChatColor.AQUA}" +
                        "https://map.seichi-haru.pgw.jp/"
            ), 2L)),
    CONTACT_US_1(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                Locale.JAPANESE to Defaults.TIPS_PREFIX +
                        "${ChatColor.WHITE}" +
                        "不具合報告や要望はこちら->" +
                        "${ChatColor.AQUA}" +
                        "https://forms.gle/PMMiXbGrRATTVReP9"
            ), 2L)),
    DISCORD(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                Locale.JAPANESE to Defaults.TIPS_PREFIX +
                        "${ChatColor.WHITE}" +
                        "非公式 整地鯖(春)Discord->" +
                        "${ChatColor.AQUA}" +
                        "https://s.seichi-haru.pgw.jp/discord"
            ), 2L)),
    RANKING_UPDATE_TIPS(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                Locale.JAPANESE to Defaults.TIPS_PREFIX +
                        "${ChatColor.WHITE}" +
                        "詳細設定からランキングの更新時に通知を受け取ることができるぞ！"
            ), 2L)),
    HIDE_TIPS(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                Locale.JAPANESE to Defaults.TIPS_PREFIX +
                        "${ChatColor.WHITE}" +
                        "メニューの詳細設定→通知設定からTIPSをオフにできるぞ！"
            ), 2L)),
    ;

    fun sendTo(player: Player) {
        if (sendingCondition(player))
            linedMessage.sendTo(player)
    }
}