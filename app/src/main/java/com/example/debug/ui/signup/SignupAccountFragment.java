package com.example.debug.ui.signup;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.debug.Models.SignUpViewModel;
import com.example.debug.R;
import com.example.debug.Controller.FileUploadManager;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignupAccountFragment extends Fragment {

    private SignUpViewModel signUpViewModel;
    private TextInputEditText usernameEditText;
    private TextInputEditText passwordEditText;
    private ProgressDialog progressDialog;

    public SignupAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup_account, container, false);
    }

    private void setupFieldListeners() {
        // Set listeners for EditText fields to update ViewModel in real-time
        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                signUpViewModel.setUsername(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                signUpViewModel.setPassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void uploadData() {
        // Show progress dialog
        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Signing up...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Get data from ViewModel
        Map<String, String> params = new HashMap<>();
        params.put("type", getValueOrEmpty(signUpViewModel.getType()));
        params.put("lrn", getValueOrEmpty(signUpViewModel.getLrn()));
        params.put("jhs_attended", getValueOrEmpty(signUpViewModel.getJhsAttended()));
        params.put("shs_attended", getValueOrEmpty(signUpViewModel.getShsAttended()));
        params.put("fname", getValueOrEmpty(signUpViewModel.getFname()));
        params.put("lname", getValueOrEmpty(signUpViewModel.getLname()));
        params.put("mname", getValueOrEmpty(signUpViewModel.getMname()));
        params.put("sex", getValueOrEmpty(signUpViewModel.getSex()));
        params.put("birthdate", getValueOrEmpty(signUpViewModel.getBirthdate()));
        params.put("email", getValueOrEmpty(signUpViewModel.getEmail()));
        params.put("phone", getValueOrEmpty(signUpViewModel.getPhone()));
        params.put("province", getValueOrEmpty(signUpViewModel.getProvince()));
        params.put("municipality", getValueOrEmpty(signUpViewModel.getMunicipality()));
        params.put("barangay", getValueOrEmpty(signUpViewModel.getBarangay()));
        params.put("purok", getValueOrEmpty(signUpViewModel.getPurok()));
        params.put("parish", getValueOrEmpty(signUpViewModel.getParish()));
        params.put("username", getValueOrEmpty(signUpViewModel.getUsername()));
        params.put("password", getValueOrEmpty(signUpViewModel.getPassword()));

        // Create a request queue
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        // URL of the PHP script
        String url = "https://enrol.lesterintheclouds.com/signup.php";

        // Create a POST request
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    // Hide progress dialog
                    progressDialog.dismiss();

                    // Navigate to the next fragment
                    NavController navController = NavHostFragment.findNavController(SignupAccountFragment.this);
                    navController.navigate(R.id.action_account_next); // Update with your action ID
                },
                error -> {
                    // Hide progress dialog
                    progressDialog.dismiss();

                    // Handle error
                    Toast.makeText(requireContext(), "Error Uploading Data: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };

        // Add request to queue
        queue.add(postRequest);
    }

    private String getValueOrEmpty(MutableLiveData<String> data) {
        return data.getValue() != null ? data.getValue() : "";
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

            // Upload data
            uploadData();
        });
    }
}
