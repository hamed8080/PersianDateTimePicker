package ir.amozkade.hamed.datetimepickerlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;


/**
 * Created by hamed
 */

public class DateTime extends LinearLayout implements View.OnTouchListener {
    SpannableStringBuilder spanTitle, spanDateIC, spanDate, spanTimeIC, spanTime;
    TextView txtDateIC, txtDate, txtTitle, txtTime, txtTimeIC;
    int year, month, day, hour, minute;
    Context context;
    View view;
    Typeface textTypeFace, iconTypeFace;
    private boolean isBirthDate, hideTime, hideDate;
    private String typeFaceTextFileName;
    private String typeFaceIconFileName="segmdl2.ttf";
    private String dateString, timeString;
    private String title;

    public DateTime(Context context) {
        super(context);
        initialize(context);
        this.context = context;
    }

    public DateTime(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CDateTime);
        setTypedArrayValues(ta);
        initialize(context);
    }

    public DateTime(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CDateTime);
        setTypedArrayValues(ta);
        initialize(context);

    }

    public void setTypedArrayValues(TypedArray ta) {
        try {
            title = ta.getString(R.styleable.CDateTime_title);
            isBirthDate = ta.getBoolean(R.styleable.CDateTime_isBirthDate, false);
            hideTime = ta.getBoolean(R.styleable.CDateTime_hideTime, false);
            hideDate = ta.getBoolean(R.styleable.CDateTime_hideDate, false);
            typeFaceTextFileName = ta.getString(R.styleable.CDateTime_typeFaceText);
            if(ta.getString(R.styleable.CDateTime_typeFaceIcon)!=null && !ta.getString(R.styleable.CDateTime_typeFaceIcon).equals("")){
               typeFaceIconFileName =  ta.getString(R.styleable.CDateTime_typeFaceIcon);
            }
        } finally {
            ta.recycle();
        }
    }
    public void initialize(Context context) {
        if (typeFaceIconFileName != null) {
            iconTypeFace = Typeface.createFromAsset(context.getAssets(), typeFaceIconFileName);
        }
        if (typeFaceTextFileName != null) {
            textTypeFace = Typeface.createFromAsset(context.getAssets(), typeFaceTextFileName);
        }

        view = LayoutInflater.from(context).inflate(R.layout.date_time, null);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        txtDateIC = (TextView) view.findViewById(R.id.txtDateIC);
        txtDate = (TextView) view.findViewById(R.id.txtDate);
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        txtTime = (TextView) view.findViewById(R.id.txtTime);
        txtTimeIC = (TextView) view.findViewById(R.id.txtTimeIC);
        view.setOnTouchListener(this);

        if (hideTime) {
            hideTime();
        }
        if (isBirthDate) {
            hideTime();
        }
        if (hideDate) {
            hideDate();
        }
        if (title != null) {
            setTitle(title);
        }

        this.addView(view);
    }


    public void setDate(String date) {
        dateString = date;
        if (!date.equals("")) {
            String[] listDate = date.split("/");
            try {
                year = Integer.valueOf(listDate[0]);
                String tempMonth = listDate[1].trim();
                month = Integer.valueOf(listDate[1]);
                if (month <= 9) {
                    tempMonth = "0" + Integer.valueOf(listDate[1]);
                }

                String tempDay = listDate[2].trim();
                day = Integer.valueOf(listDate[2]);
                if (day <= 9) {
                    tempDay = "0" + Integer.valueOf(listDate[2]);
                }
                String dateIC = "\ue163";
                String dateString = year + "/" + tempMonth + "/" + tempDay;

                spanDateIC = new SpannableStringBuilder(dateIC);
                spanDate = new SpannableStringBuilder(dateString);
                if (iconTypeFace != null) {
                    spanDateIC.setSpan(new CustomTypefaceSpan("", iconTypeFace), 0, 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                }
                txtDateIC.setText(spanDateIC);
                txtDate.setText(spanDate);
                txtDate.setTypeface(textTypeFace);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    public void setTitle(String title) {
        spanTitle = new SpannableStringBuilder(title);
        spanTitle.setSpan(new RelativeSizeSpan(1.1f), 0, title.length(), SPAN_INCLUSIVE_INCLUSIVE);
        txtTitle.setText(spanTitle);
        txtTitle.setTypeface(textTypeFace);
    }

    public void setTime(String time) {
        timeString = time;
        if (!time.equals("")) {
            String[] listDate = time.split(":");
            try {

                String tempHour = listDate[0].trim();
                hour = Integer.valueOf(listDate[0].trim());
                if (Integer.valueOf(listDate[0].trim()) <= 9) {
                    tempHour = "0" + Integer.valueOf(listDate[0].trim());
                }
                minute = Integer.valueOf(listDate[1].trim());
                String tempMinute = listDate[1].trim();
                if (Integer.valueOf(listDate[1].trim()) <= 9) {
                    tempMinute = "0" + Integer.valueOf(listDate[1].trim());
                }
                String timeStringIC = "\ue121";
                String timeString = tempHour + ":" + tempMinute;
                spanTimeIC = new SpannableStringBuilder(timeStringIC);
                spanTime = new SpannableStringBuilder(timeString);
                spanTimeIC.setSpan(new RelativeSizeSpan(1.1f), 0, timeStringIC.length() - 1, SPAN_INCLUSIVE_INCLUSIVE);
                if (iconTypeFace != null) {
                    spanTimeIC.setSpan(new CustomTypefaceSpan("", iconTypeFace), timeStringIC.length() - 1, timeStringIC.length(), SPAN_INCLUSIVE_INCLUSIVE);
                }
                spanTimeIC.setSpan(new RelativeSizeSpan(1.1f), timeStringIC.length() - 1, timeStringIC.length(), SPAN_INCLUSIVE_INCLUSIVE);

                txtTimeIC.setText(spanTimeIC);
                txtTime.setText(spanTime);
                txtTime.setTypeface(textTypeFace);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }
    }

    AlertDialog myDialog;
    DateTimeNumberPicker dateTime;

    public void setText() {
        if (spanDate != null && spanTime != null) {

            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDialogDateTimePicker();
                }
            });
        }

    }

    private void openDialogDateTimePicker() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        View dialogViewStatement = LayoutInflater.from(getContext()).inflate(R.layout.dialog_date_picker, null);
        Button btnToday = (Button) dialogViewStatement.findViewById(R.id.btnToday);
        Button btnNegative = (Button) dialogViewStatement.findViewById(R.id.btnNegative);
        Button btnPositive = (Button) dialogViewStatement.findViewById(R.id.btnPositive);
        btnToday.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dateTime.setToday();
            }
        });
        btnToday.setTypeface(textTypeFace);
        btnNegative.setTypeface(textTypeFace);
        btnPositive.setTypeface(textTypeFace);
        btnPositive.setOnTouchListener(this);
        btnNegative.setOnTouchListener(this);
        btnToday.setOnTouchListener(this);

        dateTime = (DateTimeNumberPicker) dialogViewStatement.findViewById(R.id.dateTime);
        dialog.setView(dialogViewStatement);
        myDialog = dialog.show();
        dateTime.setHideDate(hideDate);
        dateTime.setHideTime(hideTime);
        dateTime.setTypeFace(textTypeFace);
        dateTime.setTitle(title);
        dateTime.setTime(timeString);
        dateTime.setDate(dateString);
        if (isBirthDate) {
            dateTime.setBirthDate();
        }


        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        //myDialog.getWindow().setLayout(size.x * 95 / 100, size.y * 95 / 100);
        myDialog.setCancelable(false);
        btnNegative.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        btnPositive.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime(dateTime.getTimeString());
                setDate(dateTime.getDateString());
                setText();
                myDialog.dismiss();
            }
        });
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        view.setOnClickListener(null);
        view.setClickable(false);
        view.setFocusable(false);
    }


    public void hideTime() {
        txtTime.setVisibility(GONE);
        txtTimeIC.setVisibility(GONE);
    }

    public void showTime() {
        txtTime.setVisibility(VISIBLE);
        txtTimeIC.setVisibility(VISIBLE);
    }

    public void hideDate() {
        txtDate.setVisibility(GONE);
        txtDateIC.setVisibility(GONE);
    }

    public void showDate() {
        txtDate.setVisibility(VISIBLE);
        txtDateIC.setVisibility(VISIBLE);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() ==MotionEvent.ACTION_MASK || event.getAction() ==MotionEvent.ACTION_HOVER_MOVE) {
            view.setAlpha(.3f);
        }else {
            view.setAlpha(1f);
        }
        return false;
    }
    public  String getDate(){
        String tempYear =String.valueOf(year);
        if (year <= 9) {
            tempYear = "0" + year;
        }
        String tempMonth = String.valueOf(month);
        if (month <= 9) {
            tempMonth = "0" +month;
        }
        String tempDay = String.valueOf(day);
        if (day <= 9) {
            tempDay = "0" +day;
        }
        return   tempYear + "/" + tempMonth + "/" + tempDay;
    }

    public  String getTime(){
        String tempHour =String.valueOf(hour);
        if (hour <= 9) {
            tempHour = "0" + hour;
        }
        String tempMinute = String.valueOf(minute);
        if (minute <= 9) {
            tempMinute = "0" +minute;
        }
        return tempHour + ":" + tempMinute;
    }
}
