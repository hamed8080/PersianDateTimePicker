package ir.amozkade.hamed.datetimepicker;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

/**
 * Created by hamed on 12/20/17.
 */

public class CDateTime {
    public class StructDateTime {
        public String date;
        public String time;
    }

    public StructDateTime getCurrentDateTimeObject() {
        PersianDate persianDate = new PersianDate();
        PersianDateFormat pFormat = new PersianDateFormat("Y/m/d");
        String nowDate = pFormat.format(persianDate).substring(2, pFormat.format(persianDate).length());//96/05/20
        String nowTime = new StringBuilder()
                .append(persianDate.getHour() <= 9 ? "0" + persianDate.getHour() : persianDate.getHour())
                .append(":")
                .append(persianDate.getMinute() <= 9 ? "0" + persianDate.getMinute() : persianDate.getMinute()).toString();// 14:22
        StructDateTime dateTime = new StructDateTime();
        dateTime.date = nowDate;
        dateTime.time = nowTime;

        return dateTime;
    }

}
