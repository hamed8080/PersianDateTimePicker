package ir.amozkade.hamed.datetimepickerlib

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Point
import android.graphics.Typeface
import android.support.v7.app.AlertDialog
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

import android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE


/**
 * Created by hamed
 */

class DateTime : LinearLayout, View.OnTouchListener {
    private var spanTitle: SpannableStringBuilder? = null
    private var spanDateIC: SpannableStringBuilder? = null
    private var spanDate: SpannableStringBuilder? = null
    private var spanTimeIC: SpannableStringBuilder? = null
    private var spanTime: SpannableStringBuilder? = null
    private var txtDateIC: TextView? = null
    private var txtDate: TextView? = null
    private var txtTitle: TextView? = null
    private var txtTime: TextView? = null
    private var txtTimeIC: TextView? = null
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    private var hour: Int = 0
    private var minute: Int = 0
    //private var context: Context
    private lateinit var view: View
    private lateinit var textTypeFace: Typeface
    private var iconTypeFace: Typeface? = null
    private var isBirthDate: Boolean = false
    private var hideTime: Boolean = false
    private var hideDate: Boolean = false
    private var typeFaceTextFileName: String? = null
    private var typeFaceIconFileName: String? = "segmdl2.ttf"
    private var dateString: String? = null
    private var timeString: String? = null
    private var title: String? = null

    private lateinit var myDialog: AlertDialog
    private lateinit var dateTime: DateTimeNumberPicker

