package co.diana.tallereco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class ActivityInicio extends AppCompatActivity {

    private EditText editTextNombre;
    private EditText editTextTelefono;
    private Button buttonAgregar;
    private ListView Contenedor;
    private AdaptadorContacto adaptadorContacto;
    private Usuario usuarioActual;
    private ValueEventListener valueEventListener;
    private FirebaseDatabase db;
    private FirebaseAuth autenticacion;


   // private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        editTextNombre= findViewById(R.id.editTextNombre);
        db=FirebaseDatabase.getInstance();
        autenticacion=FirebaseAuth.getInstance();
        editTextTelefono= findViewById(R.id.inputTelefono);
        buttonAgregar= findViewById(R.id.botonAgregar);
        Contenedor= findViewById(R.id.Contenedor);
        //database = FirebaseDatabase.getInstance();
        adaptadorContacto = new AdaptadorContacto();
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.CALL_PHONE,}, 1);

        String id =autenticacion.getCurrentUser().getUid();

        db.getReference().child("usuarios").child(id).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        usuarioActual = snapshot.getValue(Usuario.class);
                        MostrarContactos();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                }

        );


        buttonAgregar.setOnClickListener(

                (v)->{

                if(editTextNombre.getText().toString().isEmpty()||editTextTelefono.getText().toString().isEmpty()){

                    Toast.makeText(this, "Los campos vacios", Toast.LENGTH_SHORT).show();
                }

                else{
                        Contacto contacto = new Contacto(id,usuarioActual.getId(),editTextNombre.getText().toString(),editTextTelefono.getText().toString());
                        adaptadorContacto.AñadirContacto(contacto);
                        db.getReference().child("contactos/"+usuarioActual.getId()).setValue(contacto);
                }

                }
        );

        Contenedor.setAdapter(adaptadorContacto);
    }

    public void MostrarContactos(){

        valueEventListener = db.getReference().child("contactos").orderByChild("idusuario").equalTo(usuarioActual.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adaptadorContacto.LimpiarLista();
                for (DataSnapshot child : snapshot.getChildren()) {

                    Contacto contacto = child.getValue(Contacto.class);
                    adaptadorContacto.AñadirContacto(contacto);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}