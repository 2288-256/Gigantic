package click.seichi.gigantic.database.table.user

import org.jetbrains.exposed.dao.IntIdTable

/**
 * @author 2288-256
 */
object UserSettingTable : IntIdTable("users_setting") {
    val userId = reference("unique_id", UserTable).primaryKey()

    val settingId = integer("setting_id").primaryKey()

    val value = integer("setting_value").default(0)
}