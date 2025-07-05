package com.example.gameloginapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignUpActivity extends AppCompatActivity {

    private EditText etEmailPhone, etDisplayName, etUsername, etPassword, etConfirmPassword;
    private Button btnContinue;
    private ImageView btnBack;
    private TextView tvLogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
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
        String email = etEmailPhone.getText().toString().trim();
        String password = etPassword.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(SignUpActivity.this, "Sign up successful!", Toast.LENGTH_SHORT).show();
                    // Chuyển sang màn hình đăng nhập hoặc main
                    Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SignUpActivity.this, "Sign up failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
}