package click.seichi.gigantic.will

import click.seichi.gigantic.message.LocalizedString
import click.seichi.gigantic.message.lang.WillLang

/**
 * @author tar0ss
 * @author unicroak
 */
enum class WillSize(val memory: Int, val probability: Double, val prefix: LocalizedString, val renderingData: WillRenderingData) {

    TINY(1, 0.2, WillLang.PREFIX_TINY_WILL, WillRenderingData(0.1, 1, 2, 40)),

    SMALL(2, 0.4, WillLang.PREFIX_SMALL_WILL, WillRenderingData(0.2, 1, 2, 20)),

    MEDIUM(3, 0.8, WillLang.PREFIX_MEDIUM_WILL, WillRenderingData(0.3, 1, 2, 5)),

    LARGE(4, 0.4, WillLang.PREFIX_LARGE_WILL, WillRenderingData(0.4, 2, 4)),

    HUGE(5, 0.2, WillLang.PREFIX_HUGE_WILL, WillRenderingData(0.5, 3, 6));

}