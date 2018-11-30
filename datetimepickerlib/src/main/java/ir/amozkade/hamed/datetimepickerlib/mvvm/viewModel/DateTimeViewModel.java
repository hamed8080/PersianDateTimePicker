package ir.amozkade.hamed.datetimepickerlib.mvvm.viewModel;



import com.shawnlin.numberpicker.NumberPicker;

import androidx.lifecycle.ViewModel;
import ir.amozkade.hamed.datetimepickerlib.mvvm.model.DateTime;

public class DateTimeViewModel extends ViewModel {

    private DateTime dateTime;

    private NumberPicker.OnValueChangeListener yearListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            dateTime.setYear(newVal);
        }
    };

    private NumberPicker.OnValueChangeListener monthListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            dateTime.setMonth(newVal);
        }
    };

    private NumberPicker.OnValueChangeListener dayListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            dateTime.setDay(newVal);

        }
    };

    private NumberPicker.OnValueChangeListener hourListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            dateTime.setHour(newVal);
        }
    };

    private NumberPicker.OnValueChangeListener minuteListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            dateTime.setMinute(newVal);
        }
    };

    public DateTimeViewModel(DateTime dateTime){
        this.dateTime = dateTime;
    }


    public DateTime getDateTime() {
        return dateTime;
    }


    public NumberPicker.OnValueChangeListener getYearListener() {
        return yearListener;
    }

    public NumberPicker.OnValueChangeListener getMonthListener() {
        return monthListener;
    }

    public NumberPicker.OnValueChangeListener getDayListener() {
        return dayListener;
    }

    public NumberPicker.OnValueChangeListener getHourListener() {
        return hourListener;
    }

    public NumberPicker.OnValueChangeListener getMinuteListener() {
        return minuteListener;
    }
}
