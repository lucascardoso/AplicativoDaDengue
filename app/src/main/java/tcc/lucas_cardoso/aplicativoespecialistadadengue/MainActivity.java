package tcc.lucas_cardoso.aplicativoespecialistadadengue;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private ImageView imagem = null;
    private TextView dica = null;
    private TextView pergunta = null;
    private int count_questao = 1;
    private RadioButton radioButtonNao;
    private RadioButton radioButtonSim;
    private LocationManager locationManager;
    private RadioGroup radioGroup;
    double latitude;
    double longitude;
    double longitudeNetwork, latitudeNetwork;
    JSONObject respostasQuestoes;
    Button proximaPergunta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioButtonSim = (RadioButton) findViewById(R.id.radio_sim);
        radioButtonNao = (RadioButton) findViewById(R.id.radio_nao);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        dica = (TextView) findViewById(R.id.texto_dica);
        pergunta = (TextView) findViewById(R.id.pergunta);
        proximaPergunta = (Button) findViewById(R.id.proxima_pergunta);
        imagem = (ImageView) findViewById(R.id.pergunta_imagem);
        respostasQuestoes = new JSONObject();

        dica.setText(R.string.dica_1);
        imagem.setImageResource(R.drawable.vaso_com_agua);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 20000, 1, this);

        radioButtonSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dica.setVisibility(View.VISIBLE);
            }
        });

        radioButtonNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dica.setVisibility(View.GONE);
            }
        });

        proximaPergunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserirResposta(String.valueOf(count_questao));
                count_questao++;
                dica.setVisibility(View.GONE);
                mudarQuestao();
            }
        });

    }

    private void inserirResposta(String indice) {
        Log.i("IS Checked Sim ", String.valueOf(radioButtonSim.isChecked()));
        Log.i("IS Checked Não ", String.valueOf(radioButtonNao.isChecked()));
        if(radioButtonSim.isChecked()){
            try {
                respostasQuestoes.put(indice, "true");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                respostasQuestoes.put(indice, "false");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.i("Respotas: ", String.valueOf(respostasQuestoes));
    }

    private void limparRadioButtons() {
        /*if (radioButtonSim.isChecked()) {
            radioButtonSim.setChecked(false);
        } else {
            radioButtonNao.setChecked(false);
        }*/
        radioGroup.clearCheck();
    }

    private void mudarQuestao() {
        if (count_questao == 2) {
            pergunta.setText(R.string.questao_2);
            dica.setText(R.string.dica_2);
            imagem.setImageResource(R.drawable.recipientes_dengue);
            limparRadioButtons();
        } else if (count_questao == 3) {
            pergunta.setText(R.string.questao_3);
            dica.setText(R.string.dica_3);
            imagem.setImageResource(R.drawable.latao_com_agua);
            limparRadioButtons();
        } else if (count_questao == 4) {
            pergunta.setText(R.string.questao_4);
            dica.setText(R.string.dica_4);
            imagem.setImageResource(R.drawable.caixa_de_agua_destampada);
            limparRadioButtons();
        } else if (count_questao == 5) {
            pergunta.setText(R.string.questao_5);
            dica.setText(R.string.dica_5);
            imagem.setImageResource(R.drawable.calha_entupida);
            limparRadioButtons();
        } else if (count_questao == 6) {
            pergunta.setText(R.string.questao_6);
            dica.setText(R.string.dica_6);
            imagem.setImageResource(R.drawable.agua_laje);
            limparRadioButtons();
        } else if (count_questao == 7) {
            pergunta.setText(R.string.questao_7);
            dica.setText(R.string.dica_7);
            imagem.setImageResource(R.drawable.pneu_com_agua);
            limparRadioButtons();
        } else if (count_questao == 8) {
            pergunta.setText(R.string.questao_8);
            dica.setText(R.string.dica_8);
            imagem.setImageResource(R.drawable.garrafa_com_agua);
            limparRadioButtons();
        } else if (count_questao == 9) {
            pergunta.setText(R.string.questao_9);
            dica.setText(R.string.dica_9);
            imagem.setImageResource(R.drawable.lixos);
            limparRadioButtons();
        } else if (count_questao == 10) {
            pergunta.setText(R.string.questao_10);
            imagem.setImageResource(R.drawable.mosquito_da_dengue);
            dica.setText(R.string.dica_10);
            limparRadioButtons();
        } else if (count_questao == 11) {
            pergunta.setText(R.string.questao_11);
            imagem.setImageResource(R.drawable.mosquito_da_dengue2);
            dica.setText(R.string.dica_11);
            limparRadioButtons();
        } else if (count_questao == 12) {
            pergunta.setText(R.string.questao_12);
            imagem.setImageResource(R.drawable.mosquito_da_dengue3);
            dica.setText(R.string.dica_12);
            limparRadioButtons();
            proximaPergunta.setText("Resultado");
        } else if (count_questao == 13) {
            Intent intent = new Intent(getApplicationContext(), EnviaCoordenadas.class);
            intent.putExtra("latitude", String.valueOf(latitude));
            intent.putExtra("longitude", String.valueOf(longitude));
            intent.putExtra("respostas", respostasQuestoes.toString());
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        verificaLocalizacao();
        /*if (!enabled) {

        } else {
            if (locationManager != null) {
                Location location = locationManager
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    Toast.makeText(getApplication(), "ENTROU", Toast.LENGTH_SHORT).show();
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    Toast.makeText(getApplication(), "latitude: " + latitude + " longitude: " + longitude, Toast.LENGTH_LONG).show();
                }
            }
        }*/
    }

    private boolean isLocationEnabled() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2 * 60 * 1000, 10, this);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private boolean verificaLocalizacao() {
        if(!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

        alertDialog.setTitle("Configurações do GPS");
        alertDialog.setMessage("GPS não está habilitado. Para indentificar a sua posição ative o GPS.");

        alertDialog.setPositiveButton("Ativar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }


    @Override
    public void onLocationChanged(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getBaseContext(), "Gps esta ligado!! ",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
       // showAlert();
    }
}
