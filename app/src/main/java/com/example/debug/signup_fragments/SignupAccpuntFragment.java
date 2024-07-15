package com.example.debug.signup_fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.debug.R;


public class SignupAccpuntFragment extends Fragment {

    public SignupAccpuntFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the continue button
        Button finishButton = view.findViewById(R.id.finish_button);
        // Initialize the back button
        Button backButton = view.findViewById(R.id.back_button);

        // Check if the continue button is not null before setting the click listener
        if (finishButton != null) {
            finishButton.setOnClickListener(v -> {
                // Navigate to the next fragment
                NavHostFragment.findNavController(SignupAccpuntFragment.this)
                        .navigate(R.id.action_signup_account);
            });
        } else {
            Log.e("SignupPersonalFragment", "Continue button is null");
        }

        // Check if the back button is not null before setting the click listener
        if (backButton != null) {
            backButton.setOnClickListener(v -> {
                // Navigate back to the previous fragment
                NavHostFragment.findNavController(SignupAccpuntFragment.this).popBackStack();
            });
        } else {
            Log.e("SignupPersonalFragment", "Back button is null");
        }
    }
}