    var date: String?
        get() {
            var tempYear = year.toString()
            if (year <= 9) {
                tempYear = "0" + year
            }
            var tempMonth = month.toString()
            if (month <= 9) {
                tempMonth = "0" + month
            }
            var tempDay = day.toString()
            if (day <= 9) {
                tempDay = "0" + day
            }
            return "$tempYear/$tempMonth/$tempDay"
        }
        set(date) {
            dateString = date
            if (date != "") {
                val listDate = date!!.split("/".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                try {
                    year = Integer.valueOf(listDate[0])!!
                    var tempMonth = listDate[1].trim({ it <= ' ' })
                    month = Integer.valueOf(listDate[1])!!
                    if (month <= 9) {
                        tempMonth = "0" + Integer.valueOf(listDate[1])!!
                    }

                    var tempDay = listDate[2].trim({ it <= ' ' })
                    day = Integer.valueOf(listDate[2])!!
                    if (day <= 9) {
                        tempDay = "0" + Integer.valueOf(listDate[2])!!
                    }
                    val dateIC = "\ue163"
                    val dateString = year.toString() + "/" + tempMonth + "/" + tempDay

                    spanDateIC = SpannableStringBuilder(dateIC)
                    spanDate = SpannableStringBuilder(dateString)
                    if (iconTypeFace != null) {
                        spanDateIC?.setSpan(CustomTypefaceSpan("", iconTypeFace!!), 0, 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
                    }
                    txtDateIC?.text = spanDateIC
                    txtDate?.text = spanDate
                    txtDate?.typeface = textTypeFace
                } catch (e: NumberFormatException) {
                    e.printStackTrace()
                }

            }
        }

    var time: String?
        get() {
            var tempHour = hour.toString()
            if (hour <= 9) {
                tempHour = "0" + hour
            }
            var tempMinute = minute.toString()
            if (minute <= 9) {
                tempMinute = "0" + minute
            }
            return tempHour + ":" + tempMinute
        }
        set(time) {
            timeString = time
            if (time != "") {
                val listDate = time!!.split(":".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                try {

                    var tempHour = listDate[0].trim({ it <= ' ' })
                    hour = Integer.valueOf(listDate[0].trim({ it <= ' ' }))!!
                    if (Integer.valueOf(listDate[0].trim({ it <= ' ' })) <= 9) {
                        tempHour = "0" + Integer.valueOf(listDate[0].trim({ it <= ' ' }))!!
                    }
                    minute = Integer.valueOf(listDate[1].trim({ it <= ' ' }))!!
                    var tempMinute = listDate[1].trim({ it <= ' ' })
                    if (Integer.valueOf(listDate[1].trim({ it <= ' ' })) <= 9) {
                        tempMinute = "0" + Integer.valueOf(listDate[1].trim({ it <= ' ' }))!!
                    }
                    val timeStringIC = "\ue121"
                    val timeString = tempHour + ":" + tempMinute
                    spanTimeIC = SpannableStringBuilder(timeStringIC)
                    spanTime = SpannableStringBuilder(timeString)
                    spanTimeIC?.setSpan(RelativeSizeSpan(1.1f), 0, timeStringIC.length - 1, SPAN_INCLUSIVE_INCLUSIVE)
                    if (iconTypeFace != null) {
                        spanTimeIC?.setSpan(CustomTypefaceSpan("", iconTypeFace!!), timeStringIC.length - 1, timeStringIC.length, SPAN_INCLUSIVE_INCLUSIVE)
                    }
                    spanTimeIC?.setSpan(RelativeSizeSpan(1.1f), timeStringIC.length - 1, timeStringIC.length, SPAN_INCLUSIVE_INCLUSIVE)

                    txtTimeIC?.text = spanTimeIC
                    txtTime?.text = spanTime
                    txtTime?.typeface = textTypeFace
                } catch (e: NumberFormatException) {
                    e.printStackTrace()
                }

            }
        }

    constructor(context: Context) : super(context) {
        initialize(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.CDateTime)
        setTypedArrayValues(ta)
        initialize(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.CDateTime)
        setTypedArrayValues(ta)
        initialize(context)

    }

    private  fun setTypedArrayValues(ta: TypedArray) {
        try {
            title = ta.getString(R.styleable.CDateTime_title)
            isBirthDate = ta.getBoolean(R.styleable.CDateTime_isBirthDate, false)
            hideTime = ta.getBoolean(R.styleable.CDateTime_hideTime, false)
            hideDate = ta.getBoolean(R.styleable.CDateTime_hideDate, false)
            typeFaceTextFileName = ta.getString(R.styleable.CDateTime_typeFaceText)
            if (ta.getString(R.styleable.CDateTime_typeFaceIcon) != null && ta.getString(R.styleable.CDateTime_typeFaceIcon) != "") {
                typeFaceIconFileName = ta.getString(R.styleable.CDateTime_typeFaceIcon)
            }
        } finally {
            ta.recycle()
        }
    }

    private fun initialize(context: Context) {
        if (typeFaceIconFileName != null) {
            iconTypeFace = Typeface.createFromAsset(context.assets, typeFaceIconFileName)
        }
        if (typeFaceTextFileName != null) {
            textTypeFace = Typeface.createFromAsset(context.assets, typeFaceTextFileName)
        }

        view = LayoutInflater.from(context).inflate(R.layout.date_time, null)
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        view.layoutParams = params
        txtDateIC = view.findViewById(R.id.txtDateIC) as TextView
        txtDate = view.findViewById(R.id.txtDate) as TextView
        txtTitle = view.findViewById(R.id.txtTitle) as TextView
        txtTime = view.findViewById(R.id.txtTime) as TextView
        txtTimeIC = view.findViewById(R.id.txtTimeIC) as TextView
        view.setOnTouchListener(this)

        if (hideTime) {
            hideTime()
        }
        if (isBirthDate) {
            hideTime()
        }
        if (hideDate) {
            hideDate()
        }
        if (title != null) {
            setTitle(title!!)
        }

        this.addView(view)
    }

    private fun setTitle(title: String) {
        spanTitle = SpannableStringBuilder(title)
        spanTitle?.setSpan(RelativeSizeSpan(1.1f), 0, title.length, SPAN_INCLUSIVE_INCLUSIVE)
        txtTitle?.text = spanTitle
        txtTitle?.typeface = textTypeFace
    }

    fun setText() {
        if (spanDate != null && spanTime != null) {
            view.setOnClickListener { openDialogDateTimePicker() }
        } else {
            setDefaultDate()
            setDefaultTime()
            setText()
        }
    }

    private fun openDialogDateTimePicker() {
        val dialog = AlertDialog.Builder(getContext())
        val dialogViewStatement = LayoutInflater.from(getContext()).inflate(R.layout.dialog_date_picker, null)
        val btnToday = dialogViewStatement.findViewById(R.id.btnToday) as Button
        val btnNegative = dialogViewStatement.findViewById(R.id.btnNegative) as Button
        val btnPositive = dialogViewStatement.findViewById(R.id.btnPositive) as Button
        btnToday.setOnClickListener { dateTime.setToday() }
        btnToday.typeface = textTypeFace
        btnNegative.typeface = textTypeFace
        btnPositive.typeface = textTypeFace
        btnPositive.setOnTouchListener(this)
        btnNegative.setOnTouchListener(this)
        btnToday.setOnTouchListener(this)

        dateTime = dialogViewStatement.findViewById(R.id.dateTime) as DateTimeNumberPicker
        dialog.setView(dialogViewStatement)
        myDialog = dialog.show()
        dateTime.setHideDate(hideDate)
        dateTime.setHideTime(hideTime)
        dateTime.setTypeFace(textTypeFace)
        dateTime.setTitle(title!!)
        dateTime.setTime(timeString)
        dateTime.setDate(dateString)
        if (isBirthDate) {
            dateTime.setBirthDate()
        }


        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)
        //myDialog.getWindow().setLayout(size.x * 95 / 100, size.y * 95 / 100);
        myDialog.setCancelable(false)
        btnNegative.setOnClickListener { myDialog.dismiss() }
        btnPositive.setOnClickListener {
            time = dateTime.timeString
            date = dateTime.dateString
            setText()
            myDialog.dismiss()
        }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        view.setOnClickListener(null)
        view.isClickable = false
        view.isFocusable = false
    }


    private fun hideTime() {
        txtTime?.visibility = View.GONE
        txtTimeIC?.visibility = View.GONE
    }

    private fun showTime() {
        txtTime?.visibility = View.VISIBLE
        txtTimeIC?.visibility = View.VISIBLE
    }

    private fun hideDate() {
        txtDate?.visibility = View.GONE
        txtDateIC?.visibility = View.GONE
    }

    private fun showDate() {
        txtDate?.visibility = View.VISIBLE
        txtDateIC?.visibility = View.VISIBLE
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_MASK || event.action == MotionEvent.ACTION_HOVER_MOVE) {
            view.alpha = .3f
        } else {
            view.alpha = 1f
        }
        return false
    }

    private fun setDefaultDate() {
        dateString = CDateTime().currentDateTimeObject.date
        date = dateString
    }

    private fun setDefaultTime() {
        timeString = CDateTime().currentDateTimeObject.time
        time = timeString
    }
}
