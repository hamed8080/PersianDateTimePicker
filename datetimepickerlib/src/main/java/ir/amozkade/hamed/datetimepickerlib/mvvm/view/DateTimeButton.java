package ir.amozkade.hamed.datetimepickerlib.mvvm.view;


import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.LinearLayout;


import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import ir.amozkade.hamed.datetimepickerlib.R;
import ir.amozkade.hamed.datetimepickerlib.databinding.DateTimeButtonBinding;
import ir.amozkade.hamed.datetimepickerlib.mvvm.model.DateTime;
import ir.amozkade.hamed.datetimepickerlib.mvvm.viewModel.DateTimeButtonViewModel;


public class DateTimeButton extends ConstraintLayout implements DateTimeDialogListener {

    private DateTimeButtonBinding mBinding;
    private DateTimeButtonViewModel dateTimeButtonViewModel;
    private AlertDialog dialog;
    private DateTime dateTime;
    private String typeFaceTextFileName;
    private Typeface textTypeFace, iconTypeFace;
    private String typeFaceIconFileName = "segmdl2.ttf";
    private DateTimeDialog dateTimeDialog;


    public DateTimeButton(Context context) {
        super(context);
    }

    public DateTimeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DateTimeButton);
        setTypedArrayValues(ta);
        initialize(context);
    }

    public DateTimeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DateTimeButton);
        setTypedArrayValues(ta);
        initialize(context);
    }

    public void setTypedArrayValues(TypedArray ta) {
        try {
            String title = ta.getString(R.styleable.DateTimeButton_title);
            boolean isBirthDate = ta.getBoolean(R.styleable.DateTimeButton_isBirthDate, false);
            boolean hideTime = ta.getBoolean(R.styleable.DateTimeButton_hideTime, false);
            boolean hideDate = ta.getBoolean(R.styleable.DateTimeButton_hideDate, false);
            typeFaceTextFileName = ta.getString(R.styleable.DateTimeButton_typeFaceText);
            if (ta.getString(R.styleable.DateTimeButton_typeFaceIcon) != null && !ta.getString(R.styleable.DateTimeButton_typeFaceIcon).equals("")) {
                typeFaceIconFileName = ta.getString(R.styleable.DateTimeButton_typeFaceIcon);
            }
            dateTime = new DateTime();
            dateTime.setTitle(title);
            dateTime.setHideDate(hideDate);
            dateTime.setHideTime(hideTime);
            dateTime.setBirthDate(isBirthDate);

        } finally {
            ta.recycle();
        }
    }

    public void initialize(Context context) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.date_time_button, null, false);
        if (typeFaceIconFileName != null) {
            iconTypeFace = Typeface.createFromAsset(context.getAssets(), typeFaceIconFileName);
        }
        if (typeFaceTextFileName != null) {
            textTypeFace = Typeface.createFromAsset(context.getAssets(), typeFaceTextFileName);
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBinding.layout.setLayoutParams(params);
        dateTimeButtonViewModel = new DateTimeButtonViewModel(dateTime, iconTypeFace, textTypeFace);
        dateTimeButtonViewModel.getDateTimeButton().setTitleTypeSpan(dateTime.getTitle());
        mBinding.setVm(dateTimeButtonViewModel);
        mBinding.setHandler(this);
        this.addView(mBinding.getRoot());
    }

    public void openDialogDateTimePicker() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dateTime.setDate(dateTimeButtonViewModel.getDateTimeButton().getDateTime().getDate());
        dateTime.setTime(dateTimeButtonViewModel.getDateTimeButton().getDateTime().getTime());
        dateTimeDialog = new DateTimeDialog(getContext(), dateTime, textTypeFace, this);
        dialog.setView(dateTimeDialog);
        DateTimeButton.this.dialog = dialog.show();
        WindowManager wm = (WindowManager) dialog.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
    }

    public DateTimeButtonViewModel getDateTimeButtonViewModel() {
        return dateTimeButtonViewModel;
    }

    @Override
    public void onDismiss() {
        dialog.dismiss();
    }

    @Override
    public void onSubmit() {
        dateTimeButtonViewModel.getDateTimeButton().setupDateTime();
        onDismiss();
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(String date, String time) {
        dateTime.setDate(date);
        dateTime.setTime(time);
        dateTimeButtonViewModel.getDateTimeButton().setupDateTime();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mBinding.layout.setEnabled(enabled);
        mBinding.layout.setAlpha(enabled?1f:0.5f);
    }
}
