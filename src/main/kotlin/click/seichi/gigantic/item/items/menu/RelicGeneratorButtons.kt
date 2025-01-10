package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.event.events.RelicGenerateEvent
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.menus.RelicGeneratorMenu
import click.seichi.gigantic.menu.menus.RelicGeneratorMenu.reopen
import click.seichi.gigantic.menu.menus.RelicGeneratorMenu.close
import click.seichi.gigantic.message.messages.MenuMessages
import click.seichi.gigantic.message.messages.menu.RelicGeneratorMenuMessages
import click.seichi.gigantic.message.messages.menu.RelicMenuMessages
import click.seichi.gigantic.message.messages.menu.ToolSwitchMessages
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.player.Setting
import click.seichi.gigantic.player.ToggleSetting
import click.seichi.gigantic.relic.Relic
import click.seichi.gigantic.sound.sounds.MenuSounds
import click.seichi.gigantic.sound.sounds.PlayerSounds
import click.seichi.gigantic.util.Random
import click.seichi.gigantic.will.Will
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.random.asKotlinRandom

/**
 * author tar0ss
 */
object RelicGeneratorButtons {

    val SELECT_ETHEL: (Will) -> Button = { will ->
        object : Button {
            override fun toShownItemStack(player: Player): ItemStack? {
                if (!player.hasAptitude(will)) return null
                val selected = player.getOrPut(Keys.SELECTED_WILL)
                return ItemStack(will.material).apply {
                    setDisplayName("${ChatColor.WHITE}" +
                            (if (selected == will) RelicGeneratorMenuMessages.SELECTED.asSafety(player.wrappedLocale)
                            else "") +
                            will.chatColor + "${ChatColor.BOLD}" +
                            will.getName(player.wrappedLocale) +
                            RelicGeneratorMenuMessages.SELECT_ETHEL.asSafety(player.wrappedLocale) +
                            "(${player.ethel(will)})"
                    )
                    clearLore()
                    addLore("${ChatColor.GRAY}" +
                            RelicMenuMessages.RELATIONSHIP.asSafety(player.wrappedLocale) +
                            "${ChatColor.GREEN}${ChatColor.BOLD}" +
                            player.relationship(will).getName(player.wrappedLocale)
                    )
                    if (player.getOrPut(Keys.SELECTED_WILL) == will) {
                        setEnchanted(true)
                    }
                }
            }

            override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
                if (!player.hasAptitude(will)) return false
                val selected = player.getOrPut(Keys.SELECTED_WILL)
                if (selected != null && selected == will) return true
                player.offer(Keys.SELECTED_WILL, will)
                MenuSounds.WILL_SELECT.playOnly(player)
                RelicGeneratorMenu.reopen(player)
                return true
            }
        }
    }

    val GENERATE = object : Button {
        val genMultList = listOf(1, 10, 50, 100)
        override fun toShownItemStack(player: Player): ItemStack? {
            val selected = player.getOrPut(Keys.SELECTED_WILL)
            val generated = player.getOrPut(Keys.GENERATED_RELIC)
            val genMultiValueIndex = Setting.RELIC_GENERATION_VALUE.getValue(player)
            return itemStackOf(Material.END_PORTAL_FRAME) {
                clearLore()
                when {
                    // 何も選択していない場合
                    selected == null ->
                        setDisplayName(RelicGeneratorMenuMessages.NULL_OF_ETHEL.asSafety(player.wrappedLocale))
                    // 選択したエーテルが不足している場合
                    player.ethel(selected) < Defaults.RELIC_GENERATOR_REQUIRE_ETHEL * genMultList[genMultiValueIndex] ->
                        setDisplayName(RelicGeneratorMenuMessages.LOST_OF_ETHEL.asSafety(player.wrappedLocale))
                    else -> {
                        setDisplayName(RelicGeneratorMenuMessages.GENERATE.asSafety(player.wrappedLocale))
                        addLore("${ChatColor.RESET}" +
                                selected.chatColor +
                                "${ChatColor.BOLD}" +
                                selected.getName(player.wrappedLocale) +
                                RelicGeneratorMenuMessages.USE_ETHEL(genMultList[genMultiValueIndex]).asSafety(player.wrappedLocale)
                        )
                    }
                }
                addLore(MenuMessages.LINE)

                if (generated != null) {
                    val will = Will.findByRelic(generated) ?: return@itemStackOf
                    addLore(*RelicMenuMessages.GENERATED_ETHEL_LORE(will, generated)
                            .map { it.asSafety(player.wrappedLocale) }
                            .toTypedArray())
                }
            }
        }
        
        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            val selected = player.getOrPut(Keys.SELECTED_WILL) ?: return true
            val genMultiValueIndex = Setting.RELIC_GENERATION_VALUE.getValue(player)
            if (player.ethel(selected) < Defaults.RELIC_GENERATOR_REQUIRE_ETHEL * genMultList[genMultiValueIndex]) return true
            // 減算処理
            player.transform(Keys.ETHEL_MAP[selected]!!) { it - Defaults.RELIC_GENERATOR_REQUIRE_ETHEL * genMultList[genMultiValueIndex]}

            var lastRelic: Relic? = null
            val relicCountMap = mutableMapOf<Relic, Int>()
            
            repeat(genMultList[genMultiValueIndex]) {
                lastRelic = relicGenerate(selected, player)
                lastRelic?.let { relic ->
                    relicCountMap[relic] = relicCountMap.getOrDefault(relic, 0) + 1
                }
            }

            // 音を再生
            MenuSounds.RELIC_GENERATE.play(player.location)
            // 更新(サイドバー更新も兼ねて)
            Achievement.update(player, isForced = true)

            val playerLocale = player.wrappedLocale
            if (genMultList[genMultiValueIndex] != 1) {
                if (ToggleSetting.RELIC_GENERATION_RESULT.getToggle(player)) {
                    player.sendMessage(
                        RelicGeneratorMenuMessages.GENERATE_MESSAGE_SIMPLE(selected, genMultList[genMultiValueIndex])
                            .asSafety(playerLocale)
                    )
                    player.sendMessage(RelicGeneratorMenuMessages.GENERATE_MESSAGE_MORE_START.asSafety(playerLocale))
                    for ((relic, count) in relicCountMap) {
                        player.sendMessage(
                            RelicGeneratorMenuMessages.GENERATE_MESSAGE_DETAIL(relic, count).asSafety(playerLocale)
                        )
                    }
                    player.sendMessage(RelicGeneratorMenuMessages.GENERATE_MESSAGE_MORE_END.asSafety(playerLocale))
                } else {
                    player.sendMessage(
                        RelicGeneratorMenuMessages.GENERATE_MESSAGE_SIMPLE(selected, genMultList[genMultiValueIndex])
                            .asSafety(playerLocale)
                    )
                }
            }

            lastRelic?.let {
                //todo: 2個目の引数generatedが現在使われていないので最後のレリックを渡している。使用するなら適切なものに変えるべき。
                Bukkit.getPluginManager().callEvent(RelicGenerateEvent(player, it, selected, Defaults.RELIC_GENERATOR_REQUIRE_ETHEL * genMultList[genMultiValueIndex]))
            }

            if (genMultiValueIndex == 0) {
                reopen(player)
            } else {
                close(player)
            }

            // 更新後にすぐに削除
            runTaskLater(1L) {
                if (!player.isValid) return@runTaskLater
                player.offer(Keys.GENERATED_RELIC, null)
            }
            return true
        }

        private fun relicGenerate(selected: Will, player: Player): Relic {
            // ランダムにレリックを選択
            val relic = Relic.values().filter { selected.has(it) }.random(Random.generator.asKotlinRandom())
            // レリックを付与
            relic.dropTo(player)
            // レリックを保存
            player.offer(Keys.GENERATED_RELIC, relic)
            return relic
        }
    }

    val GENERATED = object : Button {
        override fun toShownItemStack(player: Player): ItemStack? {
            if (Setting.RELIC_GENERATION_VALUE.getValue(player) == 0) {
                val generated = player.getOrPut(Keys.GENERATED_RELIC) ?: return null
                val will = Will.findByRelic(generated) ?: return null
                return generated.getIcon().apply {
                    setDisplayName(
                        player,
                        RelicMenuMessages.RELIC_TITLE(will.chatColor, generated, generated.getDroppedNum(player))
                    )
                    setLore(*generated.getLore(player.wrappedLocale).map { "${ChatColor.GRAY}" + it }.toTypedArray())
                    addLore("${ChatColor.WHITE}" + MenuMessages.LINE)
                    val multiplier = generated.calcMultiplier(player)
                    addLore(RelicMenuMessages.BONUS_EXP(multiplier.toBigDecimal()).asSafety(player.wrappedLocale))
                    val bonusLore = generated.getBonusLore(player.wrappedLocale)
                    bonusLore.forEachIndexed { index, s ->
                        if (index == 0) {
                            addLore(RelicMenuMessages.CONDITIONS_FIRST_LINE(s).asSafety(player.wrappedLocale))
                        } else {
                            addLore(RelicMenuMessages.CONDITIONS(s).asSafety(player.wrappedLocale))
                        }
                    }
                }
            } else {
                return null
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            return true
        }
    }
    val GENERATE_VALUE_SETTING = object : Button {
        override fun toShownItemStack(player: Player): ItemStack? {
            val itemStack = ItemStack(Material.CHEST)
            val genMultList = listOf(1, 10, 50, 100)
            itemStack.amount = genMultList[Setting.RELIC_GENERATION_VALUE.getValue(player)]
            return itemStack.apply {
                setDisplayName(
                    "${ChatColor.WHITE}${ChatColor.BOLD}" +
                            Setting.RELIC_GENERATION_VALUE.getName(player.wrappedLocale)
                )
                val lore = mutableListOf<String>()
                lore.addAll(Setting.RELIC_GENERATION_VALUE.getDescription(player))
                setLore(*lore.toTypedArray())
                addLore(ToolSwitchMessages.CLICK_TO_TOGGLE.asSafety(player.wrappedLocale))
            }
        }
        val coolTimeSet = mutableSetOf<UUID>()

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            val uniqueId = player.uniqueId
            if (coolTimeSet.contains(uniqueId)) return false
            coolTimeSet.add(uniqueId)
            runTaskLater(5L) {
                coolTimeSet.remove(uniqueId)
            }
            Setting.RELIC_GENERATION_VALUE.rotateValue(player)
            PlayerSounds.TOGGLE.playOnly(player)
            reopen(player)
            player.updateDisplay(applyMainHand = true, applyOffHand = true)
            return true
        }
    }
    val GENERATE_NOTIFICATION_SETTING = object : Button {
        override fun toShownItemStack(player: Player): ItemStack? {
            val itemStack = ItemStack(Material.OAK_SIGN)
            return itemStack.apply {
                setDisplayName(
                    "${ChatColor.WHITE}${ChatColor.BOLD}" +
                            ToggleSetting.RELIC_GENERATION_RESULT.getName(player.wrappedLocale) + ": " +
                            if (ToggleSetting.RELIC_GENERATION_RESULT.getToggle(player)) {
                                "${ChatColor.GREEN}${ChatColor.BOLD}ON"
                            } else {
                                "${ChatColor.RED}${ChatColor.BOLD}OFF"
                            }
                )
                addLore(RelicGeneratorMenuMessages.GENERATE_NOTIFICATION_SETTING_LORE.asSafety(player.wrappedLocale))
                addLore("")
                addLore(ToolSwitchMessages.CLICK_TO_TOGGLE.asSafety(player.wrappedLocale))
            }
        }

        val coolTimeSet = mutableSetOf<UUID>()

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            val uniqueId = player.uniqueId
            if (coolTimeSet.contains(uniqueId)) return false
            coolTimeSet.add(uniqueId)
            runTaskLater(5L) {
                coolTimeSet.remove(uniqueId)
            }
            ToggleSetting.RELIC_GENERATION_RESULT.toggle(player)
            PlayerSounds.TOGGLE.playOnly(player)
            reopen(player)
            player.updateDisplay(applyMainHand = true, applyOffHand = true)
            return true
        }
    }
}