package com.example.admin.resume;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class FriendsResume extends AppCompatActivity {

    ListView resumelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_resume);
        resumelist = (ListView)findViewById(R.id.FriendsResumeList);
        ParseQuery<ParseObject> pquery = new ParseQuery("Resume");
        pquery.findInBackground(new FindCallback<ParseObject>() {
            public void done(final List<ParseObject> ResumeList, ParseException e) {
                if (e == null) {
                    final ArrayAdapter<String> ListItems = new ArrayAdapter<String>(FriendsResume.this, android.R.layout.simple_list_item_1, android.R.id.text1);

                    for (ParseObject r : ResumeList) {
                        if(!r.getACL().getWriteAccess(ParseUser.getCurrentUser()))
                        {
                            ListItems.add(r.getCreatedAt().toString());
                        }

                    }

                    resumelist.setAdapter(ListItems);
                    final Intent intent = new Intent (FriendsResume.this, ResumeOpen.class);

                    resumelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            // When clicked, show a toast with the TextView text

                            intent.putExtra("ResumeData", ResumeList.get(position).getCreatedAt().getTime());


                            startActivity(intent);
                        }
                    });



                } else {
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friends_resume, menu);
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
