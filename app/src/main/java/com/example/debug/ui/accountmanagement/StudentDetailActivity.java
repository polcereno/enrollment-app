package com.example.debug.ui.accountmanagement;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.debug.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog; // Add this import
import android.os.AsyncTask; // Add this import

import java.util.Calendar;

public class StudentDetailActivity extends AppCompatActivity {

    private TextView studentIDTextView;
    private TextInputLayout firstNameLayout, lastNameLayout, middleNameLayout, sexLayout, birthdateLayout, emailLayout, phoneLayout, provinceLayout, municipalityLayout, barangayLayout, purokLayout, levelLayout, lrnLayout;
    private TextInputEditText firstName, lastName, middleName, sex, birthdate, email, phone, province, municipality, barangay, purok, level, lrn;
    private Button saveButton;
    private String studentID;
    private boolean isDataChanged = false;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_detail);

        Toolbar toolbar = findViewById(R.id.studentDetailToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_arrow_back_24);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.student_detail_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        studentIDTextView = findViewById(R.id.studentID);

        firstNameLayout = findViewById(R.id.firstNameLayout);
        lastNameLayout = findViewById(R.id.lastNameLayout);
        middleNameLayout = findViewById(R.id.middleNameLayout);
        sexLayout = findViewById(R.id.sexLayout);
        birthdateLayout = findViewById(R.id.birthdateLayout);
        emailLayout = findViewById(R.id.emailLayout);
        phoneLayout = findViewById(R.id.phoneLayout);
        provinceLayout = findViewById(R.id.provinceLayout);
        municipalityLayout = findViewById(R.id.municipalityLayout);
        barangayLayout = findViewById(R.id.barangayLayout);
        purokLayout = findViewById(R.id.purokLayout);
        levelLayout = findViewById(R.id.levelLayout);
        lrnLayout = findViewById(R.id.lrnLayout);

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
        purok = findViewById(R.id.purok);
        level = findViewById(R.id.level);
        lrn = findViewById(R.id.lrn);

        saveButton = findViewById(R.id.saveButton);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving data...");
        progressDialog.setCancelable(false);

        studentID = getIntent().getStringExtra("studentID");
        studentIDTextView.setText(studentID);

        fetchStudentData();

        setTextChangeListeners();

        birthdate.setOnClickListener(v -> showDatePickerDialog());

        saveButton.setOnClickListener(v -> saveStudentData());
    }

    private void setTextChangeListeners() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isDataChanged = true;
                saveButton.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        firstName.addTextChangedListener(textWatcher);
        lastName.addTextChangedListener(textWatcher);
        middleName.addTextChangedListener(textWatcher);
        sex.addTextChangedListener(textWatcher);
        birthdate.addTextChangedListener(textWatcher);
        email.addTextChangedListener(textWatcher);
        phone.addTextChangedListener(textWatcher);
        province.addTextChangedListener(textWatcher);
        municipality.addTextChangedListener(textWatcher);
        barangay.addTextChangedListener(textWatcher);
        purok.addTextChangedListener(textWatcher);
        level.addTextChangedListener(textWatcher);
        lrn.addTextChangedListener(textWatcher);
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, monthOfYear, dayOfMonth1) -> {
                    String selectedDate = dayOfMonth1 + "/" + (monthOfYear + 1) + "/" + year1;
                    birthdate.setText(selectedDate);
                },
                year, month, dayOfMonth);

        datePickerDialog.show();
    }

    private void fetchStudentData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://enrol.lesterintheclouds.com/get_student.php?studentID=" + studentID;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        String firstNameText = response.optString("firstName", "N/A");
                        String lastNameText = response.optString("lastName", "N/A");
                        String middleNameText = response.optString("middleName", "N/A");
                        String sexText = response.optString("sex", "N/A");
                        String birthdateText = response.optString("birthdate", "N/A");
                        String emailText = response.optString("email", "N/A");
                        String phoneText = response.optString("phone", "N/A");
                        String provinceText = response.optString("province", "N/A");
                        String municipalityText = response.optString("municipality", "N/A");
                        String barangayText = response.optString("barangay", "N/A");
                        String purokText = response.optString("purok", "N/A");
                        String levelText = response.optString("level", "N/A");
                        String lrnText = response.optString("lrn", "N/A");

                        firstName.setText(firstNameText);
                        lastName.setText(lastNameText);
                        middleName.setText(middleNameText);
                        sex.setText(sexText);
                        birthdate.setText(birthdateText);
                        email.setText(emailText);
                        phone.setText(phoneText);
                        province.setText(provinceText);
                        municipality.setText(municipalityText);
                        barangay.setText(barangayText);
                        purok.setText(purokText);
                        level.setText(levelText);
                        lrn.setText(lrnText);

                        isDataChanged = false;
                        saveButton.setEnabled(false);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(StudentDetailActivity.this, "Error parsing data: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(StudentDetailActivity.this, "Error fetching data: " + error.getMessage(), Toast.LENGTH_LONG).show();
                });

        requestQueue.add(jsonObjectRequest);
    }

    private void saveStudentData() {
        if (!isDataChanged) return;

        progressDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://enrol.lesterintheclouds.com/update_student.php";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("studentID", studentID);
            jsonObject.put("firstName", firstName.getText().toString());
            jsonObject.put("middleName", middleName.getText().toString());
            jsonObject.put("lastName", lastName.getText().toString());
            jsonObject.put("sex", sex.getText().toString());
            jsonObject.put("birthdate", birthdate.getText().toString());
            jsonObject.put("email", email.getText().toString());
            jsonObject.put("phone", phone.getText().toString());
            jsonObject.put("province", province.getText().toString());
            jsonObject.put("municipality", municipality.getText().toString());
            jsonObject.put("barangay", barangay.getText().toString());
            jsonObject.put("purok", purok.getText().toString());
            jsonObject.put("level", level.getText().toString());
            jsonObject.put("lrn", lrn.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        if (success) {
                            Toast.makeText(StudentDetailActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(StudentDetailActivity.this, "Error saving data", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(StudentDetailActivity.this, "Error parsing response: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    } finally {
                        progressDialog.dismiss();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(StudentDetailActivity.this, "Error saving data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                });

        requestQueue.add(jsonObjectRequest);

        isDataChanged = false;
        saveButton.setEnabled(false);
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