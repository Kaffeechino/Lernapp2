package com.example.lernapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.HashMap;
import java.util.Map;

public class Main extends AppCompatActivity {
    public static String testData;

    TextView textViewName;
    SharedPreferences sharedPreferences;
    Button buttonLogout, buttonTestStarten;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String serverIp = getResources().getString(R.string.serverIp);
        MyAsyncTask task = new MyAsyncTask(serverIp);
        task.execute();

        // Initialize views
        textViewName = findViewById(R.id.textViewName);

        buttonLogout = findViewById(R.id.logout);
        buttonTestStarten = findViewById(R.id.buttonTestStarten);
        // Get the shared preferences
        sharedPreferences = getSharedPreferences("MyAppName", MODE_PRIVATE);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.test) {
                    Intent intent = new Intent(getApplicationContext(), Quiz.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (item.getItemId() == R.id.ranking) {
                    Intent intent1 = new Intent(getApplicationContext(), com.example.lernapp.Ranking.class);
                    startActivity(intent1);
                    finish();
                    return true;
                } else return false;

            }
        });

        // Check if user is logged in, if not, redirect to login screen
        if (sharedPreferences.getString("logged", "false").equals("true") == false) {
            Intent intent = new Intent(getApplicationContext(), com.example.lernapp.Login.class);
            startActivity(intent);
            finish();
        }

        // Set the name and email in the text views
        textViewName.setText("Wilkommen in der Lernapp,\n" + sharedPreferences.getString("name", "").toUpperCase());

        // Logout button click listener
        buttonLogout.setOnClickListener(v -> {
            // Create a request queue
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url = "http://" + getResources().getString(R.string.serverIp) + "/login-registration-android/logout.php";

            // Create a StringRequest to send a POST request to the logout URL
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        if (response.equals("success")) {
                            // Clear the shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("logged", "");
                            editor.putString("name", "");
                            editor.putString("email", "");
                            editor.putString("apiKey", "");
                            editor.apply();

                            // Redirect to login screen
                            Intent intent = new Intent(getApplicationContext(), com.example.lernapp.Login.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Show an error message using a toast
                            Toast.makeText(Main.this, response, Toast.LENGTH_SHORT).show();
                        }
                    },
                    Throwable::printStackTrace) {
                // Define the POST parameters
                protected Map<String, String> getParams() {
                    Map<String, String> paramV = new HashMap<>();
                    paramV.put("email", sharedPreferences.getString("email", ""));
                    paramV.put("apiKey", sharedPreferences.getString("apiKey", ""));
                    return paramV;
                }
            };
            queue.add(stringRequest);
        });

        buttonTestStarten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.lernapp.TestActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
