package com.example.debug.ui.signup;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.debug.controller.AddressSelector;
import com.example.debug.controller.DataFetcher;
import com.example.debug.model.SignUpViewModel;
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
    private AutoCompleteTextView province;
    private AutoCompleteTextView municipality;
    private AutoCompleteTextView barangay;
    private TextInputEditText purokEditText;
    private TextInputEditText birthdateEditText;

    private TextInputLayout fnameLayout;
    private TextInputLayout mnameLayout;
    private TextInputLayout lnameLayout;
    private TextInputLayout emailLayout;
    private TextInputLayout phoneLayout;
    private TextInputLayout provinceLayout;
    private TextInputLayout municipalityLayout;
    private TextInputLayout barangayLayout;
    private TextInputLayout purokLayout;
    private TextInputLayout birthdateLayout;

    private TextView sexErrorText;

    private AddressSelector addressSelector;

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
        addressSelector = new AddressSelector(this);

        // Initialize Views
        fnameEditText = view.findViewById(R.id.fname);
        mnameEditText = view.findViewById(R.id.mname);
        lnameEditText = view.findViewById(R.id.lname);
        sexSpinner = view.findViewById(R.id.sex);
        emailEditText = view.findViewById(R.id.email);
        phoneEditText = view.findViewById(R.id.phone);
        purokEditText = view.findViewById(R.id.purok);
        birthdateEditText = view.findViewById(R.id.birthdate);

        province = view.findViewById(R.id.province);
        municipality = view.findViewById(R.id.municipality);
        barangay = view.findViewById(R.id.barangay);

        provinceLayout = view.findViewById(R.id.provinceLayout);
        municipalityLayout = view.findViewById(R.id.municipalityLayout);
        barangayLayout = view.findViewById(R.id.barangayLayout);

        fnameLayout = view.findViewById(R.id.fname_input);
        mnameLayout = view.findViewById(R.id.mname_input);
        lnameLayout = view.findViewById(R.id.lname_input);
        emailLayout = view.findViewById(R.id.email_input);
        phoneLayout = view.findViewById(R.id.phone_input);
        purokLayout = view.findViewById(R.id.purok_input);
        birthdateLayout = view.findViewById(R.id.birthdate_input);

        sexErrorText = view.findViewById(R.id.sexErrorText);

        addressSelector.loadProvinces(province, municipality, barangay);
        setupGenderSpinner();
        setupFieldListeners();
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

    private void setupFieldListeners() {
        // Set listeners for EditText fields to update ViewModel in real-time
        fnameEditText.addTextChangedListener(createTextWatcher(signUpViewModel::setFname));
        mnameEditText.addTextChangedListener(createTextWatcher(signUpViewModel::setMname));
        lnameEditText.addTextChangedListener(createTextWatcher(signUpViewModel::setLname));
        emailEditText.addTextChangedListener(createTextWatcher(signUpViewModel::setEmail));
        phoneEditText.addTextChangedListener(createTextWatcher(signUpViewModel::setPhone));
        purokEditText.addTextChangedListener(createTextWatcher(signUpViewModel::setPurok));
        birthdateEditText.addTextChangedListener(createTextWatcher(signUpViewModel::setBirthdate));

        // Add TextWatcher to AutoCompleteTextView fields
        province.addTextChangedListener(createTextWatcher(signUpViewModel::setProvince));
        municipality.addTextChangedListener(createTextWatcher(signUpViewModel::setMunicipality));
        barangay.addTextChangedListener(createTextWatcher(signUpViewModel::setBarangay));
    }

    private TextWatcher createTextWatcher(Consumer<String> updateMethod) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateMethod.accept(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not used
            }
        };
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

        // Validate Address Fields
        if (Objects.requireNonNull(province.getText()).toString().trim().isEmpty()) {
            provinceLayout.setError("Province is required");
            isValid = false;
        } else {
            provinceLayout.setError(null);
        }

        if (Objects.requireNonNull(municipality.getText()).toString().trim().isEmpty()) {
            municipalityLayout.setError("Municipality is required");
            isValid = false;
        } else {
            municipalityLayout.setError(null);
        }

        if (Objects.requireNonNull(barangay.getText()).toString().trim().isEmpty()) {
            barangayLayout.setError("Barangay is required");
            isValid = false;
        } else {
            barangayLayout.setError(null);
        }

        return isValid;
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(), (view, selectedYear, selectedMonth, selectedDay) -> {
            String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
            birthdateEditText.setText(date);
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }
}
