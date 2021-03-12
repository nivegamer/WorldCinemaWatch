package com.example.worldcinema;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class SignInScreen extends AppCompatActivity {
    Button btnLog,btnReg;
    Intent intent;
    private EditText edtEmail;
    private EditText edtPassword;
    private void autocomplete(){
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_passwd);

        edtEmail.setText("");
        edtPassword.setText("");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_screen);
        btnLog = (Button) findViewById(R.id.btnSignIn);
        btnReg = (Button) findViewById(R.id.btnReg);

        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtPassword = (EditText) findViewById(R.id.edt_passwd);
        autocomplete();
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivity(v);
            }
        };
        btnLog.setOnClickListener(listener);
        btnReg.setOnClickListener(listener);


    }


    public void nextActivity(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn:
                singIn();
                intent = new Intent(SignInScreen.this, MainActivity.class);
                break;
            case R.id.btnReg:
                intent = new Intent(SignInScreen.this, SignUpScreen.class);
                break;
            default:
                break;
        }
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//root
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);//search itself
        startActivity(intent);
    }

    private boolean validateEmail() {
        if (edtEmail.getText().toString().trim().isEmpty()) {
            edtEmail.setError("Field can't be empty");
            return false;
        }else if(!edtEmail.getText().toString().trim().matches(
                "^\\w+" +
                        "\\@" +
                        "[a-z_0-9]*" +
                        "\\." +
                        "[a-z]{0,3}$")){
            return false;
        } else{
            edtEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        if (edtPassword.getText().toString().trim().isEmpty()) {
            edtPassword.setError("Field can't be empty");
            return false;
        } else {
            edtPassword.setError(null);
            return true;
        }
    }

    private void singIn(){
        if (!validatePassword() || !validateEmail()) return;
        if (edtPassword.getText().toString().equals("toor")&&edtEmail.getText().toString().equals("name@domenname.ru")) {
            Toast.makeText(SignInScreen.this, "Success log in", (int) 1).show();
            //verfy = true;
        }
    }
}
