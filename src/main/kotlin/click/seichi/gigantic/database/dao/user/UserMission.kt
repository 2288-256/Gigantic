package click.seichi.gigantic.database.dao.user

import click.seichi.gigantic.database.table.user.UserMissionTable
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity

/**
 * @author 2288-256
 */
class UserMission(id: EntityID<Int>) : IntEntity(id) {

    companion object : EntityClass<Int, UserMission>(UserMissionTable)

    var user by User referencedOn UserMissionTable.userId

    var missionType by UserMissionTable.missionType

    var missionId by UserMissionTable.missionId

    var missionDifficulty by UserMissionTable.missionDifficulty

    var missionReqSize by UserMissionTable.missionReqSize

    var missionReqBlock by UserMissionTable.missionReqBlock

    var progress by UserMissionTable.progress

    var complete by UserMissionTable.complete

    var date by UserMissionTable.date
}