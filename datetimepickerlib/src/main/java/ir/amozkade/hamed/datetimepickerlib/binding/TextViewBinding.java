package ir.amozkade.hamed.datetimepickerlib.binding;

import android.graphics.Typeface;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class TextViewBinding {
    @BindingAdapter("customTypeFace")
    public static void setCustomTypeFace(TextView tv, Typeface typeface) {
        tv.setTypeface(typeface);
    }
}
