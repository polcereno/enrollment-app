package com.example.debug.student_activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
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
import com.example.debug.UserUtil;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {

    private TextInputLayout passwordLayout, newPasswordLayout;
    private TextInputEditText passwordEditText, newPasswordEditText;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);

        Toolbar toolbar = findViewById(R.id.changePasswordToolbar);
        setSupportActionBar(toolbar);

        // Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_arrow_back_24);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.change_password_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        passwordLayout = findViewById(R.id.password_input);
        newPasswordLayout = findViewById(R.id.new_password_input);
        passwordEditText = findViewById(R.id.password);
        newPasswordEditText = findViewById(R.id.new_password);

        Button saveButton = findViewById(R.id.save_button);

        saveButton.setOnClickListener(v -> {
            String currentPassword = passwordEditText.getText().toString().trim();
            String newPassword = newPasswordEditText.getText().toString().trim();

            // Validate input
            if (currentPassword.isEmpty() || newPassword.isEmpty()) {
                Toast.makeText(ChangePasswordActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Call the method to change the password
                changePassword(currentPassword, newPassword);
            }
        });
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

    private void changePassword(String currentPassword, String newPassword) {
        String url = "https://enrol.lesterintheclouds.com/authentication/change_password.php";

        // Replace with actual user ID if needed
        int userId = UserUtil.getUserId(this);

        // Show progress dialog
        progressDialog = new ProgressDialog(ChangePasswordActivity.this);
        progressDialog.setMessage("Changing password...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Create a request using Volley
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    // Dismiss the progress dialog
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    // Handle the server response
                    handleResponse(response);
                },
                error -> {
                    // Dismiss the progress dialog
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    // Handle error
                    Toast.makeText(ChangePasswordActivity.this, "Network error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(userId));
                params.put("current_password", currentPassword);
                params.put("new_password", newPassword);
                return params;
            }
        };

        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void handleResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");

            if (status.equals("success")) {
                Toast.makeText(ChangePasswordActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                // Optionally, navigate back or clear fields
                finish();
            } else {
                String message = jsonObject.optString("message", "Failed to change password");
                Toast.makeText(ChangePasswordActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(ChangePasswordActivity.this, "JSON parsing error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
