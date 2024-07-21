package com.example.debug.signup_fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.debug.Controller.DataFetcher;
import com.example.debug.Models.SignUpViewModel;
import com.example.debug.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Objects;

public class SignupPersonalFragment extends Fragment {

    private SignUpViewModel signUpViewModel;

    private TextInputEditText fnameEditText;
    private TextInputEditText mnameEditText;
    private TextInputEditText lnameEditText;
    private Spinner sexSpinner;
    private TextInputEditText emailEditText;
    private TextInputEditText phoneEditText;
    private TextInputEditText purokEditText;
    private TextInputEditText birthdateEditText;
    private Spinner provinceSpinner;
    private Spinner municipalitySpinner;
    private Spinner barangaySpinner;

    private TextInputLayout fnameLayout;
    private TextInputLayout mnameLayout;
    private TextInputLayout lnameLayout;
    private TextInputLayout emailLayout;
    private TextInputLayout phoneLayout;
    private TextInputLayout purokLayout;
    private TextInputLayout birthdateLayout;

    private TextView sexErrorText;
    private TextView provinceErrorText;
    private TextView municipalityErrorText;
    private TextView barangayErrorText;

    private DataFetcher dataFetcher;

    public SignupPersonalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup_personal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize ViewModel
        signUpViewModel = new ViewModelProvider(requireActivity()).get(SignUpViewModel.class);

        // Initialize DataFetcher
        dataFetcher = new DataFetcher(requireContext());

        // Initialize Views
        fnameEditText = view.findViewById(R.id.fname);
        mnameEditText = view.findViewById(R.id.mname);
        lnameEditText = view.findViewById(R.id.lname);
        sexSpinner = view.findViewById(R.id.sex);
        emailEditText = view.findViewById(R.id.email);
        phoneEditText = view.findViewById(R.id.phone);
        purokEditText = view.findViewById(R.id.purok);
        birthdateEditText = view.findViewById(R.id.birthdate);
        provinceSpinner = view.findViewById(R.id.spinner_province);
        municipalitySpinner = view.findViewById(R.id.spinner_municipality);
        barangaySpinner = view.findViewById(R.id.spinner_barangay);

        fnameLayout = view.findViewById(R.id.fname_input);
        mnameLayout = view.findViewById(R.id.mname_input);
        lnameLayout = view.findViewById(R.id.lname_input);
        emailLayout = view.findViewById(R.id.email_input);
        phoneLayout = view.findViewById(R.id.phone_input);
        purokLayout = view.findViewById(R.id.purok_input);
        birthdateLayout = view.findViewById(R.id.birthdate_input);

        sexErrorText = view.findViewById(R.id.sexErrorText);
        provinceErrorText = view.findViewById(R.id.provinceErrorText);
        municipalityErrorText = view.findViewById(R.id.municipalityErrorText);
        barangayErrorText = view.findViewById(R.id.barangayErrorText);

        // Set up Gender Spinner
        setupGenderSpinner();

        // Load Provinces into the Spinner
        loadProvinces();

        // Set listeners to update ViewModel in real-time
        setupFieldListeners();

