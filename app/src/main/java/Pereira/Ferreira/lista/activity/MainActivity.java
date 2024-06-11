package Pereira.Ferreira.lista.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import Pereira.Ferreira.lista.R;
import Pereira.Ferreira.lista.adapter.MyAdapter;
import Pereira.Ferreira.lista.model.MainActivityViewModel;
import Pereira.Ferreira.lista.model.MyItem;
import Pereira.Ferreira.lista.util.Util;

public class MainActivity extends AppCompatActivity {

    // Constante para identificar a solicitação de um novo item
    static int NEW_ITEM_REQUEST = 1;

    // Adaptador para a RecyclerView
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Habilita o EdgeToEdge para estender as visualizações para as bordas da tela
        EdgeToEdge.enable(this);

        // Define o layout da atividade
        setContentView(R.layout.activity_main);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtém a referência para o RecyclerView que mostra os itens
        RecyclerView rvItens = findViewById(R.id.rvItens);

        // O View Model referente a MainActivity é obtido
        MainActivityViewModel vm = new ViewModelProvider( this  ).get(MainActivityViewModel.class);
        //A lista de itens obtida é passada pro viewModel e passada pro adapter
        List<MyItem> itens = vm.getItens();

        // configura o adaptador para a RecyclerView
        myAdapter = new MyAdapter(this, itens);
        rvItens.setAdapter(myAdapter);

        // Configura a RecyclerView para ter tamanho fixo
        rvItens.setHasFixedSize(true);

        // Define o gerenciador de layout para a RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvItens.setLayoutManager(layoutManager);

        // Adiciona uma decoração de divisor à RecyclerView, pode ser alterado para deixar em duas colunas
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvItens.getContext(), DividerItemDecoration.VERTICAL);
        rvItens.addItemDecoration(dividerItemDecoration);

        // Inicializa o botão de adicionar novo item e define um ouvinte de clique
        FloatingActionButton fabAddItem = findViewById(R.id.fabAddNewItem);
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicia a atividade NewItemActivity para adicionar um novo item
                Intent i = new Intent(MainActivity.this, NewItemActivity.class);
                startActivityForResult(i, NEW_ITEM_REQUEST);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_ITEM_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                // Se o resultado for OK, cria um novo item com os dados fornecidos
                MyItem myItem = new MyItem();
                myItem.title = data.getStringExtra("title");
                myItem.description = data.getStringExtra("description");
                // Essa função carrega a imagem e a guarda dentro de um Bitmap
                Uri selectedPhotoURI = data.getData();

                try {
                    Bitmap photo = Util.getBitmap(MainActivity.this, selectedPhotoURI, 100, 100);

                    myItem.photo = photo;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                // O View Model referente a MainActivity é obtido
                MainActivityViewModel vm = new ViewModelProvider( this  ).get(MainActivityViewModel.class);
                //A lista de itens obtida é passada pro viewModel e passada pro adapter
                List<MyItem> itens = vm.getItens();

                // Adiciona o novo item à lista e notifica o adaptador sobre a inserção
                itens.add(myItem);
                myAdapter.notifyItemInserted(itens.size() - 1);
            }
        }
    }
}
