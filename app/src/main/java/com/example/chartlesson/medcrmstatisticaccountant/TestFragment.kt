package com.example.chartlesson.medcrmstatisticaccountant


import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.chartlesson.R
import com.example.chartlesson.databinding.FragmentTestBinding
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale


class TestFragment : Fragment(R.layout.fragment_test) {
    private var _binding: FragmentTestBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUIPieChart()
        setupUIBarChart()
    }

    private fun setupUIBarChart() {
        val entries = mutableListOf<BarEntry>()

        val months: Array<String> =
            arrayOf("Янв", "Фев", "Мар", "Апр", "Май", "Июн", "Июл", "Авг", "Сен", "Окт", "Ноя", "Дек")

        entries.add(BarEntry(0f, 20f))
        entries.add(BarEntry(1f, 35f))
        entries.add(BarEntry(2f, 45f))
        entries.add(BarEntry(3f, 30f))
        entries.add(BarEntry(4f, 50f))
        entries.add(BarEntry(5f, 70f))
        entries.add(BarEntry(6f, 60f))
        entries.add(BarEntry(7f, 45f))
        entries.add(BarEntry(8f, 55f))
        entries.add(BarEntry(9f, 40f))
        entries.add(BarEntry(10f, 30f))
        entries.add(BarEntry(11f, 25f))

        val dataSetBar = BarDataSet(entries, "Гистограмма")
        dataSetBar.color = Color.parseColor("#67BEFF")
        dataSetBar.valueTextColor = Color.parseColor("#0F172A")
        dataSetBar.setDrawValues(false)
        val barData = BarData(dataSetBar)

        binding.barChart.data = barData
        binding.barChart.data.notifyDataChanged()

        binding.barChart.apply {
            val xAxis = xAxis
            with(xAxis) {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                labelCount = entries.size
                granularity = 1f
                isGranularityEnabled = true
                valueFormatter = IndexAxisValueFormatter(months)
            }
            setFitBars(true)
            animateY(2000)

            val customLabels = arrayOf("0", "1 900 000k", "2 000 000k", "2 100 000k", "2 200 000")
            val labelIndices = arrayOf(0, 1, 2, 3, 4)

            val yAxis = binding.barChart.axisLeft
            with(yAxis) {
                setDrawGridLines(true)
                gridColor = Color.parseColor("#CBD5E1")
                gridLineWidth = 1f
                enableGridDashedLine(0f, 0f, 0f)
                setLabelCount(5, true)

                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        val index = value.toInt()
                        if (index >= 0 && index < labelIndices.size) {
                            return customLabels[labelIndices[index]]
                        }
                        return ""
                    }
                }
                labelCount = customLabels.size
                axisMinimum = 0f
            }
            axisRight.isEnabled = false
            invalidate()
        }
    }


    private fun setupUIPieChart() {
        binding.pieChart.apply {
            val entries = mutableListOf<PieEntry>()
            val centerText = SpannableString(getString(R.string.Aa) + "\n" + getString(R.string.Bb))
            val color1 = context?.let { ContextCompat.getColor(it, R.color.black1) }
            centerText.setSpan(
                color1?.let { ForegroundColorSpan(it) },
                0,
                12,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            val color2 =
                context?.let { ContextCompat.getColor(it, R.color.label) }
            centerText.setSpan(
                color2?.let { ForegroundColorSpan(it) },
                13,
                centerText.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            entries.add(PieEntry(33.53f, "Plastik karta"))
            entries.add(PieEntry(66.47f, "Naqd pul"))
            val dataSet = PieDataSet(entries, "")
            dataSet.colors = mutableListOf(Color.parseColor("#0074CB"), Color.parseColor("#67BEFF"))
            dataSet.valueTextColor = Color.WHITE
            val pieData = PieData(dataSet)
            pieData.setValueFormatter(PercentFormatter(binding.pieChart))
            pieData.setValueTextSize(14f)
            data = pieData
            description.isEnabled = false
            isDrawHoleEnabled = true
            setTransparentCircleAlpha(0)
            setCenterText(centerText)
            setDrawEntryLabels(false)
            setEntryLabelTextSize(14f)
            invalidate()
        }
    }
}