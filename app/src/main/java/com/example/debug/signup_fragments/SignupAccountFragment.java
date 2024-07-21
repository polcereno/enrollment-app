package com.example.debug.signup_fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.debug.Models.SignUpViewModel;
import com.example.debug.R;
import com.example.debug.FileUploadManager;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class SignupAccountFragment extends Fragment {

    private SignUpViewModel signUpViewModel;
    private TextInputEditText usernameEditText;
    private TextInputEditText passwordEditText;

    public SignupAccountFragment() {
        // Required empty public constructor
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

        // Initialize ViewModel
        signUpViewModel = new ViewModelProvider(requireActivity()).get(SignUpViewModel.class);

        usernameEditText = view.findViewById(R.id.username);
        passwordEditText = view.findViewById(R.id.password);

        setupFieldListeners(); // Setup listeners for EditText fields

        setupButtonListeners(); // Setup listeners for buttons
    }

    private void setupFieldListeners() {
        // Set listeners for EditText fields to update ViewModel in real-time
        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                signUpViewModel.setUsername(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not used
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                signUpViewModel.setPassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not used
            }
        });
    }


    private void setupButtonListeners() {
        Button finishButton = requireView().findViewById(R.id.finish_button);

        finishButton.setOnClickListener(v -> {
            // Validate username and password fields
            String username = Objects.requireNonNull(usernameEditText.getText()).toString().trim();
            String password = Objects.requireNonNull(passwordEditText.getText()).toString().trim();

            if (TextUtils.isEmpty(username)) {
                usernameEditText.setError("Username is required");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                passwordEditText.setError("Password is required");
                return;
            }

            // Set username and password in ViewModel
            signUpViewModel.setUsername(username);
            signUpViewModel.setPassword(password);

            // Initialize FileUploadManager and upload data
            FileUploadManager fileUploadManager = new FileUploadManager("https://enrol.lesterintheclouds.com", getContext());
            fileUploadManager.uploadData(signUpViewModel, new FileUploadManager.UploadCallback() {
                @Override
                public void onSuccess() {
                    // Navigate to the next fragment on success
                    NavHostFragment.findNavController(SignupAccountFragment.this)
                            .navigate(R.id.action_account_next);
                }

                @Override
                public void onFailure(String errorMessage) {
                    // Show error message on failure
                    Toast.makeText(getContext(), "Upload failed: " + errorMessage, Toast.LENGTH_LONG).show();
                }
            });
        });
    }


}
