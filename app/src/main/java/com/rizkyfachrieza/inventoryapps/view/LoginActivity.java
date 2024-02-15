package com.rizkyfachrieza.inventoryapps.view;

import static com.rizkyfachrieza.inventoryapps.model.Preferences.getSharedPreference;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.rizkyfachrieza.inventoryapps.R;
import com.rizkyfachrieza.inventoryapps.dbhelper.SqliteHelper;
import com.rizkyfachrieza.inventoryapps.model.ModelUser;
import com.rizkyfachrieza.inventoryapps.model.Preferences;

public class LoginActivity extends AppCompatActivity {

    TextView clickDaftar;
    EditText editTextUsername;
    EditText editTextPassword;
    TextInputLayout textInputLayoutUsername;
    TextInputLayout textInputLayoutPassword;
    Button buttonLogin;
    SqliteHelper sqliteHelper;
    ProgressDialog nDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_toolbar);
        TextView textView = getSupportActionBar().getCustomView().findViewById(R.id.titletoolbar);
//        textView.setText("Inventory Apps");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().hide();
        sqliteHelper = new SqliteHelper(this);
        initViews();
        buttonLogin.setEnabled(false);
        clickDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,DaftarActivity.class);
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
                if(charSequence.toString().equals("") || pwd.equals("")){
                    buttonLogin.setEnabled(false);
                }else{
                    buttonLogin.setEnabled(true);
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
                if(charSequence.toString().equals("") || usrname.equals("")){
                    buttonLogin.setEnabled(false);
                }else{
                    buttonLogin.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nDialog = new ProgressDialog(LoginActivity.this);
                nDialog.setMessage("Loading...");
                nDialog.setTitle("Login");
                nDialog.setIndeterminate(false);
                nDialog.setCancelable(false);
                nDialog.setCanceledOnTouchOutside(false);
                nDialog.show();
                if (validate()) {

                    String Username = editTextUsername.getText().toString();
                    String Password = editTextPassword.getText().toString();

                    ModelUser currentUser = sqliteHelper.Authenticate(new ModelUser(null, Username, null, Password));

                    if (currentUser != null) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                SharedPreferences.Editor editor = getSharedPreference(getBaseContext()).edit();
                                Preferences.setUserLogin(getBaseContext(),true,currentUser.nama,currentUser.username);

                                Intent intent=new Intent(LoginActivity.this,DashboardActivity.class);
                                startActivity(intent);
                                finish();
                                nDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Berhasil Login", Toast.LENGTH_LONG).show();

                            }
                        }, 3000);
                    } else {
                        nDialog.dismiss();
                        Snackbar.make(buttonLogin, "Username atau password salah", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public boolean validate() {
        boolean valid = false;

        String Username = editTextUsername.getText().toString();
        String Password = editTextPassword.getText().toString();

        if (Username.isEmpty()) {
            valid = false;
            textInputLayoutUsername.setError("Username tidak boleh kosong!");
        } else {
            valid = true;
            textInputLayoutUsername.setError(null);
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

    private void initViews() {
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textInputLayoutUsername = (TextInputLayout) findViewById(R.id.textInputLayoutUsername);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        clickDaftar = (TextView) findViewById(R.id.textView2);
    }
}