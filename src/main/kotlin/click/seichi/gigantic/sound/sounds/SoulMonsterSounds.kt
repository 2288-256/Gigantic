package click.seichi.gigantic.sound.sounds

import click.seichi.gigantic.sound.DetailedSound
import org.bukkit.Sound
import org.bukkit.SoundCategory

/**
 * @author tar0ss
 */
object SoulMonsterSounds {

    val SPAWN = DetailedSound(
            Sound.ENTITY_ELDER_GUARDIAN_HURT,
            SoundCategory.HOSTILE,
            pitch = 0.6F,
            volume = 1.0F
    )

    val SENSE_SUB = DetailedSound(
            Sound.ENTITY_ZOMBIE_HORSE_AMBIENT,
            SoundCategory.HOSTILE,
            pitch = 0.2F,
            volume = 0.5F
    )

    val ATTACK_READY = DetailedSound(
            Sound.ENTITY_ZOMBIE_INFECT,
            SoundCategory.HOSTILE,
            pitch = 0.6F,
            volume = 1.0F
    )

    val ATTACK_READY_SUB1 = DetailedSound(
        Sound.ENTITY_HORSE_AMBIENT,
        SoundCategory.HOSTILE,
        pitch = 0.6F,
        volume = 0.5F
    )
    val ATTACK_READY_SUB2 = DetailedSound(
        Sound.BLOCK_NOTE_BLOCK_PLING,
        SoundCategory.HOSTILE,
        pitch = 2F,
        volume = 0.5F
    )

    val MONSTER_HEAL = DetailedSound(
            Sound.ENTITY_PLAYER_LEVELUP,
            SoundCategory.HOSTILE,
            pitch = 2F,
            volume = 0.8F
    )

    val DEBUFF_ATTACK = DetailedSound(
            Sound.ENTITY_ELDER_GUARDIAN_CURSE,
            SoundCategory.HOSTILE,
            pitch = 1F,
            volume = 0.8F
    )

    val DEFENCE = DetailedSound(
            Sound.ITEM_SHIELD_BLOCK,
            SoundCategory.HOSTILE,
            pitch = 0.5F,
            volume = 1.5F
    )

}