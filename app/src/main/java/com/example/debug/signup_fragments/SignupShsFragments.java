package com.example.debug.signup_fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.debug.Controller.FileChooser;
import com.example.debug.Models.SignUpViewModel;
import com.example.debug.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class SignupShsFragments extends Fragment {

    private SignUpViewModel signUpViewModel;
    private TextInputEditText lrnEditText;
    private TextInputEditText jhsAttendedEditText;
    private TextInputLayout lrnLayout;
    private TextInputLayout jhsAttendedLayout;

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
        // Required empty public constructor
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

        // Initialize ViewModel
        signUpViewModel = new ViewModelProvider(requireActivity()).get(SignUpViewModel.class);

        lrnEditText = view.findViewById(R.id.lrn);
        jhsAttendedEditText = view.findViewById(R.id.jhs_attended);
        lrnLayout = view.findViewById(R.id.lrn_input);
        jhsAttendedLayout = view.findViewById(R.id.jhs_attended_input);

        form137 = view.findViewById(R.id.form137);
        jhs_diploma = view.findViewById(R.id.jhs_diploma);
        esc_cert = view.findViewById(R.id.esc_cert);

        fileChooser1 = new FileChooser(requireContext(), form137);
        fileChooser2 = new FileChooser(requireContext(), jhs_diploma);
        fileChooser3 = new FileChooser(requireContext(), esc_cert);

        form137.setOnClickListener(v -> fileChooser1.openFileChooser(SignupShsFragments.this, FILE_CHOOSER_1_REQUEST_CODE));
        jhs_diploma.setOnClickListener(v -> fileChooser2.openFileChooser(SignupShsFragments.this, FILE_CHOOSER_2_REQUEST_CODE));
        esc_cert.setOnClickListener(v -> fileChooser3.openFileChooser(SignupShsFragments.this, FILE_CHOOSER_3_REQUEST_CODE));

        setupButtonListeners();
        setupFieldListeners();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data != null ? data.getData() : null;
        if (requestCode == FILE_CHOOSER_1_REQUEST_CODE) {
            fileChooser1.handleActivityResult(requestCode, resultCode, data);
            signUpViewModel.setForm137(uri);
        } else if (requestCode == FILE_CHOOSER_2_REQUEST_CODE) {
            fileChooser2.handleActivityResult(requestCode, resultCode, data);
            signUpViewModel.setJhsDiploma(uri);
        } else if (requestCode == FILE_CHOOSER_3_REQUEST_CODE) {
            fileChooser3.handleActivityResult(requestCode, resultCode, data);
            signUpViewModel.setEscCertificate(uri);
        }
    }

    private void setupFieldListeners() {
        // Set listeners for EditText fields to update ViewModel in real-time
        lrnEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                signUpViewModel.setLrn(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not used
            }
        });

        jhsAttendedEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                signUpViewModel.setJhsAttended(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not used
            }
        });
    }

    private void setupButtonListeners() {
        Button continueButton = requireView().findViewById(R.id.continue_button);

        continueButton.setOnClickListener(v -> {
            if (isInputValid()) {
                // Navigate to the next fragment
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_shs_next);
            }
        });
    }

    private boolean isInputValid() {
        boolean isValid = true;

        String lrn = Objects.requireNonNull(lrnEditText.getText()).toString().trim();
        String jhsAttended = Objects.requireNonNull(jhsAttendedEditText.getText()).toString().trim();

        if (lrn.isEmpty()) {
            lrnLayout.setError("This field is required");
            isValid = false;
        } else {
            lrnLayout.setError(null);
        }

        if (jhsAttended.isEmpty()) {
            jhsAttendedLayout.setError("This field is required");
            isValid = false;
        } else {
            jhsAttendedLayout.setError(null);
        }

        if (signUpViewModel.getForm137().getValue() == null) {
            form137.setError("File is required");
            isValid = false;
        } else {
            form137.setError(null);
        }

        if (signUpViewModel.getJhsDiploma().getValue() == null) {
            jhs_diploma.setError("File is required");
            isValid = false;
        } else {
            jhs_diploma.setError(null);
        }

        return isValid;
    }
}
