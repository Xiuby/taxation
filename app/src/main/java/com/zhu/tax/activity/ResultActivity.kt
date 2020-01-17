package com.zhu.tax.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import com.zhu.tax.R
import com.zhu.tax.listener.CrashHandler
import com.zhu.tax.utils.RateAndDeduction
import kotlinx.android.synthetic.main.activity_result.*
import java.text.DecimalFormat
import kotlin.math.max

/**
 * Description:
 * Data：2019/7/19 0019-16:20
 * Author: Zhu
 */
class ResultActivity : AppCompatActivity() {

    private var type: Int = 0
    private var times: Int = 0
    private var salary: Double = 0.0
    private var insurance: Double = 0.0
    private var additional: Double = 0.0
    private var income: Double = 0.0
    private var management: Int = 0

    private var rate: Int = 0
    private var deduction: Int = 0
    private var payable: Double = 0.0
    private var realIncome: Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "返回"

        CrashHandler()

        getData()
        initView()
    }

    private fun setBorder(vararg tvs: TextView) {
        for (tv in tvs) {
            tv.setBackgroundResource(R.drawable.tv_border)
        }
    }

    private fun setText(one: Double, two: Int, three: Int, four: Double, five: Double, six: Double, seven: Double) {
        tv_one_right.text = one.toString()
        tv_two_right.text = two.toString()
        tv_three_right.text = three.toString()
        tv_four_right.text = DecimalFormat(".##").format(four).toString()
        if (0.0 == five) {
            tv_five_right.text = five.toString()
        } else {
            tv_five_right.text = DecimalFormat(".##").format(five).toString()
        }
        tv_six_right.text = DecimalFormat(".##").format(six).toString()
        tv_seven_right.text = seven.toString()
        setBorder(tv_four_right, tv_five_right, tv_six_right, tv_seven_right)
    }

    private fun setText(one: Double, two: Int, three: Double, four: Double) {
        tv_one_right.text = one.toString()
        tv_two_right.text = two.toString()
        tv_three_right.text = three.toString()
        tv_four_right.text = four.toString()
        setBorder(tv_three_right, tv_four_right)
    }

    private fun setText(one: Double, two: Double, three: Int, four: Int, five: Double, six: Double) {
        tv_one_right.text = one.toString()
        tv_two_right.text = two.toString()
        tv_three_right.text = three.toString()
        tv_four_right.text = four.toString()
        tv_five_right.text = DecimalFormat(".##").format(five).toString()
        tv_six_right.text = six.toString()
        setBorder(tv_five_right, tv_six_right)
    }

    private fun setText(one: Double, two: Int, three: Int, four: Double, five: Double) {
        tv_one_right.text = one.toString()
        tv_two_right.text = two.toString()
        tv_three_right.text = three.toString()
        tv_four_right.text = DecimalFormat(".##").format(four).toString()
        tv_five_right.text = five.toString()
        setBorder(tv_four_right, tv_five_right)
    }

    private fun setText(one: Double, two: Double, three: Int, four: Double, five: Double) {
        tv_one_right.text = one.toString()
        tv_two_right.text = two.toString()
        tv_three_right.text = three.toString()
        tv_four_right.text = DecimalFormat(".##").format(four).toString()
        tv_five_right.text = five.toString()
        setBorder(tv_four_right, tv_five_right)
    }

    private fun initView() {
        when (type) {
            0 -> {
                tv_one_left.text = "应纳税所得额"
                tv_two_left.text = "适用税率(%)"
                tv_three_left.text = "速算扣除数"
                tv_four_left.text = "累计应缴税款"
                tv_five_left.text = "已缴税款"
                tv_six_left.text = "应补（退）税款"
                tv_seven_left.text = "本月税后收入"

                val salaryAll = salary * times
                val lastSalary = salaryAll - salary
                val insuranceAll = insurance * times
                val lastInsurance = insuranceAll - insurance
                val additionalAll = additional * times
                val lastAdditional = additionalAll - additional
                val baseline = 5000

                val taxable = salaryAll - insuranceAll - additionalAll - baseline * times

                rate = RateAndDeduction().salaryRD(taxable)[0]
                deduction = RateAndDeduction().salaryRD(taxable)[1]
                payable = taxable * rate / 100 - deduction
                var paid = 0.0

                if (times > 1) {
                    val lastTaxable = lastSalary - lastInsurance - lastAdditional - baseline * (times - 1)
                    rate = RateAndDeduction().salaryRD(lastTaxable)[0]
                    deduction = RateAndDeduction().salaryRD(lastTaxable)[1]
                    paid = lastTaxable * rate / 100 - deduction
                }
                val toBePaid = payable - max(paid, 0.0)
                realIncome = salary - toBePaid
                setText(taxable, rate, deduction, payable, paid, toBePaid, realIncome)

            }
            1 -> {
                tv_one_left.text = "平均每月"
                tv_two_left.text = "适用税率(%)"
                tv_three_left.text = "速算扣除数"
                tv_four_left.text = "应缴税款"
                tv_five_left.text = "实发工资"
                linearLayout_six.visibility = View.GONE
                linearLayout_seven.visibility = View.GONE

                val average = income / 12
                rate = RateAndDeduction().bonusRD(average)[0]
                deduction = RateAndDeduction().bonusRD(average)[1]
                payable = income * rate / 100 - deduction
                realIncome = income - payable
                setText(average, rate, deduction, payable, realIncome)
            }
            2 -> {
                tv_one_left.text = "减除费用"
                tv_two_left.text = "适用税率(%)"
                tv_three_left.text = "速算扣除数"
                tv_four_left.text = "预扣预缴应纳税所得额"
                tv_five_left.text = "预扣预缴应缴税款"
                tv_six_left.text = "税后收入"
                linearLayout_seven.visibility = View.GONE

                var deduct = 800.0
                if (income > 4000)
                    deduct = income * 0.2
                val preTax = income - deduct

                rate = RateAndDeduction().serviceRD(preTax)[0]
                deduction = RateAndDeduction().serviceRD(preTax)[1]
                payable = preTax * rate / 100 - deduction
                realIncome = income - payable
                setText(deduct, preTax, rate, deduction, payable, realIncome)
            }
            3 -> {
                tv_one_left.text = "应纳税所得额"
                tv_two_left.text = "适用税率(%)"
                tv_three_left.text = "速算扣除数"
                tv_four_left.text = "应缴税款"
                tv_five_left.text = "税后收入"
                linearLayout_six.visibility = View.GONE
                linearLayout_seven.visibility = View.GONE

                rate = RateAndDeduction().businessAndGovernmentRD(income)[0]
                deduction = RateAndDeduction().businessAndGovernmentRD(income)[1]
                payable = income * rate / 100 - deduction
                realIncome = income - payable
                setText(income, rate, deduction, payable, realIncome)
            }
            4 -> {
                tv_one_left.text = "减除费用"
                tv_two_left.text = "应纳税所得额"
                tv_three_left.text = "适用税率(%)"
                tv_four_left.text = "速算扣除数"
                tv_five_left.text = "应缴税款"
                tv_six_left.text = "税后收入"
                linearLayout_seven.visibility = View.GONE

                val preTax = income - management * 3500
                if (preTax <= 0) {
                    setText(0.0, 0.0, 0, 0, 0.0, income)
                    return
                }
                rate = RateAndDeduction().businessAndGovernmentRD(preTax)[0]
                deduction = RateAndDeduction().businessAndGovernmentRD(preTax)[1]
                payable = preTax * rate / 100 - deduction
                realIncome = income - payable
                setText(0.0, preTax, rate, deduction, payable, realIncome)
            }
            5 -> {
                tv_one_left.text = "减除费用"
                tv_two_left.text = "预扣预缴应纳税所得额"
                tv_three_left.text = "适用税率(%)"
                tv_four_left.text = "预扣预缴应缴税款"
                tv_five_left.text = "税后收入"
                linearLayout_six.visibility = View.GONE
                linearLayout_seven.visibility = View.GONE

                var deduct = 800.0
                if (income > 4000)
                    deduct = income * 0.2
                val preTax = income - deduct
                payable = preTax * 0.14
                realIncome = income - payable
                setText(deduct, preTax, 14, payable, realIncome)
            }
            6, 7 -> {
                tv_one_left.text = "减除费用"
                tv_two_left.text = "应纳税所得额"
                tv_three_left.text = "适用税率(%)"
                tv_four_left.text = "应缴税款"
                tv_five_left.text = "税后收入"
                linearLayout_six.visibility = View.GONE
                linearLayout_seven.visibility = View.GONE

                var deduct = 800.0
                if (income > 4000)
                    deduct = income * 0.2
                val preTax = income - deduct
                payable = preTax * 0.2
                realIncome = income - payable
                setText(deduct, preTax, 20, payable, realIncome)
            }
            8, 9, 10 -> {
                tv_one_left.text = "应纳税所得额"
                tv_two_left.text = "适用税率(%)"
                tv_three_left.text = "应缴税款"
                tv_four_left.text = "税后收入"
                linearLayout_five.visibility = View.GONE
                linearLayout_six.visibility = View.GONE
                linearLayout_seven.visibility = View.GONE

                payable = income * 0.2
                realIncome = income - payable
                setText(income, 20, payable, realIncome)
            }
        }
    }

    private fun getData() {
        type = intent.getStringExtra("type").toInt()
        times = intent.getStringExtra("times").toInt() + 1
        salary = intent.getStringExtra("salary").toDouble()
        insurance = intent.getStringExtra("insurance").toDouble()
        additional = intent.getStringExtra("additional").toDouble()
        income = intent.getStringExtra("income").toDouble()
        management = intent.getStringExtra("management").toInt() + 1
    }
}
