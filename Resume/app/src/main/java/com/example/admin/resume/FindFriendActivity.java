package com.example.admin.resume;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class FindFriendActivity extends AppCompatActivity {

    EditText UserSearch;
    Button SearchForUsers;
    ListView userlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friend_activity);
        UserSearch = (EditText)findViewById(R.id.NameSearcher);
        SearchForUsers = (Button)findViewById(R.id.SearchUsers);
        userlist = (ListView)findViewById(R.id.UserList);
    }

    public void QueryUsers(View v)
    {
        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereEqualTo("username", UserSearch.getText().toString());
        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> userslist, ParseException e) {
                if (e == null) {
                    final ArrayAdapter<String> ListItems = new ArrayAdapter<String>(FindFriendActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1);
                    if(userslist.size() == 0)
                    {
                        ListItems.add("User not found");
                    }
                    else
                    {
                        for(ParseUser u: userslist)
                        {
                            ListItems.add(u.getUsername());
                        }

                    }

                    userlist.setAdapter(ListItems);

                    userlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view,
                                                final int position, long id) {
                            // When clicked, show a toast with the TextView text

                            AlertDialog.Builder builder = new AlertDialog.Builder(FindFriendActivity.this);
                            builder.setTitle("Pick an Option");
                            CharSequence options[] = new CharSequence[] {"Add friend", "View Resumes" ,"Send Message", "Block User"};
                            builder.setItems(options, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(which==0)
                                    {
                                        ParseQuery findfriends = new ParseQuery("Friends");

                                        findfriends.findInBackground(new FindCallback<ParseObject>() {
                                            public void done(List<ParseObject> FriendList, ParseException e) {
                                                if (e == null) {
                                                    ParseObject FriendListObject = FriendList.get(0);
                                                    List<String> tempo = FriendListObject.getList("FriendsList");
                                                    if(tempo.contains(ListItems.getItem(position)))
                                                    {
                                                        Toast.makeText(FindFriendActivity.this, "Already added", Toast.LENGTH_SHORT).show();
                                                    }
                                                    else
                                                    {
                                                        tempo.add(ListItems.getItem(position));
                                                    }

                                                    FriendListObject.put("FriendsList", tempo);
                                                    FriendListObject.saveInBackground();
                                                    ResumeReadAccess(ListItems.getItem(position));
                                                } else {


                                                }
                                            }
                                        });



                                    }

                                    if(which == 1)
                                    {
                                        Intent intent = new Intent(FindFriendActivity.this, FriendsResume.class);
                                        startActivity(intent);
                                    }
                                }
                            });
                            builder.show();

                        }
                    });

                } else {
                    System.out.println("Something is flawed");
                }
            }
        });
    }

    public void ResumeReadAccess(String data) {
        ParseQuery pquery = ParseUser.getQuery();
        pquery.whereEqualTo("username", data);
        pquery.findInBackground(new FindCallback<ParseUser>() {
                                    public void done(final List<ParseUser> userslist, ParseException e) {
                                        if (e == null) {
                                                ParseQuery<ParseObject> presumequery = new ParseQuery("Resume");
                                                presumequery.findInBackground(new FindCallback<ParseObject>() {
                                                    public void done(final List<ParseObject> ResumeList, ParseException e) {
                                                        if (e == null) {
                                                            for (ParseObject r : ResumeList) {
                                                                ParseACL groupACL = r.getACL();
                                                                groupACL.setReadAccess(userslist.get(0), true);
                                                                r.setACL(groupACL);
                                                                r.saveInBackground();
                                                            }
                                                        } else {
                                                        }
                                                    }
                                                });



                                            }


            else {
                                            System.out.println("Something is flawed");
                                        }

                                    }
                                }
        );
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_near_me, menu);
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
