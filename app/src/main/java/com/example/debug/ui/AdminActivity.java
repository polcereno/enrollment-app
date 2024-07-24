package com.example.debug.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.debug.ui.Fragments.DashboardFragment;
import com.example.debug.Controller.LogoutTask;
import com.example.debug.R;
import com.example.debug.ui.accountmanagement.CreateAccountActivity;
import com.example.debug.ui.accountmanagement.ManageStudentAccountActivity;
import com.example.debug.ui.admission.AdmissionApplicantsActivity;
import com.example.debug.ui.admission.AdmissionResultsActivity;
import com.example.debug.ui.coursemanagement.CourseActivity;
import com.example.debug.ui.curriculummanagement.CurriculumActivity;
import com.example.debug.ui.accountmanagement.ManageAccountActivity;
import com.example.debug.ui.feemanagement.FeeManagementActivity;
import com.example.debug.ui.registrar.Registrar2Activity;
import com.example.debug.ui.schedulemanagement.SchedulingActivity;
import com.example.debug.ui.accountmanagement.CreateStudentAccountActivity;
import com.example.debug.ui.studentledger.StudentLedgerActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.admin_activity);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_create_account) {
                startActivity(new Intent(AdminActivity.this, CreateAccountActivity.class));
            } else if (id == R.id.nav_create_student_account) {
                startActivity(new Intent(AdminActivity.this, CreateStudentAccountActivity.class));
            } else if (id == R.id.nav_manage_student_account) {
                startActivity(new Intent(AdminActivity.this, ManageStudentAccountActivity.class));
            } else if (id == R.id.nav_manage_account) {
                startActivity(new Intent(AdminActivity.this, ManageAccountActivity.class));
            } else if (id == R.id.nav_curriculum) {
                startActivity(new Intent(AdminActivity.this, CurriculumActivity.class));
            } else if (id == R.id.nav_schedule) {
                startActivity(new Intent(AdminActivity.this, SchedulingActivity.class));
            } else if (id == R.id.nav_admission_results) {
                startActivity(new Intent(AdminActivity.this, AdmissionResultsActivity.class));
            } else if (id == R.id.nav_admission_applicants) {
                startActivity(new Intent(AdminActivity.this, AdmissionApplicantsActivity.class));
            } else if (id == R.id.nav_create_student_account) {
                startActivity(new Intent(AdminActivity.this, CreateStudentAccountActivity.class));
            }else if (id == R.id.nav_manage_student_account) {
                startActivity(new Intent(AdminActivity.this, ManageStudentAccountActivity.class));
            } else if (id == R.id.nav_fee_management) {
                startActivity(new Intent(AdminActivity.this, FeeManagementActivity.class));
            } else if (id == R.id.nav_ledger) {
                startActivity(new Intent(AdminActivity.this, StudentLedgerActivity.class));
            } else if (id == R.id.nav_registrar) {
                startActivity(new Intent(AdminActivity.this, Registrar2Activity.class));
            } else if (id == R.id.nav_setup) {
                startActivity(new Intent(AdminActivity.this, SetupActivity.class));
            } else if (id == R.id.nav_course) {
                startActivity(new Intent(AdminActivity.this, CourseActivity.class));
            } else if (id == R.id.nav_curriculum) {
                startActivity(new Intent(AdminActivity.this, CurriculumActivity.class));
            } else if (id == R.id.nav_certification) {
                startActivity(new Intent(AdminActivity.this, CertificationActivity.class));
            } else if (id == R.id.nav_prerequisites) {
                startActivity(new Intent(AdminActivity.this, PrerequisitesActivity.class));
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
