package com.example.debug.controller;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressSelector {
    private RequestQueue requestQueue;
    private Fragment fragment;

    public AddressSelector(Fragment fragment) {
        this.fragment = fragment;
        requestQueue = Volley.newRequestQueue(fragment.requireContext());
    }

    public void loadProvinces(final AutoCompleteTextView autoCompleteTextViewProvince, final AutoCompleteTextView autoCompleteTextViewMunicipality, final AutoCompleteTextView autoCompleteTextViewBarangay) {
        String url = "https://psgc.cloud/api/provinces";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    List<String> provinces = new ArrayList<>();
                    final Map<String, String> provinceCodeMap = new HashMap<>();

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject province = response.getJSONObject(i);
                            String name = province.getString("name");
                            String code = province.getString("code");
                            provinces.add(name);
                            provinceCodeMap.put(name, code);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    Collections.sort(provinces.subList(1, provinces.size())); // Sort only the non-hint items

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(fragment.requireContext(),
                            android.R.layout.simple_dropdown_item_1line, provinces);
                    autoCompleteTextViewProvince.setAdapter(adapter);

                    autoCompleteTextViewProvince.setOnItemClickListener((parent, view, position, id) -> {
                        String selectedProvince = (String) parent.getItemAtPosition(position);
                        String provinceCode = provinceCodeMap.get(selectedProvince);
                        if (provinceCode != null) {
                            loadMunicipalities(provinceCode, autoCompleteTextViewMunicipality, autoCompleteTextViewBarangay);
                        }
                    });
                },
                error -> {
                    Toast.makeText(fragment.requireContext(), "Error loading provinces", Toast.LENGTH_SHORT).show();
                    Log.e("AddressSelector", "Error loading provinces: " + error.getMessage());
                });

        requestQueue.add(request);
    }

    public void loadMunicipalities(String provinceCode, final AutoCompleteTextView autoCompleteTextViewMunicipality, final AutoCompleteTextView autoCompleteTextViewBarangay) {
        String url = "https://psgc.cloud/api/provinces/" + provinceCode + "/municipalities";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    List<String> municipalities = new ArrayList<>();
                    final Map<String, String> municipalityCodeMap = new HashMap<>();

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject municipality = response.getJSONObject(i);
                            String name = municipality.getString("name");
                            String code = municipality.getString("code");
                            municipalities.add(name);
                            municipalityCodeMap.put(name, code);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    Collections.sort(municipalities.subList(1, municipalities.size())); // Sort only the non-hint items

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(fragment.requireContext(),
                            android.R.layout.simple_dropdown_item_1line, municipalities);
                    autoCompleteTextViewMunicipality.setAdapter(adapter);

                    autoCompleteTextViewMunicipality.setOnItemClickListener((parent, view, position, id) -> {
                        String selectedMunicipality = (String) parent.getItemAtPosition(position);
                        String municipalityCode = municipalityCodeMap.get(selectedMunicipality);
                        if (municipalityCode != null) {
                            loadBarangays(municipalityCode, autoCompleteTextViewBarangay);
                        }
                    });
                },
                error -> {
                    Toast.makeText(fragment.requireContext(), "Error loading municipalities", Toast.LENGTH_SHORT).show();
                    Log.e("AddressSelector", "Error loading municipalities: " + error.getMessage());
                });

        requestQueue.add(request);
    }

    public void loadBarangays(String municipalityCode, final AutoCompleteTextView autoCompleteTextViewBarangay) {
        String url = "https://psgc.cloud/api/municipalities/" + municipalityCode + "/barangays";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    List<String> barangays = new ArrayList<>();

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject barangay = response.getJSONObject(i);
                            barangays.add(barangay.getString("name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    Collections.sort(barangays.subList(1, barangays.size())); // Sort only the non-hint items

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(fragment.requireContext(),
                            android.R.layout.simple_dropdown_item_1line, barangays);
                    autoCompleteTextViewBarangay.setAdapter(adapter);
                },
                error -> {
                    Toast.makeText(fragment.requireContext(), "Error loading barangays", Toast.LENGTH_SHORT).show();
                    Log.e("AddressSelector", "Error loading barangays: " + error.getMessage());
                });

        requestQueue.add(request);
    }
}
