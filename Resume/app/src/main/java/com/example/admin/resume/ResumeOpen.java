package com.example.admin.resume;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Date;
import java.util.List;

public class ResumeOpen extends AppCompatActivity {

    EditText NameText;
    EditText EmailText;
    EditText AddressText;
    EditText MajorText;
    EditText EducationText;
    EditText LocationText;
    EditText WebsiteText;
    EditText CommentsText;

    ParseObject result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_open);
        Bundle bundle = getIntent().getExtras();


        NameText = (EditText)findViewById(R.id.NameTextOpen);
        EmailText = (EditText)findViewById(R.id.EmailTextOpen);
        AddressText = (EditText)findViewById(R.id.AddressTextOpen);
        MajorText = (EditText)findViewById(R.id.MajorTextOpen);
        EducationText = (EditText)findViewById(R.id.EducationTextOpen);
        LocationText = (EditText)findViewById(R.id.LocationTextOpen);
        WebsiteText = (EditText)findViewById(R.id.WebsiteTextOpen);
        CommentsText = (EditText)findViewById(R.id.CommentsTextOpen);

        ParseQuery<ParseObject> parseQuery = new ParseQuery("Resume");

        Date ResumeDateTemp = new Date();

        ResumeDateTemp.setTime(bundle.getLong("ResumeData"));

        parseQuery.whereEqualTo("createdAt", ResumeDateTemp);


        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> ResumeFound, ParseException e) {
                if (e == null) {
                    result = ResumeFound.get(0);
                    NameText.setText(ResumeFound.get(0).get("Name").toString());
                    EmailText.setText(ResumeFound.get(0).get("Email").toString());
                    AddressText.setText(ResumeFound.get(0).get("Address").toString());
                    MajorText.setText(ResumeFound.get(0).get("Major").toString());
                    EducationText.setText(ResumeFound.get(0).get("Education").toString());
                    LocationText.setText(ResumeFound.get(0).get("Loc").toString());
                    WebsiteText.setText(ResumeFound.get(0).get("WebSite").toString());
                    CommentsText.setText(ResumeFound.get(0).get("Extra").toString());


                } else {


                }
            }
        });




    }

    public void UpdatedResume(View v)
    {
        result.put("Name", NameText.getText().toString());
        result.put("Email", EmailText.getText().toString());
        result.put("Address", AddressText.getText().toString());
        result.put("Major", MajorText.getText().toString());
        result.put("Education", EducationText.getText().toString());
        result.put("Loc", LocationText.getText().toString());
        result.put("WebSite", WebsiteText.getText().toString());
        result.put("Extra", CommentsText.getText().toString());

        result.saveInBackground();

        ParseUser currentUser = ParseUser.getCurrentUser();

        Intent intent = new Intent(ResumeOpen.this, ResumeHome.class);
        intent.putExtra("UserName", currentUser.getUsername());

        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_resume_open, menu);
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
