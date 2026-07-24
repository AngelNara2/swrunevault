package com.example.swrunevault.controls

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.swrunevault.R
import com.example.swrunevault.extensions.colorRes
import com.example.swrunevault.models.RuneStat
import com.example.swrunevault.models.RuneStatType

class SubStatView(context: Context) : LinearLayout(context) {
    private val iconView: ImageView

    private val nameView: TextView

    private val valueView: TextView

    private val grindstoneView: TextView

    private val grindstoneMaxView: TextView

    private val totalView: TextView

    private val totalMaxView: TextView

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()

    private fun row(context: Context, weight: Float): LinearLayout {
        return LinearLayout(context).apply {
            orientation = HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setBackgroundColor(Color.TRANSPARENT)
            layoutParams = LayoutParams(
                0,
                LayoutParams.WRAP_CONTENT,
                weight)
        }
    }

    private fun textView(context: Context): TextView {
        return TextView(context).apply {
            textSize = 13f
            typeface = Typeface.DEFAULT_BOLD
            layoutParams = LayoutParams(
                0,
                LayoutParams.WRAP_CONTENT,
                1f
            )
        }
    }

    init {
        orientation = HORIZONTAL

        gravity = Gravity.CENTER_VERTICAL
        setBackgroundColor(Color.TRANSPARENT)
        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
        weightSum = 5f
        setPadding(dp(8),dp(8),dp(8),dp(8))

        val rowSubStat = row(context,2f)

        iconView = ImageView(context).apply {
            layoutParams = LayoutParams(50, 50)
            scaleType = ImageView.ScaleType.FIT_CENTER
        }

        nameView = TextView(context).apply {
            textSize = 13f
            typeface = Typeface.DEFAULT_BOLD
            setPadding(dp(10),0,0,0)
        }

        valueView = textView(context).apply { gravity = Gravity.CENTER }

        val rowGrindstone = row(context,1f)

        grindstoneView = textView(context).apply { gravity = Gravity.CENTER }

        grindstoneMaxView = textView(context).apply {
            setTextColor(context.colorRes(R.color.green))
            gravity = Gravity.START
        }

        val rowTotal = row(context,1f)

        totalView = textView(context).apply { gravity = Gravity.CENTER }

        totalMaxView = textView(context).apply {
            setTextColor(context.colorRes(R.color.green))
            gravity = Gravity.START
        }

        rowSubStat.addView(iconView)
        rowSubStat.addView(nameView)
        addView(rowSubStat)

        addView(valueView)

        rowGrindstone.addView(grindstoneView)
        rowGrindstone.addView(grindstoneMaxView)
        addView(rowGrindstone)

        rowTotal.addView(totalView)
        rowTotal.addView(totalMaxView)
        addView(rowTotal)
    }
    private var _indexSubStat: Int = 0
    var indexSubStat: Int
        get() = _indexSubStat
        set(value) {
            _indexSubStat = value
        }

    private var visibility: Boolean = false
    var visibleMaxValue: Boolean
        get() = visibility
        set(value) {
            visibility = value

            val visible: Int = if(value) VISIBLE else GONE

            grindstoneMaxView.apply { visibility = visible }
            totalMaxView.apply { visibility = visible }

            val direction: Int =  if(value) Gravity.END else Gravity.CENTER

            grindstoneView.apply{ gravity = direction }

            totalView.apply{
                setTextColor(context.colorRes(
                    if(value) R.color.orange else R.color.green
                ))
                gravity = direction
            }
        }

    private lateinit var _subStat: RuneStat
    var runeStat: RuneStat
        get() = _subStat
        set(value) {
            _subStat = value

            iconView.setImageResource(_subStat.imgStat())
            nameView.text = _subStat.statType.displayText
            valueView.text = _subStat.textValueStat()
            grindstoneView.text = _subStat.textGrindstoneValue()
            grindstoneView.apply {
                setTextColor(context.colorRes(_subStat.getColorByValueGrinstone()))
            }
            totalView.text = _subStat.textTotalValue(visibility)

            grindstoneMaxView.text = _subStat.textGrindstoneMaxValue()
            totalMaxView.text = _subStat.textTotalMaxValue()
        }

    var nameStat: String
        get() = nameView.text.toString()
        set(value){
            nameView.text = value

        }

    var colorSubStat: Int
        get() = 0
        set(value) {
            iconView.apply { setColorFilter(value) }
            nameView.apply { setTextColor(value) }
            valueView.apply { setTextColor(value) }
        }

    private var _imEditable: Boolean = false
    var imEditable: Boolean
        get() = _imEditable
        set(value) {_imEditable = value}



    private var _availableSubStats: List<RuneStatType> = emptyList()
    var availableSubStats: List<RuneStatType>
        get() = _availableSubStats
        set(value) {_availableSubStats = value}
}