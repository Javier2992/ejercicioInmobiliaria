package javier.gonzalez.ejercicioinmobiliaria;

import android.content.Intent;
import android.os.Bundle;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;


import java.util.ArrayList;

import javier.gonzalez.ejercicioinmobiliaria.databinding.ActivityMainBinding;
import javier.gonzalez.ejercicioinmobiliaria.modelos.Inmueble;


public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> launcherAddInmueble;
    private ArrayList<Inmueble> listaInmuebles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listaInmuebles= new ArrayList<>();
        incializarLaunchers();




        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcherAddInmueble.launch(new Intent(MainActivity.this,AddInmuebleActivity.class));
            }
        });
    }

    private void incializarLaunchers() {
        launcherAddInmueble = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        //Resultado de volver a la actividad inmueble
                        if(o.getResultCode()==RESULT_OK){
                            if(o.getData()!=null && o.getData().getExtras()!=null){
                                Inmueble inmueble =(Inmueble) o.getData().getExtras().getSerializable("Inmueble");
                                listaInmuebles.add(inmueble);
                                Toast.makeText(MainActivity.this, inmueble.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "Accion cancelada", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }


}