package com.example.gameloginapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText etEmailPhone, etDisplayName, etUsername, etPassword, etConfirmPassword;
    private Button btnContinue;
    private ImageView btnBack;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initViews();
        setupListeners();
    }

    private void initViews() {
        etEmailPhone = findViewById(R.id.et_email_phone);
        etDisplayName = findViewById(R.id.et_display_name);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        btnContinue = findViewById(R.id.btn_continue);
        btnBack = findViewById(R.id.btn_back);
        tvLogin = findViewById(R.id.tv_login);
    }

    private void setupListeners() {
        // Back button
        btnBack.setOnClickListener(v -> onBackPressed());

        // Login text
        tvLogin.setOnClickListener(v -> {
            // Navigate to login activity
            // Intent intent = new Intent(this, LoginActivity.class);
            // startActivity(intent);
        });

        // Continue button
        btnContinue.setOnClickListener(v -> {
            if (validateInputs()) {
                // Handle sign up logic
                handleSignUp();
            }
        });

        // Character counter for display name
        etDisplayName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateCharacterCount(s.length(), 20);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Character counter for username
        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateUsernameCharacterCount(s.length(), 20);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void updateCharacterCount(int current, int max) {
        TextView counter = findViewById(R.id.tv_display_name_counter);
        if (counter != null) {
            counter.setText(current + "/" + max);
        }
    }

    private void updateUsernameCharacterCount(int current, int max) {
        TextView counter = findViewById(R.id.tv_username_counter);
        if (counter != null) {
            counter.setText(current + "/" + max);
        }
    }

    private boolean validateInputs() {
        String emailPhone = etEmailPhone.getText().toString().trim();
        String displayName = etDisplayName.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        if (emailPhone.isEmpty()) {
            etEmailPhone.setError("Email or phone number is required");
            return false;
        }

        if (displayName.isEmpty()) {
            etDisplayName.setError("Display name is required");
            return false;
        }

        if (username.isEmpty()) {
            etUsername.setError("Username is required");
            return false;
        }

        if (password.isEmpty()) {
            etPassword.setError("Password is required");
            return false;
        }

        if (!isPasswordValid(password)) {
            etPassword.setError("Password doesn't meet requirements");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords don't match");
            return false;
        }

        return true;
    }

    private boolean isPasswordValid(String password) {
        // Check if password meets requirements
        if (password.length() < 8) return false;

        boolean hasSpecialChar = password.matches(".*[#$%&@!].*");
        boolean hasNumber = password.matches(".*\\d.*");

        return hasSpecialChar && hasNumber;
    }

    private void handleSignUp() {
        // Implement your sign up logic here
        // This could involve API calls, database operations, etc.

        // Example:
        // String emailPhone = etEmailPhone.getText().toString().trim();
        // String displayName = etDisplayName.getText().toString().trim();
        // String username = etUsername.getText().toString().trim();
        // String password = etPassword.getText().toString();

        // Call your API or handle registration
    }
}