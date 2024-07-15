package com.example.debug.signup_fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.debug.Controller.DataFetcher;
import com.example.debug.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class SignupPersonalFragment extends Fragment {

    private TextInputEditText birthdateEditText;
    private Spinner sexSpinner;
    private Spinner spinnerProvince;
    private Spinner spinnerMunicipality;
    private Spinner spinnerBarangay;

    private DataFetcher dataFetcher;

    public SignupPersonalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup_personal_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize DataFetcher
        dataFetcher = new DataFetcher(requireContext());

        // Initialize Views
        birthdateEditText = view.findViewById(R.id.birthdate);
        sexSpinner = view.findViewById(R.id.sex);
        spinnerProvince = view.findViewById(R.id.spinner_province);
        spinnerMunicipality = view.findViewById(R.id.spinner_municipality);
        spinnerBarangay = view.findViewById(R.id.spinner_barangay);

        // Set up Gender Spinner
        setupGenderSpinner();

        // Load Provinces into the Spinner
        loadProvinces();

        // Set Click Listeners for Buttons
        setupButtonListeners(view);
    }

    private void setupGenderSpinner() {
        ArrayAdapter<CharSequence> sexAdapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.gender_array, android.R.layout.simple_spinner_item);
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexSpinner.setAdapter(sexAdapter);
    }

    private void loadProvinces() {
        dataFetcher.loadProvinces(spinnerProvince, spinnerMunicipality, spinnerBarangay);
    }

    private void setupButtonListeners(View view) {
        Button continueButton = view.findViewById(R.id.continue_button);
        Button backButton = view.findViewById(R.id.back_button);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the next fragment
                NavHostFragment.findNavController(SignupPersonalFragment.this)
                        .navigate(R.id.action_personal_next);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous fragment
                NavHostFragment.findNavController(SignupPersonalFragment.this).popBackStack();
            }
        });

        birthdateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Update the TextInputEditText with the selected date
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        birthdateEditText.setText(selectedDate);
                    }
                },
                year, month, dayOfMonth);

        datePickerDialog.show();
    }
}
