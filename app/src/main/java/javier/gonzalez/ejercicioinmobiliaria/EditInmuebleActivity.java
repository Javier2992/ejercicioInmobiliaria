package javier.gonzalez.ejercicioinmobiliaria;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import javier.gonzalez.ejercicioinmobiliaria.databinding.ActivityEditInmuebleBinding;
import javier.gonzalez.ejercicioinmobiliaria.modelos.Inmueble;

public class EditInmuebleActivity extends AppCompatActivity {
    private ActivityEditInmuebleBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityEditInmuebleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Inmueble inmueble = (Inmueble) bundle.getSerializable("Inmueble") ;
        int posicion = bundle.getInt("Posicion");

        rellenarVista(inmueble);
        binding.btnEliminarEditInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nuevoIntent = new Intent();
                Bundle nuevoBundle = new Bundle();
                nuevoBundle.putInt("Posicion",posicion);
                nuevoIntent.putExtras(nuevoBundle);

                setResult(RESULT_OK,nuevoIntent);
                finish();
            }
        });

        binding.btnEditarEditInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Inmueble inmuebleNuevo = crearInmueble();
                if(inmuebleNuevo==null){
                    Toast.makeText(EditInmuebleActivity.this, "FALTAN DATOS", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intentNuevo = new Intent();
                    Bundle bundleNuevo = new Bundle();
                    bundleNuevo.putSerializable("Inmueble",inmuebleNuevo);
                    bundleNuevo.putInt("Posicion",posicion);
                    intentNuevo.putExtras(bundleNuevo);

                    setResult(RESULT_OK,intentNuevo);
                    finish();
                }
            }
        });
    }

    private void rellenarVista(Inmueble inmueble) {
        binding.txtDireccionEditInmueble.setText(inmueble.getDireccion());
        binding.txtNumeroEditInmueble.setText(String.valueOf(inmueble.getNumero()));
        binding.txtCPEditInmueble.setText(inmueble.getCp());
        binding.txtCiudadEditInmueble.setText(inmueble.getCiudad());
        binding.txtprovinciaEditInmueble.setText(inmueble.getProvincia());
        binding.rbValoracionEditInmueble.setRating(inmueble.getValoracion());

    }
    private Inmueble crearInmueble() {
        if(binding.txtDireccionEditInmueble.getText().toString().isEmpty()||binding.txtNumeroEditInmueble.getText().toString().isEmpty()||binding.txtCPEditInmueble.getText().toString().isEmpty()||binding.txtprovinciaEditInmueble.getText().toString().isEmpty()||binding.txtCiudadEditInmueble.getText().toString().isEmpty()){
            return null;
        }else{
            return new Inmueble(
                    binding.txtDireccionEditInmueble.getText().toString(),
                    Integer.parseInt(binding.txtNumeroEditInmueble.getText().toString()),
                    binding.txtCPEditInmueble.getText().toString(),
                    binding.txtCiudadEditInmueble.getText().toString(),
                    binding.txtprovinciaEditInmueble.getText().toString(),
                    binding.rbValoracionEditInmueble.getNumStars()
            );
        }
    }
}