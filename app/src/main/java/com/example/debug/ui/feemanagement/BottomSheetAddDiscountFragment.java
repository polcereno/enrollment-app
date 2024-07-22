package com.example.debug.ui.feemanagement;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.debug.R;
import com.google.android.material.button.MaterialButton;

public class BottomSheetAddDiscountFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bottom_sheet_add_discount, container, false);

        MaterialButton doneButton = rootView.findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a toast message when "Done" button is clicked
                Toast.makeText(requireContext(), "Assessment added successfully!", Toast.LENGTH_SHORT).show();

                // Optionally, you can dismiss the bottom sheet dialog if needed
                dismissBottomSheetDialog();
            }
        });

        return rootView;
    }

    private void dismissBottomSheetDialog() {
        // Dismiss the bottom sheet dialog if it is shown
        if (getParentFragmentManager() != null) {
            getParentFragmentManager().beginTransaction().remove(this).commit();
        }
    }
}
