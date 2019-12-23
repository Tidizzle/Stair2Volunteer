package com.stair2.Volunteer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity implements AsyncBooleanResponse, AsyncUserResponse{

    CheckSignUpTask signupCheckTask = new CheckSignUpTask();
    CreateUserTask createUserTask = new CreateUserTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signupCheckTask.delegate = this;
        createUserTask.delegate = this;
    }

    public void signupClick(View view)
    {
        //get the applicable fields for use in this method
        String userName = ((EditText)findViewById(R.id.signup_userName)).getText().toString();
        String password = ((EditText)findViewById(R.id.signup_password)).getText().toString();
        String passConf = ((EditText)findViewById(R.id.signup_passwordConf)).getText().toString();

        //if the password and confirmation match move to check database
        if(password.equals(passConf))
        {
            signupCheckTask.execute(userName);
        }
        else
        {
            //notify the user if the passwords dont match, & clear the confirmation box
            Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_LONG).show();
            ((EditText)findViewById(R.id.signup_passwordConf)).setText("");
        }
    }

    //Callback for signup check
    @Override
    public void processFinish(boolean output) {

        if(output)
        {
            String firstName = ((EditText)findViewById(R.id.signup_firstName)).getText().toString();
            String lastName = ((EditText)findViewById(R.id.signup_lastName)).getText().toString();
            String userName = ((EditText)findViewById(R.id.signup_userName)).getText().toString();
            String password = ((EditText)findViewById(R.id.signup_password)).getText().toString();

            //secure the password
            password = PassHash.HashPassword(password);

            //let the user know it was good and we are making their account
            Toast.makeText(this, "Creating new user...", Toast.LENGTH_SHORT).show();

            //pass it to async thread
            createUserTask.execute(firstName, lastName, userName, password);
        }
        else
        {
            //let the user know he fucked up, let him change the username
            Toast.makeText(this, "Username already taken.", Toast.LENGTH_LONG).show();
            ((EditText)findViewById(R.id.signup_userName)).requestFocus();

        }
    }

    //Callback for create user check
    @Override
    public void processFinish(User u) {
        //TODO: add logic for storing user and moving to main activity
        Toast.makeText(this, "Welcome, " + u.firstName + "!", Toast.LENGTH_SHORT).show();
    }
}
