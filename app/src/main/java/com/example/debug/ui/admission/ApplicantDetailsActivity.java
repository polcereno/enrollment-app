package com.example.debug.ui.admission;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.debug.R;

import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;


public class ApplicantDetailsActivity extends AppCompatActivity {

    private Button acceptButton, denyButton;
    private TextView level, lrn, jhsAttended, shsAttended, firstName, lastName, middleName, sex, birthdate, email, phone, province, municipality, barangay, purokAndStreet;
    private RequestQueue requestQueue;
    private static final String URL = "http://enrol.lesterintheclouds.com/fetch_applicant_details.php";

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

        requestQueue = Volley.newRequestQueue(this);
        fetchApplicantDetails();
    }

    private void setupButtonListeners() {
        Button acceptButton = findViewById(R.id.acceptButton);
        Button denyButton = findViewById(R.id.denyButton);

        acceptButton.setOnClickListener(v -> handleAcceptClick());
        denyButton.setOnClickListener(v -> handleDenyClick());
    }

    private void handleAcceptClick() {
        // Get applicant ID from the intent
        int applicantID = getIntent().getIntExtra("applicantID", -1);
        if (applicantID != -1) {
            // Perform network operation to accept the applicant
            submitApplicantResult(applicantID, "accepted");
        }
    }

    private void handleDenyClick() {
        // Get applicant ID from the intent
        int applicantID = getIntent().getIntExtra("applicantID", -1);
        if (applicantID != -1) {
            // Perform network operation to deny the applicant
            submitApplicantResult(applicantID, "denied");
        }
    }

    private void submitApplicantResult(int applicantID, String result) {
        // URL for the PHP script
        String url = "http://enrol.lesterintheclouds.com/submit_applicant_result.php";

        // Create the request parameters
        Map<String, String> params = new HashMap<>();
        params.put("applicantID", String.valueOf(applicantID));
        params.put("result", result);

        // Create a JsonObjectRequest to submit the result
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                response -> {
                    // Handle success
                    Toast.makeText(ApplicantDetailsActivity.this, "Applicant " + result, Toast.LENGTH_SHORT).show();
                    // Redirect to another activity or update UI as needed
                    finish(); // Close the current activity
                },
                error -> {
                    // Handle error
                    Toast.makeText(ApplicantDetailsActivity.this, "Error submitting result", Toast.LENGTH_SHORT).show();
                }
        );

        // Add the request to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }



    private void fetchApplicantDetails() {
        // Get applicant ID from intent extras
        int applicantID = getIntent().getIntExtra("applicantID", -1);

        // Create a JsonObjectRequest to fetch applicant details from the server
        String url = URL + "?applicantID=" + applicantID;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        // Parse the JSON response
                        String levelValue = response.optString("type");
                        String lrnValue = response.optString("lrn");
                        String jhsAttendedValue = response.optString("jhs_attended");
                        String shsAttendedValue = response.optString("shs_attended");
                        String firstNameValue = response.optString("fname");
                        String lastNameValue = response.optString("lname");
                        String middleNameValue = response.optString("mname");
                        String sexValue = response.optString("sex");
                        String birthdateValue = response.optString("birthdate");
                        String emailValue = response.optString("email");
                        String phoneValue = response.optString("phone");
                        String provinceValue = response.optString("province");
                        String municipalityValue = response.optString("municipality");
                        String barangayValue = response.optString("barangay");
                        String purokAndStreetValue = response.optString("purok");

                        // Set the TextViews
                        level.setText(levelValue);
                        lrn.setText(lrnValue);
                        jhsAttended.setText(jhsAttendedValue);
                        shsAttended.setText(shsAttendedValue);
                        firstName.setText(firstNameValue);
                        lastName.setText(lastNameValue);
                        middleName.setText(middleNameValue);
                        sex.setText(sexValue);
                        birthdate.setText(birthdateValue);
                        email.setText(emailValue);
                        phone.setText(phoneValue);
                        province.setText(provinceValue);
                        municipality.setText(municipalityValue);
                        barangay.setText(barangayValue);
                        purokAndStreet.setText(purokAndStreetValue);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(ApplicantDetailsActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    // Handle error
                    Toast.makeText(ApplicantDetailsActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
        );

        // Add the request to the RequestQueue
        requestQueue.add(jsonObjectRequest);
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
