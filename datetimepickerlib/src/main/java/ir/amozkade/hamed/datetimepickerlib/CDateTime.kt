package ir.amozkade.hamed.datetimepickerlib

import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat

/**
 * Created by hamed on 12/20/17
 */

class CDateTime {

    //96/05/20
    // 14:22
    val currentDateTimeObject: StructDateTime
        get() {
            val persianDate = PersianDate()
            val pFormat = PersianDateFormat("Y/m/d")
            val nowDate = pFormat.format(persianDate).substring(2, pFormat.format(persianDate).length)
            val nowTime = (if (persianDate.hour <= 9) "0" + persianDate.hour else persianDate.hour).toString() +
                    ":" +
                    if (persianDate.minute <= 9) "0" + persianDate.minute else persianDate.minute
            val dateTime = StructDateTime()
            dateTime.date = nowDate
            dateTime.time = nowTime

            return dateTime
        }

    inner class StructDateTime {
        var date: String? = null
        var time: String? = null
    }

}
