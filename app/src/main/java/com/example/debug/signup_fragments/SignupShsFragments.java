package com.example.debug.signup_fragments;

import android.content.Intent;
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
import android.widget.TextView;

import com.example.debug.Controller.FileChooser;
import com.example.debug.R;


public class SignupShsFragments extends Fragment {

    private TextView form137;
    private TextView jhs_diploma;
    private TextView esc_cert;
    private FileChooser fileChooser1;
    private FileChooser fileChooser2;
    private FileChooser fileChooser3;
    private static final int FILE_CHOOSER_1_REQUEST_CODE = 1;
    private static final int FILE_CHOOSER_2_REQUEST_CODE = 2;
    private static final int FILE_CHOOSER_3_REQUEST_CODE = 3;

    public SignupShsFragments() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup_shs_fragments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        form137 = view.findViewById(R.id.form137);
        jhs_diploma = view.findViewById(R.id.shs_diploma);
        esc_cert = view.findViewById(R.id.esc_cert);

        fileChooser1 = new FileChooser(requireContext(), form137);
        fileChooser2 = new FileChooser(requireContext(), jhs_diploma);
        fileChooser3 = new FileChooser(requireContext(), esc_cert);

        form137.setOnClickListener(v -> fileChooser1.openFileChooser(SignupShsFragments.this, FILE_CHOOSER_1_REQUEST_CODE));
        jhs_diploma.setOnClickListener(v -> fileChooser2.openFileChooser(SignupShsFragments.this, FILE_CHOOSER_2_REQUEST_CODE));
        esc_cert.setOnClickListener(v -> fileChooser3.openFileChooser(SignupShsFragments.this, FILE_CHOOSER_3_REQUEST_CODE));

        // Initialize the continue button
        Button continueButton = view.findViewById(R.id.continue_button);
        // Initialize the back button
        Button backButton = view.findViewById(R.id.back_button);

        // Check if the continue button is not null before setting the click listener
        if (continueButton != null) {
            continueButton.setOnClickListener(v -> {
                // Navigate to the next fragment
                NavHostFragment.findNavController(SignupShsFragments.this)
                        .navigate(R.id.action_shs_next);
            });
        } else {
            Log.e("SignupPersonalFragment", "Continue button is null");
        }

        // Check if the back button is not null before setting the click listener
        if (backButton != null) {
            backButton.setOnClickListener(v -> {
                // Navigate back to the previous fragment
                NavHostFragment.findNavController(SignupShsFragments.this).popBackStack();
            });
        } else {
            Log.e("SignupPersonalFragment", "Back button is null");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_CHOOSER_1_REQUEST_CODE) {
            fileChooser1.handleActivityResult(requestCode, resultCode, data);
        } else if (requestCode == FILE_CHOOSER_2_REQUEST_CODE) {
            fileChooser2.handleActivityResult(requestCode, resultCode, data);
        } else if (requestCode == FILE_CHOOSER_3_REQUEST_CODE) {
            fileChooser3.handleActivityResult(requestCode, resultCode, data);
        }
    }
}