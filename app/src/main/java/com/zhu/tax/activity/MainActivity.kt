package com.zhu.tax.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.view.View
import android.widget.*
import com.zhu.tax.R
import com.zhu.tax.listener.ItemSelectListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val typeList = mutableListOf<String>()
    private val timesList = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "个税计算"

        initSpinner()
        listener()

    }

    fun goneIncomeAndManagement() {
        layout_times.visibility = View.VISIBLE
        layout_salary.visibility = View.VISIBLE
        layout_insurance.visibility = View.VISIBLE
        layout_additional.visibility = View.VISIBLE
        layout_income.visibility = View.GONE
        layout_management.visibility = View.GONE
    }

    fun goneExceptIncome() {
        layout_times.visibility = View.GONE
        layout_salary.visibility = View.GONE
        layout_insurance.visibility = View.GONE
        layout_additional.visibility = View.GONE
        layout_income.visibility = View.VISIBLE
        layout_management.visibility = View.GONE
    }

    fun goneExceptIM() {
        layout_times.visibility = View.GONE
        layout_salary.visibility = View.GONE
        layout_insurance.visibility = View.GONE
        layout_additional.visibility = View.GONE
        layout_income.visibility = View.VISIBLE
        layout_management.visibility = View.VISIBLE
    }

    private fun listener() {
        spinner_type.onItemSelectedListener = ItemSelectListener(this)
        btn_reset.setOnClickListener { reset() }
        btn_calculate.setOnClickListener {
            val type = spinner_type.selectedItemPosition.toString()
            val times = spinner_times.selectedItemPosition.toString()
            var salary = edtTxt_salary.text.toString()
            val insurance = edtTxt_insurance.text.toString()
            val additional = edtTxt_additional.text.toString()
            val income = edtTxt_income.text.toString()
            val management = spinner_management.selectedItemPosition.toString()

            if (salary.isEmpty()) {
                salary = "0"
            }

            if (insurance.isEmpty() || additional.isEmpty() || income.isEmpty()) {
                Toast.makeText(this, "不能包含空白项", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if ("0" == type && salary.toDouble() <= 5000) {
                Toast.makeText(this, "收入低于5000不用交税", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            } else if ("1" == type && income.toDouble() <= 0) {
                Toast.makeText(this, "收入低于0不用交税", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            } else if ("2" == type && income.toDouble() < 800) {
                Toast.makeText(this, "收入低于800不用交税", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            } else if ("5" == type && income.toDouble() < 800) {
                Toast.makeText(this, "收入低于800不用交税", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            } else if ("6" == type && income.toDouble() < 800) {
                Toast.makeText(this, "收入低于800不用交税", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            } else if ("7" == type && income.toDouble() < 800) {
                Toast.makeText(this, "收入低于800不用交税", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            intent.setClass(this, ResultActivity::class.java)
            intent.putExtra("type", type)
            intent.putExtra("times", times)
            intent.putExtra("salary", salary)
            intent.putExtra("insurance", insurance)
            intent.putExtra("additional", additional)
            intent.putExtra("income", income)
            intent.putExtra("management", management)
            startActivity(intent)
        }
    }

    fun reset() {
        edtTxt_salary.text.clear()
        edtTxt_insurance.text = Editable.Factory.getInstance().newEditable("0")
        edtTxt_additional.text = Editable.Factory.getInstance().newEditable("0")
        edtTxt_income.text = Editable.Factory.getInstance().newEditable("0")
    }

    private fun initSpinner() {
        typeList.add("工资、薪金所得（月薪）")
        typeList.add("年终奖所得")
        typeList.add("劳务报酬所得")
        typeList.add("个体工商户生产、经营所得")
        typeList.add("对企事业单位的承包、承租经营所得")
        typeList.add("稿酬所得")
        typeList.add("特许权使用费所得")
        typeList.add("财产租赁所得")
        typeList.add("财产转让所得")
        typeList.add("利息、股息、红利所得")
        typeList.add("偶然所得")
        spinner_type.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, typeList)
        spinner_type.dropDownVerticalOffset = 70

        for (i in 1..12) {
            timesList.add(i)
        }
        spinner_times.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, timesList)
        spinner_times.dropDownVerticalOffset = 70
        spinner_management.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, timesList)
        spinner_management.dropDownVerticalOffset = 70
    }

}
