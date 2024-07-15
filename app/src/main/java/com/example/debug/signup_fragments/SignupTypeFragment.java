package com.example.debug.signup_fragments;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.debug.R;

public class SignupTypeFragment extends Fragment {

    public SignupTypeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup_type, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the continue button
        Button continueButton = view.findViewById(R.id.continue_button);
        RadioGroup radioGroup = view.findViewById(R.id.radio_group);

        // Check if the button is not null before setting the click listener
        if (continueButton != null && radioGroup != null) {
            continueButton.setOnClickListener(v -> {
                // Check which radio button is selected
                int selectedId = radioGroup.getCheckedRadioButtonId();

                if (selectedId == R.id.college_radio) {
                    // Navigate to the fragment for college selection
                    NavHostFragment.findNavController(SignupTypeFragment.this)
                            .navigate(R.id.action_type_to_college);
                } else if (selectedId == R.id.shs_radio) {
                    // Navigate to the fragment for SHS selection
                    NavHostFragment.findNavController(SignupTypeFragment.this)
                            .navigate(R.id.action_type_to_shs);
                } else {
                    // No radio button selected, handle appropriately
                    Log.e("SignupTypeFragment", "No radio button selected");
                }
            });
        } else {
            Log.e("SignupTypeFragment", "Continue button or radio group is null");
        }
    }
}
