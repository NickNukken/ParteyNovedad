package com.protector.parteenovedad;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;


import android.content.Intent;  // Agregamos esta importación

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView registerTextView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Inicializar vistas
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerTextView = findViewById(R.id.registerTextView);

        // Configurar click listener para el botón de inicio de sesión
        loginButton.setOnClickListener(v -> loginUser());

        // Configurar click listener para el texto de registro
        registerTextView.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, ChatActivity.class));
            finish();
            // TODO: Implementar navegación a la pantalla de registro
            Toast.makeText(LoginActivity.this, "Registro próximamente", Toast.LENGTH_SHORT).show();
        });
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Login exitoso
                        Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso",
                                Toast.LENGTH_SHORT).show();
                        // TODO: Navegar a la actividad principal
                    } else {
                        // Si falla el login
                        Toast.makeText(LoginActivity.this, "Error en el inicio de sesión: "
                                        + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}