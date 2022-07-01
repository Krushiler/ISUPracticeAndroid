package com.bravedeveloper.sandbase.presentation.sellerfilter

import android.content.Context
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.bravedeveloper.sandbase.R

class SellerFilterSpinnerAdapter(context: Context, filterTypeArray: List<FilterTypeItem>) :
    ArrayAdapter<FilterTypeItem>(context, 0, filterTypeArray) {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView
            ?: layoutInflater.inflate(R.layout.spinner_filter_seller_item, parent, false)
        getItem(position)?.let { country ->
            setItemForFilterType(view, country)
        }
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        if (position == 0) {
            view = layoutInflater.inflate(R.layout.spinner_filter_seller_header, parent, false)
            view.setOnClickListener {
                val root = parent.rootView
                root.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK))
                root.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK))
            }
        } else {
            view = layoutInflater.inflate(
                R.layout.spinner_filter_seller_item_non_header,
                parent,
                false
            )
            getItem(position)?.let { country ->
                setItemForFilterType(view, country)
            }
        }
        return view
    }

    override fun getItem(position: Int): FilterTypeItem? {
        if (position == 0) {
            return null
        }
        return super.getItem(position - 1)
    }

    override fun getCount() = super.getCount() + 1

    override fun isEnabled(position: Int) = position != 0

    private fun setItemForFilterType(view: View, filterType: FilterTypeItem) {
        val tvFilterType = view.findViewById<TextView>(R.id.filterTypeTV)
        tvFilterType.text = filterType.typeName
    }
}