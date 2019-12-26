package com.stair2.Volunteer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class LoginActivity extends AppCompatActivity implements AsyncUserResponse {

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
        //check to see if the login task is null, which will only happen if this isnt the first time to attempt login
        if(checkLogin == null)
        {
            //create a new one and set the delegate so it can be used correctly
            checkLogin = new CheckLoginTask();
            checkLogin.delegate = this;
        }

        //get username and password
        String username = ((EditText)findViewById(R.id.login_username)).getText().toString();
        String password = ((EditText)findViewById(R.id.login_password)).getText().toString();

        String passwordSecure = PassHash.HashPassword(password); //get the hash
        password = null; //overwrite the variable to remove plaintext password from memory

        //execute the asynchronous login check
        checkLogin.execute(username, passwordSecure);

        //disable the submit button so the user cant spam it and crash the app
        ((Button)findViewById(R.id.login_submit)).setEnabled(false);

        //Show a short toast to make sure user knows things are happening in case query takes longer
        Toast.makeText(this,"Logging in...", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void processFinish(User output)
    {

        if(output != null)
        {
            AppState.LoggedInUser = output; //set the appstate user for use other places

            ((Button)findViewById(R.id.login_submit)).setEnabled(true); //turn on the submit again
            checkLogin = null; //remove the login activity

            Intent openHome = new Intent(this, HomeActivity.class); //create the intent to open home
            startActivity(openHome); //open home activity


        }
        else
        {

            Toast.makeText(this, "User not found!", Toast.LENGTH_LONG).show(); //tell the user he screwed up

            ((EditText)findViewById(R.id.login_password)).setText(""); //clear password and set the focus to it
            ((EditText)findViewById(R.id.login_password)).requestFocus();


            ((Button)findViewById(R.id.login_submit)).setEnabled(true); //reenable the login button
            checkLogin = null;
        }




    }

    public void signup(View view)
    {
        //create the intent to open the signup activity and move to it
        Intent signupIntent = new Intent(this, SignUpActivity.class);
        startActivity(signupIntent);
    }

}
