package com.example.debug.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class FetchApplicantDetailsTask {

    private static final String URL = "http://enrol.lesterintheclouds.com/admission/fetch_applicant_details.php";
    private Context context;
    private RequestQueue requestQueue;
    private FetchApplicantDetailsCallback callback;

    public FetchApplicantDetailsTask(Context context, FetchApplicantDetailsCallback callback) {
        this.context = context;
        this.callback = callback;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public void fetchApplicantDetails(int applicantID) {
        // Show progress dialog
        if (callback != null) {
            callback.onStartLoading();
        }

        // Create a JsonObjectRequest to fetch applicant details from the server
        String url = URL + "?applicantID=" + applicantID;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parse the JSON response
                            String levelValue = response.optString("type");
                            String lrnValue = response.optString("lrn");
                            String jhsAttendedValue = response.optString("jhs_attended");
                            String shsAttendedValue = response.optString("shs_attended");
                            String firstNameValue = response.optString("fname");
                            String lastNameValue = response.optString("lname");
                            String middleNameValue = response.optString("mname");
                            String sexValue = response.optString("sex");
                            String birthdateValue = response.optString("birthdate");
                            String emailValue = response.optString("email");
                            String phoneValue = response.optString("phone");
                            String provinceValue = response.optString("province");
                            String municipalityValue = response.optString("municipality");
                            String barangayValue = response.optString("barangay");
                            String purokAndStreetValue = response.optString("purok");

                            // Pass the data to the callback
                            if (callback != null) {
                                callback.onSuccess(levelValue, lrnValue, jhsAttendedValue, shsAttendedValue, firstNameValue, lastNameValue, middleNameValue, sexValue, birthdateValue, emailValue, phoneValue, provinceValue, municipalityValue, barangayValue, purokAndStreetValue);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Error parsing data", Toast.LENGTH_SHORT).show();
                        }
                        // Hide progress dialog
                        if (callback != null) {
                            callback.onFinishLoading();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT).show();
                        // Hide progress dialog
                        if (callback != null) {
                            callback.onFinishLoading();
                        }
                    }
                }
        );

        // Add the request to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }

    public interface FetchApplicantDetailsCallback {
        void onStartLoading();
        void onSuccess(String level, String lrn, String jhsAttended, String shsAttended, String firstName, String lastName, String middleName, String sex, String birthdate, String email, String phone, String province, String municipality, String barangay, String purokAndStreet);
        void onFinishLoading();
    }
}
