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
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class StudentDetailActivity extends AppCompatActivity {

    // UI elements
    private TextView studentIDTextView;
    private TextInputEditText firstNameEditText, lastNameEditText, middleNameEditText, genderEditText,
            birthdateEditText, emailEditText, phoneEditText, provinceEditText, municipalityEditText,
            barangayEditText, purokEditText, levelEditText, lrnEditText;
    private AutoCompleteTextView parentAutoComplete, benefactorAutoComplete;
    private Button saveButton;

    // Other fields
    private ProgressDialog progressDialog;
    private ArrayAdapter<String> parentAdapter, benefactorAdapter;
    private ArrayList<String> parentNames = new ArrayList<>(), benefactorNames = new ArrayList<>();
    private boolean isDataChanged = false;
    private String studentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_detail);

        setupToolbar();
        initializeUIElements();
        setupProgressDialog();
        setupStudentDetails();
        fetchParentAccounts();
        fetchBenefactorAccounts();
        setTextChangeListeners();
        setupBirthdatePicker();
        setupSaveButton();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.studentDetailToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_arrow_back_24);
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.student_detail_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initializeUIElements() {
        studentIDTextView = findViewById(R.id.studentID);
        firstNameEditText = findViewById(R.id.firstName);
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
        levelEditText = findViewById(R.id.level);
        lrnEditText = findViewById(R.id.lrn);
        saveButton = findViewById(R.id.saveButton);
        parentAutoComplete = findViewById(R.id.parent);
        benefactorAutoComplete = findViewById(R.id.benefactor);
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

    private HashMap<String, String> parentIdMap = new HashMap<>();
    private HashMap<String, String> benefactorIdMap = new HashMap<>();

    private void fetchParentAccounts() {
        String url = "http://enrol.lesterintheclouds.com/accounts/fetch_parents.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> populateAutoCompleteList(response, "parents", parentNames, parentAutoComplete, parentIdMap),
                this::handleErrorResponse);
        Volley.newRequestQueue(this).add(request);
    }

    private void fetchBenefactorAccounts() {
        String url = "http://enrol.lesterintheclouds.com/accounts/fetch_benefactors.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    Log.d("BenefactorResponse", response.toString()); // Log the response for debugging
                    populateAutoCompleteList(response, "benefactors", benefactorNames, benefactorAutoComplete, benefactorIdMap);
                },
                this::handleErrorResponse);
        Volley.newRequestQueue(this).add(request);
    }

    private void populateAutoCompleteList(JSONObject response, String arrayKey, ArrayList<String> namesList, AutoCompleteTextView autoCompleteTextView, HashMap<String, String> idMap) {
        namesList.clear(); // Clear existing data
        idMap.clear(); // Clear existing ID mappings

        try {
            if (response.getBoolean("success")) {
                JSONArray array = response.getJSONArray(arrayKey);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    String id = obj.getString("id");
                    String name = obj.getString("name");
                    namesList.add(name);
                    idMap.put(name, id);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, namesList);
                autoCompleteTextView.setAdapter(adapter);
            } else {
                Toast.makeText(this, "No " + arrayKey + " found", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            Log.e("StudentDetailActivity", "JSON Error: " + e.getMessage());
            Toast.makeText(this, "Error fetching " + arrayKey + " accounts", Toast.LENGTH_SHORT).show();
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
        for (TextInputEditText field : new TextInputEditText[]{firstNameEditText, lastNameEditText, middleNameEditText, genderEditText, birthdateEditText, emailEditText, phoneEditText, provinceEditText, municipalityEditText, barangayEditText, purokEditText, levelEditText, lrnEditText}) {
            field.addTextChangedListener(textWatcher);
        }
        parentAutoComplete.addTextChangedListener(textWatcher);
        benefactorAutoComplete.addTextChangedListener(textWatcher);
    }

    private void setupBirthdatePicker() {
        birthdateEditText.setOnClickListener(v -> showDatePickerDialog());
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> birthdateEditText.setText(dayOfMonth + "/" + (month + 1) + "/" + year),
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void fetchStudentData() {
        String url = "http://enrol.lesterintheclouds.com/accounts/get_student.php?studentID=" + studentID;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, this::populateStudentFields, this::handleErrorResponse);
        Volley.newRequestQueue(this).add(request);
    }

    private void populateStudentFields(JSONObject response) {
        try {
            firstNameEditText.setText(response.optString("firstName", "N/A"));
            lastNameEditText.setText(response.optString("lastName", "N/A"));
            middleNameEditText.setText(response.optString("middleName", "N/A"));
            genderEditText.setText(response.optString("sex", "N/A"));
            birthdateEditText.setText(response.optString("birthdate", "N/A"));
            emailEditText.setText(response.optString("email", "N/A"));
            phoneEditText.setText(response.optString("phone", "N/A"));
            provinceEditText.setText(response.optString("province", "N/A"));
            municipalityEditText.setText(response.optString("municipality", "N/A"));
            barangayEditText.setText(response.optString("barangay", "N/A"));
            purokEditText.setText(response.optString("purok", "N/A"));
            levelEditText.setText(response.optString("level", "N/A"));
            lrnEditText.setText(response.optString("lrn", "N/A"));
        } catch (Exception e) {
            Log.e("StudentDetailActivity", "Error parsing student data: " + e.getMessage());
        }
    }

    private void setupSaveButton() {
        saveButton.setOnClickListener(v -> saveStudentData());
        saveButton.setEnabled(false);
    }

    private void saveStudentData() {
        progressDialog.show();

        // Collect data from all fields
        JSONObject studentData = new JSONObject();
        try {
            studentData.put("studentID", studentID);
            studentData.put("firstName", firstNameEditText.getText().toString().trim());
            studentData.put("lastName", lastNameEditText.getText().toString().trim());
            studentData.put("middleName", middleNameEditText.getText().toString().trim());
            studentData.put("sex", genderEditText.getText().toString().trim());
            studentData.put("birthdate", birthdateEditText.getText().toString().trim());
            studentData.put("email", emailEditText.getText().toString().trim());
            studentData.put("phone", phoneEditText.getText().toString().trim());
            studentData.put("province", provinceEditText.getText().toString().trim());
            studentData.put("municipality", municipalityEditText.getText().toString().trim());
            studentData.put("barangay", barangayEditText.getText().toString().trim());
            studentData.put("purok", purokEditText.getText().toString().trim());
            studentData.put("level", levelEditText.getText().toString().trim());
            studentData.put("lrn", lrnEditText.getText().toString().trim());

            // Save the parent and benefactor IDs instead of names
            String parentId = parentIdMap.get(parentAutoComplete.getText().toString().trim());
            String benefactorId = benefactorIdMap.get(benefactorAutoComplete.getText().toString().trim());
            studentData.put("parent", parentId != null ? parentId : ""); // save ID
            studentData.put("benefactor", benefactorId != null ? benefactorId : ""); // save ID

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error preparing data for save.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }

        // Define URL for update request
        String url = "http://enrol.lesterintheclouds.com/accounts/save_student.php";

        // Send request to update student details
        JsonObjectRequest updateRequest = new JsonObjectRequest(Request.Method.POST, url, studentData,
                response -> {
                    progressDialog.dismiss();
                    try {
                        if (response.getBoolean("success")) {
                            Toast.makeText(this, "Student data saved successfully", Toast.LENGTH_SHORT).show();
                            saveButton.setEnabled(false);
                            isDataChanged = false;
                        } else {
                            Toast.makeText(this, "Failed to save data. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Network error. Could not save data.", Toast.LENGTH_SHORT).show();
                }
        );

        // Add request to queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(updateRequest);
    }


    private void handleErrorResponse(Throwable error) {
        Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
