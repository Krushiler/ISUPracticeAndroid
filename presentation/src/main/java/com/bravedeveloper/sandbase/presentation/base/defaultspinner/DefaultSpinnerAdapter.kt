package com.bravedeveloper.sandbase.presentation.base.defaultspinner

import android.content.Context
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.bravedeveloper.sandbase.R

/* itemsArray - Данные, предостовляемые в спиннер
*  defaultText - Подсказка спиннера, если пользователь ничего не выбрал. */

class DefaultSpinnerAdapter<T: DefaultSpinnerItem>(
    context: Context,
    itemsArray: List<T>,
    private var defaultText: String
) :
    ArrayAdapter<T>(context, 0, itemsArray) {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView
            ?: layoutInflater.inflate(R.layout.layout_spinner_default, parent, false)
        getItem(position)?.let { country ->
            setItemForFilterType(view, country)
        }
        view.findViewById<TextView>(R.id.itemNameTV)
            .setTextColor(ContextCompat.getColor(context, R.color.black))
        if (position == 0) {
            view.findViewById<TextView>(R.id.itemNameTV)
                .setTextColor(ContextCompat.getColor(context, R.color.grey5))
            view.findViewById<TextView>(R.id.itemNameTV).text = defaultText
        }
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        if (position == 0) {
            view = layoutInflater.inflate(R.layout.layout_spinner_default, parent, false)
            view.setOnClickListener {
                val root = parent.rootView
                root.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK))
                root.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK))
            }
            view.findViewById<TextView>(R.id.itemNameTV)
                .setTextColor(ContextCompat.getColor(context, R.color.grey5))
            view.findViewById<TextView>(R.id.itemNameTV).text = defaultText
        } else {
            view = layoutInflater.inflate(
                R.layout.layout_spinner_default,
                parent,
                false
            )
            view.findViewById<TextView>(R.id.itemNameTV).typeface =
                ResourcesCompat.getFont(context, R.font.weblysleekuil)
            view.findViewById<TextView>(R.id.itemNameTV)
                .setTextColor(ContextCompat.getColor(context, R.color.black))
            getItem(position)?.let { country ->
                setItemForFilterType(view, country)
            }
        }
        return view
    }

    override fun getItem(position: Int): T? {
        if (position == 0) {
            return null
        }
        return super.getItem(position - 1)
    }

    override fun getCount() = super.getCount() + 1

    override fun isEnabled(position: Int) = position != 0

    private fun setItemForFilterType(view: View, spinnerItem: T) {
        val tvFilterType = view.findViewById<TextView>(R.id.itemNameTV)
        tvFilterType.text = spinnerItem.itemName
    }
}