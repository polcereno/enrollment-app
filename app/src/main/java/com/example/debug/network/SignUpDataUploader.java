package com.example.debug.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SignUpDataUploader {

    public interface UploadCallback {
        void onSuccess(String response);
        void onError(String errorMessage);
    }

    private Context context;
    private UploadCallback callback;

    public SignUpDataUploader(Context context, UploadCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void uploadData(Map<String, String> params) {

        Log.d("SignUpDataUploader", "Params: " + params.toString());

        // Create a request queue
        RequestQueue queue = Volley.newRequestQueue(context);

        // URL of the PHP script
        String url = "https://enrol.lesterintheclouds.com/admission/signup.php";

        // Create a POST request
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle response from server
                        if ("Username already exists".equals(response.trim())) {
                            callback.onError("Username already exists");
                        } else if ("New record created successfully".equals(response.trim())) {
                            callback.onSuccess(response);
                        } else {
                            callback.onError("Error Uploading Data: " + response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError("Error Uploading Data: " + error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };

        // Add request to queue
        queue.add(postRequest);
    }
}
