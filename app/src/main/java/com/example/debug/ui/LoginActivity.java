package com.example.debug.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.debug.controller.LoginTask;
import com.example.debug.R;
import com.example.debug.ui.signup.SignupActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText usernameEditText;
    private TextInputEditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        final TextInputLayout usernameInputLayout = findViewById(R.id.username_input);
        final TextInputLayout passwordInputLayout = findViewById(R.id.password_input);
        final Button loginButton = findViewById(R.id.login_button);
        final AppCompatTextView signup = findViewById(R.id.signup);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            String role = sharedPreferences.getString("role", "");
            int userId = sharedPreferences.getInt("user_id", -1);

            Class<?> destinationActivity;
            switch (role) {
                case "admin":
                    destinationActivity = AdminActivity.class;
                    break;
                case "registrar":
                    destinationActivity = RegistrarActivity.class;
                    break;
                case "student":
                    destinationActivity = StudentActivity.class;
                    break;
                case "benefactor":
                    destinationActivity = BenefactorActivity.class;
                    break;
                case "parent":
                    destinationActivity = ParentActivity.class;
                    break;
                case "instructor":
                    destinationActivity = InstructorActivity.class;
                    break;
                default:
                    Toast.makeText(LoginActivity.this, "Unknown role: " + role, Toast.LENGTH_SHORT).show();
                    return;
            }

            Intent intent = new Intent(LoginActivity.this, destinationActivity);
            intent.putExtra("user_id", userId);  // Pass user_id to the next activity if needed
            startActivity(intent);
            finish();
        }

        loginButton.setOnClickListener(v -> attemptLogin());

        signup.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(i);
        });
    }

    private void attemptLogin() {
        String username = Objects.requireNonNull(usernameEditText.getText()).toString().trim();
        String password = Objects.requireNonNull(passwordEditText.getText()).toString().trim();

        if (!username.isEmpty() && !password.isEmpty()) {
            new LoginTask(LoginActivity.this).execute(username, password);
        } else {
            Toast.makeText(LoginActivity.this, "Username or password is empty", Toast.LENGTH_SHORT).show();
        }
    }
}
