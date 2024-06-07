package Pereira.Ferreira.lista.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import Pereira.Ferreira.lista.R;
import Pereira.Ferreira.lista.model.NewItemActivityViewModel;

public class NewItemActivity extends AppCompatActivity {

    // Constante para identificar a solicitação do seletor de fotos
    static int PHOTO_PICKER_REQUEST = 1;

    // URI da foto selecionada

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        //obtem o viewModel referente a newActivity
        NewItemActivityViewModel vm = new ViewModelProvider(this).get(NewItemActivityViewModel.class);

        //Obtem o endereço URI dentro do ViewModel caso não seja nulo
        Uri selectPhotoLocation = vm.getSelectPhotoLocation();
        if (selectPhotoLocation != null) {

            //Fixa a imagem no ImagemView da tela
            ImageView imvfotoPreview = findViewById(R.id.imvPhotoPreview);
            imvfotoPreview.setImageURI(selectPhotoLocation);
        }

        // Habilita o EdgeToEdge para estender as visualizações para as bordas da tela
        EdgeToEdge.enable(this);

        // Configura o botão para abrir o seletor de fotos
        ImageButton imgCI = findViewById(R.id.imbCl);
        imgCI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PHOTO_PICKER_REQUEST);
            }
        });

        // Configura o botão para adicionar um novo item
        Button btnAddItem = findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri photoSelected = vm.getSelectedPhotoLocation();
                // Verifica se uma foto foi selecionada
                if (photoSelected == null) {
                    Toast.makeText(NewItemActivity.this, "É necessário selecionar uma imagem!", Toast.LENGTH_LONG).show();
                    return;
                }

                // Obtém o texto digitado
                EditText etTitle = findViewById(R.id.etTitle);
                String title = etTitle.getText().toString();
                if (title.isEmpty()) {
                    Toast.makeText(NewItemActivity.this, "É necessário inserir um título", Toast.LENGTH_LONG).show();
                    return;
                }

                // Obtém a descrição digitada
                EditText etDesc = findViewById(R.id.etDesc);
                String description = etDesc.getText().toString();
                if (description.isEmpty()) {
                    Toast.makeText(NewItemActivity.this, "É necessário inserir uma descrição", Toast.LENGTH_LONG).show();
                    return;
                }

                // Cria um Intent com os dados do novo item e define como resultado
                Intent i = new Intent();
                i.setData(photoSelected);
                i.putExtra("title", title);
                i.putExtra("description", description);
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Verifica se a solicitação foi bem sucedida
        if (requestCode == PHOTO_PICKER_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri photoSelected = data.getData();
            ImageView imvfotoPreview = findViewById(R.id.imvPhotoPreview);
            imvfotoPreview.setImageURI(photoSelected);

            //Obtem o viewModel
            NewItemActivityViewModel vm = new ViewModelProvider(this).get(NewItemActivityViewModel.class);
            
            //Guarda o endereço URI da imagem escolhida
            vm.setSelectedPhotoLocation(selectedPhoto);
        }
    }
}