        // Set Click Listeners for Buttons
        setupButtonListeners();
    }

    private void setupGenderSpinner() {
        ArrayAdapter<CharSequence> sexAdapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.gender_array, android.R.layout.simple_spinner_item);
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexSpinner.setAdapter(sexAdapter);

        sexSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                signUpViewModel.setSex(sexSpinner.getSelectedItem().toString());
                sexErrorText.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle no selection if needed
            }
        });
    }

    private void loadProvinces() {
        dataFetcher.loadProvinces(provinceSpinner, municipalitySpinner, barangaySpinner);

        // Set a listener to update ViewModel when province is selected
        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                signUpViewModel.setProvince(provinceSpinner.getSelectedItem().toString());
                provinceErrorText.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle no selection if needed
            }
        });

        // Set a listener to update ViewModel when municipality is selected
        municipalitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                signUpViewModel.setMunicipality(municipalitySpinner.getSelectedItem().toString());
                municipalityErrorText.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle no selection if needed
            }
        });

        // Set a listener to update ViewModel when barangay is selected
        barangaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                signUpViewModel.setBarangay(barangaySpinner.getSelectedItem().toString());
                barangayErrorText.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle no selection if needed
            }
        });
    }

    private void setupFieldListeners() {
        // Set listeners for EditText fields to update ViewModel in real-time
        fnameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                signUpViewModel.setFname(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not used
            }
        });

        mnameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                signUpViewModel.setMname(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not used
            }
        });

        lnameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                signUpViewModel.setLname(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not used
            }
        });

        // Set similar listeners for emailEditText, phoneEditText, purokEditText, and birthdateEditText
        // Example for emailEditText:
        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                signUpViewModel.setEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not used
            }
        });

        phoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                signUpViewModel.setPhone(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not used
            }
        });

        purokEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                signUpViewModel.setPurok(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not used
            }
        });

        birthdateEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                signUpViewModel.setBirthdate(s.toString());
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
            if (validateInputs()) {
                // Navigate to the next fragment
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_personal_next);
            }
        });

        birthdateEditText.setOnClickListener(v -> showDatePickerDialog());
    }

    @SuppressLint("SetTextI18n")
    private boolean validateInputs() {
        boolean isValid = true;

        // Validate First Name
        if (Objects.requireNonNull(fnameEditText.getText()).toString().trim().isEmpty()) {
            fnameLayout.setError("First name is required");
            isValid = false;
        } else {
            fnameLayout.setError(null);
        }

        // Validate Last Name
        if (Objects.requireNonNull(lnameEditText.getText()).toString().trim().isEmpty()) {
            lnameLayout.setError("Last name is required");
            isValid = false;
        } else {
            lnameLayout.setError(null);
        }

        // Validate Email
        if (Objects.requireNonNull(emailEditText.getText()).toString().trim().isEmpty()) {
            emailLayout.setError("Email is required");
            isValid = false;
        } else {
            emailLayout.setError(null);
        }

        // Validate Phone
        if (Objects.requireNonNull(phoneEditText.getText()).toString().trim().isEmpty()) {
            phoneLayout.setError("Phone number is required");
            isValid = false;
        } else {
            phoneLayout.setError(null);
        }

        // Validate Purok
        if (Objects.requireNonNull(purokEditText.getText()).toString().trim().isEmpty()) {
            purokLayout.setError("Purok is required");
            isValid = false;
        } else {
            purokLayout.setError(null);
        }

        // Validate Birthdate
        if (Objects.requireNonNull(birthdateEditText.getText()).toString().trim().isEmpty()) {
            birthdateLayout.setError("Birthdate is required");
            isValid = false;
        } else {
            birthdateLayout.setError(null);
        }

        // Validate Gender Spinner
        if (sexSpinner.getSelectedItemPosition() == 0) {
            sexErrorText.setVisibility(View.VISIBLE);
            sexErrorText.setText("Sex is required");
            isValid = false;
        } else {
            sexErrorText.setVisibility(View.GONE);
        }

        // Validate Province Spinner
        if (provinceSpinner.getSelectedItemPosition() == 0) {
            provinceErrorText.setVisibility(View.VISIBLE);
            provinceErrorText.setText("Province is required");
            isValid = false;
        } else {
            provinceErrorText.setVisibility(View.GONE);
        }

        // Validate Municipality Spinner
        if (municipalitySpinner.getSelectedItemPosition() == 0) {
            municipalityErrorText.setVisibility(View.VISIBLE);
            municipalityErrorText.setText("Municipality is required");
            isValid = false;
        } else {
            municipalityErrorText.setVisibility(View.GONE);
        }

        // Validate Barangay Spinner
        if (barangaySpinner.getSelectedItemPosition() == 0) {
            barangayErrorText.setVisibility(View.VISIBLE);
            barangayErrorText.setText("Barangay is required");
            isValid = false;
        } else {
            barangayErrorText.setVisibility(View.GONE);
        }

        return isValid;
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year1, monthOfYear, dayOfMonth1) -> {
                    // Update the TextInputEditText with the selected date
                    String selectedDate = dayOfMonth1 + "/" + (monthOfYear + 1) + "/" + year1;
                    birthdateEditText.setText(selectedDate);
                    signUpViewModel.setBirthdate(selectedDate); // Update ViewModel with birthdate
                },
                year, month, dayOfMonth);

        datePickerDialog.show();
    }
}
