package ir.amozkade.hamed.datetimepicker;

/**
 * Created by hamed on 12/4/17
 */

public class DateTimeChecker {

    public boolean isDate1LargerDate1(String d1, String t1, String d2, String t2) {
        if (d1.equals(d2)) {
            if (checkTimeLarger(t1, t2)) {
                return true;
            }
        } else {
            return false;
        }
        if (checkDateLarger(d1, d2)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkTimeLarger(String t1, String t2) {
        try {
            int hour = getHour(t1);
            int minute = getMinute(t1);

            int hour2 = getHour(t2);
            int minute2 = getMinute(t2);
            if (hour > hour2) {
                return true;
            } else if (hour == hour2) {
                if (minute > minute2) {
                    return true;
                } else {
                    return false;
                }
            } else if (hour < hour2) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }

    }

    public boolean checkDateLarger(String t1, String t2) {
        boolean res = false;
        try {
            int year = getYear(t1);
            int month = getMonth(t1);
            int day = getDay(t1);

            int year2 = getYear(t2);
            int month2 = getMonth(t2);
            int day2 = getDay(t2);

            if (year > year2) {
                res = true;
            } else if (year == year2 && month > month2)
                res = true;
            else if (year == year2 && month == month2 && day > day2) {
                res = true;

            } else {
                res = false;
            }

        } catch (Exception e) {
            res = false;
        }
        return res;
    }

    public int getHour(String t) {
        try {
            return Integer.parseInt(t.split(":")[0]);
        } catch (Exception e) {
            return -1;
        }
    }

    public int getMinute(String t) {
        try {
            return Integer.parseInt(t.split(":")[1]);
        } catch (Exception e) {
            return -1;
        }
    }

    public int getYear(String t) {
        try {
            return Integer.parseInt(t.split("/")[0]);
        } catch (Exception e) {
            return -1;
        }
    }

    public int getMonth(String t) {
        try {
            return Integer.parseInt(t.split("/")[1]);
        } catch (Exception e) {
            return -1;
        }
    }

    public int getDay(String t) {
        try {
            return Integer.parseInt(t.split("/")[2]);
        } catch (Exception e) {
            return -1;
        }
    }


}
