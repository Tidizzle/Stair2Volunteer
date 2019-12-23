package com.stair2.Volunteer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class LoginActivity extends AppCompatActivity implements AsyncBooleanResponse {

    //Declare login task
    CheckLoginTask checkLogin = new CheckLoginTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //set the login task's delegate to this class, to allow callback method
        checkLogin.delegate = this;
    }

    public void submitLogin(View view)
    {
        //get username and password
        String username = ((EditText)findViewById(R.id.login_username)).getText().toString();
        String password = ((EditText)findViewById(R.id.login_password)).getText().toString();

        String passwordSecure = PassHash.HashPassword(password); //get the hash
        password = null; //overwrite the variable to remove plaintext password from memory

        //execute the asynchronous login check
        checkLogin.execute(username, passwordSecure);

        //Show a short toast to make sure user knows things are happening in case query takes longer
        Toast.makeText(this,"Logging in...", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void processFinish(boolean output)
    {
        //TODO: create login logic



        new AlertDialog.Builder(this).setTitle("dbResult").setMessage(Boolean.toString(output)).setNegativeButton("exit", null).show();
    }

    public void signup(View view)
    {
        //create the intent to open the signup activity and move to it

        Intent signupIntent = new Intent(this, SignUpActivity.class);
        startActivity(signupIntent);
    }

}
