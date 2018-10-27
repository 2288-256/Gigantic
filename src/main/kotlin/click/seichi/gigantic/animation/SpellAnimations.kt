package click.seichi.gigantic.animation

import click.seichi.gigantic.extension.spawnColoredParticle
import click.seichi.gigantic.util.Random
import org.bukkit.Color
import org.bukkit.Particle

/**
 * @author tar0ss
 */
object SpellAnimations {


    val TERRA_DRAIN_TREE = Animation(1) { location, _ ->
        location.world.spawnParticle(Particle.VILLAGER_HAPPY, location, 1,
                Random.nextGaussian(0.0, 0.3),
                Random.nextGaussian(0.0, 0.3),
                Random.nextGaussian(0.0, 0.3)
        )
    }

    val TERRA_DRAIN_HEAL = Animation(1) { location, _ ->
        location.world.spawnParticle(Particle.HEART, location, 10,
                Random.nextGaussian(0.0, 0.3),
                Random.nextGaussian(0.0, 0.3),
                Random.nextGaussian(0.0, 0.3)
        )
    }

    val STELLA_CLAIR = Animation(15) { location, _ ->
        location.world.spawnParticle(Particle.DOLPHIN, location, 10,
                Random.nextGaussian(0.0, 0.15),
                Random.nextGaussian(0.0, 0.15),
                Random.nextGaussian(0.0, 0.15)
        )
        location.world.spawnColoredParticle(location, Color.fromRGB(51, 103, 217), 1)
    }

}