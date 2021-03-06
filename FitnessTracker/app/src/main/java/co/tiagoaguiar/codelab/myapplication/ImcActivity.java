package co.tiagoaguiar.codelab.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ImcActivity extends AppCompatActivity {

    private EditText edtWeight;
    private EditText edtHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc);

        edtWeight = findViewById(R.id.edit_imc_weight);
        edtHeight = findViewById(R.id.edit_imc_height);
        Button btnSend = findViewById(R.id.btn_imc_send);


        btnSend.setOnClickListener(view -> {
            if (!validate()) {
                Toast.makeText(getBaseContext(), R.string.fields_message, Toast.LENGTH_LONG).show();
                return;
            }

            int height = Integer.parseInt(edtHeight.getText().toString());
            int weight = Integer.parseInt(edtWeight.getText().toString());
            double imc = calculateImc(height, weight);

            int imcResponseId = imcResponse(imc);

            Log.d("Teste", String.format("IMC: %f, peso: %d, Altura: %d - %d", imc, weight, height, imcResponseId));
            AlertDialog dialog = new AlertDialog.Builder(ImcActivity.this)
                    .setTitle(getString(R.string.imc_response, imc))
                    .setMessage(imcResponseId)
                    .setPositiveButton(android.R.string.ok, (dialog1, which) -> {
                    })
                    .setNegativeButton(R.string.save, (dialog1, which) ->{
                        new Thread(() -> {
                            long calcId = SqlHelper.getInstance(ImcActivity.this).addItem("imc", imc);
//                             executar na trhead principal para apresentar a mensagem
                            runOnUiThread(() -> {
                                if (calcId > 0) {
                                    Toast.makeText(ImcActivity.this, R.string.calc_saved, Toast.LENGTH_SHORT).show();
                                    openListCalcActivity();

                                } else
                                    Toast.makeText(ImcActivity.this, R.string.falha_save, Toast.LENGTH_LONG).show();
                            });

                        }).start();
                    })
                    .create();
            dialog.show();

            Log.d("Teste", "localizando objeto");
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            Log.d("Teste", "objeto localizado");

            imm.hideSoftInputFromWindow(edtHeight.getWindowToken(), 0);
            imm.hideSoftInputFromWindow(edtWeight.getWindowToken(), 0);
            Log.d("Teste", "mensagem exibida");
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_list:
                openListCalcActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openListCalcActivity() {
        Intent intent = new Intent(ImcActivity.this, ListCalcActivity.class);
        intent.putExtra("type", "tmb");
        startActivity(intent);
    }

    @StringRes
    private int imcResponse(double imc)
    {
        if (imc < 15)
            return R.string.imc_severely_low_weight;
        if (imc < 16)
            return R.string.imc_very_low_weight;
        if (imc < 18.5)
            return R.string.imc_low_weight;
        if (imc < 25)
            return R.string.imc_normal;
        if (imc < 30)
            return R.string.imc_high_weight;
        if (imc < 35)
            return R.string.imc_so_high_weight;
        if (imc < 40)
            return R.string.imc_severely_high_weight;

        return R.string.imc_extreme_weight;
    }


    private double calculateImc(int height, int weight) {
        // imc =  peso / altura * altura;
        double altura = (double) height / 100;
        return weight / (altura * altura);
    }

    private boolean validate() {
        return ! (edtWeight.getText().toString().isEmpty() ||
                edtHeight.getText().toString().isEmpty() ||
                Integer.parseInt(edtWeight.getText().toString())==0 ||
                Integer.parseInt(edtHeight.getText().toString())==0 );

    }


}