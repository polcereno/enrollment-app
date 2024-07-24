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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.debug.adapter.ApplicantAdapter;
import com.example.debug.model.Applicant;
import com.example.debug.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdmissionApplicantsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ApplicantAdapter adapter;
    private List<Applicant> applicantList;
    private RequestQueue requestQueue;
    private static final String URL = "http://enrol.lesterintheclouds.com/admission/fetch_applicants.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admission_applicants);

        Toolbar toolbar = findViewById(R.id.admissionApplicantsToolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.applicantsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        applicantList = new ArrayList<>();
        adapter = new ApplicantAdapter(this, applicantList);
        recyclerView.setAdapter(adapter);

        // Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_arrow_back_24);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.admission_applicants_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        requestQueue = Volley.newRequestQueue(this);
        fetchApplicants();
    }

    private void fetchApplicants() {
        // Create a JsonArrayRequest to fetch applicants from the server
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                response -> {
                    // Clear the existing list
                    applicantList.clear();

                    // Parse the JSON response
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);

                            int id = jsonObject.getInt("id");
                            String level = jsonObject.getString("level");
                            String firstName = jsonObject.getString("firstName");
                            String lastName = jsonObject.getString("lastName");
                            String middleName = jsonObject.getString("middleName");

                            // Create an Applicant object and add it to the list
                            Applicant applicant = new Applicant(id, level, firstName, lastName, middleName);
                            applicantList.add(applicant);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    // Notify the adapter that data has changed
                    adapter.notifyDataSetChanged();
                },
                error -> {
                    // Handle error
                    Toast.makeText(AdmissionApplicantsActivity.this, "No Applicants Available", Toast.LENGTH_SHORT).show();
                }
        );

        // Add the request to the RequestQueue
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
