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

public class SignupCollegeFragment extends Fragment {

    private TextView form137;
    private TextView shs_diploma;
    private TextView transcript_records;
    private TextView dismissal_cert;
    private FileChooser fileChooser1;
    private FileChooser fileChooser2;
    private FileChooser fileChooser3;
    private FileChooser fileChooser4;
    private static final int FILE_CHOOSER_1_REQUEST_CODE = 1;
    private static final int FILE_CHOOSER_2_REQUEST_CODE = 2;
    private static final int FILE_CHOOSER_3_REQUEST_CODE = 3;
    private static final int FILE_CHOOSER_4_REQUEST_CODE = 4;

    public SignupCollegeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup_college, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        form137 = view.findViewById(R.id.form137);
        shs_diploma = view.findViewById(R.id.shs_diploma);
        transcript_records = view.findViewById(R.id.transcript_records);
        dismissal_cert = view.findViewById(R.id.dismissal_cert);

        fileChooser1 = new FileChooser(requireContext(), form137);
        fileChooser2 = new FileChooser(requireContext(), shs_diploma);
        fileChooser3 = new FileChooser(requireContext(), transcript_records);
        fileChooser4 = new FileChooser(requireContext(), dismissal_cert);

        form137.setOnClickListener(v -> fileChooser1.openFileChooser(SignupCollegeFragment.this, FILE_CHOOSER_1_REQUEST_CODE));
        shs_diploma.setOnClickListener(v -> fileChooser2.openFileChooser(SignupCollegeFragment.this, FILE_CHOOSER_2_REQUEST_CODE));
        transcript_records.setOnClickListener(v -> fileChooser3.openFileChooser(SignupCollegeFragment.this, FILE_CHOOSER_3_REQUEST_CODE));
        dismissal_cert.setOnClickListener(v -> fileChooser4.openFileChooser(SignupCollegeFragment.this, FILE_CHOOSER_4_REQUEST_CODE));

        // Initialize the continue button
        Button continueButton = view.findViewById(R.id.continue_button);
        // Initialize the back button
        Button backButton = view.findViewById(R.id.back_button);

        // Check if the continue button is not null before setting the click listener
        if (continueButton != null) {
            continueButton.setOnClickListener(v -> {
                // Navigate to the next fragment
                NavHostFragment.findNavController(SignupCollegeFragment.this)
                        .navigate(R.id.action_college_next);
            });
        } else {
            Log.e("SignupCollegeFragment", "Continue button is null");
        }

        // Check if the back button is not null before setting the click listener
        if (backButton != null) {
            backButton.setOnClickListener(v -> {
                // Navigate back to the previous fragment
                NavHostFragment.findNavController(SignupCollegeFragment.this).popBackStack();
            });
        } else {
            Log.e("SignupCollegeFragment", "Back button is null");
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
        } else if (requestCode == FILE_CHOOSER_4_REQUEST_CODE) {
            fileChooser4.handleActivityResult(requestCode, resultCode, data);
        }
    }
}
