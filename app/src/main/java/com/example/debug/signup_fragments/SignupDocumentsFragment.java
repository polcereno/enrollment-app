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


public class SignupDocumentsFragment extends Fragment {

    private TextView baptismal;
    private TextView confirmation_cert;
    private TextView birth_cert;
    private TextView marriage_cert;
    private TextView brgy_residence;
    private TextView indigency;
    private TextView bir;
    private TextView recommendation_letter;
    private TextView medical_cert;

    private FileChooser fileChooser1;
    private FileChooser fileChooser2;
    private FileChooser fileChooser3;
    private FileChooser fileChooser4;
    private FileChooser fileChooser5;
    private FileChooser fileChooser6;
    private FileChooser fileChooser7;
    private FileChooser fileChooser8;
    private FileChooser fileChooser9;

    private static final int FILE_CHOOSER_1_REQUEST_CODE = 1;
    private static final int FILE_CHOOSER_2_REQUEST_CODE = 2;
    private static final int FILE_CHOOSER_3_REQUEST_CODE = 3;
    private static final int FILE_CHOOSER_4_REQUEST_CODE = 4;
    private static final int FILE_CHOOSER_5_REQUEST_CODE = 5;
    private static final int FILE_CHOOSER_6_REQUEST_CODE = 6;
    private static final int FILE_CHOOSER_7_REQUEST_CODE = 7;
    private static final int FILE_CHOOSER_8_REQUEST_CODE = 8;
    private static final int FILE_CHOOSER_9_REQUEST_CODE = 9;

    public SignupDocumentsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup_documents, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        baptismal = view.findViewById(R.id.baptismal);
        confirmation_cert = view.findViewById(R.id.confirmation_cert);
        birth_cert = view.findViewById(R.id.birth_cert);
        marriage_cert = view.findViewById(R.id.marriage_cert);
        brgy_residence = view.findViewById(R.id.brgy_residence);
        indigency = view.findViewById(R.id.indigency);
        bir = view.findViewById(R.id.bir);
        recommendation_letter = view.findViewById(R.id.recommendation_letter);
        medical_cert = view.findViewById(R.id.medical_cert);

        fileChooser1 = new FileChooser(requireContext(), baptismal);
        fileChooser2 = new FileChooser(requireContext(), confirmation_cert);
        fileChooser3 = new FileChooser(requireContext(), birth_cert);
        fileChooser4 = new FileChooser(requireContext(), marriage_cert);
        fileChooser5 = new FileChooser(requireContext(), brgy_residence);
        fileChooser6 = new FileChooser(requireContext(), indigency);
        fileChooser7 = new FileChooser(requireContext(), bir);
        fileChooser8 = new FileChooser(requireContext(), recommendation_letter);
        fileChooser9 = new FileChooser(requireContext(), medical_cert);

        baptismal.setOnClickListener(v -> fileChooser1.openFileChooser(SignupDocumentsFragment.this, FILE_CHOOSER_1_REQUEST_CODE));
        confirmation_cert.setOnClickListener(v -> fileChooser2.openFileChooser(SignupDocumentsFragment.this, FILE_CHOOSER_2_REQUEST_CODE));
        birth_cert.setOnClickListener(v -> fileChooser3.openFileChooser(SignupDocumentsFragment.this, FILE_CHOOSER_3_REQUEST_CODE));
        marriage_cert.setOnClickListener(v -> fileChooser4.openFileChooser(SignupDocumentsFragment.this, FILE_CHOOSER_4_REQUEST_CODE));
        brgy_residence.setOnClickListener(v -> fileChooser5.openFileChooser(SignupDocumentsFragment.this, FILE_CHOOSER_5_REQUEST_CODE));
        indigency.setOnClickListener(v -> fileChooser6.openFileChooser(SignupDocumentsFragment.this, FILE_CHOOSER_6_REQUEST_CODE));
        bir.setOnClickListener(v -> fileChooser7.openFileChooser(SignupDocumentsFragment.this, FILE_CHOOSER_7_REQUEST_CODE));
        recommendation_letter.setOnClickListener(v -> fileChooser8.openFileChooser(SignupDocumentsFragment.this, FILE_CHOOSER_8_REQUEST_CODE));
        medical_cert.setOnClickListener(v -> fileChooser9.openFileChooser(SignupDocumentsFragment.this, FILE_CHOOSER_9_REQUEST_CODE));

        // Initialize the continue button
        Button continueButton = view.findViewById(R.id.continue_button);
        // Initialize the back button
        Button backButton = view.findViewById(R.id.back_button);

        // Check if the continue button is not null before setting the click listener
        if (continueButton != null) {
            continueButton.setOnClickListener(v -> {
                // Navigate to the next fragment
                NavHostFragment.findNavController(SignupDocumentsFragment.this)
                        .navigate(R.id.action_document_next);
            });
        } else {
            Log.e("SignupPersonalFragment", "Continue button is null");
        }

        // Check if the back button is not null before setting the click listener
        if (backButton != null) {
            backButton.setOnClickListener(v -> {
                // Navigate back to the previous fragment
                NavHostFragment.findNavController(SignupDocumentsFragment.this).popBackStack();
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
        } else if (requestCode == FILE_CHOOSER_4_REQUEST_CODE) {
            fileChooser4.handleActivityResult(requestCode, resultCode, data);
        } else if (requestCode == FILE_CHOOSER_5_REQUEST_CODE) {
            fileChooser5.handleActivityResult(requestCode, resultCode, data);
        } else if (requestCode == FILE_CHOOSER_6_REQUEST_CODE) {
            fileChooser6.handleActivityResult(requestCode, resultCode, data);
        } else if (requestCode == FILE_CHOOSER_7_REQUEST_CODE) {
            fileChooser7.handleActivityResult(requestCode, resultCode, data);
        } else if (requestCode == FILE_CHOOSER_8_REQUEST_CODE) {
            fileChooser8.handleActivityResult(requestCode, resultCode, data);
        } else if (requestCode == FILE_CHOOSER_9_REQUEST_CODE) {
            fileChooser9.handleActivityResult(requestCode, resultCode, data);
        }
    }
}