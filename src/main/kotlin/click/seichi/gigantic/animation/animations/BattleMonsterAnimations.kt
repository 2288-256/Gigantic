package click.seichi.gigantic.animation.animations

import click.seichi.gigantic.animation.Animation
import click.seichi.gigantic.extension.spawnColoredParticle
import click.seichi.gigantic.extension.spawnColoredParticleSpherically
import click.seichi.gigantic.util.NoiseData
import org.bukkit.Color
import org.bukkit.Particle

/**
 * @author tar0ss
 */
object BattleMonsterAnimations {

    val AMBIENT = { color: Color ->
        Animation(0) { location, _ ->
            location.world?.spawnColoredParticleSpherically(
                    location,
                    color,
                    count = 2,
                    radius = 1.5
            )
        }
    }

    val ATTACK_READY = { color: Color ->
        Animation(20) { location, _ ->
            location.world?.spawnColoredParticle(
                    location,
                    color,
                    noiseData = NoiseData(0.01)
            )
        }
    }

    val ATTACK_READY_BLOCK = Animation(0) { location, _ ->
        location.world?.spawnParticle(
            Particle.LAVA,
            location,
            1,
            0.1,
            0.1,
            0.1
        )
    }

    val SELF_HEAL_READY_BLOCK = Animation(0) { location, _ ->
        location.world?.spawnParticle(
            Particle.REDSTONE, location.add(0.0,0.5,0.0), 25,
            0.25,
            0.25,
            0.25,
            Particle.DustOptions(
                Color.fromRGB(
                    255,141,161
                ), 1F
            )
        )
    }

    val DEBUFF_READY_BLOCK = Animation(0) { location, _ ->
        location.world?.spawnParticle(
            Particle.REDSTONE, location.add(0.0,0.5,0.0), 25,
            0.25,
            0.25,
            0.25,
            Particle.DustOptions(
                Color.PURPLE, 1F
            )
        )
    }

    val DEFENCE = { color: Color ->
        Animation(20) { location, _ ->
            location.world?.spawnColoredParticle(
                    location,
                    color,
                    noiseData = NoiseData(0.01)
            )
        }
    }

    val DAMAGE_FROM_PLAYER = Animation(0) { location, _ ->
        location.world?.spawnParticle(
                Particle.DAMAGE_INDICATOR,
                location,
                1
        )
    }

    val WIN_PARTICLE = Animation(0) { location, _ ->
        location.world?.spawnParticle(
            Particle.REDSTONE, location.add(0.0,0.5,0.0), 50,
            1.0,
            1.0,
            1.0,
            Particle.DustOptions(
                Color.WHITE, 2F
            )
        )
    }
}