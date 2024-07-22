package com.example.debug.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.debug.ui.Fragments.DashboardFragment;
import com.example.debug.Controller.LogoutTask;
import com.example.debug.R;
import com.example.debug.ui.student.ChangePasswordActivity;
import com.example.debug.ui.student.CurriculumEvaluationActivity;
import com.example.debug.ui.student.DueAmountActivity;
import com.example.debug.ui.student.EnrolledSubjectsActivity;
import com.example.debug.ui.student.StatementOfAccountsActivity;
import com.example.debug.ui.student.StudentProfileActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;

public class StudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.student_activity);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.student_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_change_password) {
                startActivity(new Intent(StudentActivity.this, ChangePasswordActivity.class));
            } else if (id == R.id.nav_student_profile) {
                startActivity(new Intent(StudentActivity.this, StudentProfileActivity.class));
            } else if (id == R.id.nav_due_amount) {
                startActivity(new Intent(StudentActivity.this, DueAmountActivity.class));
            } else if (id == R.id.nav_enrolled_subjects) {
                startActivity(new Intent(StudentActivity.this, EnrolledSubjectsActivity.class));
            } else if (id == R.id.nav_curriculum_evaluation) {
                startActivity(new Intent(StudentActivity.this, CurriculumEvaluationActivity.class));
            } else if (id == R.id.nav_statement_of_accounts) {
                startActivity(new Intent(StudentActivity.this, StatementOfAccountsActivity.class));
            } else if (id == R.id.nav_school_calendar) {
                startActivity(new Intent(StudentActivity.this, SchoolCalendarActivity.class));
            } else if (id == R.id.nav_logout) {
                showLogoutConfirmationDialog();
            }

            drawer.closeDrawer(GravityCompat.START);
            return true;
        });

        // Example: Adding a fragment dynamically
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, new DashboardFragment())
                .commit();
    }

    private void showLogoutConfirmationDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Confirm Logout")
                .setMessage("Click “Confirm” to logout of the system.")
                .setPositiveButton("Confirm", (dialog, which) -> {
                    new LogoutTask(this).execute();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

}