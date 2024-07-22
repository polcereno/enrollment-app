package com.example.debug.Controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.example.debug.ui.LoginActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class LogoutTask extends AsyncTask<Void, Void, String> {

    private ProgressDialog progressDialog;
    private Context context;

    public LogoutTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        // Show a progress dialog or loading indicator
        progressDialog = ProgressDialog.show(context, "Logging out", "Please wait...", true, false);
    }

    @Override
    protected String doInBackground(Void... params) {
        String response = null;
        HttpURLConnection conn = null;
        BufferedReader reader = null;

        try {
            URL url = new URL("https://enrol.lesterintheclouds.com/authentication/logout.php");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000); // Set timeout for connection
            conn.setReadTimeout(10000);    // Set timeout for reading response

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
                Log.e("LogoutTask", "HTTP error code: " + responseCode);
            }

        } catch (IOException e) {
            Log.e("LogoutTask", "Error during connection", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e("LogoutTask", "Error closing reader", e);
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
            handleLogoutResponse(result);
        } else {
            Toast.makeText(context, "Failed to connect to server", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleLogoutResponse(String result) {
        try {
            JSONObject json = new JSONObject(result);

            // Check for 'status' in JSON response
            if (json.has("status")) {
                String status = json.getString("status");

                if (status.equals("success")) {
                    // Logout successful, clear SharedPreferences and navigate to LoginActivity
                    Toast.makeText(context, json.getString("message"), Toast.LENGTH_SHORT).show();

                    // Clear SharedPreferences
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();

                    // Navigate to LoginActivity
                    context.startActivity(new Intent(context, LoginActivity.class));
                    if (context instanceof Activity) {
                        ((Activity) context).finish();
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
