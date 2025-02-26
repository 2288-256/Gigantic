package click.seichi.gigantic.cache.cache

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.database.RankingEntity
import click.seichi.gigantic.database.UserEntity
import click.seichi.gigantic.ranking.Score
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.util.*

/**
 * @author tar0ss
 */
class PlayerCache(private val uniqueId: UUID, private val playerName: String) : Cache<PlayerCache>() {

    fun canRead(): Boolean {
        var ans = false
        transaction {
            // オフラインならTRUE
            UserEntity(uniqueId, playerName).user.run {
                ans = !isOnline
            }
        }
        return ans
    }

    override fun read() {
        transaction {
            val entity = UserEntity(uniqueId, playerName)

            // 初期読み込み時の更新
            entity.user.run {
                isOnline = true
                name = playerName.toLowerCase()
                updatedDate = DateTime.now()
            }

            // 読み込み時のみ値を挿入するもの（読み込み時以外での書き換えが禁止されているもの）
            Keys.VOTE.let {
                force(it, it.read(entity))
            }
            Keys.POMME.let {
                force(it, it.read(entity))
            }
            Keys.DONATION.let {
                force(it, it.read(entity))
            }
            Keys.PURCHASE_TICKET_LIST.let {
                force(it, it.read(entity))
            }

            // 書き換えがあるもの
            Keys.MAX_COMBO.let {
                offer(it, it.read(entity))
            }
            Keys.LOCALE.let {
                offer(it, it.read(entity))
            }
            Keys.MANA.let {
                offer(it, it.read(entity))
            }
            Keys.TOOL.let {
                offer(it, it.read(entity))
            }
            Keys.BELT.let {
                offer(it, it.read(entity))
            }
            Keys.SPELL_TOGGLE.let {
                offer(it, it.read(entity))
            }
            Keys.TELEPORT_TOGGLE.let {
                offer(it, it.read(entity))
            }
            Keys.SPELL_MULTI_BREAK_AREA.let {
                offer(it, it.read(entity))
            }
            Keys.EFFECT.let {
                offer(it, it.read(entity))
            }
            Keys.FOLLOW_SET.let {
                offer(it, it.read(entity))
            }
            Keys.MUTE_SET.let {
                offer(it, it.read(entity))
            }
            Keys.AUTO_SWITCH.let {
                offer(it, it.read(entity))
            }
            Keys.HOME_MAP.let {
                offer(it, it.read(entity))
            }
            Keys.SPELL_SKY_WALK_TOGGLE.let {
                offer(it, it.read(entity))
            }
            Keys.COMBO.let {
                offer(it, it.read(entity))
            }
            Keys.LAST_COMBO_TIME.let {
                offer(it, it.read(entity))
            }
            Keys.GIVEN_VOTE_BONUS.let {
                offer(it, it.read(entity))
            }
            Keys.WALK_SPEED.let {
                offer(it, it.read(entity))
            }
            Keys.TOTEM.let {
                offer(it, it.read(entity))
            }
            Keys.TOTEM_PIECE.let {
                offer(it, it.read(entity))
            }
            Keys.DAILY_MISSION_COUNT.let {
                offer(it, it.read(entity))
            }
            Keys.EVENT_JMS_KING_GIVEN_AT.let {
                offer(it, it.read(entity))
            }
            Keys.IS_NORMAL_TEXTURE.let {
                offer(it, it.read(entity))
            }
            Keys.STRIP_MINE.let {
                offer(it, it.read(entity))
            }
            Keys.MISSION_MAP.let {
                offer(it, it.read(entity))
            }
            Keys.EXP_MAP.forEach { (_, key) ->
                offer(key, key.read(entity))
            }
            Keys.ETHEL_MAP.forEach { (_, key) ->
                offer(key, key.read(entity))
            }
            Keys.WILL_SECRET_MAP.forEach { (_, key) ->
                offer(key, key.read(entity))
            }
            Keys.APTITUDE_MAP.forEach { (_, key) ->
                offer(key, key.read(entity))
            }
            Keys.SOUL_MONSTER.forEach { (_, key) ->
                offer(key, key.read(entity))
            }
            Keys.RELIC_MAP.forEach { (_, key) ->
                offer(key, key.read(entity))
            }
            Keys.ACHIEVEMENT_MAP.forEach { (_, key) ->
                offer(key, key.read(entity))
            }
            Keys.BELT_TOGGLE_MAP.forEach { (_, key) ->
                offer(key, key.read(entity))
            }
            Keys.BELT_UNLOCK_MAP.forEach { (_, key) ->
                offer(key, key.read(entity))
            }
            Keys.TOOL_TOGGLE_MAP.forEach { (_, key) ->
                offer(key, key.read(entity))
            }
            Keys.TOOL_UNLOCK_MAP.forEach { (_, key) ->
                offer(key, key.read(entity))
            }
            Keys.QUEST_MAP.forEach { (_, key) ->
                offer(key, key.read(entity))
            }
            Keys.TOGGLE_SETTING_MAP.forEach { (_, key) ->
                offer(key, key.read(entity))
            }
            Keys.SETTING_MAP.forEach { (_, key) ->
                offer(key, key.read(entity))
            }
        }
    }

