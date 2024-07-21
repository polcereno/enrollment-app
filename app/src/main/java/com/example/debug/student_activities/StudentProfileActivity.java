package com.example.debug.student_activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.debug.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.student_profile_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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

        // Get user_id from Intent
        int userId = getIntent().getIntExtra("user_id", -1);

        if (userId != -1) {
            fetchStudentData(userId);
        } else {
            Toast.makeText(this, "Invalid user ID", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchStudentData(int userId) {
        String url = "https://enrol.lesterintheclouds.com/authentication/fetch_student.php?user_id=" + userId;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.has("error")) {
                            Toast.makeText(StudentProfileActivity.this, jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Set the fetched data to the views
                        firstName.setText(jsonObject.getString("fname"));
                        lastName.setText(jsonObject.getString("lname"));
                        middleName.setText(jsonObject.optString("mname", ""));
                        sex.setText(jsonObject.getString("sex"));
                        birthdate.setText(jsonObject.getString("birthdate"));
                        email.setText(jsonObject.getString("email"));
                        phone.setText(jsonObject.getString("phone"));
                        province.setText(jsonObject.getString("province"));
                        municipality.setText(jsonObject.getString("municipality"));
                        barangay.setText(jsonObject.getString("barangay"));
                        purokAndStreet.setText(jsonObject.getString("purok"));
                        level.setText(jsonObject.getString("level"));
                        lrn.setText(jsonObject.getString("lrn"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(StudentProfileActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(StudentProfileActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

