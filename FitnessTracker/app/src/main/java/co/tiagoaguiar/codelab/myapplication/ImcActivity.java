package co.tiagoaguiar.codelab.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) {
                    Toast.makeText(getBaseContext(), R.string.fields_message, Toast.LENGTH_LONG).show();
                    return;
                }

                int height = Integer.parseInt(edtHeight.getText().toString());
                int weight = Integer.parseInt(edtWeight.getText().toString());
                double imc = calculateImc(height, weight);

                int imcResponseId = imcResponse(imc);

                Log.d("Teste", String.format("IMC: %f, peso: %d, Altura: %d - %d", imc, weight, height, imcResponseId));

//                Toast.makeText(ImcActivity.this, imcResponseId, Toast.LENGTH_LONG).show();
//                Toast.makeText(ImcActivity.this, "TESTEDDAFDASDF", Toast.LENGTH_LONG).show();

                AlertDialog dialog = new AlertDialog.Builder(ImcActivity.this)
                        .setTitle(getString(R.string.imc_response, imc))
                        .setMessage(imcResponseId)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create();
                dialog.show();
            }
        });

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