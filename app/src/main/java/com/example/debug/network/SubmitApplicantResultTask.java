package com.example.debug.network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SubmitApplicantResultTask {

    private final Activity activity;
    private final int applicantID;
    private final String result;
    private ProgressDialog progressDialog;

    public SubmitApplicantResultTask(Activity activity, int applicantID, String result) {
        this.activity = activity;
        this.applicantID = applicantID;
        this.result = result;
    }

    public void execute() {
        // Show progress dialog
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Submitting result...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Create a Volley request queue
        RequestQueue requestQueue = Volley.newRequestQueue(activity);

        // URL of your PHP script
        String url = "http://enrol.lesterintheclouds.com/admission/submit_applicant_result.php";

        // Create a String request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    // Hide progress dialog
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }

                    // Handle successful response
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        if (jsonResponse.optBoolean("success")) {
                            Toast.makeText(activity, "Result submitted successfully.", Toast.LENGTH_SHORT).show();
                            // Close the current activity and go back to the previous one
                            activity.finish();
                        } else {
                            Toast.makeText(activity, "Failed to submit result: " + jsonResponse.optString("error"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(activity, "Error parsing response: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    // Hide progress dialog
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }

                    // Handle error
                    Toast.makeText(activity, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("applicantID", String.valueOf(applicantID));
                params.put("result", result);
                return params;
            }
        };

        // Add the request to the queue
        requestQueue.add(stringRequest);
    }
}
