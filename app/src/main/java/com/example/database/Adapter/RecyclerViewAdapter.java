package com.example.database.Adapter;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.database.Data.Database.MyApp;
import com.example.database.Data.Model.Mahasiswa;
import com.example.database.Data.common.DataListListener;
import com.example.database.R;
import com.example.database.UI.AddRoomDataActivity;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Mahasiswa> dataList = new ArrayList<>();
    private DataListListener listener;

    public void setData(List<Mahasiswa> dataList) {
        for (int i = 0; i < dataList.size(); i++) {
            Mahasiswa data = dataList.get(i);
            int position = findPosition(data);
            if (position == -1) {
                this.dataList.add(data);
                notifyItemInserted(this.dataList.size() - 1);
            } else {
                this.dataList.remove(position);
                this.dataList.add(position, data);
                notifyItemChanged(position);
            }
        }
    }

    private int findPosition(Mahasiswa data) {
        int position = -1;

        if (!this.dataList.isEmpty()) {
            for (int i = 0; i < dataList.size(); i++) {
                if (this.dataList.get(i).getId() == data.getId()) {
                    position = i;
                }
            }
        }

        return position;
    }

    public void removeData(Mahasiswa data) {
        if (this.dataList.isEmpty()) {
            return;
        }

        for (int i = 0; i < dataList.size(); i++) {
            if (this.dataList.get(i).getId() == data.getId()) {
                this.dataList.remove(i);
                notifyItemRemoved(i);
            }
        }
    }
    public void setRemoveListener(DataListListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mahasiswa, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(dataList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private TextView tvNama, tvNim,tvAlamat,tvKejuruan,tvSks;
        private ImageView btnHapus;
        private Mahasiswa data;
        private DataListListener listener;
        private AlertDialog.Builder rvListMahasiswa;
        private ListAdapter adapter;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvNim = itemView.findViewById(R.id.tvNim);
            tvAlamat = itemView.findViewById(R.id.tvAlamat);
            tvKejuruan = itemView.findViewById(R.id.tvKejuruan);
            btnHapus = itemView.findViewById(R.id.btn_hapus);
            tvSks = itemView.findViewById(R.id.tvSKS);
            btnHapus.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        void bind(Mahasiswa data, DataListListener listener) {
            this.data = data;
            this.listener = listener;

            tvNama.setText(data.getNama());
            tvNim.setText(data.getNim());
            tvAlamat.setText(data.getAlamat());
            tvKejuruan.setText(data.getKejuruan());
            tvSks.setText(String.valueOf(data.getSks()));
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_hapus) {
                MyApp.getInstance().getDatabase().userDao().delete(data);
                listener.onRemoveClick(data);
                Toast.makeText(itemView.getContext(), "Berhasil Dihapus", Toast.LENGTH_SHORT).show();

            } else if (view.getId() == R.id.item_list) {

                Intent intent = new Intent(itemView.getContext(), AddRoomDataActivity.class);
                intent.putExtra(AddRoomDataActivity.TAG_DATA_INTENT, data.getId());
                itemView.getContext().startActivity(intent);

            }

        }
    }

}

