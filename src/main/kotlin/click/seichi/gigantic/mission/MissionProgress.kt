package click.seichi.gigantic.mission

import click.seichi.gigantic.extension.info

abstract class MissionProgress {
    val lifeExpectancy
        get() = lifespan - count

    val isAlive
        get() = isSummoned && 0 < lifeExpectancy

    var isSummoned = false
        private set

    private var count = 0L

    // ticks
    abstract val lifespan: Long

    fun render() {
        count++
        if (!isAlive) {
            remove()
            return
        }
        onRender()
    }

    fun spawn() {
        onSpawn()

        isSummoned = true
    }

    fun update() {
        onUpdate()

        count = 0
    }

    fun remove() {
        onRemove()

        isSummoned = false
    }

    open fun onRender() {}

    open fun onSpawn() {}

    open fun onUpdate() {}

    open fun onRemove() {}
}