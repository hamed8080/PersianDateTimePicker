package ir.amozkade.hamed.datetimepickerlib.binding;

import android.graphics.Typeface;

import com.shawnlin.numberpicker.NumberPicker;

import androidx.databinding.BindingAdapter;

public class NumberPickerBindings {

    @BindingAdapter("valueChangeListener")
    public static void setValueChangeListener(NumberPicker npm, NumberPicker.OnValueChangeListener valueChangeListener) {
        npm.setOnValueChangedListener(valueChangeListener);
        npm.setWrapSelectorWheel(false);
    }

    @BindingAdapter("displayValues")
    public static void setDisplayValues(NumberPicker npm, String[] displayValues) {
        npm.setDisplayedValues(displayValues);
        npm.setWrapSelectorWheel(false);
    }
    
    @BindingAdapter("npMax")
    public static void setMax(NumberPicker npm, int max) {
        npm.setMaxValue(max);
        npm.setWrapSelectorWheel(false);
    }
    
    @BindingAdapter("npMin")
    public static void setMin(NumberPicker npm, int min) {
        npm.setMinValue(min);
        npm.setWrapSelectorWheel(false);
    } 
    
    @BindingAdapter("npValue")
    public static void setValue(NumberPicker npm, int value) {
        npm.setValue(value);
        npm.setWrapSelectorWheel(false);
    }

    @BindingAdapter("npTypeface")
    public static void setTypeface(NumberPicker npm, Typeface typeface) {
        npm.setTypeface(typeface);
        npm.setWrapSelectorWheel(false);
    }

}
