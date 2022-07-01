package com.bravedeveloper.sandbase.presentation.ordercheckout.spinneritems

import android.content.Context
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.bravedeveloper.domain.model.city.cargo.Measure
import com.bravedeveloper.sandbase.R

class MeasureSpinnerAdapter(context: Context, measureArray: List<Measure>) :
    ArrayAdapter<Measure>(context, 0, measureArray) {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView
            ?: layoutInflater.inflate(R.layout.spinner_measure_item, parent, false)
        getItem(position)?.let { measure ->
            setItemForMeasure(view, measure)
        }
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        if (position == 0) {
            view = layoutInflater.inflate(R.layout.spinner_measure_header, parent, false)
            view.setOnClickListener {
                val root = parent.rootView
                root.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK))
                root.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK))
            }
        } else {
            view = layoutInflater.inflate(
                R.layout.spinner_measure_item_non_header,
                parent,
                false
            )
            getItem(position)?.let { measure ->
                setItemForMeasure(view, measure)
            }
        }
        return view
    }

    override fun getItem(position: Int): Measure? {
        if (position == 0) {
            return null
        }
        return super.getItem(position - 1)
    }

    override fun getCount() = super.getCount() + 1

    override fun isEnabled(position: Int) = position != 0

    private fun setItemForMeasure(view: View, measure: Measure) {
        val tvMeasure = view.findViewById<TextView>(R.id.tv_measure_type)
        tvMeasure.text = measure.name.replace(" ", "\n")
    }
}