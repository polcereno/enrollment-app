package com.example.debug.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.debug.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PrerequisitesActivity extends AppCompatActivity {

    private Spinner currSpinner, subSpinner, reqSpinner, typeSpinner;
    private ArrayAdapter<String> currAdapter, subAdapter, reqAdapter, typeAdapter;
    private ArrayList<String> currList = new ArrayList<>();
    private ArrayList<String> subList = new ArrayList<>();
    private ArrayList<String> reqList = new ArrayList<>();
    private ArrayList<String> typeList = new ArrayList<>();
    private RequestQueue requestQueue;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_prerequisites);

        Toolbar toolbar = findViewById(R.id.prerequisitesToolbar);
        setSupportActionBar(toolbar);

        // Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_arrow_back_24);

        currSpinner = findViewById(R.id.currspinner);
        subSpinner = findViewById(R.id.subspinner);
        reqSpinner = findViewById(R.id.reqspinner);
        typeSpinner = findViewById(R.id.prespinner);
        saveButton = findViewById(R.id.curriculum_add);

        requestQueue = Volley.newRequestQueue(this);

        // Initialize Spinners and Adapters
        currAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currList);
        currAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currSpinner.setAdapter(currAdapter);

        subAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subList);
        subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subSpinner.setAdapter(subAdapter);

        reqAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, reqList);
        reqAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reqSpinner.setAdapter(reqAdapter);

        typeList.add("Pre-requisites");
        typeList.add("Co-requisites");
        typeList.add("Pre-requisite Any");
        typeList.add("Co-requisite Any");
        typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typeList);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        // Set Spinner Item Selected Listener
        currSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fetchSubjectsAndRequired(currList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Set Save Button Click Listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        // Fetch initial data
        fetchCurriculums();
    }

    private void fetchCurriculums() {
        String url = "https://enrol.lesterintheclouds.com/fetchcurr.php";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        currList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String curriculum = jsonObject.getString("program");
                                currList.add(curriculum);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        currAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PrerequisitesActivity.this, "Error fetching curriculums", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }

    private void fetchSubjectsAndRequired(String curriculum) {
        String url = "https://enrol.lesterintheclouds.com/fetcsubs.php";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray subjectsArray = jsonObject.getJSONArray("subjects");

                            subList.clear();
                            for (int i = 0; i < subjectsArray.length(); i++) {
                                subList.add(subjectsArray.getString(i));
                            }
                            subAdapter.notifyDataSetChanged();

                            fetchRequiredCourses(curriculum); // Fetch required courses with the curriculum

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PrerequisitesActivity.this, "Error fetching subjects", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("curriculum", curriculum);
                return params;
            }
        };
        requestQueue.add(request);
    }

    private void fetchRequiredCourses(String curriculum) {
        String url = "https://enrol.lesterintheclouds.com/fetchrequired.php";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray requiredArray = jsonObject.getJSONArray("requiredCourses");

                            reqList.clear();
                            for (int i = 0; i < requiredArray.length(); i++) {
                                reqList.add(requiredArray.getString(i));
                            }
                            reqAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(PrerequisitesActivity.this, "Parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError", error.toString());
                Toast.makeText(PrerequisitesActivity.this, "Error fetching required courses", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("curriculum", curriculum);
                return params;
            }
        };
        requestQueue.add(request);
    }

    private void saveData() {
        String selectedCurriculum = currSpinner.getSelectedItem().toString();
        String selectedSubject = subSpinner.getSelectedItem().toString();
        String selectedRequired = reqSpinner.getSelectedItem().toString();
        String selectedType = typeSpinner.getSelectedItem().toString();

        String url = "https://enrol.lesterintheclouds.com/saveprerequisites.php"; // Update URL to correct endpoint for saving data
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(PrerequisitesActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PrerequisitesActivity.this, "Error saving data", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("curriculum", selectedCurriculum);
                params.put("subject", selectedSubject);
                params.put("required", selectedRequired);
                params.put("type", selectedType);
                return params;
            }
        };
        requestQueue.add(request);
    }
}
