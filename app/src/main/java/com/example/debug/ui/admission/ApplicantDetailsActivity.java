package com.example.debug.ui.admission;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.debug.R;
import com.example.debug.network.FetchApplicantDetailsTask;
import com.example.debug.network.SubmitApplicantResultCallback;
import com.example.debug.network.SubmitApplicantResultTask;

public class ApplicantDetailsActivity extends AppCompatActivity
        implements FetchApplicantDetailsTask.FetchApplicantDetailsCallback, SubmitApplicantResultCallback {

    private Button acceptButton, denyButton;
    private TextView level, lrn, jhsAttended, shsAttended, firstName, lastName, middleName, sex, birthdate, email, phone, province, municipality, barangay, purokAndStreet;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_applicant_details);

        Toolbar toolbar = findViewById(R.id.applicantDetailsToolbar);
        setSupportActionBar(toolbar);

        // Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_arrow_back_24);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.applicant_details_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        level = findViewById(R.id.level);
        lrn = findViewById(R.id.lrn);
        jhsAttended = findViewById(R.id.jhsAttended);
        shsAttended = findViewById(R.id.shsAttended);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        middleName = findViewById(R.id.middleName);
        sex = findViewById(R.id.sex);
        birthdate = findViewById(R.id.birthdate);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        province = findViewById(R.id.province);
        municipality = findViewById(R.id.municipality);
        barangay = findViewById(R.id.barangay);
        purokAndStreet = findViewById(R.id.purokAndStreet);

        // Initialize ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Prevent dialog from being dismissed by tapping outside

        setupButtonListeners();
        fetchApplicantDetails();
    }

    private void setupButtonListeners() {
        acceptButton = findViewById(R.id.acceptButton);
        denyButton = findViewById(R.id.denyButton);

        acceptButton.setOnClickListener(v -> showConfirmationDialog("accept"));
        denyButton.setOnClickListener(v -> showConfirmationDialog("deny"));
    }

    private void showConfirmationDialog(String action) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Are you sure you want to " + (action.equals("accept") ? "accept" : "deny") + " this applicant?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Perform action
                    if (action.equals("accept")) {
                        handleAcceptClick();
                    } else {
                        handleDenyClick();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void handleAcceptClick() {
        int applicantID = getIntent().getIntExtra("applicantID", -1);
        if (applicantID != -1) {
            // Execute SubmitApplicantResultTask with "accepted"
            SubmitApplicantResultTask task = new SubmitApplicantResultTask(this, applicantID, "accepted", this);
            task.execute();
        }
    }

    private void handleDenyClick() {
        int applicantID = getIntent().getIntExtra("applicantID", -1);
        if (applicantID != -1) {
            // Execute SubmitApplicantResultTask with "denied"
            SubmitApplicantResultTask task = new SubmitApplicantResultTask(this, applicantID, "denied", this);
            task.execute();
        }
    }

    private void fetchApplicantDetails() {
        // Create and execute FetchApplicantDetailsTask
        int applicantID = getIntent().getIntExtra("applicantID", -1);
        if (applicantID != -1) {
            FetchApplicantDetailsTask task = new FetchApplicantDetailsTask(this, this);
            task.fetchApplicantDetails(applicantID);
        }
    }

    @Override
    public void onStartLoading() {
        progressDialog.show();
    }

    @Override
    public void onSuccess(String level, String lrn, String jhsAttended, String shsAttended, String firstName, String lastName, String middleName, String sex, String birthdate, String email, String phone, String province, String municipality, String barangay, String purokAndStreet) {
        // Set the TextViews
        this.level.setText(level);
        this.lrn.setText(lrn);
        this.jhsAttended.setText(jhsAttended);
        this.shsAttended.setText(shsAttended);
        this.firstName.setText(firstName);
        this.lastName.setText(lastName);
        this.middleName.setText(middleName);
        this.sex.setText(sex);
        this.birthdate.setText(birthdate);
        this.email.setText(email);
        this.phone.setText(phone);
        this.province.setText(province);
        this.municipality.setText(municipality);
        this.barangay.setText(barangay);
        this.purokAndStreet.setText(purokAndStreet);
    }

    @Override
    public void onFinishLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void onResultSubmitted(boolean success, String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        if (success) {
            // Finish activity if the result was submitted successfully
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        if (item.getItemId() == android.R.id.home) {
            // Navigate back to the previous activity
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
