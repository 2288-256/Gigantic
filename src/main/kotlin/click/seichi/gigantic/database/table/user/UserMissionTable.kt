package click.seichi.gigantic.database.table.user

import org.jetbrains.exposed.dao.IntIdTable

/**
 * @author 2288-256
 */
object UserMissionTable : IntIdTable("users_mission") {

    val userId = reference("unique_id", UserTable).primaryKey()

    val missionId = integer("mission_id").primaryKey()

    /**
     * 1:デイリーミッション
     * 2:ウィークリーミッション
     * 3:イベントミッション
     */
    val missionType = integer("mission_type").default(0)

    val missionDifficulty = integer("mission_difficulty").default(0)

    val missionReqSize = integer("mission_req_size").default(0)

    val missionReqBlock = integer("mission_req_block").default(0)

    val progress = double("mission_progress").default(0.0)

    val complete = bool("mission_complete").default(false)

    val rewardReceived = bool("reward_received").default(false)

    val date = date("mission_date")

}