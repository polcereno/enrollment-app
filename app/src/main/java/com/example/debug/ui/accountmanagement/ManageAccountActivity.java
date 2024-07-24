package com.example.debug.ui.accountmanagement;

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
import com.example.debug.adapter.AccountsAdapter;
import com.example.debug.model.Accounts;
import com.example.debug.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ManageAccountActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AccountsAdapter accountsAdapter;
    private List<Accounts> accountsList = new ArrayList<>();
    private static final String URL = "http://enrol.lesterintheclouds.com/authentication/fetch_accounts.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_account);

        Toolbar toolbar = findViewById(R.id.manageAccountToolbar);
        setSupportActionBar(toolbar);

        // Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_arrow_back_24);

        recyclerView = findViewById(R.id.accountsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.manage_account_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Fetch accounts data
        fetchAccountsData();
    }

    private void fetchAccountsData() {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    try {
                        // Clear existing data
                        accountsList.clear();

                        // Parse the JSON array response
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            String username = jsonObject.getString("username");
                            String role = jsonObject.getString("role");

                            Accounts account = new Accounts(id, username, role);
                            accountsList.add(account);
                        }

                        // Set up the adapter with the data
                        accountsAdapter = new AccountsAdapter(this, accountsList);
                        recyclerView.setAdapter(accountsAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        // Handle JSON parsing error
                        Toast.makeText(this, "Error parsing data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    // Handle network error
                    error.printStackTrace();
                    Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
        );

        queue.add(jsonArrayRequest);
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
