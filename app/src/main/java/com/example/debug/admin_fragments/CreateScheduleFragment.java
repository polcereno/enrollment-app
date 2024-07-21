package com.example.debug.admin_fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.debug.R;
import com.example.debug.instructor_activities.ScheduleActivity;

public class CreateScheduleFragment extends Fragment {

    public CreateScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find views by ID
        View sectionInput = view.findViewById(R.id.section_input);
        View section = view.findViewById(R.id.section);
        View codeInput = view.findViewById(R.id.code_input);
        View code = view.findViewById(R.id.code);
        View descriptionInput = view.findViewById(R.id.description_input);
        View description = view.findViewById(R.id.description);
        View timeInput = view.findViewById(R.id.time_input);
        View time = view.findViewById(R.id.time);
        View daysInput = view.findViewById(R.id.days_input);
        View days = view.findViewById(R.id.days);
        View roomInput = view.findViewById(R.id.room_input);
        View room = view.findViewById(R.id.room);
        View instructorInput = view.findViewById(R.id.instructor_input);
        View instructor = view.findViewById(R.id.instructor);
        View unitsInput = view.findViewById(R.id.units_input);
        View units = view.findViewById(R.id.units);
        View semesterInput = view.findViewById(R.id.semester_input);
        View semester = view.findViewById(R.id.semester);
        View backButton = view.findViewById(R.id.back_button);
        View continueButton = view.findViewById(R.id.save_button);

        setupButtonListeners();

    }

    private void setupButtonListeners() {
        Button backButton = requireView().findViewById(R.id.back_button);
        Button saveButton = requireView().findViewById(R.id.save_button);

        // Setup back button listener
        backButton.setOnClickListener(v -> {

            NavHostFragment.findNavController(this)
                    .navigate(R.id.back_action);
        });

        // Setup save button listener
        saveButton.setOnClickListener(v -> {
        });
    }


}
