package com.example.debug.network;

import android.app.Activity;
import android.app.ProgressDialog;
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
    private final SubmitApplicantResultCallback callback;

    public SubmitApplicantResultTask(Activity activity, int applicantID, String result, SubmitApplicantResultCallback callback) {
        this.activity = activity;
        this.applicantID = applicantID;
        this.result = result;
        this.callback = callback;
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
                        boolean success = jsonResponse.optBoolean("success");
                        String message = success ? "Result submitted successfully." : "Failed to submit result: " + jsonResponse.optString("error");
                        callback.onResultSubmitted(success, message);
                    } catch (Exception e) {
                        callback.onResultSubmitted(false, "Error parsing response: " + e.getMessage());
                    }
                },
                error -> {
                    // Hide progress dialog
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }

                    // Handle error
                    callback.onResultSubmitted(false, "Result submitted successfully. " );
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
