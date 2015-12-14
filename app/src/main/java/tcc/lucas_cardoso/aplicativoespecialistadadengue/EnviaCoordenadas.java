package tcc.lucas_cardoso.aplicativoespecialistadadengue;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class EnviaCoordenadas extends AppCompatActivity {


    private JSONObject objRespostas;
    private String latitude;
    private String longitude;
    TextView txt_resultado_com_criadouro;
    TextView txt_resultado_sem_criadouro;
    TextView txt_resultado_com_mosquito;
    TextView txt_resultado_sem_mosquito;
    TextView txt_resultado_final;
    int count_respostas_com_criadouro = 0;
    int count_respostas_sem_criadouro = 0;
    int count_com_mosquito = 0;
    int count_sem_mosquito = 0;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envia_coordenadas);

        txt_resultado_com_criadouro = (TextView) findViewById(R.id.resultado_locais_com_criadouro);
        txt_resultado_sem_criadouro = (TextView) findViewById(R.id.resultado_locais_sem_criadouro);
        txt_resultado_com_mosquito= (TextView) findViewById(R.id.resultado_locais_com_mosquito);
        txt_resultado_sem_mosquito = (TextView) findViewById(R.id.resultado_locais_sem_mosquito);
        txt_resultado_final = (TextView) findViewById(R.id.resultado_final);

        //Toast.makeText(getApplicationContext(), "Latitude: " + this.getIntent().getExtras().get("latitude"), Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(), "Longitude: " + this.getIntent().getExtras().get("longitude"), Toast.LENGTH_LONG).show();

        String respostas = (String) this.getIntent().getExtras().get("respostas");
        String respostas_locais_com_criadouro;
        String respostas_locais_sem_criadouro;
        String respostas_locais_sem_dengue;
        String respostas_locais_com_dengue;


        Log.i("Respostas coordenadas", respostas);

        try {
            objRespostas = new JSONObject(respostas);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int i = 1; i <= objRespostas.length(); i++){
            try {
                String resposta = String.valueOf(objRespostas.get(String.valueOf(i)));
                Log.i("Interador: ", resposta);
                if (i <= 9 && resposta.equals("true")){
                  count_respostas_com_criadouro ++;
                } else if (i <= 9 && resposta.equals("false")){
                    count_respostas_sem_criadouro ++;
                } else if (i >= 10 && resposta.equals("true")) {
                    count_com_mosquito ++;
                } else {
                    count_sem_mosquito ++;
                }

                Log.i("Count local criadouro: ", String.valueOf(count_respostas_com_criadouro));
                Log.i("Count local sem criadouro: ", String.valueOf(count_respostas_sem_criadouro));
                Log.i("Count Com mosquito: ", String.valueOf(count_com_mosquito));
                Log.i("Count sem mosquito: ", String.valueOf(count_sem_mosquito));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        respostas_locais_com_criadouro = "Resposta com local propício: " + count_respostas_com_criadouro;
        respostas_locais_sem_criadouro = "Resposta com local não é propício: " + count_respostas_sem_criadouro;
        respostas_locais_com_dengue = "Resposta com presença do mosquito: " + count_com_mosquito;
        respostas_locais_sem_dengue = "Resposta sem presença do mosquito: " + count_sem_mosquito;
        txt_resultado_com_criadouro.setText(respostas_locais_com_criadouro);
        txt_resultado_sem_criadouro.setText(respostas_locais_sem_criadouro);
        txt_resultado_com_mosquito.setText(respostas_locais_com_dengue);
        txt_resultado_sem_mosquito.setText(respostas_locais_sem_dengue);

        if (count_com_mosquito > 0 && count_respostas_com_criadouro > 0){
            status = "dengue";
            txt_resultado_final.setText("Com base nas respostas foi constatado que o seu local é favorável a proliferação do mosquito e já possui o mosquito da dengue no local. Portanto será enviado a sua posição no mapa");
            enviarCoordenadas();
        } else if (count_com_mosquito > 0) {
            status = "dengue";
            txt_resultado_final.setText("Com base nas respostas foi constatado que o seu local já possui o mosquito da dengue. Portanto será enviado a sua posição para o mapa.");
            enviarCoordenadas();
        } else if (count_respostas_com_criadouro > 0){
            status = "local_propicio";
            txt_resultado_final.setText("Com base nas respostas foi constatado que o seu local é favorável a proliferação do mosquito. Portanto será enviado a sua posição para o mapa.");
            enviarCoordenadas();
        } else {
            txt_resultado_final.setText("Parabéns, o seu local foi constatado como limpo.");
        }
    }

    private void enviarCoordenadas(){
        try {
            mandarCoordenadas();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void mandarCoordenadas() throws MalformedURLException {
        latitude = (String) this.getIntent().getExtras().get("latitude");
        longitude = (String) this.getIntent().getExtras().get("longitude");
        if (longitude.equals("0.0") && latitude.equals("0.0")){
            latitude = "-26.067680";
            longitude = "-53.033669";
        }

        final String nova_lat = latitude.replace(".", ",");
        final String nova_lng = longitude.replace(".", ",");

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                String dataUrl = "http://sistemageorreferenciamento.invent.to/localizacao/"+nova_lat+"&"+nova_lng+"&"+status;
                final String dataUrlParameters = "/"+"-26,073205" + "&" + "-53,050074";
                URL url = null;
                HttpURLConnection urlConnection = null;

                //Toast.makeText(getApplication(), "EXECUTANDO...", Toast.LENGTH_LONG).show();
                try {
                    url = new URL(dataUrl);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setRequestProperty("Accept", "application/json");

                    /*DataOutputStream wr = new DataOutputStream(
                            urlConnection.getOutputStream());
                    wr.writeBytes(dataUrlParameters);
                    wr.flush();
                    wr.close();*/

                    InputStream is = urlConnection.getInputStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                    String line;
                    StringBuffer response = new StringBuffer();
                    while ((line = rd.readLine()) != null) {
                        response.append(line);
                        response.append('\r');
                    }
                    rd.close();
                    String responseStr = response.toString();
                    Log.d("Server response", responseStr);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
                return null;
            }
        }.execute();

    }
}
