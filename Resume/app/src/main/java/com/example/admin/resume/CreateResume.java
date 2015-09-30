package com.example.admin.resume;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

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

        final ParseObject ResumeInfo = new ParseObject("Resume");

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

        ParseACL tempACL = new ParseACL(currentUser);

        ResumeInfo.setACL(tempACL);

        ResumeInfo.saveInBackground();

        ParseQuery findfriends = new ParseQuery("Friends");

        findfriends.findInBackground(new FindCallback<ParseObject>() {
                                         public void done(List<ParseObject> FriendList, ParseException e) {
                                             if (e == null) {
                                                 ParseObject FriendListObject = FriendList.get(0);
                                                 List<String> tempo = FriendListObject.getList("FriendsList");
                                                 for (String u : tempo) {

                                                     ParseQuery pquery = ParseUser.getQuery();
                                                     pquery.whereEqualTo("username", u);
                                                     pquery.findInBackground(new FindCallback<ParseUser>() {
                                                                                 public void done(List<ParseUser> userslist, ParseException e) {
                                                                                     if (e == null) {
                                                                                         System.out.println(userslist.get(0));
                                                                                         ParseACL tACL = ResumeInfo.getACL();
                                                                                         tACL.setReadAccess(userslist.get(0), true);
                                                                                         ResumeInfo.setACL(tACL);
                                                                                         ResumeInfo.saveInBackground();



                                                                                     } else {

                                                                                     }
                                                                                 }
                                                                             }
                                                     );

                                                 }
                                             } else {

                                             }
                                         }
                                     }
        );







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
