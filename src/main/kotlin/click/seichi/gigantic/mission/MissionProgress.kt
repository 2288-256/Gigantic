package click.seichi.gigantic.mission

abstract class MissionProgress {
    val lifeExpectancy
        get() = lifespan - count

    val isAlive
        get() = isShowed && 0 < lifeExpectancy

    var isShowed = false
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

        isShowed = true
    }

    fun update() {
        onUpdate()

        count = 0
    }

    fun remove() {
        onRemove()

        isShowed = false
    }

    open fun onRender() {}

    open fun onSpawn() {}

    open fun onUpdate() {}

    open fun onRemove() {}
}