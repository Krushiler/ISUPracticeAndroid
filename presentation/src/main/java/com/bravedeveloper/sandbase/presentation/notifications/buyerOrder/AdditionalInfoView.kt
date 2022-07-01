package com.bravedeveloper.sandbase.presentation.notifications.buyerOrder

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.bravedeveloper.sandbase.R

class AdditionalInfoView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var approved: Boolean
    private var money: Boolean
    private var sertificated: Boolean

    private var infos: List<ImageView>

    init {
        var view  = LayoutInflater.from(context).inflate(R.layout.layout_additional_inf_view, this, true)

        infos = listOf(
            view.findViewById(R.id.imageApprove),
            view.findViewById(R.id.imageMoney),
            view.findViewById(R.id.imageSertificate)
        )

        val attributesArray = context.obtainStyledAttributes(attrs, R.styleable.AdditionalInfoView)

        approved = attributesArray.getBoolean(R.styleable.AdditionalInfoView_approved, false)
        money = attributesArray.getBoolean(R.styleable.AdditionalInfoView_money, false)
        sertificated = attributesArray.getBoolean(R.styleable.AdditionalInfoView_sertificated, false)

        updateInfo(approved, APPROVE_INFO)
        updateInfo(money, MONEY_INFO)
        updateInfo(sertificated, SERTIFICATE_INFO)
    }

    fun updateInfo(info: Boolean, infoType: Int){
        if(info) {
            infos[infoType].visibility = View.VISIBLE
        }else {
            infos[infoType].visibility = View.GONE
        }
    }


    companion object{
        val APPROVE_INFO = 0
        val MONEY_INFO = 1
        val SERTIFICATE_INFO = 2
    }
}