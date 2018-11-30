package ir.amozkade.hamed.datetimepickerlib.mvvm.model;

import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;


import androidx.databinding.BaseObservable;
import ir.amozkade.hamed.datetimepickerlib.helper.CustomTypefaceSpan;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;

public class DateTimeButton extends BaseObservable implements Parcelable {

    private DateTime dateTime = new DateTime();
    private SpannableStringBuilder spanTitle, spanDateIC, spanDate, spanTimeIC, spanTime;
    private Typeface iconTypeFace, textTypeFace;

    public DateTimeButton(DateTime dateTime, Typeface iconTypeFace, Typeface textTypeFace) {
        this.dateTime = dateTime;
        this.iconTypeFace = iconTypeFace;
        this.textTypeFace = textTypeFace;
    }


    protected DateTimeButton(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DateTimeButton> CREATOR = new Creator<DateTimeButton>() {
        @Override
        public DateTimeButton createFromParcel(Parcel in) {
            return new DateTimeButton(in);
        }

        @Override
        public DateTimeButton[] newArray(int size) {
            return new DateTimeButton[size];
        }
    };

    public SpannableStringBuilder getSpanTitle() {
        return spanTitle;
    }

    public SpannableStringBuilder getSpanDateIC() {
        return spanDateIC;
    }

    public SpannableStringBuilder getSpanDate() {
        return spanDate;
    }

    public SpannableStringBuilder getSpanTimeIC() {
        return spanTimeIC;
    }

    public SpannableStringBuilder getSpanTime() {
        return spanTime;
    }

    public void setTitleTypeSpan(String title) {
        spanTitle = new SpannableStringBuilder(title);
        spanTitle.setSpan(new RelativeSizeSpan(1.1f), 0, title.length(), SPAN_INCLUSIVE_INCLUSIVE);

    }


    public void setupDateTime() {
        try {
            String dateIC = "\ue163";
            String dateString = dateTime.getYear() + "/" + dateTime.getMonth() + "/" + dateTime.getDay();

            spanDateIC = new SpannableStringBuilder(dateIC);
            spanDate = new SpannableStringBuilder(dateString);
            if (iconTypeFace != null) {
                spanDateIC.setSpan(new CustomTypefaceSpan("", iconTypeFace), 0, 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }

            String timeStringIC = "\ue121";
            String timeString = dateTime.getHourString() + ":" + dateTime.getMinuteString();
            spanTimeIC = new SpannableStringBuilder(timeStringIC);
            spanTime = new SpannableStringBuilder(timeString);
            spanTimeIC.setSpan(new RelativeSizeSpan(1.1f), 0, timeStringIC.length() - 1, SPAN_INCLUSIVE_INCLUSIVE);
            if (iconTypeFace != null) {
                spanTimeIC.setSpan(new CustomTypefaceSpan("", iconTypeFace), timeStringIC.length() - 1, timeStringIC.length(), SPAN_INCLUSIVE_INCLUSIVE);
            }
            spanTimeIC.setSpan(new RelativeSizeSpan(1.1f), timeStringIC.length() - 1, timeStringIC.length(), SPAN_INCLUSIVE_INCLUSIVE);


        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        notifyChange();
    }

    public Typeface getIconTypeFace() {
        return iconTypeFace;
    }

    public Typeface getTextTypeFace() {
        return textTypeFace;
    }


    public DateTime getDateTime() {
        return dateTime;
    }
}
