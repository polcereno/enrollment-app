package com.example.debug.Controller;


import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.debug.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataFetcher {
    private RequestQueue requestQueue;

    public DataFetcher(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public void loadProvinces(final Spinner spinnerProvince, final Spinner spinnerMunicipality, final Spinner spinnerBarangay) {
        String url = "https://psgc.cloud/api/provinces";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<String> provinces = new ArrayList<>();
                provinces.add("Province"); // Add hint
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject province = response.getJSONObject(i);
                        provinces.add(province.getString("name"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(spinnerProvince.getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, provinces);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerProvince.setAdapter(adapter);

                spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position > 0) { // Skip the hint
                            String selectedProvince = provinces.get(position);
                            try {
                                String provinceCode = response.getJSONObject(position - 1).getString("code");
                                loadMunicipalities(provinceCode, spinnerMunicipality, spinnerBarangay);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(spinnerProvince.getContext(), "Error loading provinces", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(request);
    }

    public void loadMunicipalities(String provinceCode, final Spinner spinnerMunicipality, final Spinner spinnerBarangay) {
        String url = "https://psgc.cloud/api/provinces/" + provinceCode + "/municipalities";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<String> municipalities = new ArrayList<>();
                municipalities.add("Municipality"); // Add hint
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject municipality = response.getJSONObject(i);
                        municipalities.add(municipality.getString("name"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(spinnerMunicipality.getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, municipalities);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerMunicipality.setAdapter(adapter);

                spinnerMunicipality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position > 0) { // Skip the hint
                            String selectedMunicipality = municipalities.get(position);
                            try {
                                String municipalityCode = response.getJSONObject(position - 1).getString("code");
                                loadBarangays(municipalityCode, spinnerBarangay);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(spinnerMunicipality.getContext(), "Error loading municipalities", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(request);
    }

    public void loadBarangays(String municipalityCode, final Spinner spinnerBarangay) {
        String url = "https://psgc.cloud/api/municipalities/" + municipalityCode + "/barangays";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<String> barangays = new ArrayList<>();
                barangays.add("Barangay"); // Add hint
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject barangay = response.getJSONObject(i);
                        barangays.add(barangay.getString("name"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(spinnerBarangay.getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, barangays);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerBarangay.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(spinnerBarangay.getContext(), "Error loading barangays", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(request);
    }
}

