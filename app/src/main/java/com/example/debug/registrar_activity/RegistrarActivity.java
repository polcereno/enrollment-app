package com.example.debug.registrar_activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.debug.LoginActivity;
import com.example.debug.LogoutTask;
import com.example.debug.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;

import android.widget.CheckBox;
import android.widget.ExpandableListView;

import java.util.HashMap;
import java.util.List;

public class RegistrarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.registrar_activity);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.registrar_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_enrollment) {
                startActivity(new Intent(RegistrarActivity.this, EnrollmentActivity.class));
            } else if (id == R.id.nav_assessment) {
                // startActivity(new Intent(RegistrarActivity.this, .class));
            } else if (id == R.id.nav_admission_results) {
                startActivity(new Intent(RegistrarActivity.this, AdmissionResultsActivity.class));
            } else if (id == R.id.nav_admission_applicants) {
                startActivity(new Intent(RegistrarActivity.this, AdmissionApplicantsActivity.class));
            } else if (id == R.id.nav_fee_management) {
                startActivity(new Intent(RegistrarActivity.this, FeeManagementActivity.class));
            } else if (id == R.id.nav_ledger) {
                startActivity(new Intent(RegistrarActivity.this, StudentLedgerActivity.class));
            } else if (id == R.id.nav_registrar) {
                startActivity(new Intent(RegistrarActivity.this, Registrar2Activity.class));
            } else if (id == R.id.nav_setup) {
                startActivity(new Intent(RegistrarActivity.this, SetupActivity.class));
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
