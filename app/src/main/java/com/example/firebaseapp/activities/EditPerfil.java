package com.example.firebaseapp.activities;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
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
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebaseapp.MainActivity;
import com.example.firebaseapp.R;
import com.example.firebaseapp.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class EditPerfil extends AppCompatActivity {

    private Button btnAlterar;
    private ImageView imagem;
    private Uri imageUri;
    private Uri selectedImage;
    String url;

    private static final int GALERIA_IMAGENS = 1;
    private static final int PERMISSAO_RESQUEST = 2;
    private FirebaseAuth mAuth;
    private EditText edtNome;
    private EditText edtTelefone;
    private EditText edtEmail;
    private EditText edtCpf;
    private DatabaseReference mDatabase;
    Button btnsalvar;
    Button btnvoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_perfil);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSAO_RESQUEST);
            }
        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        imagem = (ImageView) findViewById(R.id.imageView6);
        btnAlterar = (Button)findViewById(R.id.btnAlterar);
        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GALERIA_IMAGENS);
            }
        });

         edtNome = findViewById(R.id.edtNome);
         edtTelefone = findViewById(R.id.edtTelefone);
         edtEmail = findViewById(R.id.edtEmail_Cadastro);
         edtCpf = findViewById(R.id.edtCpf);

        btnsalvar = findViewById(R.id.btnsalvar);

        btnvoltar = findViewById(R.id.btnVoltar);


        btnsalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserModel userModel = new UserModel();

                userModel.setNome( edtNome.getText().toString());
                userModel.setCpf(edtCpf.getText().toString());
                userModel.setTelefone(edtTelefone.getText().toString());
                userModel.setEmail(edtEmail.getText().toString());
                userModel.setProfileUrl(url);
                if(!TextUtils.isEmpty(userModel.getNome())){
                    mDatabase.child("usuarios").child(user.getUid()).child("nome").setValue(userModel.getNome());
                }
                if(!TextUtils.isEmpty(userModel.getCpf())){
                    mDatabase.child("usuarios").child(user.getUid()).child("cpf").setValue(userModel.getCpf());
                }
                if(!TextUtils.isEmpty(userModel.getEmail())){
                    mDatabase.child("usuarios").child(user.getUid()).child("email").setValue(userModel.getEmail());
                }
                if(!TextUtils.isEmpty(userModel.getProfileUrl())){
                    mDatabase.child("usuarios").child(user.getUid()).child("profileUrl").setValue(userModel.getProfileUrl());
                }
                if(!TextUtils.isEmpty(userModel.getTelefone())){
                    mDatabase.child("usuarios").child(user.getUid()).child("telefone").setValue(userModel.getTelefone());
                }

                    userModel.setId(user.getUid());
                    //userModel.salvar();
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditPerfil.this);
                    //define o titulo

                    //define a mensagem
                    builder.setMessage("Cadastro Efetuado!");
                    //Exibe
                    builder.show();
                    abrirTelaPrincipal();

            }
        });

    }


    private void abrirTelaPrincipal() {
        Intent intent = new Intent(EditPerfil.this, MainActivity.class);
        startActivity(intent);
        finish();
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
                            Toast.makeText(EditPerfil.this, "Image upload sucessfully", Toast.LENGTH_SHORT).show();
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
}

