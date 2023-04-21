package com.example.tipper.inmemory;

import com.example.tipper.BMIActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author Michał Wadas s20495
 */
public class BMIResultData {
    private final List<BMIActivity.BMIResult> resultBMI = new ArrayList<>();
    private static BMIResultData bmiResultData;

    private BMIResultData() {}

    public static BMIResultData getBmiResultData() {
        if (bmiResultData == null) {
            bmiResultData = new BMIResultData();
        }
        return bmiResultData;
    }

    public void addBMIResult(double bmi) {
        BMIActivity.BMIResult result = new BMIActivity.BMIResult(bmi, new Date());
        resultBMI.add(result);
    }
    public List<BMIActivity.BMIResult> getBMIResults() {
        return resultBMI;
    }


}