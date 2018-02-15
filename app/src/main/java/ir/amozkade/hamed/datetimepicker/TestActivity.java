package ir.amozkade.hamed.datetimepicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ir.amozkade.hamed.datetimepickerlib.DateTime;

public class TestActivity extends AppCompatActivity {
    DateTime dtTest ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_dialog);
        dtTest = (DateTime) findViewById(R.id.dtTest);
        dtTest.setDate("96/11/30");
        dtTest.setTime("19:42");
        dtTest.setText();
    }
}
