package com.example.jatingarg.section1;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static String ENTERED_NAME = "entered_name";
    private EditText mEditText;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = (EditText) findViewById(R.id.nameEditText);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if(savedInstanceState != null){
            if(savedInstanceState.getString(ENTERED_NAME) != null){
                mEditText.setText("Hello " + savedInstanceState.getString(ENTERED_NAME));
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(ENTERED_NAME,mEditText.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_next){
            String nameText = mEditText.getText().toString().trim();
            if(nameText.length() == 0){
                //display error dialog
                new AlertDialog.Builder(this).setTitle("Error")
                        .setMessage("You need to enter a name").setPositiveButton("ok",null)
                        .setNegativeButton("Cancel",null).show();
            }else{
                Intent intent = new Intent(this,SecondActivity.class);
                Log.d(TAG, "onOptionsItemSelected: launching");
                intent.putExtra(SecondActivity.NAME_KEY,mEditText.getText().toString().trim());
                startActivity(intent);
            }
            return true;
        }
        return false;
    }
}
