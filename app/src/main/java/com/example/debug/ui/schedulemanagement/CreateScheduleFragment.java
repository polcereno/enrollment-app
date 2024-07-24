package com.example.debug.ui.schedulemanagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.debug.R;

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
        Button backButton = view.findViewById(R.id.back_button);
        Button saveButton = view.findViewById(R.id.save_button);

        // Set up button listeners
        backButton.setOnClickListener(v ->
                NavHostFragment.findNavController(this)
                        .navigate(R.id.back_action)
        );

        saveButton.setOnClickListener(v -> showFirstDialog());

        // Handle back press
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                showFirstDialog();
            }
        });
    }

    private void showFirstDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Confirmation")
                .setMessage("Do you want to proceed with adding this schedule?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                        navigateToSchedulingActivity();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void navigateToSchedulingActivity() {
        startActivity(new Intent(getActivity(), SchedulingActivity.class));
        requireActivity().finish(); // Finish current activity if needed
    }
}
