package ir.amozkade.hamed.datetimepickerlib

/**
 * Created by hamed on 12/4/17
 */

class DateTimeChecker {

    fun isDate1LargerDate1(d1: String, t1: String, d2: String, t2: String): Boolean {
        if (d1 == d2) {
            if (checkTimeLarger(t1, t2)) {
                return true
            }
        } else {
            return false
        }
        return if (checkDateLarger(d1, d2)) {
            true
        } else {
            false
        }
    }

    fun checkTimeLarger(t1: String, t2: String): Boolean {
        try {
            val hour = getHour(t1)
            val minute = getMinute(t1)

            val hour2 = getHour(t2)
            val minute2 = getMinute(t2)
            return if (hour > hour2) {
                true
            } else if (hour == hour2) {
                if (minute > minute2) {
                    true
                } else {
                    false
                }
            } else if (hour < hour2) {
                false
            } else {
                true
            }
        } catch (e: Exception) {
            return false
        }
    }

    fun checkDateLarger(t1: String, t2: String): Boolean {
        var res = false
        try {
            val year = getYear(t1)
            val month = getMonth(t1)
            val day = getDay(t1)

            val year2 = getYear(t2)
            val month2 = getMonth(t2)
            val day2 = getDay(t2)
            if (year > year2) {
                res = true
            } else if (year == year2 && month > month2)
                res = true
            else if (year == year2 && month == month2 && day > day2) {
                res = true
            } else {
                res = false
            }
        } catch (e: Exception) {
            res = false
        }
        return res
    }

    fun getHour(t: String): Int {
        try {
            return Integer.parseInt(t.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0])
        } catch (e: Exception) {
            return -1
        }
    }

    fun getMinute(t: String): Int {
        try {
            return Integer.parseInt(t.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1])
        } catch (e: Exception) {
            return -1
        }
    }

    fun getYear(t: String): Int {
        try {
            return Integer.parseInt(t.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0])
        } catch (e: Exception) {
            return -1
        }
    }

    fun getMonth(t: String): Int {
        try {
            return Integer.parseInt(t.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1])
        } catch (e: Exception) {
            return -1
        }
    }

    fun getDay(t: String): Int {
        try {
            return Integer.parseInt(t.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[2])
        } catch (e: Exception) {
            return -1
        }
    }
}
