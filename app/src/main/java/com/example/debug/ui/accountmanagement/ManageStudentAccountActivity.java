package com.example.debug.ui.accountmanagement;

import android.os.Bundle;
import android.view.MenuItem;

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
import com.example.debug.Adapters.StudentAccountAdapter;
import com.example.debug.Models.Account;
import com.example.debug.Models.Student;
import com.example.debug.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ManageStudentAccountActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StudentAccountAdapter adapter;
    private List<Account> accountList;
    private RequestQueue requestQueue;
    private static final String URL = "http://enrol.lesterintheclouds.com/fetch_students_account.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_student_account);

        Toolbar toolbar = findViewById(R.id.manageStudentAccountToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_arrow_back_24);

        recyclerView = findViewById(R.id.accountRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        accountList = new ArrayList<>();
        adapter = new StudentAccountAdapter(this, accountList);
        recyclerView.setAdapter(adapter);

        // Initialize Volley RequestQueue
        requestQueue = Volley.newRequestQueue(this);

        // Fetch student data
        fetchStudentData();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.manage_student_account_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchStudentData() {
        // Create a JsonArrayRequest to fetch data from the server
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                response -> {
                    try {
                        // Clear the existing list
                        accountList.clear();

                        // Parse the JSON response
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);

                            String name = jsonObject.getString("name");
                            String level = jsonObject.getString("level");
                            String studentID = jsonObject.getString("studentID");

                            // Add to the list
                            accountList.add(new Account(0, name, "", "", level, studentID));
                        }

                        // Notify the adapter of data changes
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // Handle error
                    error.printStackTrace();
                });

        // Add the request to the RequestQueue.
        requestQueue.add(jsonArrayRequest);
    }
}


