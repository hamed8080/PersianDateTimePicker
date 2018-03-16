package ir.amozkade.hamed.datetimepickerlib

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast

import com.shawnlin.numberpicker.NumberPicker


/**
 * Created by hamed on 1/10/2017
 */

class DateTimeNumberPicker : RelativeLayout, NumberPicker.OnValueChangeListener {
    private lateinit var view: View
    private lateinit var npmYear: NumberPicker
    private lateinit var npmMonth: NumberPicker
    private lateinit var npmDay: NumberPicker
    private lateinit var npmHour: NumberPicker
    private lateinit var npmMinute: NumberPicker
    private lateinit var txtYearHint: TextView
    private lateinit var txtMonthHint: TextView
    private lateinit var txtDayHint: TextView
    private lateinit var txtHourHint: TextView
    private lateinit var txtMinutesHint: TextView
    internal lateinit var txtTitle: TextView
    private lateinit var txtSelectedDate: TextView
    private var hideDate = false
    private var hideTime = false
    private var today = CDateTime()
    private var isBirthDate: Boolean = false
    private var increasedYear = false


    val dateString: String?
        get() {

            val year = year
            val month = month
            val day = day
            return if (year != null && month != null && day != null) {
                "$year/$month/$day"
            } else {
                null
            }
        }

    private val day: String?
        get() = if (npmDay.value < 10) {
            "0" + npmDay.value
        } else npmDay.value.toString()

    private val month: String?
        get() = if (npmMonth.value < 10) {
            "0" + npmMonth.value
        } else npmMonth.value.toString()

    private val year: String?
        get() = npmYear.value.toString().substring(2, 4)


    val timeString: String?
        get() {
            if (Integer.valueOf(hour) > 23 && Integer.valueOf(minute) > 59) {
                Toast.makeText(context, "ساعت اشتباه تنظیم شده است", Toast.LENGTH_LONG).show()
                return "00:00"
            }
            return if (hour != null && minute != null) {
                hour + ":" + minute
            } else {
               return null
            }
        }


    private val hour: String
        get() = if (npmHour.value < 10) {
            "0" + npmHour.value
        } else npmHour.value.toString()

    private val minute: String
        get() = if (npmMinute.value < 10) {
            "0" + npmMinute.value
        } else npmMinute.value.toString()

