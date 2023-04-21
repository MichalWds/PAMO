package com.example.tipper;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
/**
 * Author Michał Wadas s20495
 */

public class BMIResultAdapter extends RecyclerView.Adapter<BMIResultAdapter.BMIResultViewHolder> {

    private List<BMIActivity.BMIResult> bmiResults;
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");
    private static final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
    private static final int[] COLOR_RES_IDS = {
            R.color.colorEmaciation,
            R.color.colorUnderweight,
            R.color.colorLowWeight,
            R.color.colorNormalWeight,
            R.color.colorOverweight,
            R.color.colorFirstDegreeObesity,
            R.color.colorSecondDegreeObesity,
            R.color.colorExtremeObesity
    };

    public BMIResultAdapter(List<BMIActivity.BMIResult> bmiResults) {
        this.bmiResults = bmiResults;
        Collections.sort(this.bmiResults, new Comparator<BMIActivity.BMIResult>() {
            @Override
            public int compare(BMIActivity.BMIResult o1, BMIActivity.BMIResult o2) {
                return o2.date.compareTo(o1.date);
            }
        });
    }

    @NonNull
    @Override
    public BMIResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bmi_result_item, parent, false);
        return new BMIResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BMIResultViewHolder holder, int position) {
        BMIActivity.BMIResult result = bmiResults.get(position);
        holder.bmiValueTextView.setText(decimalFormat.format(result.bmi));
        holder.dateTextView.setText(dateFormat.format(result.date));
        holder.itemView.setBackgroundColor(Color.parseColor(holder.itemView.getContext().getString(COLOR_RES_IDS[getBMIStatus(result.bmi)])));
    }

    @Override
    public int getItemCount() {
        return bmiResults.size();
    }

    private int getBMIStatus(double bmi) {
        if (bmi < 16) {
            return 0; // Wygłodzenie
        } else if (bmi >= 16 && bmi <= 16.99) {
            return 1; // Wychudzenie
        } else if (bmi >= 17 && bmi <= 18.49) {
            return 2; // Niedowaga
        } else if (bmi >= 18.5 && bmi <= 24.99) {
            return 3; // Waga włąściwa
        } else if (bmi >= 25 && bmi <= 29.99) {
            return 4; // Nadwaga
        } else if (bmi >= 30 && bmi <= 34.99) {
            return 5; // I stopień otyłości
        } else if (bmi >= 35 && bmi <= 39.99) {
            return 6; // II stopień otyłości
        } else {
            return 7; // Otyłość skrajna
        }
    }

    public static class BMIResultViewHolder extends RecyclerView.ViewHolder {

        TextView dateTextView;
        TextView bmiValueTextView;

        public BMIResultViewHolder(@NonNull View itemView) {
            super(itemView);
            bmiValueTextView = itemView.findViewById(R.id.bmiValueTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }
    }
}
