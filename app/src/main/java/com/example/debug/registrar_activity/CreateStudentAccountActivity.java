package com.example.debug.registrar_activity;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.debug.Controller.DataFetcher;
import com.example.debug.StudentAccount;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.fragment.NavHostFragment;

import com.example.debug.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreateStudentAccountActivity extends AppCompatActivity {

    private DataFetcher dataFetcher;
    private ProgressDialog progressDialog;

    private TextInputEditText fnameEditText;
    private TextInputEditText mnameEditText;
    private TextInputEditText lnameEditText;
    private Spinner sexSpinner;
    private TextInputEditText emailEditText;
    private TextInputEditText phoneEditText;
    private TextInputEditText purokEditText;
    private TextInputEditText birthdateEditText;
    private Spinner provinceSpinner;
    private Spinner municipalitySpinner;
    private Spinner barangaySpinner;
    private TextInputEditText lrnEditText;
    private Spinner levelSpinner;
    private TextInputEditText usernameEditText;
    private TextInputEditText passwordEditText;

    private TextInputLayout fnameLayout;
    private TextInputLayout mnameLayout;
    private TextInputLayout lnameLayout;
    private TextInputLayout emailLayout;
    private TextInputLayout phoneLayout;
    private TextInputLayout purokLayout;
    private TextInputLayout birthdateLayout;
    private TextInputLayout lrnLayout;
    private TextInputLayout usernameLayout;
    private TextInputLayout passwordLayout;

    private TextView sexErrorText;
    private TextView provinceErrorText;
    private TextView municipalityErrorText;
    private TextView barangayErrorText;
    private TextView levelErrorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_student_account);

        dataFetcher = new DataFetcher(this);

        Toolbar toolbar = findViewById(R.id.createStudentAccountToolbar);
        setSupportActionBar(toolbar);

        // Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_arrow_back_24);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.create_student_account_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fnameEditText = findViewById(R.id.fname);
        mnameEditText = findViewById(R.id.mname);
        lnameEditText = findViewById(R.id.lname);
        sexSpinner = findViewById(R.id.sex);
        emailEditText = findViewById(R.id.email);
        phoneEditText = findViewById(R.id.phone);
        purokEditText = findViewById(R.id.purok);
        birthdateEditText = findViewById(R.id.birthdate);
        provinceSpinner = findViewById(R.id.spinner_province);
        municipalitySpinner = findViewById(R.id.spinner_municipality);
        barangaySpinner = findViewById(R.id.spinner_barangay);
        lrnEditText = findViewById(R.id.lrn);
        levelSpinner = findViewById(R.id.level);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);

        fnameLayout = findViewById(R.id.fname_input);
        mnameLayout = findViewById(R.id.mname_input);
        lnameLayout = findViewById(R.id.lname_input);
        emailLayout = findViewById(R.id.email_input);
        phoneLayout = findViewById(R.id.phone_input);
        purokLayout = findViewById(R.id.purok_input);
        birthdateLayout = findViewById(R.id.birthdate_input);
        lrnLayout = findViewById(R.id.lrn_input);
        usernameLayout = findViewById(R.id.username_input);
        passwordLayout = findViewById(R.id.password_input);

        sexErrorText = findViewById(R.id.sexErrorText);
        provinceErrorText = findViewById(R.id.provinceErrorText);
        municipalityErrorText = findViewById(R.id.municipalityErrorText);
        barangayErrorText = findViewById(R.id.barangayErrorText);
        levelErrorText = findViewById(R.id.levelErrorText);

        setupGenderSpinner();
        loadProvinces();
        setupLevelSpinner();
        setupButtonListeners();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupButtonListeners() {
        Button finishButton = findViewById(R.id.finish_button);
        finishButton.setOnClickListener(v -> {
            if (validateForm()) {
                submitForm();
            }
        });

        birthdateEditText.setOnClickListener(v -> showDatePickerDialog());
    }

    private void setupGenderSpinner() {
        ArrayAdapter<CharSequence> sexAdapter = ArrayAdapter.createFromResource(
                this, R.array.gender_array, android.R.layout.simple_spinner_item);
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexSpinner.setAdapter(sexAdapter);

        sexSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sexErrorText.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void loadProvinces() {
        dataFetcher.loadProvinces(provinceSpinner, municipalitySpinner, barangaySpinner);

        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                provinceErrorText.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        municipalitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                municipalityErrorText.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        barangaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                barangayErrorText.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupLevelSpinner() {
        ArrayAdapter<CharSequence> levelAdapter = ArrayAdapter.createFromResource(
                this, R.array.level_array, android.R.layout.simple_spinner_item);
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelSpinner.setAdapter(levelAdapter);

        levelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                levelErrorText.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, monthOfYear, dayOfMonth1) -> {
                    String selectedDate = String.format("%04d-%02d-%02d", year1, monthOfYear + 1, dayOfMonth1);
                    birthdateEditText.setText(selectedDate);
                },
                year, month, dayOfMonth);

        datePickerDialog.show();
    }

    private boolean validateForm() {
        boolean isValid = true;

        if (TextUtils.isEmpty(fnameEditText.getText())) {
            fnameLayout.setError("First name is required");
            isValid = false;
        } else {
            fnameLayout.setError(null);
        }

        if (TextUtils.isEmpty(lnameEditText.getText())) {
            lnameLayout.setError("Last name is required");
            isValid = false;
        } else {
            lnameLayout.setError(null);
        }

        if (sexSpinner.getSelectedItemPosition() == 0) {
            sexErrorText.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            sexErrorText.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(emailEditText.getText())) {
            emailLayout.setError("Email is required");
            isValid = false;
        } else {
            emailLayout.setError(null);
        }

        if (TextUtils.isEmpty(phoneEditText.getText())) {
            phoneLayout.setError("Phone is required");
            isValid = false;
        } else {
            phoneLayout.setError(null);
        }

        if (TextUtils.isEmpty(purokEditText.getText())) {
            purokLayout.setError("Purok is required");
            isValid = false;
        } else {
            purokLayout.setError(null);
        }

        if (TextUtils.isEmpty(birthdateEditText.getText())) {
            birthdateLayout.setError("Birthdate is required");
            isValid = false;
        } else {
            birthdateLayout.setError(null);
        }

        if (provinceSpinner.getSelectedItemPosition() == 0) {
            provinceErrorText.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            provinceErrorText.setVisibility(View.GONE);
        }

        if (municipalitySpinner.getSelectedItemPosition() == 0) {
            municipalityErrorText.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            municipalityErrorText.setVisibility(View.GONE);
        }

        if (barangaySpinner.getSelectedItemPosition() == 0) {
            barangayErrorText.setVisibility(View.VISIBLE);
            isValid = false;
        } else {
            barangayErrorText.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(lrnEditText.getText())) {
            lrnLayout.setError("LRN is required");
            isValid = false;
        } else {
            lrnLayout.setError(null);
        }

        if (TextUtils.isEmpty(usernameEditText.getText())) {
            usernameLayout.setError("Username is required");
            isValid = false;
        } else {
            usernameLayout.setError(null);
        }

        if (TextUtils.isEmpty(passwordEditText.getText())) {
            passwordLayout.setError("Password is required");
            isValid = false;
        } else {
            passwordLayout.setError(null);
        }

        return isValid;
    }

    private void submitForm() {
        String url = "https://enrol.lesterintheclouds.com/submit_student.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    progressDialog.dismiss();
                    if (response.contains("New student record and user credentials created successfully")) {
                        finish();
                    } else {
                        showToast("Submission failed: " + response);
                    }
                },
                error -> {
                    progressDialog.dismiss();
                    showToast("Submission failed: " + error.getMessage());
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("fname", fnameEditText.getText().toString());
                params.put("mname", mnameEditText.getText().toString()); // Can be empty
                params.put("lname", lnameEditText.getText().toString());
                params.put("sex", sexSpinner.getSelectedItem().toString());
                params.put("email", emailEditText.getText().toString());
                params.put("phone", phoneEditText.getText().toString());
                params.put("purok", purokEditText.getText().toString());
                params.put("birthdate", birthdateEditText.getText().toString());
                params.put("province", provinceSpinner.getSelectedItem().toString());
                params.put("municipality", municipalitySpinner.getSelectedItem().toString());
                params.put("barangay", barangaySpinner.getSelectedItem().toString());
                params.put("lrn", lrnEditText.getText().toString());
                params.put("level", levelSpinner.getSelectedItem().toString());
                params.put("username", usernameEditText.getText().toString());
                params.put("password", passwordEditText.getText().toString());
                return params;
            }
        };

        progressDialog = new ProgressDialog(CreateStudentAccountActivity.this);
        progressDialog.setMessage("Submitting data...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void showToast(String message) {
        Toast.makeText(CreateStudentAccountActivity.this, message, Toast.LENGTH_LONG).show();
    }
}

