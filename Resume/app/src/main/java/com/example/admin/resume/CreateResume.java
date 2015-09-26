package com.example.admin.resume;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class CreateResume extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_resume);

    }

    EditText intel;

    public void ResumeDone(View v)
    {

        ParseUser currentUser = ParseUser.getCurrentUser();

        ParseObject ResumeInfo = new ParseObject("Resume");

        intel = (EditText)findViewById(R.id.NameText);
        ResumeInfo.put("Name", intel.getText().toString());

        intel = (EditText)findViewById(R.id.EmailEditText);
        ResumeInfo.put("Email", intel.getText().toString());

        intel = (EditText)findViewById(R.id.AddressField);
        ResumeInfo.put("Address", intel.getText().toString());

        intel = (EditText)findViewById(R.id.MajorField);
        ResumeInfo.put("Major", intel.getText().toString());

        intel = (EditText)findViewById(R.id.EducationField);
        ResumeInfo.put("Education", intel.getText().toString());

        intel = (EditText)findViewById(R.id.LocField);
        ResumeInfo.put("Loc", intel.getText().toString());

        intel = (EditText)findViewById(R.id.WebField);
        ResumeInfo.put("WebSite", intel.getText().toString());

        intel = (EditText)findViewById(R.id.Comments);
        ResumeInfo.put("Extra", intel.getText().toString());

        ResumeInfo.setACL(new ParseACL(currentUser));

        ResumeInfo.saveInBackground();

        Intent intent = new Intent(this, ResumeHome.class);
        intent.putExtra("UserName", currentUser.getUsername());
        startActivity(intent);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_resume, menu);
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
