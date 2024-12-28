package click.seichi.gigantic.util

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

object NumberUtils {
    fun BigDecimal.commaSeparated(): String {
        val formatter = NumberFormat.getNumberInstance(Locale.US)
        return formatter.format(this)
    }
}