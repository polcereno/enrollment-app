package com.example.debug.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.debug.ui.Fragments.DashboardFragment;
import com.example.debug.controller.LogoutTask;
import com.example.debug.R;
import com.example.debug.ui.instructor.ClassListActivity;
import com.example.debug.ui.instructor.GradingSheetActivity;
import com.example.debug.ui.schedulemanagement.ScheduleActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;

public class InstructorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.instructor_activity);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_class_list) {
                startActivity(new Intent(InstructorActivity.this, ClassListActivity.class));
            } else if (id == R.id.nav_schedule) {
                startActivity(new Intent(InstructorActivity.this, ScheduleActivity.class));
            } else if (id == R.id.nav_grading_sheet) {
                startActivity(new Intent(InstructorActivity.this, GradingSheetActivity.class));
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