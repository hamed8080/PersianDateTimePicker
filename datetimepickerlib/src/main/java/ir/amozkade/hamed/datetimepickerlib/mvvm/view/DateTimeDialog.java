package ir.amozkade.hamed.datetimepickerlib.mvvm.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import androidx.databinding.DataBindingUtil;
import ir.amozkade.hamed.datetimepickerlib.R;
import ir.amozkade.hamed.datetimepickerlib.databinding.DialogDatePickerBinding;
import ir.amozkade.hamed.datetimepickerlib.mvvm.model.DateTime;
import ir.amozkade.hamed.datetimepickerlib.mvvm.viewModel.DateTimeViewModel;

public class DateTimeDialog extends RelativeLayout {

    private DialogDatePickerBinding mBinding;
    private DateTimeViewModel dateTimeViewModel;
    private DateTime dateTime;
    private Typeface typeface;
    private DateTimeDialogListener listener;
    //region constructor

    public DateTimeDialog(Context context, DateTime dateTime,Typeface typeface,DateTimeDialogListener listener) {
        super(context);
        this.listener = listener;
        this.typeface = typeface ;
        this.dateTime = dateTime;
        initialize(context);
    }

    public DateTimeDialog(Context context) {
        super(context);
        initialize(context);
    }

    public DateTimeDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public DateTimeDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    //endregion

    public void initialize(Context context) {
        if (dateTime == null) return;
        dateTimeViewModel = new DateTimeViewModel(dateTime);
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_date_picker, null, false);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBinding.layout.setLayoutParams(params);
        mBinding.setVm(dateTimeViewModel);
        mBinding.setTypeFace(typeface);
        mBinding.setHandler(this);
        mBinding.setDialogListener(listener);
        this.addView(mBinding.getRoot());
    }

    public DateTimeViewModel getDateTimeViewModel() {
        return dateTimeViewModel;
    }
}
