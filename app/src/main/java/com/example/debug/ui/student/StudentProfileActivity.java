package com.example.debug.ui.student;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.debug.R;

import org.json.JSONException;
import org.json.JSONObject;

public class StudentProfileActivity extends AppCompatActivity {

    private TextView studentID, firstName, lastName, middleName, sex, birthdate, email, phone, province, municipality, barangay, purokAndStreet, level, lrn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        Toolbar toolbar = findViewById(R.id.studentProfileToolbar);
        setSupportActionBar(toolbar);

        // Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_arrow_back_24);

        // Initialize views
        studentID = findViewById(R.id.studentID); // Initialize studentID TextView
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
        level = findViewById(R.id.level);
        lrn = findViewById(R.id.lrn);

        // Fetch and display student data
        fetchStudentData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchStudentData() {
        // Get the user_id from SharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int userId = sharedPreferences.getInt("user_id", -1);

        if (userId == -1) {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "https://enrol.lesterintheclouds.com/students/student_profile.php?user_id=" + userId;

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("StudentProfileActivity", "Response: " + response.toString()); // Log the raw JSON response

                        try {
                            String status = response.getString("status");
                            if (status.equals("success")) {
                                JSONObject data = response.getJSONObject("data");

                                // Update the UI with student data
                                studentID.setText(data.optString("student_id", "N/A")); // Update studentID
                                firstName.setText(data.optString("first_name", "N/A"));
                                middleName.setText(data.optString("middle_name", "N/A"));
                                lastName.setText(data.optString("last_name", "N/A"));
                                sex.setText(data.optString("sex", "N/A"));
                                birthdate.setText(data.optString("birthdate", "N/A"));
                                email.setText(data.optString("email", "N/A"));
                                phone.setText(data.optString("phone", "N/A"));
                                province.setText(data.optString("province", "N/A"));
                                municipality.setText(data.optString("municipality", "N/A"));
                                barangay.setText(data.optString("barangay", "N/A"));
                                purokAndStreet.setText(data.optString("purok_and_street", "N/A"));
                                level.setText(data.optString("level", "N/A"));
                                lrn.setText(data.optString("lrn", "N/A"));
                            } else {
                                Toast.makeText(StudentProfileActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Log.e("StudentProfileActivity", "JSON parsing error: " + e.getMessage()); // Log JSON parsing error
                            Toast.makeText(StudentProfileActivity.this, "JSON parsing error", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("StudentProfileActivity", "Network error: " + error.getMessage()); // Log network error
                        Toast.makeText(StudentProfileActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        queue.add(jsonObjectRequest);
    }
}
