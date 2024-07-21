package com.example.debug;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.example.debug.admin_activities.AdminActivity;
import com.example.debug.benefactor_activities.BenefactorActivity;
import com.example.debug.instructor_activities.InstructorActivity;
import com.example.debug.parent_activities.ParentActivity;
import com.example.debug.registrar_activity.RegistrarActivity;
import com.example.debug.student_activities.StudentActivity;

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

public class LoginTask extends AsyncTask<String, Void, String> {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    private ProgressDialog progressDialog;

    public LoginTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        // Show a progress dialog or loading indicator
        progressDialog = ProgressDialog.show(context, "Logging in", "Please wait...", true, false);
    }

    @Override
    protected String doInBackground(String... params) {
        String username = params[0];
        String password = params[1];
        String response = null;

        HttpURLConnection conn = null;
        BufferedReader reader = null;

        try {
            URL url = new URL("https://enrol.lesterintheclouds.com/authentication/login.php");
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
            e.printStackTrace();
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
            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleLoginResponse(String result) {
        try {
            Log.d("LoginTask", "Response from server: " + result);

            JSONObject json = new JSONObject(result);

            // Check for 'status' in JSON response
            if (json.has("status")) {
                String status = json.getString("status");

                if (status.equals("success")) {
                    // Login successful, retrieve 'role' and 'user_id'
                    String role = json.getString("role");
                    int userId = json.getInt("user_id");

                    // Save login information in SharedPreferences
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.putString("role", role);
                    editor.putInt("user_id", userId); // Save user_id
                    editor.apply();

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
                        case "instructor":
                            destinationActivity = InstructorActivity.class;
                            break;
                        default:
                            Toast.makeText(context, "Unknown role: " + role, Toast.LENGTH_SHORT).show();
                            return;
                    }

                    // Start the activity corresponding to the user's role
                    Intent intent = new Intent(context, destinationActivity);
                    intent.putExtra("user_id", userId);  // Pass user_id to the next activity if needed
                    context.startActivity(intent);
                    if (context instanceof LoginActivity) {
                        ((LoginActivity) context).finish(); // finish LoginActivity to prevent going back
                    }
                } else {
                    // Handle other status scenarios (e.g., error messages)
                    String errorMessage = json.optString("message", "Unknown error");
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Invalid server response", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "JSON parsing error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
