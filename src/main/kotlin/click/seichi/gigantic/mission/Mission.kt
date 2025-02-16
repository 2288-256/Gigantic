package click.seichi.gigantic.mission

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.will.WillSize
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.config.Config
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.messages.MissionMessages
import click.seichi.gigantic.player.ToggleSetting
import click.seichi.gigantic.sound.sounds.PlayerSounds
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.joda.time.DateTime
import java.util.*
import kotlin.random.Random

/**
 * @author 2288-256
 */
enum class Mission(
    val id: Int,
    private val localizedName: LocalizedText,
    private val localizedLore: (difficulty: Int, requestSize: Int?, requestBlockIndex: Int?, missionType: Int?) -> LocalizedText,
    private val requiredAmount: List<Int>,
    private val rewardType: QuestRewardType,
    private val rewardAmount: List<Int>
) {
    EXP(
        1,
        LocalizedText(Locale.JAPANESE to "expを取得する"),
        { difficulty, _, _, _ -> LocalizedText(Locale.JAPANESE to "${EXP.getRequiredAmount(difficulty)}exp以上取得すると達成") },
        listOf(20000, 50000, 100000),
        QuestRewardType.Ethel,
        listOf(100, 150, 200)
    ),
    BLOCK_BREAK(
        2,
        LocalizedText(Locale.JAPANESE to "通常破壊をする"),
        { difficulty, _, _, _ ->
            LocalizedText(Locale.JAPANESE to "${BLOCK_BREAK.getRequiredAmount(difficulty)}ブロック通常破壊すると達成") },
        listOf(3000, 5000, 10000),
        QuestRewardType.Ethel,
        listOf(100, 150, 200)
    ),
    BLOCK_BREAK_REQ_BLOCK(
        3,
        LocalizedText(Locale.JAPANESE to "特定のブロックを破壊する"),
        { difficulty, _, blockTypeIndex, missionType -> LocalizedText(
            Locale.JAPANESE to
                    "${blockTypeIndex?.let { index -> missionType?.let { RequestBlockType.getDisplayName(index, it) } } ?: "不明"}を${BLOCK_BREAK_REQ_BLOCK.getRequiredAmount(difficulty)}ブロック破壊すると達成")
        },
        listOf(1000, 2000, 3000),
        QuestRewardType.Ethel,
        listOf(100, 150, 200)
    ),
//    COMBO(
//        4,
//        LocalizedText(Locale.JAPANESE to "コンボを維持する"),
//        { difficulty, _, _ -> listOf(
//        LocalizedText(Locale.JAPANESE to "${COMBO.getRequiredAmount(difficulty)}コンボ以上になると達成"),
//        ) },
//        listOf(1000, 5000, 10000),
//        QuestRewardType.Will,
//        listOf(1, 2, 3)
//    ),
    WILL_GET(
        4,
        LocalizedText(Locale.JAPANESE to "意志を回収する"),
        { difficulty, _, _, _ ->  LocalizedText(Locale.JAPANESE to "${WILL_GET.getRequiredAmount(difficulty)}回意志を回収すると達成")},
        listOf(20, 50, 100),
        QuestRewardType.Ethel,
        listOf(100, 150, 200)
    ),
    WILL_GET_REQ_SIZE(
        5,
        LocalizedText(Locale.JAPANESE to "特定のサイズの意志を回収する"),
        { difficulty, size, _, _ -> LocalizedText(Locale.JAPANESE to "${size?.let { if ( RequestWillSize.getRequestSize(it)?.prefix?.asSafety(Locale.JAPANESE) == "" ) {"通常"} else {RequestWillSize.getRequestSize(it)?.prefix?.asSafety(Locale.JAPANESE)} }}サイズの意志を${WILL_GET_REQ_SIZE.getRequiredAmount(difficulty)}個回収すると達成")},
        listOf(5, 10, 20),
        QuestRewardType.Ethel,
        listOf(100, 150, 200)
    ),
    RELIC_CREATE(
        6,
        LocalizedText(Locale.JAPANESE to "レリックを生成する"),
        { difficulty, _, _, _ -> LocalizedText(Locale.JAPANESE to "${RELIC_CREATE.getRequiredAmount(difficulty)}回レリックを生成すると達成")},
        listOf(10, 15, 25),
        QuestRewardType.Ethel,
        listOf(100, 150, 250)
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
        fun missionCreate(player: Player,missionType: Int) {
            val nowDate = DateTime.now()
            val missionMapBefore = player.getOrPut(Keys.MISSION_MAP)
            val beforeCount = missionMapBefore.values.size
            for (mission in missionMapBefore.values){
                val createDate = mission.date
                val extendedHour = if (createDate.hourOfDay < 4) createDate.hourOfDay + 24 else createDate.hourOfDay
                val resetDate = if (extendedHour in 24 .. 27) createDate.withTime(4, 0, 0, 0) else createDate.plusDays(1).withTime(4, 0, 0, 0)
                if (resetDate.isBefore(nowDate)) {
                    player.transform(Keys.MISSION_MAP) {
                        it.toMutableMap().apply {
                            remove(mission.missionId)
                        }
                    }
                }
            }

            val missionMapAfter = player.getOrPut(Keys.MISSION_MAP)
            val afterCount = missionMapAfter.values.size
            var generateCount = beforeCount - afterCount
            if (beforeCount == 0) generateCount = Config.MISSION_DAILY_AMOUNT

            if (generateCount > 0) {
                var index = 0
                val missionIds = mutableSetOf<Int>()
                while (missionIds.size < generateCount) {
                    val concatenatedString = "${player.uniqueId}_${nowDate.withTimeAtStartOfDay()}_$index"
                    val random = Random(concatenatedString.hashCode().toLong())
                    missionIds.add(random.nextInt(1, Mission.values().size + 1))
                    index++
                }
                for (i in 0 until generateCount) {
                    val newMission = MissionClient(
                        missionId = missionIds.elementAt(i),
                        missionType = missionType,
                        missionDifficulty = Random("${player.uniqueId}_${nowDate.withTimeAtStartOfDay()}_${i}_missionDifficulty".hashCode().toLong()).nextInt(0, 3),
                        missionReqSize = if (missionIds.elementAt(i) == 5) {
                            Random("${player.uniqueId}_${nowDate.withTimeAtStartOfDay()}_${i}_missionReqSize".hashCode().toLong()).nextInt(
                                0,
                                RequestWillSize.values().size
                            )
                        } else 0,
                        missionReqBlock = if (missionIds.elementAt(i) == 3) {
                            Random("${player.uniqueId}_${nowDate.withTimeAtStartOfDay()}_missionReqBlock".hashCode().toLong()).nextInt(
                                0,
                                RequestBlockType.filterReqBlockType(missionType).size
                            )
                        } else 0,
                        progress = 0.0,
                        complete = false,
                        rewardReceived = false,
                        date = nowDate
                    )
                    player.transform(Keys.MISSION_MAP) {
                        it.toMutableMap().apply {
                            put(newMission.missionId, newMission)
                        }
                    }
                }
                MissionMessages.MISSION_CREATE.sendTo(player)
            }
        }
    }

    enum class RequestBlockType(val material: Material, val displayName: String, val missionType: List<Int>) {
        GRAVEL(Material.GRAVEL, "砂利", listOf(2,3)),
        SAND(Material.SAND, "砂", listOf(2,3)),
        CLAY(Material.CLAY, "粘土", listOf(2,3)),
        GRASS(Material.GRASS_BLOCK, "草ブロック", listOf(1,2,3)),
        DIRT(Material.DIRT, "土", listOf(1,2,3)),
        IRON_ORE(Material.IRON_ORE, "鉄鉱石", listOf(2,3)),
        COAL_ORE(Material.COAL_ORE, "石炭鉱石", listOf(2,3)),
        DIROITE(Material.DIORITE, "閃緑岩", listOf(1,2,3)),
        ANDESITE(Material.ANDESITE, "安山岩", listOf(1,2,3)),
        GRANITE(Material.GRANITE, "花崗岩", listOf(1,2,3)),
        MAGMA_BLOCK(Material.MAGMA_BLOCK, "マグマブロック", listOf(2,3)),
        PRISMARINE(Material.PRISMARINE, "プリズマリン", listOf(1,2,3)),
        PRIISMARINE_BRICKS(Material.PRISMARINE_BRICKS, "プリズマリンレンガ", listOf(1,2,3)),
        ;
        companion object {
            /**
             * インデックスから RequestBlockType を取得する
             * @param index インデックス
             * @return Material または範囲外の場合null
             */
            fun fromIndex(index: Int): Material? =
                values().getOrNull(index)?.material

            /**
             * ミッションジャンルからブロックタイプをフィルタリングしてリストで返す
             * @param missionType ミッションタイプ
             * @return List<Mission.RequestBlockType> フィルタリングされたリクエストされたブロックタイプのリスト
             */
            fun filterReqBlockType(missionType: Int): List<RequestBlockType> {
                return values().filter { it.missionType.contains(missionType) }
            }
            fun getDisplayName(index: Int, missionType: Int): String? = filterReqBlockType(missionType).getOrNull(index)?.displayName

            /**
             * ブロックがリクエストされたブロックタイプかどうかをBoolで返す
             * @param index クエストで指定されているブロックのindex
             * @param block 採掘したブロック
             * @param missionType ミッションのジャンル
             * @return Boolean 対象のブロックかどうかをboolで返す
             */
            fun ifReqBlockType(index: Int, block: Block, missionType: Int): Boolean {
                val filteringList = filterReqBlockType(missionType)
                return filteringList.any { filteringList.getOrNull(index)?.material == block.type }
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
        Ethel("ランダムなエーテル"),
        Relic("ランダムなレリック")
        ;
    }

    fun getName(locale: Locale) = localizedName.asSafety(locale)

    fun getLore(locale: Locale, missionData: MissionClient) = localizedLore(missionData.missionDifficulty, missionData.missionReqSize, missionData.missionReqBlock, missionData.missionType).asSafety(locale)

    fun getRequiredAmount(difficulty: Int) = requiredAmount[difficulty]

    fun getRewardType() = rewardType

    fun getRewardAmount(difficulty: Int) = rewardAmount[difficulty]


}