package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.item.items.menu.MissionButtons
import click.seichi.gigantic.item.items.menu.RelicButtons
import click.seichi.gigantic.menu.BookMenu
import click.seichi.gigantic.menu.MissionCategory
import click.seichi.gigantic.menu.RelicCategory
import click.seichi.gigantic.mission.Mission
import click.seichi.gigantic.relic.Relic
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

object MissionMenu : BookMenu() {

    override val size: Int
        get() = 4 * 9

    private const val numOfContentsPerPage = 2 * 9

    private const val offset = 2 * 9

    init {
        registerButton(0, MissionButtons.DAILY)
//        registerButton(1, MissionButtons.WEEKLY)
//        registerButton(2, MissionButtons.EVENT)
    }

    override fun getMaxPage(player: Player): Int {
        return player.getOrPut(Keys.MENU_MISSION_LIST).size.minus(1).div(numOfContentsPerPage).plus(1).coerceAtLeast(1)
    }

    override fun onOpen(player: Player, page: Int, isFirst: Boolean) {
        if (isFirst) {
            player.offer(Keys.MENU_MISSION_CATEGORY, MissionCategory.DAILY)
        }
        val category = player.getOrPut(Keys.MENU_MISSION_CATEGORY)
        player.offer(Keys.MENU_MISSION_LIST,
            Mission.values()
                .filter { player.hasMission(it) }
                .filter {
                    category.isContain(player, it)
                }
                .toList()
        )
    }

    override fun getTitle(player: Player, page: Int): String {
        val category = player.getOrPut(Keys.MENU_MISSION_CATEGORY)
        return "${category.menuTitle.asSafety(player.wrappedLocale)} $page/${getMaxPage(player)}"
    }

    override fun setItem(inventory: Inventory, player: Player, page: Int): Inventory {
        val contentList = player.getOrPut(Keys.MENU_MISSION_LIST)
        val start = (page - 1) * numOfContentsPerPage
        val end = page * numOfContentsPerPage
        val pattern = listOf(19, 21, 23, 25) // 3行目のスロット番号

        contentList.subList(start, end.coerceAtMost(contentList.size))
            .forEachIndexed { index, mission ->
                val slot = pattern.getOrNull(index % pattern.size) ?: return@forEachIndexed
                inventory.setItemAsync(player, slot, MissionButtons.MISSION(mission))
        }
        getButtonMap().forEach { slot, button ->
            inventory.setItemAsync(player, slot, button)
        }
        return inventory
    }

    override fun getButton(player: Player, page: Int, slot: Int): Button? {
        val index = (page - 1) * numOfContentsPerPage + slot - offset
        return getButtonMap()[slot] ?: MissionButtons.MISSION(player.getOrPut(Keys.MENU_MISSION_LIST)[index])
    }



}