package com.bravedeveloper.sandbase.presentation.ordercheckout.spinneritems

import android.content.Context
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.bravedeveloper.sandbase.R

class CargoTypeSpinnerAdapter(context: Context, cargoArray: List<CargoTypeSpinnerItem>) :
    ArrayAdapter<CargoTypeSpinnerItem>(context, 0, cargoArray) {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView
            ?: layoutInflater.inflate(R.layout.spinner_cargo_item, parent, false)
        getItem(position)?.let { cargo ->
            setItemForCargoType(view, cargo)
        }
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        if (position == 0) {
            view = layoutInflater.inflate(R.layout.spinner_cargo_header, parent, false)
            view.setOnClickListener {
                val root = parent.rootView
                root.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK))
                root.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK))
            }
        } else {
            view = layoutInflater.inflate(
                R.layout.spinner_cargo_item_non_header,
                parent,
                false
            )
            getItem(position)?.let { cargo ->
                setItemForCargoType(view, cargo)
            }
        }
        return view
    }

    override fun getItem(position: Int): CargoTypeSpinnerItem? {
        if (position == 0) {
            return null
        }
        return super.getItem(position - 1)
    }

    override fun getCount() = super.getCount() + 1

    override fun isEnabled(position: Int) = position != 0

    private fun setItemForCargoType(view: View, cargo: CargoTypeSpinnerItem) {
        val tvCargoType = view.findViewById<TextView>(R.id.tv_cargo_type)
        tvCargoType.text = cargo.itemName
    }
}