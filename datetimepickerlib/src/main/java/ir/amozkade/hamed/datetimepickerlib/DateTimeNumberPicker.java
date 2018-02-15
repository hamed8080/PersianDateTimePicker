package ir.amozkade.hamed.datetimepickerlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shawnlin.numberpicker.NumberPicker;


/**
 * Created by hamed on 1/10/2017
 */

public class DateTimeNumberPicker extends RelativeLayout implements NumberPicker.OnValueChangeListener {
    Context context;
    View view;
    NumberPicker npmYear, npmMonth, npmDay, npmHour, npmMinute;
    TextView txtYearHint, txtMonthHint, txtDayHint, txtHourHint, txtMinutesHint;
    TextView txtTitle, txtSelectedDate;
    public boolean hideDate = false;
    public boolean hideTime = false;
    CDateTime today = new CDateTime();
    private boolean isBirthDate;
    private boolean increasedYear = false;

    public DateTimeNumberPicker(Context context) {
        super(context);
        this.context = context;
        initialize();
    }

    public DateTimeNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initialize();
    }

    public DateTimeNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initialize();
    }


    public void setTypedArrayValues(TypedArray ta) {
        try {
            isBirthDate = ta.getBoolean(R.styleable.CDateTime_isBirthDate, false);
            hideTime = ta.getBoolean(R.styleable.CDateTime_hideTime, false);
            hideDate = ta.getBoolean(R.styleable.CDateTime_hideDate, false);
        } finally {
            ta.recycle();
        }
    }


    private void initialize() {

        view = LayoutInflater.from(context).inflate(R.layout.date_time_number_picker, null);
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        txtSelectedDate = (TextView) view.findViewById(R.id.txtSelectedDate);
        npmYear = (NumberPicker) view.findViewById(R.id.npmYear);
        npmYear.setOnValueChangedListener(this);
        npmMonth = (NumberPicker) view.findViewById(R.id.npmMonth);
        npmDay = (NumberPicker) view.findViewById(R.id.npmDay);
        npmDay.setOnValueChangedListener(this);
        npmHour = (NumberPicker) view.findViewById(R.id.npmHour);
        npmHour.setOnValueChangedListener(this);
        npmMinute = (NumberPicker) view.findViewById(R.id.npmMinute);
        npmMinute.setOnValueChangedListener(this);

        npmMonth.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal > 6) {
                    npmDay.setMaxValue(30);
                } else {
                    npmDay.setMaxValue(31);
                }
                if (newVal == 12) {
                    if (!increasedYear) {
                        npmYear.setMaxValue(npmYear.getMaxValue() + 1);
                        npmYear.setWrapSelectorWheel(false);
                        increasedYear = true;
                    }

                }
                npmDay.setWrapSelectorWheel(false);
                setSelectedDateTime();
            }
        });
        //hint
        txtYearHint = (TextView) view.findViewById(R.id.txtYearHint);
        txtMonthHint = (TextView) view.findViewById(R.id.txtMonthHint);
        txtDayHint = (TextView) view.findViewById(R.id.txtDayHint);
        txtHourHint = (TextView) view.findViewById(R.id.txtHourHint);
        txtMinutesHint = (TextView) view.findViewById(R.id.txtMinutesHint);
        if (this.getChildCount() > 0) {
            this.removeAllViews();
        }
        if (hideTime) {
            setHideTime(true);
        }
        if (hideDate) {
            setHideDate(true);
        }
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        this.addView(view);

    }

    private void setSelectedDateTime() {
        txtSelectedDate.setText(String.valueOf(getDateString() + "\t\t\t" + getTimeString()));
    }


    public String getDateString() {

        String year = getYear();
        String month = getMonth();
        String day = getDay();
        if (year != null && month != null && day != null) {
            return year + "/" + month + "/" + day;
        } else {
            return null;
        }
    }

    @Nullable
    private String getDay() {
        if (npmDay.getValue() < 10) {
            return "0" + npmDay.getValue();
        }
        return String.valueOf(npmDay.getValue());
    }

    @Nullable
    private String getMonth() {
        if (npmMonth.getValue() < 10) {
            return "0" + npmMonth.getValue();
        }
        return String.valueOf(npmMonth.getValue());
    }

    @Nullable
    private String getYear() {
        return String.valueOf(npmYear.getValue()).substring(2, 4);
    }

    private void errorMessage(String s, EditText editText) {
        editText.setError(s);
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
    }


    public void setDate(String date) {
        if (!date.equals("")) {
            String[] listDate = date.split("/");
            npmYear.setMinValue(1300 + Integer.valueOf(listDate[0]));
            npmYear.setValue(1300 + Integer.valueOf(listDate[0]));
            npmYear.setMaxValue(1300 + Integer.valueOf(listDate[0]));
            if (Integer.valueOf(listDate[1]) > 11) {//if we have on 12 month and deliver date is next year adviser can set next year value
                npmYear.setMaxValue(1300 + Integer.valueOf(listDate[0]) + 1);
            }
            try {
                String[] monthDisplay = new String[13];
                for (int i = 0; i <= 12; i++) {
                    if (i < 9) {
                        monthDisplay[i] = String.valueOf("0" + (i + 1));
                    } else {
                        monthDisplay[i] = String.valueOf(i + 1);
                    }
                }
                npmMonth.setDisplayedValues(monthDisplay);
                npmMonth.setValue(Integer.valueOf(listDate[1]));


                String[] dayDisplay = new String[32];
                for (int i = 0; i <= 31; i++) {
                    if (i < 9) {
                        dayDisplay[i] = String.valueOf("0" + (i + 1));
                    } else {
                        dayDisplay[i] = String.valueOf(i + 1);
                    }
                }
                if (npmMonth.getValue() > 6) {
                    npmDay.setMaxValue(30);
                } else {
                    npmDay.setMaxValue(31);
                }
                npmDay.setWrapSelectorWheel(false);
                npmDay.setDisplayedValues(dayDisplay);
                npmDay.setValue(Integer.valueOf(listDate[2]));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }

    }


    public String getTimeString() {
        if (Integer.valueOf(getHour()) > 23 && Integer.valueOf(getMinute()) > 59) {
            Toast.makeText(context, "ساعت اشتباه تنظیم شده است", Toast.LENGTH_LONG).show();
            return "00:00";
        }
        if (getHour() != null && getMinute() != null) {
            return getHour() + ":" + getMinute();
        } else {
            return null;
        }
    }


    private String getHour() {
        if (npmHour.getValue() < 10) {
            return "0" + npmHour.getValue();
        }
        return String.valueOf(npmHour.getValue());
    }

    private String getMinute() {
        if (npmMinute.getValue() < 10) {
            return "0" + npmMinute.getValue();
        }
        return String.valueOf(npmMinute.getValue());
    }


    public void setTime(String time) {
        if (!time.equals("")) {
            String[] listDate = time.split(":");
            try {
                String[] hourDisplay = new String[25];
                for (int i = 0; i <= 24; i++) {
                    if (i <= 9) {
                        hourDisplay[i] = String.valueOf("0" + (i));
                    } else {
                        hourDisplay[i] = String.valueOf(i);
                    }
                }
                npmHour.setDisplayedValues(hourDisplay);
                npmHour.setValue(Integer.valueOf(listDate[0].trim()));


                String[] minuteDisplay = new String[60];
                for (int i = 0; i <= 59; i++) {
                    if (i <= 9) {
                        minuteDisplay[i] = String.valueOf("0" + (i));
                    } else {
                        minuteDisplay[i] = String.valueOf(i);
                    }
                }
                npmMinute.setDisplayedValues(minuteDisplay);
                npmMinute.setValue(Integer.valueOf(listDate[1].trim()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }
    }

    public void setToday() {
        setDate(today.getCurrentDateTimeObject().date);
        setTime(today.getCurrentDateTimeObject().time);
        setSelectedDateTime();
    }

    public void setBirthDate() {
        npmYear.setMinValue((1300 + Integer.valueOf(getYear())) - 100);
        npmYear.setValue((1300 + Integer.valueOf(getYear())));
        npmYear.setMaxValue((1300 + Integer.valueOf(getYear())));
        npmYear.setWrapSelectorWheel(false);
        setHideTime(true);
    }

    public void setHideTime(boolean hideTime) {
        if (hideTime) {
            npmHour.setVisibility(GONE);
            npmMinute.setVisibility(GONE);
            txtHourHint.setVisibility(GONE);
            txtMinutesHint.setVisibility(GONE);
        } else {
            npmHour.setVisibility(VISIBLE);
            npmMinute.setVisibility(VISIBLE);
            txtHourHint.setVisibility(VISIBLE);
            txtMinutesHint.setVisibility(VISIBLE);
        }
    }


    public void setHideDate(boolean hideDate) {
        if (hideDate) {
            npmYear.setVisibility(GONE);
            npmMonth.setVisibility(GONE);
            npmDay.setVisibility(GONE);
            txtYearHint.setVisibility(GONE);
            txtMonthHint.setVisibility(GONE);
            txtDayHint.setVisibility(GONE);
        } else {
            npmYear.setVisibility(VISIBLE);
            npmMonth.setVisibility(VISIBLE);
            npmDay.setVisibility(VISIBLE);
            txtYearHint.setVisibility(VISIBLE);
            txtMonthHint.setVisibility(VISIBLE);
            txtDayHint.setVisibility(VISIBLE);
        }
    }

    public void setTypeFace(Typeface textTypeFace) {
        txtTitle.setTypeface(textTypeFace);
        npmYear.setTypeface(textTypeFace);
        npmMonth.setTypeface(textTypeFace);
        npmDay.setTypeface(textTypeFace);
        npmHour.setTypeface(textTypeFace);
        npmMinute.setTypeface(textTypeFace);
        txtSelectedDate.setTypeface(textTypeFace);
        txtYearHint.setTypeface(textTypeFace);
        txtMonthHint.setTypeface(textTypeFace);
        txtDayHint.setTypeface(textTypeFace);
        txtHourHint.setTypeface(textTypeFace);
        txtMinutesHint.setTypeface(textTypeFace);
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        setSelectedDateTime();
    }

    public void setTitle(String title) {
        txtTitle.setText(title);
    }
}
