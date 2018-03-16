package ir.amozkade.hamed.datetimepicker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import ir.amozkade.hamed.datetimepickerlib.DateTime

class TestActivity : AppCompatActivity() {
    lateinit var dtTest: DateTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_dialog)
        dtTest = findViewById(R.id.dtTest) as DateTime
        //        dtTest.setDate("96/11/30");
        //        dtTest.setTime("19:42");
        dtTest.setText()
        var a ="test"
        var b ="a value is $a"
    }

    fun click(view: View) {
        for (i in 1..10){
        }
        dtTest.date
        dtTest.time
    }
}
