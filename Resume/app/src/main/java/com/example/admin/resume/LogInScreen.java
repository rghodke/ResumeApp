package com.example.admin.resume;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class LogInScreen extends ActionBarActivity {

    EditText UserNameText ;
    EditText PasswordText ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "aQLIABgC5AsHxFs3hDig6ASuGBHZF6ceY4aN3omu", "D9jzIth6Ls6X2sKO7POpDjK9ppO58tTXjHWjkZNT");


        ParseObject testObject = new ParseObject("TestObject");


        testObject.put("foo", "bar");
        testObject.saveInBackground();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_screen);
        UserNameText = (EditText)findViewById(R.id.UserNameText);
        PasswordText = (EditText)findViewById(R.id.PassWordText);

    }

    public void onSignUpClick(View v)
    {
        ParseUser user = new ParseUser();

        System.out.println(UserNameText.getText().toString());
        System.out.println(PasswordText.getText().toString());
        user.setUsername(UserNameText.getText().toString());
        user.setPassword(PasswordText.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
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
