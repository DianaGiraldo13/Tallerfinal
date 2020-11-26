package co.diana.tallereco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class Registro extends AppCompatActivity {


    private EditText nombre, correo, contraseña, repetirContraseña;
    private Button registrar;
    private Button ingresar;
    private FirebaseDatabase db;
    private FirebaseAuth autenticacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nombre = findViewById(R.id.inputNombre);
        db = FirebaseDatabase.getInstance();
        autenticacion = FirebaseAuth.getInstance();
        correo = findViewById(R.id.inputEmail);
        contraseña = findViewById(R.id.inputContraseña);
        repetirContraseña = findViewById(R.id.inputRepetirContraseña);
        registrar = findViewById(R.id.botonRegistar);
        ingresar = findViewById(R.id.botonIngreso);

        ingresar.setOnClickListener(
                (v) -> {

                    Intent intent = new Intent(this, IniciarSesion.class);
                    startActivity(intent);

                }
        );
        registrar.setOnClickListener(

                (v) -> {


                    if (nombre.getText().toString().isEmpty() || correo.getText().toString().isEmpty() || contraseña.getText().toString().isEmpty() || repetirContraseña.getText().toString().isEmpty()) {

                        Toast.makeText(this, "Llenar todos los campos", Toast.LENGTH_SHORT).show();
                    } else {


                        if (contraseña.getText().toString().equals(repetirContraseña.getText().toString())) {

                            autenticacion.createUserWithEmailAndPassword(correo.getText().toString(), contraseña.getText().toString()).addOnCompleteListener(

                                    (task) -> {

                                        if (task.isSuccessful()) {

                                            String id = autenticacion.getCurrentUser().getUid();
                                            Usuario usuario = new Usuario(id, nombre.getText().toString());
                                            db.getReference("usuarios").child(id).setValue(usuario).addOnCompleteListener(
                                                    (complete) -> {

                                                        if (complete.isComplete()) {

                                                            Intent intent = new Intent(this, ActivityInicio.class);
                                                            startActivity(intent);

                                                        }
                                                    }
                                            );


                                        }


                                    }
                            );

                        }

                    }

                }
        );
    }
}


