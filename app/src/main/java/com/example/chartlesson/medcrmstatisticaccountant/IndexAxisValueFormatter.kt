package com.example.chartlesson.medcrmstatisticaccountant

import com.github.mikephil.charting.formatter.ValueFormatter

class IndexAxisValueFormatter(private val values: Array<String>) : ValueFormatter() {

    override fun getFormattedValue(value: Float): String {
        val index = value.toInt()
        return if (index >= 0 && index < values.size) {
            values[index]
        } else {
            ""
        }
    }
}

