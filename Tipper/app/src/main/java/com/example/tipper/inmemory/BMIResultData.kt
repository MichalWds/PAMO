package com.example.tipper.inmemory

import com.example.tipper.BMIActivity.BMIResult
import java.util.*

/**
 * Author Michał Wadas s20495
 */
class BMIResultData private constructor() {
    private val resultBMI: MutableList<BMIResult> = ArrayList()
    fun addBMIResult(bmi: Double) {
        val result = BMIResult(bmi, Date())
        resultBMI.add(result)
    }

    val bMIResults: List<BMIResult>
        get() = resultBMI

    companion object {
        @kotlin.jvm.JvmStatic
        var bmiResultData: BMIResultData? = null
            get() {
                if (field == null) {
                    field = BMIResultData()
                }
                return field
            }
            private set
    }
}