    override fun write() {
        transaction {
            val entity = UserEntity(uniqueId, playerName)
            entity.user.isOnline = false
            // 更新時間を記録
            entity.user.updatedDate = DateTime.now()
            Keys.MAX_COMBO.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.LOCALE.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.MANA.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.TOOL.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.BELT.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.SPELL_TOGGLE.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.TELEPORT_TOGGLE.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.SPELL_MULTI_BREAK_AREA.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.EFFECT.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.FOLLOW_SET.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.MUTE_SET.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.AUTO_SWITCH.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.HOME_MAP.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.SPELL_SKY_WALK_TOGGLE.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.COMBO.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.LAST_COMBO_TIME.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.GIVEN_VOTE_BONUS.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.WALK_SPEED.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.TOTEM.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.TOTEM_PIECE.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.DAILY_MISSION_COUNT.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.EVENT_JMS_KING_GIVEN_AT.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.IS_NORMAL_TEXTURE.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.STRIP_MINE.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.MISSION_MAP.let {
                it.store(entity, getOrDefault(it))
            }
            Keys.EXP_MAP.forEach { (_, key) ->
                key.store(entity, getOrDefault(key))
            }
            Keys.ETHEL_MAP.forEach { (_, key) ->
                key.store(entity, getOrDefault(key))
            }
            Keys.WILL_SECRET_MAP.forEach { (_, key) ->
                key.store(entity, getOrDefault(key))
            }
            Keys.APTITUDE_MAP.forEach { (_, key) ->
                key.store(entity, getOrDefault(key))
            }
            Keys.SOUL_MONSTER.forEach { (_, key) ->
                key.store(entity, getOrDefault(key))
            }
            Keys.RELIC_MAP.forEach { (_, key) ->
                key.store(entity, getOrDefault(key))
            }
            Keys.ACHIEVEMENT_MAP.forEach { (_, key) ->
                key.store(entity, getOrDefault(key))
            }
            Keys.BELT_TOGGLE_MAP.forEach { (_, key) ->
                key.store(entity, getOrDefault(key))
            }
            Keys.BELT_UNLOCK_MAP.forEach { (_, key) ->
                key.store(entity, getOrDefault(key))
            }
            Keys.TOOL_TOGGLE_MAP.forEach { (_, key) ->
                key.store(entity, getOrDefault(key))
            }
            Keys.TOOL_UNLOCK_MAP.forEach { (_, key) ->
                key.store(entity, getOrDefault(key))
            }
            Keys.QUEST_MAP.forEach { (_, key) ->
                key.store(entity, getOrDefault(key))
            }
            Keys.TOGGLE_SETTING_MAP.forEach { (_, key) ->
                key.store(entity, getOrDefault(key))
            }
            Keys.SETTING_MAP.forEach { (_, key) ->
                key.store(entity, getOrDefault(key))
            }

            // ログイン時でもいいけど、ログインに時間がかかるのは良くないのでこっちで
            val rankingEntity = RankingEntity(uniqueId)

            rankingEntity.user.run {
                name = playerName
                // デバッグ中はレベル変更の可能性があるので除外
                if (!Gigantic.IS_DEBUG)
                    level = getOrDefault(Keys.LEVEL)
            }

            Score.values().forEach { it.write(rankingEntity, this@PlayerCache) }
        }
    }


}