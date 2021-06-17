package co.tiagoaguiar.codelab.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import co.tiagoaguiar.codelab.util.Geral;

public class ListCalcActivity extends AppCompatActivity {

    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_calc);

        Bundle extras = getIntent().getExtras();

        RecyclerView rvList = findViewById(R.id.recycler_view_list);
        rvList.setLayoutManager(new LinearLayoutManager(this));



        if(null != extras) {
            type = extras.getString("type");
            TextView titulo = findViewById(R.id.text_view_list_titulo_result);
            titulo.setText(type.toUpperCase());


            new Thread(() ->{
                List<Register> registers = SqlHelper.getInstance(this).getRegisterBy(type);
                Log.d("Teste", registers.toString());
                ListCalcAdapter listCalcAdapter = new ListCalcAdapter(registers);
                runOnUiThread(()->{
                    rvList.setAdapter(listCalcAdapter);
                });

            }).start();
        }

    }

    private class ListCalcAdapter extends RecyclerView.Adapter<ListCalcViewHolder> {
        private final List<Register> datas;

        public ListCalcAdapter(List<Register> datas) {
            this.datas = datas;
        }

        @NonNull
        @Override
        public ListCalcViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ListCalcViewHolder(getLayoutInflater().inflate(R.layout.list_calc_item, parent, false));
//            return new ListCalcViewHolder(getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ListCalcActivity.ListCalcViewHolder holder, int position) {
            Register data = datas.get(position);
            holder.bind(data, position);
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
    }

    private class ListCalcViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewData;
        private TextView textViewResult;

        public ListCalcViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewData = itemView.findViewById(R.id.text_view_list_data);
            textViewResult = itemView.findViewById(R.id.text_view_list_result);
        }

        @SuppressLint("ResourceAsColor")
        public void bind(Register data, int position) {

            if (position%2==0)
                ((LinearLayout) itemView).setBackgroundColor(R.color.white);
            else
                ((LinearLayout) itemView).setBackgroundColor(R.color.lowgray);

            String formated = "";
            try {
                Date dateSaved = Geral.convertStringDbToDateTime(data.getCreatedDate());
                formated = Geral.formataDataHora(dateSaved);
            } catch (ParseException e) {
                Log.i("Teste", String.format("Falha ao converter data: %s", data.getCreatedDate()));
            }
            textViewData.setText(formated);
            textViewResult.setText( String.format("%.2f", data.response));
        }
    }
}