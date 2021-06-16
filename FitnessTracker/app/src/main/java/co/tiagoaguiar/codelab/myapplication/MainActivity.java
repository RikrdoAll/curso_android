package co.tiagoaguiar.codelab.myapplication;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
	private RecyclerView rvMain;

//	private View btnImc;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//	    //meio tradicional
//		btnImc = findViewById(R.id.btn_imc);
//
//		btnImc.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(MainActivity.this, ImcActivity.class);
////                Intent intent = new Intent(getBaseContext(), ImcActivity.class);
//				startActivity(intent);
//			}
//		});


		// usando RecyclerView

		rvMain = findViewById(R.id.main_rv);

		// definir o comportamento de exeibição do layout da recyclerview, pode ser mosai, grid, linear(vertical/horizontal)

		rvMain.setLayoutManager(new LinearLayoutManager(this));;

		MainAdapter mainAdapter = new MainAdapter();
		rvMain.setAdapter(mainAdapter);
	}

	private class MainAdapter extends RecyclerView.Adapter<MainViewHolder>{
		@NonNull
		@Override
		public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			return new MainViewHolder(getLayoutInflater().inflate(R.layout.main_item, parent, false));
		}

		@Override
		public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
			holder.bind(position);
		}

		@Override
		public int getItemCount() {
			return 15;
		}
	}

	private class MainViewHolder extends RecyclerView.ViewHolder {

		public MainViewHolder(@NonNull View itemView) {
			super(itemView);
		}

		public void bind(int position) {
			TextView textViewTitulo;
			textViewTitulo = itemView.findViewById(R.id.textview_titulo);
			textViewTitulo.setText("Pagina: " + String.valueOf(position));
		}
	}

}