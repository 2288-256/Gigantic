package click.seichi.gigantic.bag.bags

import click.seichi.gigantic.bag.Bag
import click.seichi.gigantic.item.items.BagButtons

/**
 * @author tar0ss
 */
object MainBag : Bag() {
    init {
        registerButton(9, BagButtons.PROFILE)
        registerButton(11, BagButtons.SKILL)
        registerButton(13, BagButtons.SPELL)
        registerButton(19, BagButtons.QUEST)
        registerButton(21, BagButtons.RELIC)
        registerButton(23, BagButtons.TELEPORT_DOOR)
        registerButton(25, BagButtons.AFK)
        registerButton(35, BagButtons.SPECIAL_THANKS)
    }

}