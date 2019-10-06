package com.example.pocapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editEmail, editPassword;
    private TextView textError;
    private Button buttonLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editEmail = findViewById(R.id.editTextEmail);
        editPassword = findViewById(R.id.editTextPass);
        buttonLogIn = findViewById(R.id.button_log);
        textError = findViewById(R.id.textViewError);

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editEmail.getText().toString().trim()) && !TextUtils.isEmpty(editPassword.getText().toString().trim())){
                    if (isNetworkAvailable()){
                        Intent intent = new Intent(MainActivity.this, ListActivity.class);
                        startActivity(intent);
                    } else {
                        textError.setVisibility(View.VISIBLE);
                        textError.setText(getResources().getString(R.string.string_error));
                        editEmail.setVisibility(View.INVISIBLE);
                        editPassword.setVisibility(View.INVISIBLE);
                        buttonLogIn.setVisibility(View.INVISIBLE);
                    }
                } else {
                    if (TextUtils.isEmpty(editEmail.getText())){
                        Toast.makeText(MainActivity.this, "You didnt enter your email, please make it", Toast.LENGTH_LONG).show();
                    } else if (TextUtils.isEmpty(editPassword.getText())){
                        Toast.makeText(MainActivity.this, "You didnt enter your password, please make it", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    private boolean isNetworkAvailable(){
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isConnected = false;
        if (manager != null){
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            isConnected = (networkInfo != null) && (networkInfo.isConnectedOrConnecting());
        }
        return isConnected;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
