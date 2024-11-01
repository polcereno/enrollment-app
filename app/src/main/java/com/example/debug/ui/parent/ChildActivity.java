package com.example.debug.ui.parent;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.example.debug.R;
import com.example.debug.adapter.StudentAccountAdapter;
import com.example.debug.controller.UserUtil;
import com.example.debug.model.AccountStudent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChildActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StudentAccountAdapter adapter;
    private List<AccountStudent> accountStudentList;
    private RequestQueue requestQueue;
    private static final String URL = "http://enrol.lesterintheclouds.com/parent/fetch_child.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_child);

        Toolbar toolbar = findViewById(R.id.childToolbar);
        setSupportActionBar(toolbar);

        // Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_arrow_back_24);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.child_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.childRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        accountStudentList = new ArrayList<>();
        adapter = new StudentAccountAdapter(this, accountStudentList);
        recyclerView.setAdapter(adapter);

        // Initialize Volley RequestQueue
        requestQueue = Volley.newRequestQueue(this);

        // Fetch student data
        fetchStudentData();
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

    private void fetchStudentData() {
        int parentId = UserUtil.getCurrentUserId(this);
        String urlWithParam = URL + "?parent=" + parentId; // Add parent_id to the URL

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlWithParam, null,
                response -> {
                    try {
                        accountStudentList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String name = jsonObject.getString("name");
                            String level = jsonObject.getString("level");
                            String studentID = jsonObject.getString("studentID");

                            accountStudentList.add(new AccountStudent(0, name, "", "", level, studentID));
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> error.printStackTrace());

        requestQueue.add(jsonArrayRequest);
    }

}