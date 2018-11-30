package ir.amozkade.hamed.datetimepicker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import ir.amozkade.hamed.datetimepicker.databinding.ActivityTestBinding;

public class TestActivity extends AppCompatActivity {

    private ActivityTestBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_test);
        mBinding.setHandler(this);
        mBinding.dtTest.setDateTime("1396/12/30", "09:10");
    }

    public void getDateTaped() {
        mBinding.dtTest.getDateTime().getDate();
    }

}
