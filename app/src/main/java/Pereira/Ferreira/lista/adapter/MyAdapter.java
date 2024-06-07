package Pereira.Ferreira.lista.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Pereira.Ferreira.lista.R;
import Pereira.Ferreira.lista.activity.MainActivity;
import Pereira.Ferreira.lista.model.MyItem;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    // Referência à atividade MainActivity e à lista de itens
    MainActivity mainActivity;
    List<MyItem> itens;


    public MyAdapter(MainActivity mainActivity, List<MyItem> itens) {
        this.mainActivity = mainActivity;
        this.itens = itens;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla o layout do item da lista
        LayoutInflater inflater = LayoutInflater.from(mainActivity);
        View v = inflater.inflate(R.layout.item_list, parent, false);
        // Retorna um novo objeto ViewHolder
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Obtém o item na posição especificada
        MyItem myItem = itens.get(position);
        // Obtém a visualização associada ao ViewHolder
        View v = holder.itemView;
        // Atualiza as visualizações com os dados do item


        TextView tvTitle = v.findViewById(R.id.tvTitle);
        tvTitle.setText(myItem.title);

        TextView tvdesc = v.findViewById(R.id.tvDesc);
        tvdesc.setText(myItem.description);
    }

    // Método para obter o número total de itens na lista
    @Override
    public int getItemCount() {
        return itens.size();
    }

    // Classe interna para representar os itens de lista
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // Construtor
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
