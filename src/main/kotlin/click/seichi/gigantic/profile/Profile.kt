package click.seichi.gigantic.profile

import org.joda.time.DateTime
import java.util.*

/**
 * @author tar0ss
 */
data class Profile(
        val uniqueId: UUID,
        var playerName: String,
        var locale: Locale,
        var lastSaveDate: DateTime
)