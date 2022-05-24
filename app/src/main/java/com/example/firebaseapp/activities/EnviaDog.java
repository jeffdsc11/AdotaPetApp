package com.example.firebaseapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.firebaseapp.MainActivity;
import com.example.firebaseapp.R;
import com.example.firebaseapp.fragments.PerfilFragment;
import com.example.firebaseapp.model.PetModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class EnviaDog extends AppCompatActivity {
    Button btnVolta;
    Button btnAddFotoPet;
    Button btnEnvia;
    private ImageView imagem;
    private Uri imageUri;
    private Uri selectedImage;
    String url;

    private static final int GALERIA_IMAGENS = 1;
    private static final int PERMISSAO_RESQUEST = 2;


    FirebaseAuth mAuth;
    private EditText edtNomePet;
    private EditText edtRacaPet;
    private EditText edtIdadepet;
    private EditText edtDescricao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envia_dog);
        String admin = getIntent().getStringExtra("admin");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        imagem = (ImageView) findViewById(R.id.imgPet);
        btnAddFotoPet = (Button)findViewById(R.id.btnAddFotoPet);
        btnAddFotoPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GALERIA_IMAGENS);
            }
        });


        mAuth = FirebaseAuth.getInstance();
        edtNomePet =findViewById(R.id.edtNomePet);
        edtRacaPet =findViewById(R.id.edtRacaPet);
        edtIdadepet =findViewById(R.id.edtIdadePet);
        edtDescricao =findViewById(R.id.edtDescricaoPet);
        btnEnvia = findViewById(R.id.btnCadastraPet);
        btnEnvia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PetModel petModel = new PetModel();
                petModel.setNome(edtNomePet.getText().toString());
                petModel.setRaca(edtRacaPet.getText().toString());
                petModel.setIdade(edtIdadepet.getText().toString());
                petModel.setDescricao(edtDescricao.getText().toString());
                petModel.setImageUrlDog(url);
                if(!TextUtils.isEmpty(petModel.getNome())&&!TextUtils.isEmpty(petModel.getRaca())&&!TextUtils.isEmpty(petModel.getIdade())&&!TextUtils.isEmpty(petModel.getDescricao())){
                    petModel.setIdDoador(mAuth.getUid());
                    //Verifica se a inserção do pet é do ADMIN ou do Usuario
                    if(admin.equals("sim")){
                        petModel.InserirPetAdmin();
                    }else petModel.InserirPet();
                    AlertDialog.Builder builder = new AlertDialog.Builder(EnviaDog.this);
                    //define o titulo
                    builder.setTitle("Tudo certo!");
                    //define a mensagem
                    builder.setMessage("Cadastro do Pet Efetuado!");
                    //Exibe
                    builder.show();
                    abrirTelaPrincipal();
                }else{

                    AlertDialog.Builder builder = new AlertDialog.Builder(EnviaDog.this);
                    //define o titulo
                    builder.setTitle("Erro!");
                    //define a mensagem
                    builder.setMessage("Todos os campos devem estar preenchidos!");
                    //Exibe
                    builder.show();
                    abrirTelaPrincipal();
                }
            }
        });


        btnVolta = findViewById(R.id.btnVoltarPet);
        btnVolta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == GALERIA_IMAGENS){
            selectedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            Bitmap imagemGaleria = (BitmapFactory.decodeFile(picturePath));
            imagem.setImageBitmap(imagemGaleria);
            uploadImage();

        }
    }

    private String getFileExtension (Uri uri){
        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();

        if (selectedImage != null){

            StorageReference fileRef = FirebaseStorage.getInstance().getReference().child("uploads").child(System.currentTimeMillis() + "." + getFileExtension(selectedImage));
            fileRef.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            url = uri.toString();
                            Log.d("DonwloadUrl", url);
                            pd.dismiss();
                            Toast.makeText(EnviaDog.this, "Image upload sucessfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSAO_RESQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {

            }
            return;
        }
    }


    private void abrirTelaPrincipal() {
        Intent intent = new Intent(EnviaDog.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}