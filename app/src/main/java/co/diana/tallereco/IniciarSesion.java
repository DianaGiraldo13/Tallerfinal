package co.diana.tallereco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class IniciarSesion extends AppCompatActivity {

    EditText correoIngreso;
    EditText contraseñaIngreso;
    Button botonIngresar;
    Button botonRegistro;
    private FirebaseAuth autenticacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        correoIngreso = findViewById(R.id.correoIngreso);
        contraseñaIngreso = findViewById(R.id.contraseñaIngreso);
        botonIngresar= findViewById(R.id.botonIngresar);
        botonRegistro = findViewById(R.id.botonRegistro);
        autenticacion=FirebaseAuth.getInstance();

        botonIngresar.setOnClickListener(
                (v)->{
                    if(correoIngreso.getText().toString().isEmpty()||contraseñaIngreso.getText().toString().isEmpty()){


                        Toast.makeText(this, "Campo vacios", Toast.LENGTH_SHORT).show();
                    }

                    else{


                        autenticacion.signInWithEmailAndPassword(correoIngreso.getText().toString(),contraseñaIngreso.getText().toString()).addOnCompleteListener(
                                task -> {

                                    if(task.isSuccessful()){

                                        Intent intent = new Intent(this,ActivityInicio.class);
                                        startActivity(intent);

                                    }

                                }
                        );

                    }
                }
        );

        botonRegistro.setOnClickListener(
                (v)->{
                    Intent intent=new Intent(this,Registro.class);
                    startActivity(intent);
                }
        );

    }
}