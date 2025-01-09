package click.seichi.gigantic.database.dao.user

import click.seichi.gigantic.database.table.user.UserSettingTable
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity

class UserSetting(id: EntityID<Int>) : IntEntity(id) {
    companion object : EntityClass<Int, UserSetting>(UserSettingTable)

    var user by User referencedOn UserSettingTable.userId

    var settingId by UserSettingTable.settingId

    var value by UserSettingTable.value
}