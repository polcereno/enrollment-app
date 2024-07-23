package com.example.debug.ui.admission;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.debug.Adapters.ApplicantAdapter;
import com.example.debug.Adapters.ResultAdapter;
import com.example.debug.Models.Applicant;
import com.example.debug.Models.Result;
import com.example.debug.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdmissionResultsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ResultAdapter adapter;
    private List<Result> resultList;
    private RequestQueue requestQueue;
    private static final String URL = "http://enrol.lesterintheclouds.com/admission/fetch_result.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admission_results);

        Toolbar toolbar = findViewById(R.id.admissionResultsToolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.resultRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        resultList = new ArrayList<>();
        adapter = new ResultAdapter(this, resultList);
        recyclerView.setAdapter(adapter);

        // Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_arrow_back_24);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.admission_results_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fetchResults();
    }

    private void fetchResults() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        resultList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String level = jsonObject.getString("level");
                                String firstName = jsonObject.getString("firstname");
                                String lastName = jsonObject.getString("lastname");
                                String middleName = jsonObject.optString("middlename", null);
                                String result = jsonObject.getString("result");

                                Result resultItem = new Result(id, level, firstName, lastName, middleName, result);
                                resultList.add(resultItem);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(AdmissionResultsActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AdmissionResultsActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(this);
        }
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        if (item.getItemId() == android.R.id.home) {
            // Navigate back to the previous activity
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}