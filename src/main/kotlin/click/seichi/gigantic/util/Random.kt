package click.seichi.gigantic.util

import click.seichi.gigantic.will.Will
import click.seichi.gigantic.will.WillSize

/**
 * @author unicroak
 */
internal object Random {

    private val generator = java.util.Random()

    fun nextBoolean() = generator.nextBoolean()

    fun nextDouble() = generator.nextDouble()

    fun nextDouble(to: Double) = generator.nextDouble() * to

    fun nextDouble(from: Double, to: Double) = generator.nextDouble() * to + from

    fun nextInt() = generator.nextInt()

    fun nextInt(to: Int) = generator.nextInt(to)

    fun nextInt(from: Int, to: Int) = generator.nextInt(to - from) + from

    fun nextSign() = if (nextDouble() < 0.5) 1 else -1

    fun nextGaussian(mean: Double = 0.0, variance: Double = 1.0) = generator.nextGaussian() * variance + mean

    fun nextWill() = Will.values().toList().shuffled().first()

    tailrec fun nextWillSizeWithRegularity(): WillSize = WillSize.values().toList()
            .shuffled()
            .firstOrNull { Random.nextDouble() < it.probability }
            ?.let { it }
            ?: nextWillSizeWithRegularity()
}