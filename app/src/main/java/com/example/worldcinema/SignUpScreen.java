package com.example.worldcinema;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpScreen extends AppCompatActivity{

    Button btnToSignIn,btnSignUp;
    Intent intent;
    private EditText edtEmail,
            edtUserName,edtUserSurename,edtPassword,edtRepPassword;

    private void autocomplete(){

        edtUserName = findViewById(R.id.edt_name);
        edtUserSurename = findViewById(R.id.edt_surname);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_passwd);
        edtRepPassword = findViewById(R.id.edt_repet_passwd);

        edtUserName.setText("");
        edtUserSurename.setText("");
        edtEmail.setText(edtUserName.getText().toString()+"");
        edtPassword.setText("");
        edtRepPassword.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnToSignIn = (Button) findViewById(R.id.btnToSignIn);

        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtUserName = (EditText) findViewById(R.id.edt_name);
        edtUserSurename = (EditText) findViewById(R.id.edt_surname);
        edtPassword = (EditText) findViewById(R.id.edt_passwd);
        edtRepPassword = (EditText) findViewById(R.id.edt_repet_passwd);
        autocomplete();

    }

    public void nextActivity(View v) {
        switch (v.getId()) {
            case R.id.btnToSignIn:
                intent = new Intent(SignUpScreen.this, SignInScreen.class);
                break;
            case R.id.btnSignUp:
                if(validateFields(v)) intent = new Intent(SignUpScreen.this, SignInScreen.class);
                break;
            default:
                break;
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private boolean validateEmail() {
        String emailInput = edtEmail.getText().toString().trim();
        if (emailInput.isEmpty()) {
            edtEmail.setError("Field can't be empty");
            return false;
        } else if (!edtEmail.getText().toString().trim().matches(
                "^\\w+" +
                        "\\@" +
                        "[a-z_0-9]*" +
                        "\\." +
                        "[a-z]{0,3}$")) {
            edtEmail.setError("Please enter a valid email address");
            return false;
        } else {
            edtEmail.setError(null);
            return true;
        }
    }
    private boolean validateUser() {
        String usernameInput = edtUserName.getText().toString().trim();
        String userSurenameInput = edtUserSurename.getText().toString().trim();
        if (usernameInput.isEmpty()) {
            edtUserName.setError("Field can't be empty");
            Toast.makeText(SignUpScreen.this, "Field can't be empty", (int) 0).show();
            return false;
        } else if (userSurenameInput.isEmpty()) {
            edtUserSurename.setError("Field can't be empty");
            return false;
        }else {
            edtUserName.setError(null);
            edtUserSurename.setError(null);
            return true;
        }
    }
    private boolean validatePassword() {
        String passwordInput = edtPassword.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            edtPassword.setError("Field can't be empty");
            return false;
        }else if (passwordInput.length() < 8) {
            edtPassword.setError("Password is short");
            return false;
        /*}else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            edtPassword.setError("Password too weak");
            return false;*/
        } else {
            edtPassword.setError(null);
            return true;
        }
    }
    private boolean validateConPassword(){
        String passwordInput = edtPassword.getText().toString().trim();
        String ConPasswordInput = edtRepPassword.getText().toString().trim();
        if(!passwordInput.equals(ConPasswordInput)){
            edtRepPassword.setError("Password is not match");
            return false;
        }
        else{
            edtRepPassword.setError(null);
            return true;
        }
    }

    public boolean validateFields(View v) {
        if (!validateEmail() | !validateUser() | !validatePassword() | !validateConPassword()) {
            return false;
        }
        else {
            createUser();
            //Toast.makeText(SignUpScreen.this, "Success registration", (int) 1).show();
        }
        return true;
    }
    public  void createUser(){
        User user = new User(edtEmail.getText().toString().trim(),
                edtPassword.getText().toString().trim(),
                edtUserName.getText().toString().trim(),
                edtUserSurename.getText().toString().trim());
        Gson gson = new Gson();
        String JsonPesponse = gson.toJson(user);
        Log.d("RegResponse",JsonPesponse);
        Network.getInstance().getRest().createUser(user).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("RetrofitResponse ","\n"+call.isExecuted()+"\n"+call.request().url().url().toString());
                if(response.code()==200||response.code()==201)
                    Toast.makeText(SignUpScreen.this, "Успешная регистрация", (int) 1).show();
                else if(response.code()==400)
                    Toast.makeText(SignUpScreen.this, "Проблемы при регистрации", (int) 1).show();
                else if(response.code()==404)
                    Toast.makeText(SignUpScreen.this, "Проверьте соидинение с интернетом\n"+response.code(), (int) 1).show();
                else
                    Toast.makeText(SignUpScreen.this, "Что-то пошло не так\n"+response.code(), (int) 1).show();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(SignUpScreen.this, "An error occurred during networking", (int) 1).show();
                t.printStackTrace();
                t.getMessage();
            }
        });
    }
}