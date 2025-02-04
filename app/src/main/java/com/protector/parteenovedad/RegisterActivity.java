package com.protector.parteenovedad;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    private AutoCompleteTextView tipoFuerzaDropdown, categoriaDropdown, gradoDropdown;
    private TextInputEditText nombresEditText, apellidosEditText, cedulaEditText,
            telefonoEditText, emailEditText;
    private Button registerButton;
    private TextView loginTextView;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);

            // Inicializar Firebase
            initializeFirebase();

            // Inicializar vistas
            initializeViews();

            // Configurar dropdowns
            setupDropdowns();

            // Configurar listeners
            setupListeners();

            Log.d(TAG, "Activity creada exitosamente");
        } catch (Exception e) {
            Log.e(TAG, "Error en onCreate: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void initializeFirebase() {
        try {
            mAuth = FirebaseAuth.getInstance();
            mDatabase = FirebaseDatabase.getInstance().getReference();
            Log.d(TAG, "Firebase inicializado correctamente");
        } catch (Exception e) {
            Log.e(TAG, "Error inicializando Firebase: " + e.getMessage());
            throw e;
        }
    }

    private void initializeViews() {
        try {
            tipoFuerzaDropdown = findViewById(R.id.tipoFuerzaDropdown);
            categoriaDropdown = findViewById(R.id.categoriaDropdown);
            gradoDropdown = findViewById(R.id.gradoDropdown);
            nombresEditText = findViewById(R.id.nombresEditText);
            apellidosEditText = findViewById(R.id.apellidosEditText);
            cedulaEditText = findViewById(R.id.cedulaEditText);
            telefonoEditText = findViewById(R.id.telefonoEditText);
            emailEditText = findViewById(R.id.emailEditText);
            registerButton = findViewById(R.id.registerButton);
            loginTextView = findViewById(R.id.loginTextView);
            Log.d(TAG, "Vistas inicializadas correctamente");
        } catch (Exception e) {
            Log.e(TAG, "Error inicializando vistas: " + e.getMessage());
            throw e;
        }
    }

    private void setupDropdowns() {
        try {
            // Tipo de Fuerza
            String[] tiposFuerza = {"Ejército Nacional", "Policía Nacional", "Fuerza Aérea", "Armada Nacional"};
            ArrayAdapter<String> tipoFuerzaAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_dropdown_item_1line, tiposFuerza);
            tipoFuerzaDropdown.setAdapter(tipoFuerzaAdapter);

            // Categoría
            String[] categorias = {"Oficiales", "Suboficiales", "Personal de Base"};
            ArrayAdapter<String> categoriaAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_dropdown_item_1line, categorias);
            categoriaDropdown.setAdapter(categoriaAdapter);

            // Listeners para actualización dinámica
            setupDropdownListeners(tiposFuerza, categorias);

            Log.d(TAG, "Dropdowns configurados correctamente");
        } catch (Exception e) {
            Log.e(TAG, "Error configurando dropdowns: " + e.getMessage());
            throw e;
        }
    }

    private void setupDropdownListeners(String[] tiposFuerza, String[] categorias) {
        // Actualizar grados cuando cambia el tipo de fuerza
        tipoFuerzaDropdown.setOnItemClickListener((parent, view, position, id) -> {
            String tipoFuerza = tiposFuerza[position];
            String categoria = categoriaDropdown.getText().toString();
            if (!categoria.isEmpty()) {
                updateGradosDropdown(tipoFuerza, categoria);
            }
        });

        // Actualizar grados cuando cambia la categoría
        categoriaDropdown.setOnItemClickListener((parent, view, position, id) -> {
            String tipoFuerza = tipoFuerzaDropdown.getText().toString();
            String categoria = categorias[position];
            if (!tipoFuerza.isEmpty()) {
                updateGradosDropdown(tipoFuerza, categoria);
            }
        });
    }

    private void setupListeners() {
        try {
            registerButton.setOnClickListener(v -> registerUser());
            loginTextView.setOnClickListener(v -> {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            });
            Log.d(TAG, "Listeners configurados correctamente");
        } catch (Exception e) {
            Log.e(TAG, "Error configurando listeners: " + e.getMessage());
            throw e;
        }
    }

    private void updateGradosDropdown(String tipoFuerza, String categoria) {
        String[] grados = getGradosList(tipoFuerza, categoria);
        ArrayAdapter<String> gradoAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, grados);
        gradoDropdown.setAdapter(gradoAdapter);
    }

    private String[] getGradosList(String tipoFuerza, String categoria) {
        switch (tipoFuerza) {
            case "Ejército Nacional":
                return getEjercitoGrados(categoria);
            case "Policía Nacional":
                return getPoliciaGrados(categoria);
            case "Fuerza Aérea":
                return getFuerzaAereaGrados(categoria);
            case "Armada Nacional":
                return getArmadaGrados(categoria);
            default:
                return new String[]{};
        }
    }

    private String[] getEjercitoGrados(String categoria) {
        switch (categoria) {
            case "Oficiales":
                return new String[]{
                        "General de Ejército (GDE)",
                        "Mayor General (MG)",
                        "Brigadier General (BG)",
                        "Coronel (CR)",
                        "Teniente Coronel (TC)",
                        "Mayor (MY)",
                        "Capitán (CT)",
                        "Teniente (TE)",
                        "Subteniente (ST)"
                };
            case "Suboficiales":
                return new String[]{
                        "Sargento Mayor de Comando Conjunto (SMCC)",
                        "Sargento Mayor de Ejército (SME)",
                        "Sargento Mayor de Comando (SMC)",
                        "Sargento Mayor (SM)",
                        "Sargento Primero (SP)",
                        "Sargento Viceprimero (SV)",
                        "Sargento Segundo (SS)",
                        "Cabo Primero (CP)",
                        "Cabo Segundo (CS)",
                        "Cabo Tercero (C3)"
                };
            case "Personal de Base":
                return new String[]{
                        "Dragoneante Profesional (DGP)",
                        "Soldado Profesional (SLP)",
                        "Dragoneante (DG)",
                        "Soldado (SL18/SL12)"
                };
            default:
                return new String[]{};
        }
    }

    private String[] getPoliciaGrados(String categoria) {
        switch (categoria) {
            case "Oficiales":
                return new String[]{
                        "General (GR)",
                        "Mayor General (MG)",
                        "Brigadier General (BG)",
                        "Coronel (CR)",
                        "Teniente Coronel (TC)",
                        "Mayor (MY)",
                        "Capitán (CT)",
                        "Teniente (TE)",
                        "Subteniente (ST)"
                };
            case "Suboficiales":
                return new String[]{
                        "Comisario de Dirección General (CMDG)",
                        "Comisario de Comando (CMC)",
                        "Comisario (CM)",
                        "Subcomisario (SC)",
                        "Intendente Jefe (IJ)",
                        "Intendente (IT)",
                        "Subintendente (SI)"
                };
            case "Personal de Base":
                return new String[]{
                        "Patrullero (PT)",
                        "Patrullero de Policía (PP)",
                        "Dragoneante (DG)",
                        "Agente (AG)"
                };
            default:
                return new String[]{};
        }
    }

    private String[] getFuerzaAereaGrados(String categoria) {
        switch (categoria) {
            case "Oficiales":
                return new String[]{
                        "General del Aire (GR)",
                        "Mayor General (MG)",
                        "Brigadier General (BG)",
                        "Coronel (CR)",
                        "Teniente Coronel (TC)",
                        "Mayor (MY)",
                        "Capitán (CT)",
                        "Teniente (TE)",
                        "Subteniente (ST)"
                };
            case "Suboficiales":
                return new String[]{
                        "Técnico Jefe de Comando (TJC)",
                        "Técnico Jefe (TJ)",
                        "Técnico Subjefe (TS)",
                        "Técnico Primero (T1)",
                        "Técnico Segundo (T2)",
                        "Técnico Tercero (T3)",
                        "Técnico Cuarto (T4)",
                        "Aerotécnico (AT)"
                };
            case "Personal de Base":
                return new String[]{
                        "Soldado Profesional (SLP)",
                        "Soldado (SLB/SLR)"
                };
            default:
                return new String[]{};
        }
    }

    private String[] getArmadaGrados(String categoria) {
        switch (categoria) {
            case "Oficiales":
                return new String[]{
                        "Almirante (ALM)",
                        "Vicealmirante (VA)",
                        "Contralmirante (CA)",
                        "Capitán de Navío (CN)",
                        "Capitán de Fragata (CF)",
                        "Capitán de Corbeta (CC)",
                        "Teniente de Navío (TN)",
                        "Teniente de Fragata (TF)",
                        "Teniente de Corbeta (TK)",
                        "General de Infantería de Marina (GR IM)",
                        "Mayor General de Infantería de Marina (MG IM)",
                        "Brigadier General de Infantería de Marina (BG IM)",
                        "Coronel de Infantería de Marina (CR IM)",
                        "Teniente Coronel de Infantería de Marina (TC IM)",
                        "Mayor de Infantería de Marina (MY IM)",
                        "Capitán de Infantería de Marina (CT IM)",
                        "Teniente Efectivo de Infantería de Marina (TE IM)",
                        "Subteniente de Infantería de Marina (ST IM)"
                };
            case "Suboficiales":
                return new String[]{
                        "Suboficial Jefe Técnico de Comando Conjunto (SJTCC)",
                        "Suboficial Jefe Técnico de Comando (SJTC)",
                        "Suboficial Jefe Técnico (SJT)",
                        "Suboficial Jefe (SJ)",
                        "Suboficial Primero (S1)",
                        "Suboficial Segundo (S2)",
                        "Suboficial Tercero (S3)",
                        "Marinero Primero (MA1)",
                        "Marinero Segundo (MA2)",
                        "Sargento Mayor de Comando Conjunto de IM (SMCCCIM)",
                        "Sargento Mayor de Comando de IM (SMCCIM)",
                        "Sargento Mayor de IM (SMCIM)",
                        "Sargento Primero de IM (SPCIM)",
                        "Sargento Viceprimero de IM (SVCIM)",
                        "Sargento Segundo de IM (SSCIM)",
                        "Cabo Primero de IM (CPCIM)",
                        "Cabo Segundo de IM (CSCIM)",
                        "Cabo Tercero de IM (C3CIM)"
                };
            case "Personal de Base":
                return new String[]{
                        "Dragoneante Profesional de IM (DGIMP)",
                        "Infante de Marina Profesional (IMP)",
                        "Dragoneante de IM (DGIM)"
                };
            default:
                return new String[]{};
        }
    }

    private void registerUser() {
        try {
            // Obtener y validar datos
            String tipoFuerza = Objects.requireNonNull(tipoFuerzaDropdown.getText()).toString().trim();
            String categoria = Objects.requireNonNull(categoriaDropdown.getText()).toString().trim();
            String grado = Objects.requireNonNull(gradoDropdown.getText()).toString().trim();
            String nombres = Objects.requireNonNull(nombresEditText.getText()).toString().trim();
            String apellidos = Objects.requireNonNull(apellidosEditText.getText()).toString().trim();
            String cedula = Objects.requireNonNull(cedulaEditText.getText()).toString().trim();
            String telefono = Objects.requireNonNull(telefonoEditText.getText()).toString().trim();
            String email = Objects.requireNonNull(emailEditText.getText()).toString().trim();

            // Validar campos
            if (!validateFields(tipoFuerza, categoria, grado, nombres, apellidos, cedula, telefono, email)) {
                return;
            }

            // Crear usuario en Firebase Auth
            createFirebaseUser(email, cedula, tipoFuerza, categoria, grado, nombres, apellidos, cedula, telefono);

        } catch (Exception e) {
            Log.e(TAG, "Error en el registro: " + e.getMessage());
            showToast("Error en el registro: " + e.getMessage());
        }
    }

    private boolean validateFields(String tipoFuerza, String categoria, String grado,
                                   String nombres, String apellidos, String cedula,
                                   String telefono, String email) {
        if (tipoFuerza.isEmpty()) {
            showToast("Seleccione el tipo de fuerza");
            return false;
        }
        if (categoria.isEmpty()) {
            showToast("Seleccione la categoría");
            return false;
        }
        if (grado.isEmpty()) {
            showToast("Seleccione el grado");
            return false;
        }
        if (nombres.isEmpty()) {
            showToast("Ingrese sus nombres");
            return false;
        }
        if (apellidos.isEmpty()) {
            showToast("Ingrese sus apellidos");
            return false;
        }
        if (cedula.isEmpty() || cedula.length() != 10) {
            showToast("Ingrese una cédula válida de 10 dígitos");
            return false;
        }
        if (telefono.isEmpty() || telefono.length() != 10) {
            showToast("Ingrese un teléfono válido de 10 dígitos");
            return false;
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Ingrese un correo electrónico válido");
            return false;
        }
        return true;
    }

    private void createFirebaseUser(String email, String password, String tipoFuerza,
                                    String categoria, String grado, String nombres,
                                    String apellidos, String cedula, String telefono) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                        saveUserData(userId, tipoFuerza, categoria, grado, nombres,
                                apellidos, cedula, telefono, email);
                    } else {
                        Log.e(TAG, "Error en createUserWithEmailAndPassword: ", task.getException());
                        showToast("Error en el registro: " +
                                Objects.requireNonNull(task.getException()).getMessage());
                    }
                });
    }

    private void saveUserData(String userId, String tipoFuerza, String categoria,
                              String grado, String nombres, String apellidos,
                              String cedula, String telefono, String email) {
        Map<String, Object> user = new HashMap<>();
        user.put("tipoFuerza", tipoFuerza);
        user.put("categoria", categoria);
        user.put("grado", grado);
        user.put("nombres", nombres);
        user.put("apellidos", apellidos);
        user.put("cedula", cedula);
        user.put("telefono", telefono);
        user.put("email", email);
        user.put("fechaRegistro", System.currentTimeMillis());

        mDatabase.child("users").child(userId).setValue(user)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Usuario registrado exitosamente");
                    showToast("Registro exitoso");
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error guardando datos del usuario: ", e);
                    showToast("Error al guardar los datos: " + e.getMessage());
                });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}