package com.example.admin.resume;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;


public class LogInScreen extends ActionBarActivity {

    EditText UserNameText ;
    EditText PasswordText ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "aQLIABgC5AsHxFs3hDig6ASuGBHZF6ceY4aN3omu", "D9jzIth6Ls6X2sKO7POpDjK9ppO58tTXjHWjkZNT");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_screen);
        UserNameText = (EditText)findViewById(R.id.UserNameText);
        PasswordText = (EditText)findViewById(R.id.PassWordText);

        ParseUser remove = ParseUser.getCurrentUser();
        if(remove != null)
        {
         ParseUser.logOut();
        }

    }

    public void onSignUpClick(View v)
    {

        ParseUser remove = ParseUser.getCurrentUser();
        if(remove != null)
        {
            ParseUser.logOut();
        }

        final ParseUser user = new ParseUser();


        user.setUsername(UserNameText.getText().toString());
        user.setPassword(PasswordText.getText().toString());



        final Context context = this;
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(LogInScreen.this, "Account Creation Successful", Toast.LENGTH_SHORT).show();


                    ParseObject friendlist = new ParseObject("Friends");
                    ArrayList temp = new ArrayList();
                    temp.add(UserNameText.getText().toString());
                    friendlist.put("FriendsList", temp);
                    friendlist.setACL(new ParseACL(user));
                    friendlist.saveInBackground();

                    Intent intent = new Intent(context, ResumeHome.class);
                    intent.putExtra("UserName", UserNameText.getText().toString());
                    startActivity(intent);

                } else {
                    System.out.println(e);
                    Toast.makeText(LogInScreen.this, "Invalid Username/Password", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    public void onSignInClick(View v)
    {
        final Context context = this;
        ParseUser.logInInBackground(UserNameText.getText().toString(), PasswordText.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if(parseUser != null)
                {
                    Toast.makeText(LogInScreen.this, "Logging In...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, ResumeHome.class);
                    intent.putExtra("UserName", UserNameText.getText().toString());
                    startActivity(intent);


                }
                else
                {
                    Toast.makeText(LogInScreen.this, "Wrong username/password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_in_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