    constructor(context: Context) : super(context) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize()
    }


    fun setTypedArrayValues(ta: TypedArray) {
        try {
            isBirthDate = ta.getBoolean(R.styleable.CDateTime_isBirthDate, false)
            hideTime = ta.getBoolean(R.styleable.CDateTime_hideTime, false)
            hideDate = ta.getBoolean(R.styleable.CDateTime_hideDate, false)
        } finally {
            ta.recycle()
        }
    }


    private fun initialize() {

        view = LayoutInflater.from(context).inflate(R.layout.date_time_number_picker, null)
        txtTitle = view.findViewById(R.id.txtTitle) as TextView
        txtSelectedDate = view.findViewById(R.id.txtSelectedDate) as TextView
        npmYear = view.findViewById(R.id.npmYear) as NumberPicker
        npmYear.setOnValueChangedListener(this)
        npmMonth = view.findViewById(R.id.npmMonth) as NumberPicker
        npmDay = view.findViewById(R.id.npmDay) as NumberPicker
        npmDay.setOnValueChangedListener(this)
        npmHour = view.findViewById(R.id.npmHour) as NumberPicker
        npmHour.setOnValueChangedListener(this)
        npmMinute = view.findViewById(R.id.npmMinute) as NumberPicker
        npmMinute.setOnValueChangedListener(this)

        npmMonth.setOnValueChangedListener { picker, oldVal, newVal ->
            if (newVal > 6) {
                npmDay.maxValue = 30
            } else {
                npmDay.maxValue = 31
            }
            if (newVal == 12) {
                if (!increasedYear) {
                    npmYear.maxValue = npmYear.maxValue + 1
                    npmYear.wrapSelectorWheel = false
                    increasedYear = true
                }

            }
            npmDay.wrapSelectorWheel = false
            setSelectedDateTime()
        }
        //hint
        txtYearHint = view.findViewById(R.id.txtYearHint) as TextView
        txtMonthHint = view.findViewById(R.id.txtMonthHint) as TextView
        txtDayHint = view.findViewById(R.id.txtDayHint) as TextView
        txtHourHint = view.findViewById(R.id.txtHourHint) as TextView
        txtMinutesHint = view.findViewById(R.id.txtMinutesHint) as TextView
        if (this.childCount > 0) {
            this.removeAllViews()
        }
        if (hideTime) {
            setHideTime(true)
        }
        if (hideDate) {
            setHideDate(true)
        }
        val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        view.layoutParams = params
        this.addView(view)

    }

    private fun setSelectedDateTime() {
        txtSelectedDate.text = (dateString + "\t\t\t" + timeString).toString()
    }

    fun setDate(date: String?) {
        if (date != "") {
            val listDate = date!!.split("/".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
            npmYear.minValue = 1300 + Integer.valueOf(listDate[0])!!
            npmYear.value = 1300 + Integer.valueOf(listDate[0])!!
            npmYear.maxValue = 1300 + Integer.valueOf(listDate[0])!!
            if (Integer.valueOf(listDate[1]) > 11) {//if we have on 12 month and deliver date is next year adviser can set next year value
                npmYear.maxValue = 1300 + Integer.valueOf(listDate[0])!! + 1
            }
            try {
                val monthDisplay = arrayOfNulls<String>(13)
                for (i in 0..12) {
                    if (i < 9) {
                        monthDisplay[i] = ("0" + (i + 1)).toString()
                    } else {
                        monthDisplay[i] = (i + 1).toString()
                    }
                }
                npmMonth.displayedValues = monthDisplay
                npmMonth.value = Integer.valueOf(listDate[1])!!


                val dayDisplay = arrayOfNulls<String>(32)
                for (i in 0..31) {
                    if (i < 9) {
                        dayDisplay[i] = ("0" + (i + 1)).toString()
                    } else {
                        dayDisplay[i] = (i + 1).toString()
                    }
                }
                if (npmMonth.value > 6) {
                    npmDay.maxValue = 30
                } else {
                    npmDay.maxValue = 31
                }
                npmDay.wrapSelectorWheel = false
                npmDay.displayedValues = dayDisplay
                npmDay.value = Integer.valueOf(listDate[2])!!
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }

        }

    }


    fun setTime(time: String?) {
        if (time != "") {
            val listDate = time!!.split(":".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
            try {
                val hourDisplay = arrayOfNulls<String>(25)
                for (i in 0..24) {
                    if (i <= 9) {
                        hourDisplay[i] = ("0" + i).toString()
                    } else {
                        hourDisplay[i] = i.toString()
                    }
                }
                npmHour.displayedValues = hourDisplay
                npmHour.value = Integer.valueOf(listDate[0].trim({ it <= ' ' }))!!


                val minuteDisplay = arrayOfNulls<String>(60)
                for (i in 0..59) {
                    if (i <= 9) {
                        minuteDisplay[i] = ("0" + i).toString()
                    } else {
                        minuteDisplay[i] = i.toString()
                    }
                }
                npmMinute.displayedValues = minuteDisplay
                npmMinute.value = Integer.valueOf(listDate[1].trim({ it <= ' ' }))!!
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }

        }
    }

    fun setToday() {
        setDate(today.currentDateTimeObject.date)
        setTime(today.currentDateTimeObject.time)
        setSelectedDateTime()
    }

    fun setBirthDate() {
        npmYear.minValue = 1300 + Integer.valueOf(year)!! - 100
        npmYear.value = 1300 + Integer.valueOf(year)!!
        npmYear.maxValue = 1300 + Integer.valueOf(year)!!
        npmYear.wrapSelectorWheel = false
        setHideTime(true)
    }

    fun setHideTime(hideTime: Boolean) {
        if (hideTime) {
            npmHour.visibility = View.GONE
            npmMinute.visibility = View.GONE
            txtHourHint.visibility = View.GONE
            txtMinutesHint.visibility = View.GONE
        } else {
            npmHour.visibility = View.VISIBLE
            npmMinute.visibility = View.VISIBLE
            txtHourHint.visibility = View.VISIBLE
            txtMinutesHint.visibility = View.VISIBLE
        }
    }


    fun setHideDate(hideDate: Boolean) {
        if (hideDate) {
            npmYear.visibility = View.GONE
            npmMonth.visibility = View.GONE
            npmDay.visibility = View.GONE
            txtYearHint.visibility = View.GONE
            txtMonthHint.visibility = View.GONE
            txtDayHint.visibility = View.GONE
        } else {
            npmYear.visibility = View.VISIBLE
            npmMonth.visibility = View.VISIBLE
            npmDay.visibility = View.VISIBLE
            txtYearHint.visibility = View.VISIBLE
            txtMonthHint.visibility = View.VISIBLE
            txtDayHint.visibility = View.VISIBLE
        }
    }

    fun setTypeFace(textTypeFace: Typeface) {
        txtTitle.typeface = textTypeFace
        npmYear.typeface = textTypeFace
        npmMonth.typeface = textTypeFace
        npmDay.typeface = textTypeFace
        npmHour.typeface = textTypeFace
        npmMinute.typeface = textTypeFace
        txtSelectedDate.typeface = textTypeFace
        txtYearHint.typeface = textTypeFace
        txtMonthHint.typeface = textTypeFace
        txtDayHint.typeface = textTypeFace
        txtHourHint.typeface = textTypeFace
        txtMinutesHint.typeface = textTypeFace
    }

    override fun onValueChange(picker: NumberPicker, oldVal: Int, newVal: Int) {
        setSelectedDateTime()
    }

    fun setTitle(title: String) {
        txtTitle.text = title
    }
}
