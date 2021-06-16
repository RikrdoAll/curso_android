package co.tiagoaguiar.codelab.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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


		//  Lenear vertical
//		rvMain.setLayoutManager(new LinearLayoutManager(this));

		//
		rvMain.setLayoutManager(new LinearLayoutManager(this));;

		List<MainItem> mainItens = new ArrayList<>();
		mainItens.add(new MainItem(1, R.drawable.ic_baseline_wb_sunny_24, R.string.imc, Color.GREEN));
		mainItens.add(new MainItem(2, R.drawable.ic_baseline_visibility_24, R.string.tmb, Color.YELLOW));
		mainItens.add(new MainItem(4, R.drawable.bg_button_accent, R.string.app_name, Color.RED));


		MainAdapter mainAdapter = new MainAdapter(mainItens);
		rvMain.setAdapter(mainAdapter);
	}

	private class MainAdapter extends RecyclerView.Adapter<MainViewHolder>{
		private List<MainItem> mainItens;

		public MainAdapter(List<MainItem> mainItens) {
			this.mainItens = mainItens;
		}

		@NonNull
		@Override
		public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			return new MainViewHolder(getLayoutInflater().inflate(R.layout.main_item, parent, false));
		}

		@Override
		public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
			holder.bind(mainItens.get(position));
		}

		@Override
		public int getItemCount() {
			return mainItens.size();
		}
	}

	private class MainViewHolder extends RecyclerView.ViewHolder {

		private final TextView textViewTitulo;
		private final LinearLayout containter;
		private final ImageView imgIcon;


		public MainViewHolder(@NonNull View itemView) {
			super(itemView);
			textViewTitulo = itemView.findViewById(R.id.item_txt_name);
			imgIcon = itemView.findViewById(R.id.item_img_icon);
			containter = (LinearLayout) itemView;
		}

		public void bind(MainItem mainItem) {
			textViewTitulo.setText(mainItem.getTextStringId());
			imgIcon.setImageResource(mainItem.getDrawableId());
			containter.setBackgroundColor(mainItem.getColor());
		}
	}

}