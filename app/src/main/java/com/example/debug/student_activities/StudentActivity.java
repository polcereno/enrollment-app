package com.example.debug.student_activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.debug.LoginActivity;
import com.example.debug.LogoutTask;
import com.example.debug.R;
import com.example.debug.SchoolCalendarActivity;
import com.example.debug.registrar_activity.EnrollmentActivity;
import com.example.debug.registrar_activity.FeeManagementActivity;
import com.example.debug.registrar_activity.Registrar2Activity;
import com.example.debug.registrar_activity.RegistrarActivity;
import com.example.debug.registrar_activity.SetupActivity;
import com.example.debug.registrar_activity.StudentLedgerActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;

public class StudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
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