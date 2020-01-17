package com.zhu.tax.listener

import android.view.View
import android.widget.AdapterView
import com.zhu.tax.activity.MainActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Description:
 * Data：2019/7/19 0019-14:21
 * Author: Zhu
 */

class ItemSelectListener(private val context: MainActivity) : AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        when (p2) {
            0 -> {
                context.goneIncomeAndManagement()
            }
            1 -> {
                context.tv_income.text = "年终奖金:"
                context.goneExceptIncome()
            }
            4 -> {
                context.tv_income.text = "收入金额:"
                context.tv_management.text = "经营时间"
                context.goneExceptIM()
            }
            else -> {
                context.tv_income.text = "收入金额:"
                context.goneExceptIncome()
            }
        }
        context.reset()
    }

}