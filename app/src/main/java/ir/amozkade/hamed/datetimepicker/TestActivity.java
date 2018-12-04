package ir.amozkade.hamed.datetimepicker;

import android.os.Bundle;

import java.sql.Timestamp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import ir.amozkade.hamed.datetimepicker.databinding.ActivityTestBinding;
import ir.amozkade.hamed.datetimepickerlib.mvvm.view.DateListener;

public class TestActivity extends AppCompatActivity implements DateListener {

    private ActivityTestBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_test);
        mBinding.setHandler(this);
        mBinding.dtTest.listener = this;

        mBinding.dtTest.setDateTime(null, null);
//        mBinding.dtTest.setDateTime("1396/12/30", "09:10");

    }

    public void getDateTaped() {
        mBinding.dtTest.getDateTime().getDate();
    }

    @Override
    public void onDateChange(String date, String time, Timestamp timestamp) {

    }
}
