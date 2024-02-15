package com.rizkyfachrieza.inventoryapps.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.rizkyfachrieza.inventoryapps.R;
import com.rizkyfachrieza.inventoryapps.dbhelper.SqliteHelper;
import com.rizkyfachrieza.inventoryapps.model.ModelUser;

public class DaftarActivity extends AppCompatActivity {

    TextView clickLogin;
    EditText editTextUsername;
    EditText editTextNama;
    EditText editTextPassword;
    TextInputLayout textInputLayoutUsername;
    TextInputLayout textInputLayoutNama;
    TextInputLayout textInputLayoutPassword;
    Button buttonRegister;
    SqliteHelper sqliteHelper;
    ProgressDialog nDialog;
    MaterialToolbar title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_toolbar);
        TextView textView = getSupportActionBar().getCustomView().findViewById(R.id.titletoolbar);
        textView.setText("Inventory Apps");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().hide();
        sqliteHelper = new SqliteHelper(this);
        initViews();
        buttonRegister.setEnabled(false);
        clickLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DaftarActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        editTextUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String pwd = editTextPassword.getText().toString();
                String nama = editTextNama.getText().toString();

                if(charSequence.toString().equals("") || pwd.equals("") || nama.equals("")){
                    buttonRegister.setEnabled(false);
                }else{
                    buttonRegister.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String usrname = editTextUsername.getText().toString();
                String nama = editTextNama.getText().toString();

                if(charSequence.toString().equals("") || usrname.equals("") || nama.equals("")){
                    buttonRegister.setEnabled(false);
                }else{
                    buttonRegister.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextNama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String usrname = editTextUsername.getText().toString();
                String pwd = editTextPassword.getText().toString();

                if(charSequence.toString().equals("") || usrname.equals("") || pwd.equals("")){
                    buttonRegister.setEnabled(false);
                }else{
                    buttonRegister.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nDialog = new ProgressDialog(DaftarActivity.this);
                nDialog.setMessage("Loading...");
                nDialog.setTitle("Daftar");
                nDialog.setIndeterminate(false);
                nDialog.setCancelable(false);
                nDialog.setCanceledOnTouchOutside(false);
                nDialog.show();
                if (validate()) {
                    String UserName = editTextUsername.getText().toString();
                    String Nama = editTextNama.getText().toString();
                    String Password = editTextPassword.getText().toString();

                    if (!sqliteHelper.isUsernameExist(UserName)) {
                        sqliteHelper.addUser(new ModelUser(null, UserName, Nama, Password));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent=new Intent(DaftarActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finish();
                                nDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Pendaftaran Berhasil! Silakan Login", Toast.LENGTH_LONG).show();
                            }
                        }, 3000);
                    }else {
                        nDialog.dismiss();
                        Snackbar.make(buttonRegister, "username telah digunakan", Snackbar.LENGTH_LONG).show();
                    }


                }
            }
        });
    }

    private void initViews() {
        editTextNama = (EditText) findViewById(R.id.editTextNama);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        textInputLayoutNama = (TextInputLayout) findViewById(R.id.textInputLayoutNama);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        textInputLayoutUsername = (TextInputLayout) findViewById(R.id.textInputLayoutUsername);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        clickLogin = (TextView) findViewById(R.id.textView2);
    }

    public boolean validate() {
        boolean valid = false;

        String UserName = editTextUsername.getText().toString();
        String Nama = editTextNama.getText().toString();
        String Password = editTextPassword.getText().toString();

        if (UserName.isEmpty()) {
            valid = false;
            textInputLayoutUsername.setError("Username tidak boleh kosong!");
        } else {
            valid = true;
            textInputLayoutUsername.setError(null);
        }

        if (Nama.isEmpty()) {
            valid = false;
            textInputLayoutNama.setError("Nama tidak boleh kosong!");
        } else {
            valid = true;
            textInputLayoutNama.setError(null);
        }

        if (Password.isEmpty()) {
            valid = false;
            textInputLayoutPassword.setError("Password tidak boleh kosong!");
        } else {
            valid = true;
            textInputLayoutPassword.setError(null);
        }

        return valid;
    }
}