package com.protector.parteenovedad;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import android.util.Log;


import android.content.Intent;  // Agregamos esta importación

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView registerTextView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            Log.d("LoginActivity", "Iniciando onCreate");

            setContentView(R.layout.activity_login);

            mAuth = FirebaseAuth.getInstance();
            Log.d("LoginActivity", "Firebase Auth inicializado");

            // Inicializar vistas
            emailEditText = findViewById(R.id.emailEditText);
            passwordEditText = findViewById(R.id.passwordEditText);
            loginButton = findViewById(R.id.loginButton);
            registerTextView = findViewById(R.id.registerTextView);

            Log.d("LoginActivity", "Vistas inicializadas");

            // Setup listeners
            setupClickListeners();

        } catch (Exception e) {
            Log.e("LoginActivity", "Error en onCreate: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupClickListeners() {
        try {
            loginButton.setOnClickListener(v -> loginUser());
            registerTextView.setOnClickListener(v -> {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            });
        } catch (Exception e) {
            Log.e("LoginActivity", "Error configurando listeners: " + e.getMessage());
        }
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(LoginActivity.this, ChatActivity.class));
                            finish();
                        } else {
                            String errorMessage = task.getException() != null ?
                                    task.getException().getMessage() :
                                    "Error de autenticación";
                            Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}