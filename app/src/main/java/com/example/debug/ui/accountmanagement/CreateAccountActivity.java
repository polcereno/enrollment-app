package com.example.debug.ui.accountmanagement;

import android.app.ProgressDialog;
import android.os.Bundle;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.debug.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity {

    private TextInputEditText name, username, password;
    private Spinner role;
    private TextView roleErrorText;
    private Button create;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account);

        Toolbar toolbar = findViewById(R.id.createAccountToolbar);
        setSupportActionBar(toolbar);

        // Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_arrow_back_24);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.create_account_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        role = findViewById(R.id.role);
        roleErrorText = findViewById(R.id.roleErrorText);
        create = findViewById(R.id.create_button);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Creating account...");
        progressDialog.setCancelable(false);

        setupRoleSpinner();
        setupButtonListeners();
    }

    private void setupButtonListeners() {
        create.setOnClickListener(v -> {
            if (validateInputs()) {
                sendDataToServer();
            }
        });
    }

    private void setupRoleSpinner() {
        ArrayAdapter<CharSequence> roleAdapter = ArrayAdapter.createFromResource(
                this, R.array.role_array, android.R.layout.simple_spinner_item);
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        role.setAdapter(roleAdapter);

        role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                roleErrorText.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private boolean validateInputs() {
        boolean isValid = true;

        // Validate name
        if (TextUtils.isEmpty(name.getText())) {
            name.setError("Username is required");
            isValid = false;
        }

        // Validate username
        if (TextUtils.isEmpty(username.getText())) {
            username.setError("Username is required");
            isValid = false;
        }

        // Validate password
        if (TextUtils.isEmpty(password.getText())) {
            password.setError("Password is required");
            isValid = false;
        }

        // Validate role
        if (role.getSelectedItemPosition() == 0) {
            roleErrorText.setVisibility(View.VISIBLE);
            roleErrorText.setText("Role is required");
            isValid = false;
        } else {
            roleErrorText.setVisibility(View.GONE);
        }

        return isValid;
    }

    private void sendDataToServer() {
        // Show progress dialog
        progressDialog.show();

        String url = "https://enrol.lesterintheclouds.com/authentication/create_account.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    // Dismiss progress dialog
                    progressDialog.dismiss();

                    // Handle the response from the server
                    Toast.makeText(CreateAccountActivity.this, response, Toast.LENGTH_LONG).show();
                },
                error -> {
                    // Dismiss progress dialog
                    progressDialog.dismiss();

                    // Handle error
                    Toast.makeText(CreateAccountActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name.getText().toString().trim());
                params.put("username", username.getText().toString().trim());
                params.put("password", password.getText().toString().trim());
                params.put("role", role.getSelectedItem().toString().trim());
                return params;
            }
        };

        // Add the request to the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
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
