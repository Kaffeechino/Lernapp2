package com.example.lernapp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyAsyncTask extends AsyncTask<Void, Void, String> {

    private static final String TAG = "MyAsyncTask";
    private String serverIp;

    public MyAsyncTask(String serverIp) {
        this.serverIp = serverIp;
    }

    @Override
    protected String doInBackground(Void... params) {
        // Background task - perform the HTTP request
        String result = "";
        System.out.println(serverIp);
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL("http://"+ serverIp +"/login-registration-android/10questionsTest.php");
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            Log.e(TAG, "Error: " + e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        // UI update - called when the task is finished
        Log.d(TAG, "Result: " + result);
        // Process the result or update the UI
        Main.testData = result;
    }
}

