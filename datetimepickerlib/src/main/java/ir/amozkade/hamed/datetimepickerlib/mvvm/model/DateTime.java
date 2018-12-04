package ir.amozkade.hamed.datetimepickerlib.mvvm.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;


public class DateTime extends BaseObservable implements Parcelable {

    private Integer year, month, day, hour, minute;
    private Integer minYear = 1300, minMonth = 1, minDay = 1, minHour = 0, minMinute = 0;
    private Integer maxYear = 1800, maxMonth = 12, maxDay = 31, maxHour = 23, maxMinute = 59;
    private String[] yearDS, monthDS, dayDS, hourDS, minuteDS;//DisplayValues Arrays
    private String title;
    private String selectedDateString;

    private boolean isBirthDate, hideTime, hideDate;

    private Boolean increasedYear = false;


    public DateTime() {
    }

    //region parcelable

    protected DateTime(Parcel in) {
        if (in.readByte() == 0) {
            year = null;
        } else {
            year = in.readInt();
        }
        if (in.readByte() == 0) {
            month = null;
        } else {
            month = in.readInt();
        }
        if (in.readByte() == 0) {
            day = null;
        } else {
            day = in.readInt();
        }
        if (in.readByte() == 0) {
            hour = null;
        } else {
            hour = in.readInt();
        }
        if (in.readByte() == 0) {
            minute = null;
        } else {
            minute = in.readInt();
        }
        title = in.readString();
        isBirthDate = in.readByte() != 0;
        hideTime = in.readByte() != 0;
        hideDate = in.readByte() != 0;
        byte tmpIncreasedYear = in.readByte();
        increasedYear = tmpIncreasedYear == 0 ? null : tmpIncreasedYear == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (year == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(year);
        }
        if (month == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(month);
        }
        if (day == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(day);
        }
        if (hour == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(hour);
        }
        if (minute == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(minute);
        }
        dest.writeString(title);
        dest.writeByte((byte) (isBirthDate ? 1 : 0));
        dest.writeByte((byte) (hideTime ? 1 : 0));
        dest.writeByte((byte) (hideDate ? 1 : 0));
        dest.writeByte((byte) (increasedYear == null ? 0 : increasedYear ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DateTime> CREATOR = new Creator<DateTime>() {
        @Override
        public DateTime createFromParcel(Parcel in) {
            return new DateTime(in);
        }

        @Override
        public DateTime[] newArray(int size) {
            return new DateTime[size];
        }
    };

    //endregion

    public String[] getToday() {
        String[] today = new String[2];
        PersianDate persianDate = new PersianDate();
        PersianDateFormat pFormat = new PersianDateFormat("Y/m/d");
        String nowDate = pFormat.format(persianDate);//96/05/20
        String nowTime;// 14:22
        if (persianDate.getHour() <= 9)
            nowTime = "0" + persianDate.getHour() + ":" + (persianDate.getMinute() <= 9 ? "0" + persianDate.getMinute() : persianDate.getMinute());
        else
            nowTime = persianDate.getHour() + ":" + (persianDate.getMinute() <= 9 ? "0" + persianDate.getMinute() : persianDate.getMinute());

        today[0] = nowDate;
        today[1] = nowTime;
        return today;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
        getSelectedDateString();
        notifyChange();
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
        calculateMonthCount();
        getSelectedDateString();
        notifyChange();
    }

    private void calculateMonthCount() {
        if (month > 6) {
            maxDay = 30;
        } else {
            maxDay = 31;
        }
        if (month == 12) {
            if (!increasedYear) {
                maxYear = maxYear + 1;
                increasedYear = true;
            }
        }
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
        getSelectedDateString();
        notifyChange();
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
        getSelectedDateString();
        notifyChange();
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
        getSelectedDateString();
        notifyChange();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isBirthDate() {
        return isBirthDate;
    }

    public void setBirthDate(boolean birthDate) {
        isBirthDate = birthDate;
    }

    public boolean isHideTime() {
        return hideTime;
    }

    public void setHideTime(boolean hideTime) {
        this.hideTime = hideTime;
    }

    public boolean isHideDate() {
        return hideDate;
    }

    public void setHideDate(boolean hideDate) {
        this.hideDate = hideDate;
    }

    public Boolean getIncreasedYear() {
        return increasedYear;
    }

    public void setIncreasedYear(Boolean increasedYear) {
        this.increasedYear = increasedYear;
    }

    public Integer getMinYear() {
        return minYear;
    }

    public void setMinYear(Integer minYear) {
        this.minYear = minYear;
    }

    public Integer getMinMonth() {
        return minMonth;
    }

    public void setMinMonth(Integer minMonth) {
        this.minMonth = minMonth;
    }

    public Integer getMinDay() {
        return minDay;
    }

    public void setMinDay(Integer minDay) {
        this.minDay = minDay;
    }

    public Integer getMinHour() {
        return minHour;
    }

    public void setMinHour(Integer minHour) {
        this.minHour = minHour;
    }

    public Integer getMinMinute() {
        return minMinute;
    }

    public void setMinMinute(Integer minMinute) {
        this.minMinute = minMinute;
    }

    public Integer getMaxYear() {
        return maxYear;
    }

    public void setMaxYear(Integer maxYear) {
        this.maxYear = maxYear;
    }

    public Integer getMaxMonth() {
        return maxMonth;
    }

    public void setMaxMonth(Integer maxMonth) {
        this.maxMonth = maxMonth;
    }

    public Integer getMaxDay() {
        return maxDay;
    }

    public void setMaxDay(Integer maxDay) {
        this.maxDay = maxDay;
    }

    public Integer getMaxHour() {
        return maxHour;
    }

    public void setMaxHour(Integer maxHour) {
        this.maxHour = maxHour;
    }

    public Integer getMaxMinute() {
        return maxMinute;
    }

    public void setMaxMinute(Integer maxMinute) {
        this.maxMinute = maxMinute;
    }

    //////////////////////////////////////////////BUSINESS///////////////////////////////////////////////
    public String getDate() {
        if (year == null || month == null || day == null) {
            return null;
        }
        String tempYear = String.valueOf(year);
        if (year <= 9) {
            tempYear = "0" + year;
        }
        String tempMonth = String.valueOf(month);
        if (month <= 9) {
            tempMonth = "0" + month;
        }
        String tempDay = String.valueOf(day);
        if (day <= 9) {
            tempDay = "0" + day;
        }
        return tempYear + "/" + tempMonth + "/" + tempDay;
    }

    public String getTime() {
        if (hour == null || minute == null) {
            return null;
        }
        String tempHour = String.valueOf(hour);
        if (hour <= 9) {
            tempHour = "0" + hour;
        }
        String tempMinute = String.valueOf(minute);
        if (minute <= 9) {
            tempMinute = "0" + minute;
        }
        return tempHour + ":" + tempMinute;
    }

    @Nullable
    public String getYearString() {
        if(year==null)return null;
        return String.valueOf(year);
    }

    @Nullable
    public String getMonthString() {
        if(month==null)return null;
        if (month < 10) {
            return "0" + month;
        }
        return String.valueOf(month);
    }

    @Nullable
    public String getDayString() {
        if(day==null)return null;
        if (day < 10) {
            return "0" + day;
        }
        return String.valueOf(day);
    }

    public String getHourString() {
        if (hour == null) return null;
        if (hour < 10) {
            return "0" + hour;
        }
        return String.valueOf(hour);
    }

    public String getMinuteString() {
        if (minute == null) return null;
        if (minute < 10) {
            return "0" + minute;
        }
        return String.valueOf(minute);
    }

    public String getSelectedDateString() {
        try {
            if (getDateString() != null)
                selectedDateString = String.valueOf(getDateString());
            if (getTimeString() != null)
                selectedDateString += String.valueOf("\t\t\t" + getTimeString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return selectedDateString;
    }

    public String getTimeString() {
        if (getHour() != null && getMinute() != null) {
            return getHourString() + ":" + getMinuteString();
        } else {
            return null;
        }
    }

    public String getDateString() {
        String year = getYearString();
        String month = getMonthString();
        String day = getDayString();
        if (year != null && month != null && day != null) {
            return year + "/" + month + "/" + day;
        } else {
            return null;
        }
    }


    public void setDate(String date) {
        if (date != null && !date.equals("")) {
            String[] listDate = date.split("/");
            if (Integer.valueOf(listDate[0]) < 1300) {
                Log.i("TAG", "date must larger than 1300");
                return;
            }

            minYear = Integer.valueOf(listDate[0]);
            year = Integer.valueOf(listDate[0]);
            maxYear = Integer.valueOf(listDate[0]);
            if (Integer.valueOf(listDate[1]) > 11) {//if we have on 12 month and deliver date is next year adviser can set next year value
                maxYear = Integer.valueOf(listDate[0]) + 1;
            }
            try {
                monthDS = getMinuteDisplayValues();
                month = Integer.valueOf(listDate[1]);
                maxDay = month > 6 ? 30 : 31;
                dayDS = getDayDisplayValues();
                day = Integer.valueOf(listDate[2]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        notifyChange();
    }

    public void setTime(String time) {
        if (time != null && !time.equals("")) {
            String[] listDate = time.split(":");
            try {
                hourDS = getHourDisplayValues();
                hour = Integer.valueOf(listDate[0].trim());
                minuteDS = getMinuteDisplayValues();
                minute = Integer.valueOf(listDate[1].trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        notifyChange();
    }


    public String[] getMonthDisplayValues() {
        String[] monthDisplay = new String[13];
        for (int i = 0; i <= 12; i++) {
            if (i < 9) {
                monthDisplay[i] = String.valueOf("0" + (i + 1));
            } else {
                monthDisplay[i] = String.valueOf(i + 1);
            }
        }
        return monthDisplay;
    }

    public String[] getDayDisplayValues() {
        String[] dayDisplay = new String[32];
        for (int i = 0; i <= 31; i++) {
            if (i < 9) {
                dayDisplay[i] = String.valueOf("0" + (i + 1));
            } else {
                dayDisplay[i] = String.valueOf(i + 1);
            }
        }
        return dayDisplay;
    }

    public String[] getHourDisplayValues() {
        String[] hourDisplay = new String[25];
        for (int i = 0; i <= 24; i++) {
            if (i <= 9) {
                hourDisplay[i] = String.valueOf("0" + (i));
            } else {
                hourDisplay[i] = String.valueOf(i);
            }
        }
        return hourDisplay;
    }

    public String[] getMinuteDisplayValues() {

        String[] minuteDisplay = new String[60];
        for (int i = 0; i <= 59; i++) {
            if (i <= 9) {
                minuteDisplay[i] = String.valueOf("0" + (i));
            } else {
                minuteDisplay[i] = String.valueOf(i);
            }
        }
        return minuteDisplay;
    }

    public void setToday() {
        PersianDate persianDate = new PersianDate();
        if (!hideDate) {
            year = persianDate.getShYear();
            month = persianDate.getShMonth();
            day = persianDate.getShDay();
        }
        if (!hideTime) {
            hour = persianDate.getHour();
            minute = persianDate.getMinute();
        }

        notifyChange();
    }

    //////////////////////////////////////////////BUSINESS///////////////////////////////////////////////

}
