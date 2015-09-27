package com.example.admin.resume;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class PreviousResumes extends AppCompatActivity {

    ListView PreviousResumeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_previous_resumes);

        PreviousResumeList = (ListView)findViewById(R.id.PreviousResumeList);



        ParseQuery<ParseObject> parseQuery = new ParseQuery("Resume");

        final Intent intent = new Intent(PreviousResumes.this, ResumeOpen.class);







        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            public void done(final List<ParseObject> ResumeList, ParseException e) {
                if (e == null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(PreviousResumes.this, android.R.layout.simple_list_item_1, android.R.id.text1);

                    if (ResumeList.size() == 0)
                        adapter.add("You don't have any resumes");


                    for (int i = 0; i < ResumeList.size(); i++) {
                        adapter.add(ResumeList.get(i).getCreatedAt().toString());
                    }
                    PreviousResumeList.setAdapter(adapter);

                    PreviousResumeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            // When clicked, show a toast with the TextView text

                            intent.putExtra("ResumeData", ResumeList.get(position).getCreatedAt().getTime());


                            startActivity(intent);
                        }
                    });


                } else {

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(PreviousResumes.this, android.R.layout.simple_list_item_1, android.R.id.text1);

                    adapter.add("You don't have any resumes");

                    PreviousResumeList.setAdapter(adapter);


                }
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_previous_resumes, menu);
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
