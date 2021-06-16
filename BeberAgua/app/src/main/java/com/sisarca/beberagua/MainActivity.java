package com.sisarca.beberagua;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private Button btnNotify;
    private EditText editMinutes;
    private TimePicker timerPicker;
    private int hour;
    private int minute;
    private int interval;
    private boolean active;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNotify = findViewById(R.id.btn_notify);
        editMinutes = findViewById(R.id.edit_txt_number_integer);
        timerPicker = findViewById(R.id.timer_picker);
        timerPicker.setIs24HourView(true);
        preferences = getSharedPreferences("beberagua_db", Context.MODE_PRIVATE);

        lerInformacaoBancoDados();


//        // evento de click com objeto anonimo
//        btnNotify.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                carregarCampos();
//            }
//        } );
//
//        //evento de clicque por variavel anonoma
//        btnNotify.setOnClickListener(clickBotaoVariavelAnonima);
    }

//    // evetno de clique com variável anonima
//    public View.OnClickListener clickBotaoVariavelAnonima = new View.OnClickListener(){
//        @Override
//        public void onClick(View v) {
//            Log.d("Teste", "Executou evento click por variavel anonima");
//            carregarCampos();
//        }
//    };
//

    //logica do botao clique
    public void executarBotaoClickNotificar(){
        String sInterval = editMinutes.getText().toString();
        if (sInterval.isEmpty()) {
            Log.d("Teste", "Intervalo vazio, não pode ser convertido para inteiro");
            Toast.makeText(this, R.string.error_msg, Toast.LENGTH_LONG).show();
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour = timerPicker.getHour();
            minute = timerPicker.getMinute();
        } else {
            hour = timerPicker.getCurrentHour();
            minute = timerPicker.getCurrentMinute();
        }
        interval = Integer.parseInt(sInterval);
        Log.d("Teste", String.format("hora: %d, minutos: %d, Intervalo: %d", hour, minute, interval));

        active = !active;

        atualizarBotao(active);
        salvarConfiguracao();
    }

    private void atualizarBotao(boolean novoStatus) {
        if (novoStatus) {
            btnNotify.setText(R.string.notify);
            btnNotify.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorAccent));
//            setBackgroundColor para android studio de versão antiga, antes do 4.1
//            btnNotify.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        } else {
            btnNotify.setText(R.string.pause);
            btnNotify.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.black));
        }
    }

    private void lerInformacaoBancoDados() {
        active = preferences.getBoolean("activated", false);
        interval = preferences.getInt("interval", 0);
        hour = preferences.getInt("hour", timerPicker.getCurrentHour());
        minute = preferences.getInt("minute", timerPicker.getCurrentMinute());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timerPicker.setHour(hour);
            timerPicker.setMinute(minute);
        } else {
            timerPicker.setCurrentHour(hour);
            timerPicker.setCurrentMinute(minute);
        }
        editMinutes.setText(String.valueOf(interval));
        atualizarBotao(active);
    }


    private void salvarConfiguracao() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("activated", active);
        editor.putInt("interval", interval);
        editor.putInt("hour", hour);
        editor.putInt("minute", minute);
        editor.apply();
    }

    //evento de click via xml, button ::: android:onClick="notifiyButonClick"
    public void notifiyButonClick(View view) {
        executarBotaoClickNotificar();
    }
}