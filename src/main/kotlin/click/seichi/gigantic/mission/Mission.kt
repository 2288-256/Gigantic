package click.seichi.gigantic.mission

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.will.WillSize
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.messages.MissionMessages
import click.seichi.gigantic.player.ToggleSetting
import click.seichi.gigantic.sound.sounds.PlayerSounds
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import java.util.*

/**
 * @author 2288-256
 */
enum class Mission(
    val id: Int,
    private val localizedName: LocalizedText,
    private val localizedLore: (difficulty: Int, requestSize: Int?, requestBlockIndex: Int?) -> LocalizedText,
    private val requiredAmount: List<Int>,
    private val rewardType: QuestRewardType,
    private val rewardAmount: List<Int>
) {
    EXP(
        1,
        LocalizedText(Locale.JAPANESE to "expを取得する"),
        { difficulty, _, _ -> LocalizedText(Locale.JAPANESE to "${EXP.getRequiredAmount(difficulty)}exp以上取得すると達成") },
        listOf(20000, 50000, 100000),
        QuestRewardType.Relic,
        listOf(1, 1, 2)
    ),
    BLOCK_BREAK(
        2,
        LocalizedText(Locale.JAPANESE to "通常破壊をする"),
        { difficulty, _, _ ->
            LocalizedText(Locale.JAPANESE to "${BLOCK_BREAK.getRequiredAmount(difficulty)}ブロック通常破壊すると達成") },
        listOf(3000, 5000, 10000),
        QuestRewardType.Relic,
        listOf(1, 1, 2)
    ),
    BLOCK_BREAK_REQ_BLOCK(
        3,
        LocalizedText(Locale.JAPANESE to "特定のブロックを破壊する"),
        { difficulty, _, blockTypeIndex -> LocalizedText(Locale.JAPANESE to "${blockTypeIndex?.let { RequestBlockType.getDisplayName(blockTypeIndex) }}を${BLOCK_BREAK_REQ_BLOCK.getRequiredAmount(difficulty)}ブロック破壊すると達成") },
        listOf(1000, 2000, 3000),
        QuestRewardType.Relic,
        listOf(1, 1, 2)
    ),
//    COMBO(
//        4,
//        LocalizedText(Locale.JAPANESE to "コンボを維持する"),
//        { difficulty, _, _ -> listOf(
//        LocalizedText(Locale.JAPANESE to "${COMBO.getRequiredAmount(difficulty)}コンボ以上になると達成"),
//        ) },
//        listOf(1000, 5000, 10000),
//        QuestRewardType.Relic,
//        listOf(1, 2, 3)
//    ),
    WILL_GET(
        4,
        LocalizedText(Locale.JAPANESE to "意志を回収する"),
        { difficulty, _, _ ->  LocalizedText(Locale.JAPANESE to "${WILL_GET.getRequiredAmount(difficulty)}回意志を回収すると達成")},
        listOf(20, 50, 100),
        QuestRewardType.Relic,
        listOf(1, 1, 2)
    ),
    WILL_GET_REQ_SIZE(
        5,
        LocalizedText(Locale.JAPANESE to "特定のサイズの意志を回収する"),
        { difficulty, size, _ -> LocalizedText(Locale.JAPANESE to "${size?.let { RequestWillSize.getRequestSize(it)?.prefix?.asSafety(Locale.JAPANESE) + "サイズの" } ?: "普通サイズの"}意志を${WILL_GET_REQ_SIZE.getRequiredAmount(difficulty)}個回収すると達成")},
        listOf(10, 15, 30),
        QuestRewardType.Relic,
        listOf(1, 1, 2)
    ),
    RELIC_CREATE(
        6,
        LocalizedText(Locale.JAPANESE to "レリックを生成する"),
        { difficulty, _, _ -> LocalizedText(Locale.JAPANESE to "${RELIC_CREATE.getRequiredAmount(difficulty)}回レリックを生成すると達成")},
        listOf(10, 15, 25),
        QuestRewardType.Relic,
        listOf(1, 1, 2)
    ),
    ;

    companion object {
        fun updateMissionProgress(player: Player,mission: Mission, progressValue: Double) {
            val missionClient = player.getOrPut(Keys.MISSION_MAP).values.firstOrNull { it.missionId == mission.id }
            if (missionClient != null) {
                if (!missionClient.complete) {
                    val requiredAmount = mission.getRequiredAmount(missionClient.missionDifficulty)
                    missionClient.progress += progressValue
                    if (missionClient.progress >= requiredAmount) {
                        missionClient.complete = true
                        missionClient.progress = requiredAmount.toDouble()
                        PlayerSounds.LEVEL_UP.playOnly(player)
                        val locale = player.wrappedLocale
                        player.sendMessage(MissionMessages.MISSION_COMPLETE(mission.getName(locale)).asSafety(locale))
                    }
                    player.transform(Keys.MISSION_MAP) {
                        it.toMutableMap().apply {
                            put(missionClient.missionId, missionClient)
                        }
                    }
                    if (ToggleSetting.MISSION_PROGRESS.getToggle(player)) {
                        val missionProgressBar = ProgressBossBar(player, mission, missionClient)
                        MissionProgressManager.spawn(mission,missionProgressBar)
                    }
                }
            }
        }
    }

    enum class RequestBlockType(val material: Material, val displayName: String) {
        GRAVEL(Material.GRAVEL, "砂利"),
        SAND(Material.SAND, "砂"),
        CLAY(Material.CLAY, "粘土"),
        GRASS(Material.GRASS, "草"),
        DIRT(Material.DIRT, "土"),
        IRON_ORE(Material.IRON_ORE, "鉄鉱石"),
        COAL_ORE(Material.COAL_ORE, "石炭鉱石"),
        DIROITE(Material.DIORITE, "閃緑岩"),
        ANDESITE(Material.ANDESITE, "安山岩"),
        GRANITE(Material.GRANITE, "花崗岩"),
        MAGMA_BLOCK(Material.MAGMA_BLOCK, "マグマブロック"),
        PRISMARINE(Material.PRISMARINE, "プリズマリン"),
        PRIISMARINE_BRICKS(Material.PRISMARINE_BRICKS, "プリズマリンレンガ"),
        ;
        companion object {
            /**
             * インデックスから RequestBlockType を取得する
             * @param index インデックス
             * @return Material または範囲外の場合null
             */
            fun fromIndex(index: Int): Material? =
                values().getOrNull(index)?.material

            fun getDisplayName(index: Int): String? = values().getOrNull(index)?.displayName

            /**
             * ブロックがリクエストされたブロックタイプかどうかをBoolで返す
             * @param index クエストで指定されているブロックのindex
             * @param block 採掘したブロック
             * @return Boolean 対象のブロックかどうかをboolで返す
             */
            fun ifReqBlockType(index: Int,block: Block): Boolean {
                return values().any { values().getOrNull(index)?.material == block.type }
            }
        }
    }

    enum class RequestWillSize(val size: WillSize) {
        TINY(WillSize.TINY),
        SMALL(WillSize.SMALL),
        MEDIUM(WillSize.MEDIUM),
        LARGE(WillSize.LARGE),
        HUGE(WillSize.HUGE),
        ;
        companion object {
            fun getRequestSize(index: Int): WillSize? = values().getOrNull(index)?.size
        }
    }

    enum class QuestRewardType(val displayName: String) {
        Relic("ランダムなレリック")
        ;
    }

    fun getName(locale: Locale) = localizedName.asSafety(locale)

    fun getLore(locale: Locale, difficulty: Int, requestSize: Int?, requestBlockIndex: Int?) = localizedLore(difficulty, requestSize, requestBlockIndex).asSafety(locale)

    fun getRequiredAmount(difficulty: Int) = requiredAmount[difficulty]

    fun getRewardType() = rewardType

    fun getRewardAmount(difficulty: Int) = rewardAmount[difficulty]


}