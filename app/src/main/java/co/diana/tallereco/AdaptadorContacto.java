package co.diana.tallereco;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdaptadorContacto extends BaseAdapter {

    private ArrayList <Contacto> arrayActivityContactos;
    private FirebaseDatabase db;

    public  AdaptadorContacto(){

        arrayActivityContactos = new ArrayList<>();
        db=FirebaseDatabase.getInstance();

    }



    @Override
    public int getCount() {
        return arrayActivityContactos.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayActivityContactos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View vista= layoutInflater.inflate(R.layout.tarjetacontacto,null);
        TextView nombre = vista.findViewById(R.id.textViewNombreContacto);
        TextView telefono = vista.findViewById(R.id.textViewTelefonoContacto);
        ImageButton llamar= vista.findViewById(R.id.botonLlamar);
        llamar.setOnClickListener(

                (v)->{

                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+arrayActivityContactos.get(i).getNombre()));
                    viewGroup.getContext().startActivity(intent);

                }
        );
        ImageButton borrar = vista.findViewById(R.id.botonBorrar);
        borrar.setOnClickListener(
                (v)->{

                db.getReference().child("contactos/"+arrayActivityContactos.get(i).getId()).setValue(null);


                }
        );
        nombre.setText(arrayActivityContactos.get(i).getNombre());
        telefono.setText(arrayActivityContactos.get(i).getTelefono());

        return vista;
    }
    public void AñadirContacto(Contacto contacto){

        arrayActivityContactos.add(contacto);
        notifyDataSetChanged();
    }

    public void LimpiarLista(){

        arrayActivityContactos.clear();
        notifyDataSetChanged();

    }
}
