package com.zhu.tax.utils

/**
 * Description:
 * Data：2019/7/26 0026-15:49
 * Author: Zhu
 */
class RateAndDeduction {

    private var rd = mutableListOf<Int>()

    fun salaryRD(preTax: Double): MutableList<Int> {

        when {
            preTax <= 36000 -> {
                rd.add(0, 3)
                rd.add(1, 0)
            }
            preTax <= 144000 -> {
                rd.add(0, 10)
                rd.add(1, 2520)
            }
            preTax <= 300000 -> {
                rd.add(0, 20)
                rd.add(1, 16920)
            }
            preTax <= 420000 -> {
                rd.add(0, 25)
                rd.add(1, 31920)
            }
            preTax <= 660000 -> {
                rd.add(0, 30)
                rd.add(1, 52920)
            }
            preTax <= 960000 -> {
                rd.add(0, 35)
                rd.add(1, 85920)
            }
            else -> {
                rd.add(0, 45)
                rd.add(1, 181920)
            }
        }
        return rd
    }

    fun bonusRD(averageMonth: Double): MutableList<Int> {
        when {
            averageMonth <= 3000 -> {
                rd.add(0, 3)
                rd.add(1, 0)
            }
            averageMonth <= 12000 -> {
                rd.add(0, 10)
                rd.add(1, 210)
            }
            averageMonth <= 25000 -> {
                rd.add(0, 20)
                rd.add(1, 1410)
            }
            averageMonth <= 35000 -> {
                rd.add(0, 25)
                rd.add(1, 2660)
            }
            averageMonth <= 55000 -> {
                rd.add(0, 30)
                rd.add(1, 4410)
            }
            averageMonth <= 80000 -> {
                rd.add(0, 35)
                rd.add(1, 7160)
            }
            else -> {
                rd.add(0, 45)
                rd.add(1, 15160)
            }
        }
        return rd
    }

    fun serviceRD(preTax: Double): MutableList<Int> {
        if (preTax > 20000 && preTax <= 50000) {
            rd.add(0, 30)
            rd.add(1, 2000)
        } else if (preTax > 50000) {
            rd.add(0, 40)
            rd.add(1, 7000)
        } else {
            rd.add(0, 20)
            rd.add(1, 0)
        }
        return rd
    }

    fun businessAndGovernmentRD(income: Double): MutableList<Int> {
        when {
            income <= 15000 -> {
                rd.add(0, 5)
                rd.add(1, 0)
            }
            income <= 30000 -> {
                rd.add(0, 10)
                rd.add(1, 750)
            }
            income <= 60000 -> {
                rd.add(0, 20)
                rd.add(1, 3750)
            }
            income <= 100000 -> {
                rd.add(0, 30)
                rd.add(1, 9750)
            }
            else -> {
                rd.add(0, 35)
                rd.add(1, 14750)
            }
        }
        return rd
    }

}
