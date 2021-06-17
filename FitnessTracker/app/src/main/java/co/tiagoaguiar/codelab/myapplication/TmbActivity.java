package co.tiagoaguiar.codelab.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class TmbActivity extends AppCompatActivity {
    private EditText edtWeight;
    private EditText edtHeight;
    private EditText edtAge;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmb);

        edtWeight = findViewById(R.id.edit_tmb_weight);
        edtHeight = findViewById(R.id.edit_tmb_height);
        edtAge = findViewById(R.id.edit_age);
        spinner = findViewById(R.id.tmb_lifestyle);
        Button btnSend = findViewById(R.id.btn_tmb_send);

        btnSend.setOnClickListener(view -> {
            if (!validate()) {
                Toast.makeText(getBaseContext(), R.string.fields_message, Toast.LENGTH_LONG).show();
                return;
            }

            int height = Integer.parseInt(edtHeight.getText().toString());
            int weight = Integer.parseInt(edtWeight.getText().toString());
            int age = Integer.parseInt(edtAge.getText().toString());
            double result = calculateTmb(height, weight, age);
            double tmb = tmbResponse(result);

            Log.d("Teste", String.format("TMB: %f, peso: %d, Altura: %d, Idade: %d", tmb, weight, height, age));
            AlertDialog dialog = new AlertDialog.Builder(TmbActivity.this)
                    .setTitle(getString(R.string.tmb_response, tmb))
                    .setMessage(String.valueOf(tmb))
                    .setPositiveButton(android.R.string.ok, (dialog1, which) -> {
                    })
                    .setNegativeButton(R.string.save, (dialog1, which) ->{
                        new Thread(() -> {
                            long calcId = SqlHelper.getInstance(TmbActivity.this).addItem("tmb", tmb);
//                             executar na trhead principal para apresentar a mensagem
                            runOnUiThread(() -> {
                                if (calcId > 0) {
                                    Toast.makeText(TmbActivity.this, R.string.calc_saved, Toast.LENGTH_SHORT).show();
                                    openListCalcActivity();
                                } else
                                    Toast.makeText(TmbActivity.this, R.string.falha_save, Toast.LENGTH_LONG).show();
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
        Intent intent = new Intent(TmbActivity.this, ListCalcActivity.class);
        intent.putExtra("type", "tmb");
        startActivity(intent);
    }

    private double calculateTmb(int height, int weight, int age) {
        return 66 + (weight * 13.8) + (5 * height) - (6.8 * age);
    }

    private double tmbResponse(double tmb)
    {
        int index = spinner.getSelectedItemPosition();
        switch (index) {
            case 0: return tmb * 1.2;
            case 1: return tmb * 1.375;
            case 2: return tmb * 1.55;
            case 3: return tmb * 1.725;
            case 4: return tmb * 1.9;
            default:
                return 0.0;
        }
    }


    private boolean validate() {
        return ! (edtWeight.getText().toString().isEmpty() ||
                edtHeight.getText().toString().isEmpty() ||
                edtAge.getText().toString().isEmpty() ||
                Integer.parseInt(edtWeight.getText().toString())==0 ||
                Integer.parseInt(edtHeight.getText().toString())==0 ||
                Integer.parseInt(edtAge.getText().toString())==0);

    }


}