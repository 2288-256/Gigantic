package click.seichi.gigantic.mission

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.extension.info
import org.bukkit.Bukkit

/**
 * @author 2288-256
 */
object MissionProgressManager {
    private data class ProgressPair(
        val mission: Mission,
        val progress: MissionProgress
    )

    private val progressSet: MutableSet<ProgressPair> = mutableSetOf()

    fun onEnabled() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Gigantic.PLUGIN, {
            render()
        }, 0, 1)
    }

    fun spawn(mission: Mission, progress: MissionProgress) {
        val existingProgress = progressSet.find { it.mission == mission }?.progress
        if (existingProgress != null) {
            return existingProgress.update()
        }
        progress.spawn()
        progressSet.add(ProgressPair(mission, progress))
        info(progressSet.size.toString())
    }

    fun getSpiritSet() = progressSet.map { it.progress }.toSet()

    private fun render() = progressSet
        .onEach { pair -> pair.progress.render() }
        .removeIf { pair -> !pair.progress.isAlive }
        .let { }
}