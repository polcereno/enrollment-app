package com.example.debug.ui.student;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

    private TextView firstName, lastName, middleName, sex, birthdate, email, phone, province, municipality, barangay, purokAndStreet, level, lrn;

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

        String url = "https://enrol.lesterintheclouds.com/student_profile.php?user_id=" + userId;

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")) {
                                JSONObject data = response.getJSONObject("data");
                                // Update the UI with student data
                                firstName.setText(data.getString("first_name"));
                                middleName.setText(data.optString("middle_name", "N/A"));
                                lastName.setText(data.getString("last_name"));
                                sex.setText(data.getString("sex"));
                                birthdate.setText(data.getString("birthdate"));
                                email.setText(data.getString("email"));
                                phone.setText(data.getString("phone"));
                                province.setText(data.getString("province"));
                                municipality.setText(data.getString("municipality"));
                                barangay.setText(data.getString("barangay"));
                                purokAndStreet.setText(data.getString("purok_and_street"));
                                level.setText(data.getString("level"));
                                lrn.setText(data.getString("lrn"));
                            } else {
                                Toast.makeText(StudentProfileActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(StudentProfileActivity.this, "JSON parsing error", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(StudentProfileActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        queue.add(jsonObjectRequest);
    }
}
