package com.example.debug.registrar_activity;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.fragment.NavHostFragment;

import com.example.debug.R;

import ru.nikartm.support.ImageBadgeView;

public class AdmissionApplicantsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission_applicants);

        // Initialize views
        Toolbar admissionApplicantsToolbar = findViewById(R.id.admission_applicants_toolbar);
        setSupportActionBar(admissionApplicantsToolbar);

        // Set up the toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Setup button listeners
        setupButtonListeners();
    }

    private void setupButtonListeners() {
        ImageBadgeView backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(v -> {
            // Finish the current activity to go back to the previous activity
            finish();
        });
    }
}

