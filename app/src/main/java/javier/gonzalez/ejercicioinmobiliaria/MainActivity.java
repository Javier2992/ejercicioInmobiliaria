package javier.gonzalez.ejercicioinmobiliaria;

import android.content.Intent;
import android.os.Bundle;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.util.ArrayList;

import javier.gonzalez.ejercicioinmobiliaria.databinding.ActivityMainBinding;
import javier.gonzalez.ejercicioinmobiliaria.modelos.Inmueble;


public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> launcherAddInmueble;
    private ArrayList<Inmueble> listaInmuebles;

    private ActivityResultLauncher<Intent> launcherEditInmueble;
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
                                mostarInmuebles();
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "Accion cancelada", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        launcherEditInmueble = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        //AQUI VOLVERE DESPUES DE LA ACTIVIDAD EDITAR INMUEBLE
                        if(o.getResultCode()==RESULT_OK){
                            if(o.getData() !=null && o.getData().getExtras() !=null){
                                Inmueble inmueble = (Inmueble) o.getData().getExtras().getSerializable("Inmueble");
                                int posicion = o.getData().getExtras().getInt("Posicion");
                                if (inmueble == null) {
                                    //ELIMINAR INMUEBLE DESDE POSICION
                                    listaInmuebles.remove(posicion);
                                }else{
                                    //EDitar inmueble desde posicion
                                    listaInmuebles.set(posicion,inmueble);
                                }
                                mostarInmuebles();
                            }else{
                                Toast.makeText(MainActivity.this, "ACCION CANCELADA", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "VOOLVER ATRAS", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void mostarInmuebles() {
        binding.contentMain.contenedor.removeAllViews();

        for (int i = 0; i < listaInmuebles.size(); i++) {
            Inmueble inmueble = listaInmuebles.get(i);
            View inmuebleView = LayoutInflater.from(MainActivity.this).inflate(R.layout.inmueble_model_view,null);
            TextView lbDireccion = inmuebleView.findViewById(R.id.txtDireccionInmuebleView);
            TextView lbNumero = inmuebleView.findViewById(R.id.txtNumeroInmuebleView);
            TextView lbCiudad = inmuebleView.findViewById(R.id.txtCiudadInmuebleView);
            RatingBar rbValoracion = inmuebleView.findViewById(R.id.rbValoracionInmuebleView);

            lbDireccion.setText(inmueble.getDireccion());
            lbNumero.setText(String.valueOf(inmueble.getNumero()));
            lbCiudad.setText(inmueble.getCiudad());
            rbValoracion.setRating(inmueble.getValoracion());

            int posicion = i;
            inmuebleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,EditInmuebleActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("INMUEBLE",inmueble);
                    bundle.putInt("POSICION", posicion);
                    intent.putExtras(bundle);

                    launcherEditInmueble.launch(intent);

                }
            });

            binding.contentMain.contenedor.addView(inmuebleView);

        }
    }


}
