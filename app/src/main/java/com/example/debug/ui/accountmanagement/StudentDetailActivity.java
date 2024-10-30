package com.example.debug.ui.accountmanagement;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.debug.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class StudentDetailActivity extends AppCompatActivity {

    private TextView studentIDTextView;
    private TextInputEditText firstNameEditText, lastNameEditText, middleNameEditText, genderEditText, birthdateEditText, emailEditText, phoneEditText, provinceEditText, municipalityEditText, barangayEditText, purokEditText;
    private TextInputLayout[] inputLayouts;
    private TextInputEditText[] inputFields;
    private AutoCompleteTextView parentAutoComplete;
    private ArrayAdapter<String> parentAdapter;
    private ArrayList<String> parentNames;
    private AutoCompleteTextView benefactorAutoComplete;
    private ArrayAdapter<String> benefactorAdapter;
    private ArrayList<String> benefactorNames;
    private Button saveButton;
    private ProgressDialog progressDialog;
    private String studentID;
    private boolean isDataChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_detail);

        setupToolbar();
        initializeUIElements(); // Make sure to call this before accessing UI elements
        setupProgressDialog();
        setupStudentDetails();
        fetchParentAccounts();
        fetchBenefactorAccounts();
        setTextChangeListeners();
        setupBirthdatePicker();
        setupSaveButton();
    }

    private void initializeUIElements() {
        // Initialize UI Elements
        studentIDTextView = findViewById(R.id.studentID);
        firstNameEditText = findViewById(R.id.firstName); // Initialize EditText here
        lastNameEditText = findViewById(R.id.lastName);
        middleNameEditText = findViewById(R.id.middleName);
        genderEditText = findViewById(R.id.sex);
        birthdateEditText = findViewById(R.id.birthdate);
        emailEditText = findViewById(R.id.email);
        phoneEditText = findViewById(R.id.phone);
        provinceEditText = findViewById(R.id.province);
        municipalityEditText = findViewById(R.id.municipality);
        barangayEditText = findViewById(R.id.barangay);
        purokEditText = findViewById(R.id.purok);

        inputLayouts = new TextInputLayout[] {
                findViewById(R.id.firstNameLayout),
                findViewById(R.id.lastNameLayout),
                findViewById(R.id.middleNameLayout),
                findViewById(R.id.sexLayout),
                findViewById(R.id.birthdateLayout),
                findViewById(R.id.emailLayout),
                findViewById(R.id.phoneLayout),
                findViewById(R.id.provinceLayout),
                findViewById(R.id.municipalityLayout),
                findViewById(R.id.barangayLayout),
                findViewById(R.id.purokLayout),
                findViewById(R.id.parentLayout),
                findViewById(R.id.benefactorLayout),
                findViewById(R.id.levelLayout),
                findViewById(R.id.lrnLayout)
        };

        inputFields = new TextInputEditText[] {
                firstNameEditText,
                lastNameEditText,
                middleNameEditText,
                genderEditText,
                birthdateEditText,
                emailEditText,
                phoneEditText,
                provinceEditText,
                municipalityEditText,
                barangayEditText,
                purokEditText,
                findViewById(R.id.level),
                findViewById(R.id.lrn)
        };

        saveButton = findViewById(R.id.saveButton);
        parentAutoComplete = findViewById(R.id.parent);
        parentNames = new ArrayList<>();
        benefactorAutoComplete = findViewById(R.id.benefactor);
        benefactorNames = new ArrayList<>();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.studentDetailToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_arrow_back_24);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.student_detail_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    private void setupProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving data...");
        progressDialog.setCancelable(false);
    }

    private void setupStudentDetails() {
        studentID = getIntent().getStringExtra("studentID");
        studentIDTextView.setText(studentID);
        fetchStudentData();
    }

    private void fetchParentAccounts() {
        String url = "http://enrol.lesterintheclouds.com/accounts/fetch_parents.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                this::handleParentAccountsResponse,
                this::handleErrorResponse);

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }

    private void fetchBenefactorAccounts() {
        String url = "http://enrol.lesterintheclouds.com/accounts/fetch_benefactors.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                this::handleBenefactorAccountsResponse,
                this::handleErrorResponse);

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }

    private void handleParentAccountsResponse(JSONObject response) {
        try {
            if (response.getBoolean("success")) {
                JSONArray parentsArray = response.getJSONArray("parents");
                for (int i = 0; i < parentsArray.length(); i++) {
                    String name = parentsArray.optString(i, "N/A"); // Avoid null values
                    parentNames.add(name);
                }
                parentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, parentNames);
                parentAutoComplete.setAdapter(parentAdapter);
            } else {
                Toast.makeText(this, "No parents found", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            Log.e("StudentDetailActivity", "JSON Error: " + e.getMessage());
            Toast.makeText(this, "Error fetching parent accounts", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleBenefactorAccountsResponse(JSONObject response) {
        try {
            if (response.getBoolean("success")) {
                JSONArray benefactorArray = response.getJSONArray("benefactor");
                for (int i = 0; i < benefactorArray.length(); i++) {
                    String name = benefactorArray.optString(i, "N/A"); // Avoid null values
                    benefactorNames.add(name);
                }
                benefactorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, benefactorNames);
                benefactorAutoComplete.setAdapter(benefactorAdapter);
            } else {
                Toast.makeText(this, "No parents found", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            Log.e("StudentDetailActivity", "JSON Error: " + e.getMessage());
            Toast.makeText(this, "Error fetching benefactor accounts", Toast.LENGTH_SHORT).show();
        }
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

        for (TextInputEditText field : inputFields) {
            field.addTextChangedListener(textWatcher);
        }
        parentAutoComplete.addTextChangedListener(textWatcher);

        for (TextInputEditText field : inputFields) {
            field.addTextChangedListener(textWatcher);
        }
        benefactorAutoComplete.addTextChangedListener(textWatcher);
    }

    private void setupBirthdatePicker() {
        inputFields[5].setOnClickListener(v -> showDatePickerDialog());
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                    inputFields[5].setText(selectedDate);  // Birthdate field
                },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void fetchStudentData() {
        String url = "http://enrol.lesterintheclouds.com/accounts/get_student.php?studentID=" + studentID;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                this::handleStudentDataResponse,
                this::handleErrorResponse);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void handleStudentDataResponse(JSONObject response) {
        try {
            inputFields[0].setText(response.optString("firstName", "N/A"));  // First Name
            inputFields[1].setText(response.optString("lastName", "N/A"));  // Last Name
            inputFields[2].setText(response.optString("middleName", "N/A"));  // Middle Name
            inputFields[3].setText(response.optString("sex", "N/A"));  // Sex
            inputFields[4].setText(response.optString("birthdate", "N/A"));  // Birthdate
            inputFields[5].setText(response.optString("email", "N/A"));  // Email
            inputFields[6].setText(response.optString("phone", "N/A"));  // Phone
            inputFields[7].setText(response.optString("province", "N/A"));  // Province
            inputFields[8].setText(response.optString("municipality", "N/A"));  // Municipality
            inputFields[9].setText(response.optString("barangay", "N/A"));  // Barangay
            inputFields[10].setText(response.optString("purok", "N/A"));  // Purok

            // Update parent and benefactor fields correctly
            String parent = response.optString("parent");
            String benefactor = response.optString("benefactor");
            parentAutoComplete.setText(parent != null ? parent : "N/A");  // Parent
            benefactorAutoComplete.setText(benefactor != null ? benefactor : "N/A");  // Benefactor

            inputFields[11].setText(response.optString("level", "N/A"));  // Level
            inputFields[12].setText(response.optString("lrn", "N/A"));  // LRN

        } catch (Exception e) {
            Log.e("StudentDetailActivity", "Error parsing student data: " + e.getMessage());
            Toast.makeText(this, "Error loading student data", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupSaveButton() {
        saveButton.setOnClickListener(v -> {
            if (isDataChanged) {
                saveStudentData();
            }
        });
    }

    private void saveStudentData() {
        // Create your save request here
        String url = "http://enrol.lesterintheclouds.com/accounts/save_student.php";
        progressDialog.show();

        JSONObject studentData = new JSONObject();
        try {
            studentData.put("studentID", studentID);
            // Add other fields accordingly
            studentData.put("parent", parentAutoComplete.getText().toString());
            studentData.put("benefactor", benefactorAutoComplete.getText().toString());
            // Add other input fields...
            studentData.put("firstName", firstNameEditText.getText().toString());
            studentData.put("lastName", lastNameEditText.getText().toString());
            studentData.put("middleName", middleNameEditText.getText().toString());
            studentData.put("gender", genderEditText.getText().toString());
            studentData.put("birthdate", birthdateEditText.getText().toString());
            studentData.put("email", emailEditText.getText().toString());
            studentData.put("phone", phoneEditText.getText().toString());
            studentData.put("province", provinceEditText.getText().toString());
            studentData.put("municipality", municipalityEditText.getText().toString());
            studentData.put("barangay", barangayEditText.getText().toString());
            studentData.put("purok", purokEditText.getText().toString());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, studentData,
                    response -> {
                        progressDialog.dismiss();
                        Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                        // Optionally navigate to another activity or clear the form
                    },
                    error -> {
                        progressDialog.dismiss();
                        handleErrorResponse(error);
                    }
            );

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonObjectRequest);

        } catch (JSONException e) {
            progressDialog.dismiss();
            Log.e("StudentDetailActivity", "JSON Error: " + e.getMessage());
            Toast.makeText(this, "Error preparing data for saving", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleErrorResponse(VolleyError error) {
        // Handle error response here
        if (error.networkResponse != null && error.networkResponse.data != null) {
            String errorMessage = new String(error.networkResponse.data);
            Log.e("StudentDetailActivity", "Server Error: " + errorMessage);
            Toast.makeText(this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
        } else {
            Log.e("StudentDetailActivity", "Network Error: " + error.getMessage());
            Toast.makeText(this, "Network error, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Close activity on back button
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
