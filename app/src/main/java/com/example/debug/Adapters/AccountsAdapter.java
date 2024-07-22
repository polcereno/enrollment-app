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
import com.example.debug.Models.Accounts;
import com.example.debug.R;
import com.example.debug.ui.accountmanagement.StudentDetailActivity;

import java.util.List;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.AccountsViewHolder> {

    private List<Accounts> accountsList;
    private Context context;

    public AccountsAdapter(Context context, List<Accounts> accountsList) {
        this.context = context;
        this.accountsList = accountsList;
    }

    @Override
    public int getItemCount() {
        return accountsList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull AccountsViewHolder holder, int position) {
        Accounts account = accountsList.get(position);
        holder.roleTextView.setText(account.getAccountRole()); // Set role text
        holder.accountIDTextView.setText(String.valueOf(account.getAccountId())); // Set ID text
        holder.usernameTextView.setText(account.getAccountUsername()); // Set username text
    }

    @NonNull
    @Override
    public AccountsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accounts, parent, false);
        return new AccountsViewHolder(view);
    }

    public static class AccountsViewHolder extends RecyclerView.ViewHolder {
        TextView roleTextView, accountIDTextView, usernameTextView;

        public AccountsViewHolder(@NonNull View itemView) {
            super(itemView);
            roleTextView = itemView.findViewById(R.id.roleTextView);
            accountIDTextView = itemView.findViewById(R.id.accountIDTextView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
        }
    }
}
