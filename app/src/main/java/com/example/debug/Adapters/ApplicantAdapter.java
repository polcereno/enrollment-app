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
import com.example.debug.R;
import com.example.debug.ui.accountmanagement.StudentDetailActivity;
import com.example.debug.ui.admission.ApplicantDetailsActivity;

import java.util.List;

public class ApplicantAdapter extends RecyclerView.Adapter<ApplicantAdapter.ApplicantViewHolder> {

    private List<Applicant> accountsList;
    private Context context;

    public ApplicantAdapter(Context context, List<Applicant> accountsList) {
        this.context = context;
        this.accountsList = accountsList;
    }

    @Override
    public int getItemCount() {
        return accountsList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicantViewHolder holder, int position) {
        Applicant applicant = accountsList.get(position);
        holder.levelTextView.setText(applicant.getApplicantLevel());
        holder.nameTextView.setText(String.valueOf(applicant.getApplicantFullName()));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ApplicantDetailsActivity.class);
            intent.putExtra("applicantID", applicant.getApplicantId());
            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public ApplicantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admission_applicant, parent, false);
        return new ApplicantViewHolder(view);
    }
    public static class ApplicantViewHolder extends RecyclerView.ViewHolder {
        TextView levelTextView, nameTextView;

        public ApplicantViewHolder(@NonNull View itemView) {
            super(itemView);
            levelTextView = itemView.findViewById(R.id.levelTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
        }
    }
}
