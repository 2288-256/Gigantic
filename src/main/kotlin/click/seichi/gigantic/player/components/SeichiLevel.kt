package click.seichi.gigantic.player.components

import click.seichi.gigantic.config.SeichiLevelConfig
import click.seichi.gigantic.database.UserContainer
import click.seichi.gigantic.player.GiganticPlayer
import click.seichi.gigantic.player.MineBlockReason
import click.seichi.gigantic.player.Remotable

class SeichiLevel : Remotable {
    companion object {
        private val MAX = SeichiLevelConfig.MAX
    }

    var current: Int = 0
        private set

    private fun calcLevel(mineBlock: Long) =
            (1..MAX).firstOrNull {
                !canLevelUp(it + 1, mineBlock)
            } ?: MAX

    private fun canLevelUp(nextLevel: Int, mineBlock: Long) =
            mineBlock > SeichiLevelConfig.MINEBLOCK_MAP[nextLevel] ?: Long.MAX_VALUE

    fun update(gPlayer: GiganticPlayer) {
        val mineBlock = gPlayer.status.mineBlock.get()
        while (canLevelUp(current + 1, mineBlock)) {
            current++
            if (current >= MAX) {
                current = MAX
                break
            }
        }
    }

    override fun onLoad(userContainer: UserContainer) {
        current = calcLevel(userContainer.userMineBlockMap[MineBlockReason.GENERAL]!!.mineBlock)
    }
}
