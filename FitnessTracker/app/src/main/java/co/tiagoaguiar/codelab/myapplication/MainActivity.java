package co.tiagoaguiar.codelab.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
	private RecyclerView rvMain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		rvMain = findViewById(R.id.main_rv);
		rvMain.setLayoutManager(new GridLayoutManager(this, 2 ));

		List<MainItem> mainItens = new ArrayList<>();
		mainItens.add(new MainItem(1, R.drawable.ic_baseline_wb_sunny_24, R.string.imc, Color.GREEN));
		mainItens.add(new MainItem(2, R.drawable.ic_baseline_visibility_24, R.string.tmb, Color.YELLOW));

		MainAdapter mainAdapter = new MainAdapter(mainItens);
		mainAdapter.setListener(id -> {
			switch (id) {
				case 1:
					startActivity(new Intent(MainActivity.this, ImcActivity.class));
					break;
				case 2:
					startActivity(new Intent(MainActivity.this, TmbActivity.class));
					break;
			}

		});
		rvMain.setAdapter(mainAdapter);
	}

	private class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{
		private List<MainItem> mainItens;
		private OnItemClickListener listener;

		public MainAdapter(List<MainItem> mainItens) {
			this.mainItens = mainItens;
		}

		public void setListener(OnItemClickListener listener) {
			this.listener = listener;
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

		private class MainViewHolder extends RecyclerView.ViewHolder {

			private final TextView textViewTitulo;
			private final LinearLayout btnImc;
			private final ImageView imgIcon;


			public MainViewHolder(@NonNull View itemView) {
				super(itemView);
				textViewTitulo = itemView.findViewById(R.id.item_txt_name);
				imgIcon = itemView.findViewById(R.id.item_img_icon);
				btnImc = (LinearLayout) itemView.findViewById(R.id.btn_imc);
			}

			public void bind(MainItem mainItem) {

				btnImc.setOnClickListener(view -> {
					listener.onClick(mainItem.getId());
				});

				textViewTitulo.setText(mainItem.getTextStringId());
				imgIcon.setImageResource(mainItem.getDrawableId());
				btnImc.setBackgroundColor(mainItem.getColor());

			}
		}

	}

}