package com.example.debug;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.debug.admin_activities.AdminActivity;
import com.example.debug.benefactor_activities.BenefactorActivity;
import com.example.debug.parent_activities.ParentActivity;
import com.example.debug.registrar_activity.RegistrarActivity;
import com.example.debug.student_activities.StudentActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText usernameEditText;
    private TextInputEditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        final TextInputLayout usernameInputLayout = findViewById(R.id.username_input);
        final TextInputLayout passwordInputLayout = findViewById(R.id.password_input);
        final Button loginButton = findViewById(R.id.login_button);
        final AppCompatTextView signup = findViewById(R.id.signup);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginButton.setOnClickListener(v -> attemptLogin());

        signup.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(i);
        });
    }

    private void attemptLogin() {
        String username = Objects.requireNonNull(usernameEditText.getText()).toString().trim();
        String password = Objects.requireNonNull(passwordEditText.getText()).toString().trim();

        if (!username.isEmpty() && !password.isEmpty()) {
            new LoginTask().execute(username, password);
        } else {
            Toast.makeText(LoginActivity.this, "Username or password is empty", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class LoginTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            // Show a progress dialog or loading indicator
            progressDialog = ProgressDialog.show(LoginActivity.this, "Logging in", "Please wait...", true, false);
        }

        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            String response = null;

            HttpURLConnection conn = null;
            BufferedReader reader = null;

            try {
                URL url = new URL("http://lesterintheclouds.com/enrolment_app/login.php");
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setConnectTimeout(10000); // Set timeout for connection
                conn.setReadTimeout(10000);    // Set timeout for reading response

                // Send data
                OutputStream os = conn.getOutputStream();
                OutputStreamWriter writer = new OutputStreamWriter(os, StandardCharsets.UTF_8);
                String postData = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                writer.write(postData);
                writer.flush();
                writer.close();
                os.close();

                Log.d("LoginTask", "Post data: " + postData);

                // Get response
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder responseBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        responseBuilder.append(line);
                    }
                    response = responseBuilder.toString();
                } else {
                    Log.e("LoginTask", "HTTP error code: " + responseCode);
                }

            } catch (IOException e) {
                Log.e("LoginTask", "Error during connection", e);
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e("LoginTask", "Error closing reader", e);
                    }
                }
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            // Dismiss the progress dialog
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            // Handle the result
            if (result != null) {
                handleLoginResponse(result);
            } else {
                Toast.makeText(LoginActivity.this, "Failed to connect to server", Toast.LENGTH_SHORT).show();
            }
        }

        private void handleLoginResponse(String result) {
            try {
                Log.d("LoginActivity", "Response from server: " + result);

                JSONObject json = new JSONObject(result);

                // Check for 'status' in JSON response
                if (json.has("status")) {
                    String status = json.getString("status");

                    if (status.equals("success")) {
                        // Login successful, retrieve 'role'
                        String role = json.getString("role");

                        // Navigate to respective activity based on role
                        Class<?> destinationActivity;
                        switch (role) {
                            case "admin":
                                destinationActivity = AdminActivity.class;
                                break;
                            case "registrar":
                                destinationActivity = RegistrarActivity.class;
                                break;
                            case "student":
                                destinationActivity = StudentActivity.class;
                                break;
                            case "benefactor":
                                destinationActivity = BenefactorActivity.class;
                                break;
                            case "parent":
                                destinationActivity = ParentActivity.class;
                                break;
                            default:
                                Toast.makeText(LoginActivity.this, "Unknown role: " + role, Toast.LENGTH_SHORT).show();
                                return;
                        }

                        // Start the activity corresponding to the user's role
                        startActivity(new Intent(LoginActivity.this, destinationActivity));
                        finish(); // Optionally finish LoginActivity to prevent going back
                    } else {
                        // Handle other status scenarios (e.g., error messages)
                        String errorMessage = json.optString("message", "Unknown error");
                        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid server response", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(LoginActivity.this, "JSON parsing error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

}
