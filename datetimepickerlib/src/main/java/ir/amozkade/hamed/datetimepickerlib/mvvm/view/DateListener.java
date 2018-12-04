package ir.amozkade.hamed.datetimepickerlib.mvvm.view;

import java.sql.Timestamp;

public interface DateListener {
    void onDateChange(String date, String time, Timestamp timestamp);
}
