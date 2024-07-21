package com.example.debug.admin_fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.debug.R;

public class ScheduleFragment extends Fragment {

    private Spinner secspin, semspin, yearspin;
    private Button adminSched_btn;
    private TextView errorMessage;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        secspin = view.findViewById(R.id.secspin);
        semspin = view.findViewById(R.id.semspin);
        yearspin = view.findViewById(R.id.yearspin);
        errorMessage = view.findViewById(R.id.error_message);

        setupSectionSpinner();
        setupSemesterSpinner();
        setupYearSpinner();

        setupButtonListeners();
    }

    private void setupSectionSpinner() {
        ArrayAdapter<CharSequence> secAdapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.sec_array, android.R.layout.simple_spinner_item);
        secAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        secspin.setAdapter(secAdapter);
    }

    private void setupSemesterSpinner() {
        ArrayAdapter<CharSequence> semAdapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.sem_array, android.R.layout.simple_spinner_item);
        semAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semspin.setAdapter(semAdapter);
    }

    private void setupYearSpinner() {
        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.year_array, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearspin.setAdapter(yearAdapter);
    }

    private void setupButtonListeners() {
        Button continueButton = requireView().findViewById(R.id.add_button);

        continueButton.setOnClickListener(v -> {
            String selectedSection = secspin.getSelectedItem().toString();
            String selectedSemester = semspin.getSelectedItem().toString();
            String selectedYear = yearspin.getSelectedItem().toString();

            if (selectedSection.equals("Select Section") ||
                    selectedSemester.equals("Select Semester") ||
                    selectedYear.equals("Select Year")) {
                // Show error message
                errorMessage.setVisibility(View.VISIBLE);
            } else {
                // Hide error message
                errorMessage.setVisibility(View.GONE);

                NavController navController = NavHostFragment.findNavController(this);
                Bundle bundle = new Bundle();
                bundle.putString("section", selectedSection);
                bundle.putString("semester", selectedSemester);
                bundle.putString("year", selectedYear);
                navController.navigate(R.id.add_action, bundle);
            }
        });
    }
}
