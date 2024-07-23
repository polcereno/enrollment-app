package com.example.debug.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.debug.Models.Applicant;
import com.example.debug.Models.Result;
import com.example.debug.R;
import com.example.debug.ui.admission.ApplicantDetailsActivity;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {

    private List<Result> resultList;
    private Context context;

    public ResultAdapter(Context context, List<Result> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        Result result = resultList.get(position);
        holder.levelTextView.setText(String.valueOf(result.getResultId())); // Convert int to String
        holder.nameTextView.setText(result.getApplicantFullName());

        // Set text and color based on result
        String resultText = result.getResult();
        holder.resultTextView.setText(resultText);
        if ("denied".equalsIgnoreCase(resultText)) {
            holder.resultTextView.setTextColor(context.getResources().getColor(R.color.red_button)); // Replace with your color resource
        } else if ("accepted".equalsIgnoreCase(resultText)) {
            holder.resultTextView.setTextColor(context.getResources().getColor(R.color.green_button)); // Replace with your color resource
        }
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admission_result, parent, false);
        return new ResultViewHolder(view);
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder {
        TextView levelTextView, nameTextView, resultTextView;

        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            levelTextView = itemView.findViewById(R.id.levelTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            resultTextView = itemView.findViewById(R.id.resultTextView);
        }
    }
}