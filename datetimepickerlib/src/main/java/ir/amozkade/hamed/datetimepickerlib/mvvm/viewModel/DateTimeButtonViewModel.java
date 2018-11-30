package ir.amozkade.hamed.datetimepickerlib.mvvm.viewModel;

import android.graphics.Typeface;


import androidx.lifecycle.ViewModel;
import ir.amozkade.hamed.datetimepickerlib.mvvm.model.DateTime;
import ir.amozkade.hamed.datetimepickerlib.mvvm.model.DateTimeButton;

public class DateTimeButtonViewModel extends ViewModel {

    private DateTimeButton dateTimeButton ;

    public DateTimeButtonViewModel(DateTime dateTime, Typeface iconTypeFace, Typeface textTypeFace){
        dateTimeButton = new DateTimeButton(dateTime,iconTypeFace,textTypeFace);
    }

    public DateTimeButton getDateTimeButton() {
        return dateTimeButton;
    }
}
