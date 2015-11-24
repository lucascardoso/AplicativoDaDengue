package tcc.lucas_cardoso.aplicativoespecialistadadengue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioButton radioButtonSim = (RadioButton) findViewById(R.id.radio_sim);
        RadioButton radioButtonNao = (RadioButton) findViewById(R.id.radio_nao);
        final TextView dica = (TextView) findViewById(R.id.texto_dica);
        final TextView pergunta = (TextView) findViewById(R.id.pergunta);
        Button proximaPergunta = (Button) findViewById(R.id.proxima_pergunta);

        dica.setText(R.string.dica_1);

        radioButtonSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Radio button SIM selecionado", Toast.LENGTH_SHORT).show();
                dica.setVisibility(View.VISIBLE);
            }
        });

        radioButtonNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Radio button NÂO selecionado", Toast.LENGTH_SHORT).show();
                dica.setVisibility(View.GONE);
            }
        });

        proximaPergunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onC lick(View v) {
                pergunta.setText(R.string.questao_2);
            }
        });
    }

}
