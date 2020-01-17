package com.zhu.tax.listener

import android.util.Log

/**
 * Description:
 * Data：2019/7/30 0030-10:01
 * Author: Zhu
 */
class CrashHandler : Thread.UncaughtExceptionHandler {

    override fun uncaughtException(p0: Thread?, p1: Throwable?) {
        Log.i("CrashHandler", p1?.message)
    }

}