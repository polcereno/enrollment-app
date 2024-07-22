package com.example.debug.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.debug.Models.AccountStudent;
import com.example.debug.R;
import com.example.debug.ui.accountmanagement.StudentDetailActivity;

import java.util.List;

public class StudentAccountAdapter extends RecyclerView.Adapter<StudentAccountAdapter.StudentAccountViewHolder> {

    private List<AccountStudent> accountStudentList;
    private Context context;

    public StudentAccountAdapter(Context context, List<AccountStudent> accountStudentList) {
        this.context = context;
        this.accountStudentList = accountStudentList;
    }

    @Override
    public int getItemCount() {
        return accountStudentList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAccountAdapter.StudentAccountViewHolder holder, int position) {
        AccountStudent accountStudent = accountStudentList.get(position);
        holder.nameTextView.setText(accountStudent.getAccountName());
        holder.studentIDTextView.setText(accountStudent.getAccountStudentID());
        holder.levelTextView.setText(accountStudent.getAccountLevel());

        // Set click listener
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, StudentDetailActivity.class);
            intent.putExtra("studentID", accountStudent.getAccountStudentID());
            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public StudentAccountAdapter.StudentAccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student_account, parent, false);
        return new StudentAccountAdapter.StudentAccountViewHolder(view);
    }

    public static class StudentAccountViewHolder extends RecyclerView.ViewHolder {
        TextView levelTextView, studentIDTextView, nameTextView;

        public StudentAccountViewHolder(@NonNull View itemView) {
            super(itemView);
            levelTextView = itemView.findViewById(R.id.levelTextView);
            studentIDTextView = itemView.findViewById(R.id.studentIDTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
        }
    }
}


