package com.example.admin.resume;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ResumeHome extends AppCompatActivity {

    TextView WelcomeText;
    TextView ConnectionNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_home);
        Bundle bundle = getIntent().getExtras();
        String usernamedata = bundle.getString("UserName");


        ConnectionNum = (TextView)findViewById(R.id.ConnectionTV);
        ParseQuery query = new ParseQuery("Friends");

        WelcomeText = (TextView)findViewById(R.id.WelcomeText);
        WelcomeText.setText("Hi" + " " + usernamedata);
        query.findInBackground(new FindCallback<ParseObject>() {
                                   public void done(List<ParseObject> FriendList, ParseException e) {
                                       ConnectionNum.setText(Integer.toString(FriendList.get(0).getList("FriendsList").size()) );
                                   }
                               }
        );
    }

    public void gotoConfig(View v)
    {
        Intent intent = new Intent(ResumeHome.this, ConfigScreen.class);
        startActivity(intent);
    }

    public void CreateResumeButton(View v)
    {
        Intent intent = new Intent(this, CreateResume.class);
        startActivity(intent);
    }

    public void ViewPreviousResume(View v)
    {
        Intent intent = new Intent(this, PreviousResumes.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_resume_home, menu);
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
